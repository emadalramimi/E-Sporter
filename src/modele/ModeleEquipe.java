package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.Equipe;
import modele.metier.Joueur;

public class ModeleEquipe implements DAO<Equipe, Integer> {
	
	private ModeleJoueur modeleJoueur;
	
	public ModeleEquipe() {
		this.modeleJoueur = new ModeleJoueur();
	}

	/**
	 * @return Liste de tous les équipes
	 */
	@Override
	public List<Equipe> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from equipe");
		
		// Parcourt les équipes dans la base de données et les formate dans une liste
		Stream<Equipe> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Equipe>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Equipe> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Equipe(
                    		rs.getInt("idEquipe"),
                    		rs.getString("nom"),
                    		rs.getString("pays"),
                    		rs.getInt("classement"),
                    		rs.getInt("worldRanking"),
                    		rs.getString("saison"),
                    		ModeleEquipe.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))
                        ));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
	        }, false).onClose(() -> {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		return stream.collect(Collectors.toList());
	}

	/**
	 * @return Retourne une équipe depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Equipe> getParId(Integer... idEquipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from equipe where idEquipe = ?");
		ps.setInt(1, idEquipe[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création d'équipe si elle existe
		Equipe equipe = null;
		if(rs.next()) {
			equipe = new Equipe(
	    		rs.getInt("idEquipe"),
	    		rs.getString("nom"),
	    		rs.getString("pays"),
	    		rs.getInt("classement"),
	    		rs.getInt("worldRanking"),
	    		rs.getString("saison"),
	    		ModeleEquipe.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))
            );
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(equipe);
	}

	/**
	 * Ajoute l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Equipe equipe) {
		try {
			int idEquipe = this.getNextValId();
			equipe.setIdEquipe(idEquipe);
			equipe.setSaison(String.valueOf(LocalDate.now().getYear()));
			equipe.setClassement(1000);
			
			Equipe equipeSaisonDerniere = this.getTout().stream()
				.filter(e -> e.equals(equipe) && e.getSaison().equals(String.valueOf(LocalDate.now().getYear() - 1)))
				.findFirst()
				.orElse(null);
			
			if(equipeSaisonDerniere != null) {
				equipe.setWorldRanking(equipeSaisonDerniere.getClassement());
			} else {
				equipe.setWorldRanking(1000);
			}
			
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into equipe values (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, equipe.getIdEquipe());
			ps.setString(2, equipe.getNom());
			ps.setString(3, equipe.getPays());
			ps.setInt(4, equipe.getClassement());
			ps.setInt(5, equipe.getWorldRanking());
			ps.setString(6, equipe.getSaison());
			ps.execute();
			
			for(Joueur joueur : equipe.getJoueurs()) {
				joueur.setIdEquipe(idEquipe);
				this.modeleJoueur.ajouter(joueur);
			}
			
			BDD.getConnexion().commit();
			ps.close();
			return true;
		} catch(Exception e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Modifie l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Equipe equipe) throws Exception {
		if (this.estEquipeInscriteUnTournoi(equipe)) {
			throw new RuntimeException("L'équipe est inscrite à un tournoi.");
		}
		
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update equipe set nom = ?, pays = ?, worldRanking = ? where idEquipe = ?");
			// On ne peut pas modifier le classement et la saison d'une équipe
			ps.setString(1, equipe.getNom());
			ps.setString(2, equipe.getPays());
			ps.setInt(3, equipe.getWorldRanking());
			ps.setInt(4, equipe.getIdEquipe());
			ps.execute();

			List<Joueur> joueursEquipe = equipe.getJoueurs();
			for(Joueur joueur : joueursEquipe) {
				this.modeleJoueur.modifier(joueur);
			}

			BDD.getConnexion().commit();
			ps.close();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Supprime l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Equipe equipe) throws Exception {
		if (this.estEquipeInscriteUnTournoi(equipe)) {
			throw new RuntimeException("L'équipe est inscrite à un tournoi");
		}

		try {
			this.modeleJoueur.supprimerJoueursEquipe(equipe.getIdEquipe());
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from equipe where idEquipe = ?");
			ps.setInt(1, equipe.getIdEquipe());
			ps.execute();
			
			BDD.getConnexion().commit();
			ps.close();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return le prochain identifiant unique d'équipe
	 */
	private int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idEquipe");

            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                nextVal = rs.getInt(1);
            }
            
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nextVal;
    }
	
	/**
	 * @param nom : contenu dans le nom d'une équipe
	 * @return la liste des équipes contenant la variable nom dans leur nom d'équipe
	 * @throws Exception
	 */
	public List<Equipe> getParNom(String nom) throws Exception {
		return this.getTout().stream()
				.filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
				.collect(Collectors.toList());
	}
	
	public List<Equipe> getEquipesSaison() throws Exception {
		return this.getTout().stream()
				.filter(e -> e.getSaison().equals(String.valueOf(LocalDate.now().getYear())))
				.collect(Collectors.toList());
	}
	
	public Equipe[] getTableauEquipes() throws Exception {
		return this.getTout().stream()
				.sorted()
				.toArray(Equipe[]::new);
	}

	public List<Equipe> getEquipesTournoi(int idTournoi) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement(
					"select * from equipe, participer where equipe.idEquipe = participer.idEquipe and participer.idTournoi = ?");
			ps.setInt(1, idTournoi);

			ResultSet rs = ps.executeQuery();

			// Parcourt les arbitres dans la base de données et les formate dans une liste
			Stream<Equipe> stream = StreamSupport.stream(
					new Spliterators.AbstractSpliterator<Equipe>(Long.MAX_VALUE, Spliterator.ORDERED) {
						@Override
						public boolean tryAdvance(Consumer<? super Equipe> action) {
							try {
								if (!rs.next()) {
									return false;
								}
								action.accept(new Equipe(
										rs.getInt("idEquipe"),
										rs.getString("nom"),
										rs.getString("pays"),
										rs.getInt("classement"),
										rs.getInt("worldRanking"),
										rs.getString("saison"),
										ModeleEquipe.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))));
								return true;
							} catch (SQLException e) {
								throw new RuntimeException(e);
							}
						}
					}, false).onClose(() -> {
						try {
							ps.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});

			return stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean estEquipeInscriteUnTournoi(Equipe equipe) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from participer where idEquipe = ?");
			ps.setInt(1, equipe.getIdEquipe());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}