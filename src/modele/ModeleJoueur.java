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

import modele.metier.Joueur;

/**
 * Modèle joueur
 */
public class ModeleJoueur extends DAO<Joueur, Integer> {

	/**
	 * Récupère tous les joueurs
	 * @return Liste de tous les joueurs
	 * @throws Exception Exception SQL
	 */
	@Override
	public List<Joueur> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from joueur");
		
		// Parcourt les joueurs dans la base de données et les formate dans une liste
		Stream<Joueur> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Joueur>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Joueur> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Joueur(
                    		rs.getInt("idJoueur"),
                    		rs.getString("pseudo"),
                    		rs.getInt("idEquipe")
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
	 * Récupère un joueur depuis la BDD par sa clé primaire
	 * @param idJoueur : identifiant du joueur
	 * @return Retourne un joueur depuis la BDD par sa clé primaire
	 * @throws Exception Exception SQL
	 */
	@Override
	public Optional<Joueur> getParId(Integer... idJoueur) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from joueur where idJoueur = ?");
		ps.setInt(1, idJoueur[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de joueur si il existe
		Joueur joueur = null;
		if(rs.next()) {
			joueur = new Joueur(
	    		rs.getInt("idJoueur"),
	    		rs.getString("pseudo"),
	    		rs.getInt("idEquipe")
            );
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(joueur);
	}

	/**
	 * Ajoute le joueur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean ajouter(Joueur joueur) throws Exception {
		try {
			int idJoueur = this.getNextValId();
			joueur.setIdJoueur(idJoueur);
			
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into joueur values (?, ?, ?)");
			ps.setInt(1, joueur.getIdJoueur());
			ps.setString(2, joueur.getPseudo());
			ps.setInt(3, joueur.getIdEquipe());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Modifie le joueur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean modifier(Joueur joueur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update joueur set pseudo = ?, idEquipe = ? where idJoueur = ?");
			ps.setString(1, joueur.getPseudo());
			ps.setInt(2, joueur.getIdEquipe());
			ps.setInt(3, joueur.getIdJoueur());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Méthode privée pour récupérer le prochain identifiant unique de joueur
	 * @return le prochain identifiant unique de joueur
	 */
	private int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idJoueur");

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
	 * Supprime tous les joueurs d'une équipe idEquipe
	 * @param idEquipe : identifiant de l'équipe
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	public boolean supprimerJoueursEquipe(int idEquipe) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from joueur where idEquipe = ?");
			ps.setInt(1, idEquipe);
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Récupère la liste des joueurs appartenant à l'équipe idEquipe
	 * @param idEquipe : identifiant de l'équipe
	 * @return la liste des joueurs appartenant à l'équipe idEquipe
	 */
	public List<Joueur> getListeJoueursParId(int idEquipe) {
		List<Joueur> joueurs = new ArrayList<>();
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from joueur where idEquipe = ?");
			ps.setInt(1, idEquipe);
			
			ResultSet rs = ps.executeQuery();
			
			// Création de la liste des joueurs
			while(rs.next()) {
				joueurs.add(new Joueur(rs.getInt("idJoueur"), rs.getString("pseudo"), rs.getInt("idEquipe")));
			}
			
			rs.close();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return joueurs;
	}
	
}