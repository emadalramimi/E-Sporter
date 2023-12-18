package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.Equipe;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Utilisateur;

public class ModeleRencontre extends DAO<Rencontre, Integer> {

	private ModeleJoueur modeleJoueur;

	public ModeleRencontre() {
		this.modeleJoueur = new ModeleJoueur();
	}
	
	@Override
	public List<Rencontre> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from rencontre");
		
		Stream<Rencontre> stream = StreamSupport.stream(
			new Spliterators.AbstractSpliterator<Rencontre>(Long.MAX_VALUE, Spliterator.ORDERED) {
				@Override
	            public boolean tryAdvance(Consumer <? super Rencontre> action) {
	                try {
	                    if (!rs.next()) {
	                        return false;
	                    }
	                    action.accept(new Rencontre(
	                    	rs.getInt("idRencontre"),
							rs.getInt("idPoule"),
							rs.getInt("idEquipeGagnante"),
	                    	getEquipesRencontre(rs.getInt("idRencontre"))
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
	 * Ajoute la rencontre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Rencontre rencontre) throws Exception {
		Equipe[] equipes = rencontre.getEquipes();
		if (equipes.length > 2) {
			throw new IllegalArgumentException("Une rencontre ne peut pas avoir plus de 2 équipes");
		}
		
		try {
			int idRencontre = this.getNextValId();
			rencontre.setIdRencontre(idRencontre);

			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into rencontre values (?, ?, NULL)");
			ps.setInt(1, rencontre.getIdRencontre());
			ps.setInt(2, rencontre.getIdPoule());
			ps.execute();
			ps.close();
			
			for (int i = 0; i < equipes.length; i++) {
				ps = BDD.getConnexion().prepareStatement("insert into jouer values (?, ?)");
				ps.setInt(1, equipes[i].getIdEquipe());
				ps.setInt(2, rencontre.getIdRencontre());
				ps.execute();
				ps.close();
			}
			
			return true;
		} catch (SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Supprime la rencontre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Rencontre tournoi) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from jouer where idRencontre = ?");
			ps.setInt(1, tournoi.getIdRencontre());
			ps.execute();
			ps.close();

			ps = BDD.getConnexion().prepareStatement("delete from rencontre where idRencontre = ?");
			ps.setInt(1, tournoi.getIdRencontre());
			ps.execute();
			ps.close();
			
			return true;
		} catch (SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @return le prochain identifiant unique de rencontre
	 */
	private int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idRencontre");

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
	 * @param idPoule : identifiant de la poule
	 * @return la liste des rencontres appartenant à la poule idPoule
	 */
	public List<Rencontre> getRencontresPoules(int idPoule) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from rencontre where idPoule = ?");
			ps.setInt(1, idPoule);
			ResultSet rs = ps.executeQuery();
			
			List<Rencontre> rencontres = new ArrayList<Rencontre>();
			while(rs.next()) {
				rencontres.add(new Rencontre(
					rs.getInt("idRencontre"),
					rs.getInt("idPoule"),
					rs.getInt("idEquipeGagnante"),
					this.getEquipesRencontre(rs.getInt("idRencontre"))
	            ));
			}
			
			rs.close();
			ps.close();
			return rencontres;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Equipe[] getEquipesRencontre(int idRencontre) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from equipe, jouer where jouer.idEquipe = equipe.idEquipe and jouer.idRencontre = ?");
			ps.setInt(1, idRencontre);
			ResultSet rs = ps.executeQuery();

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
										ModeleRencontre.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))));
								return true;
							} catch (SQLException e) {
								throw new RuntimeException(e);
							}
						}
					}, false).onClose(() -> {
						try {
							rs.close();
							ps.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
			Equipe[] equipes = stream.toArray(Equipe[]::new);
			return equipes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setEquipeGagnante(Rencontre rencontre, String nomEquipe) throws Exception {
		Tournoi tournoi = new ModeleTournoi().getTournoiRencontre(rencontre.getIdRencontre()).orElse(null);
		if (tournoi == null) {
			throw new IllegalArgumentException("Le tournoi n'existe pas");
		}
		if (tournoi.getEstCloture()) {
			throw new IllegalArgumentException("Le tournoi est clôturé");
		}

		/**
		 * Seuls les arbitres peuvent affecter le résultat d'une rencontre
		 * PS : Il n'y a pas besoin de vérifier que l'arbitre est bien assigné au tournoi
		 * car seuls les arbitres assignés à un unique tournoi ouvert peuvent se connecter
		 */
		if (ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ARBITRE) {
			throw new IllegalArgumentException("Seuls les arbitres peut affecter le résultat d'une rencontre");
		}

		try {
			int idEquipeGagnante;

			PreparedStatement ps = BDD.getConnexion().prepareStatement("select idEquipe from equipe where nom = ? and saison = ?");
			ps.setString(1, nomEquipe);
			ps.setString(2, String.valueOf(LocalDate.now().getYear()));
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				throw new IllegalArgumentException("L'équipe n'existe pas");
			} else {
				idEquipeGagnante = rs.getInt("idEquipe");
			}
			ps.close();
			rs.close();

			ps = BDD.getConnexion().prepareStatement("update rencontre set idEquipeGagnante = ? where idRencontre = ?");
			ps.setInt(1, idEquipeGagnante);
			ps.setInt(2, rencontre.getIdRencontre());
			ps.execute();
			ps.close();

			rencontre.setIdEquipeGagnante(idEquipeGagnante);

			BDD.getConnexion().commit();
		} catch (SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

}
