package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.Equipe;
import modele.metier.Rencontre;

public class ModeleRencontre implements DAO<Rencontre, Integer> {

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
	                    	rs.getInt("dateHeureDebut"),
	                    	rs.getInt("dateHeureFin"),
	                    	rs.getInt("idPoule"),
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
	 * @return Retourne une rencontre depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Rencontre> getParId(Integer... idRencontre) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from rencontre where idRencontre = ?");
		
		ps.setInt(1, idRencontre[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de joueur si il existe
		Rencontre rencontre = null;
		if(rs.next()) {		
			rencontre = new Rencontre(
				rs.getInt("idRencontre"),
				rs.getInt("dateHeureDebut"),
				rs.getInt("dateHeureFin"),
				rs.getInt("idPoule"),
				getEquipesRencontre(rs.getInt("idRencontre"))
            );
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(rencontre);
	}

	/**
	 * Ajoute la rencontre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Rencontre rencontre) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into rencontre values (?, ?, ?, ?, ?)");
			ps.setInt(1, rencontre.getIdRencontre());
			ps.setInt(2, rencontre.getDateHeureDebut());
			ps.setInt(3, rencontre.getDateHeureFin());
			ps.setInt(4, rencontre.getIdPoule());
			ps.setInt(5, rencontre.getEquipes()[0].getIdEquipe());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifie la rencontre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Rencontre rencontre) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update rencontre set dateHeureDebut = ?, dateHeureFin = ?, idPoule = ?, idEquipe = ? where idRencontre = ?");
			ps.setInt(1, rencontre.getDateHeureDebut());
			ps.setInt(2, rencontre.getDateHeureFin());
			ps.setInt(3, rencontre.getIdPoule());
			ps.setInt(4, rencontre.getEquipes()[0].getIdEquipe());
			ps.setInt(5, rencontre.getIdRencontre());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Supprime la rencontre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Rencontre tournoi) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from rencontre where idRencontre = ?");
			ps.setInt(1, tournoi.getIdRencontre());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Supprime tous les joueurs d'une équipe idEquipe
	 * @param idEquipe : identifiant de l'équipe
	 * @return tru si l'opération s'est bien déroulée, false sinon
	 */
	public boolean supprimerRencontresPoule(int idPoule) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from rencontre where idPoule = ?");
			ps.setInt(1, idPoule);
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param idPoule : identifiant de la poule
	 * @return la liste des rencontres appartenant à la poule idPoule
	 */
	public List<Rencontre> getListeRencontresParId(int idPoule) {
		List<Rencontre> rencontres = new ArrayList<>();
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from rencontre where idPoule = ?");
			ps.setInt(1, idPoule);
			
			ResultSet rs = ps.executeQuery();
			
			// Création de la liste des rencontres
			while(rs.next()) {
				rencontres.add(new Rencontre(
					rs.getInt("idRencontre"),
					rs.getInt("dateHeureDebut"),
					rs.getInt("dateHeureFin"),
					rs.getInt("idPoule"),
					this.getEquipesRencontre(rs.getInt("idRencontre"))
				));
			}
			
			rs.close();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rencontres;
	}
	
	/**
	 * @return le prochain identifiant unique de rencontre
	 */
	public int getNextValId() {
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
	
	public Equipe[] getEquipesRencontre(int idRencontre) {
		try{
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from equipe, rencontre where rencontre.idEquipe = equipe.idEquipe and rencontre.idRencontre = ?");
			ps.setInt(1, idRencontre);
			ResultSet rs = ps.executeQuery();
			
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
								ModeleRencontre.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))
							));
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
			
			return stream.toArray(Equipe[]::new);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

}
