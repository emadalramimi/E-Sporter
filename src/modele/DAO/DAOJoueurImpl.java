package modele.DAO;

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
 * Implémentation DAO pour la classe Joueur
 */
public class DAOJoueurImpl implements DAOJoueur {

	/**
	 * Retourne la liste de tous les joueurs
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
                        action.accept(DAOJoueurImpl.this.construireJoueur(rs));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
	        }, false).onClose(() -> {
				try {
					rs.close();
                    st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		return stream.collect(Collectors.toList());
	}

	/**
	 * Retourne un joueur depuis la BDD par sa clé primaire
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
			joueur = this.construireJoueur(rs);
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(joueur);
	}

	/**
	 * Ajoute le joueur dans la BDD
	 * @param joueur : joueur à ajouter
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
			ps.executeUpdate();
			
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
	 * Retourne le prochain identifiant unique de joueur
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
	 * Modifie le joueur dans la BDD
	 * @param joueur : joueur à modifier
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
			ps.executeUpdate();
			
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

	@Override
	public boolean supprimer(Joueur joueur) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}
	
	/**
	 * Supprime tous les joueurs d'une équipe idEquipe
	 * @param idEquipe : identifiant de l'équipe
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean supprimerJoueursEquipe(int idEquipe) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from joueur where idEquipe = ?");
			ps.setInt(1, idEquipe);
			ps.executeUpdate();
			
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
	 * Retourne la liste des joueurs appartenant à l'équipe idEquipe
	 * @param idEquipe : identifiant de l'équipe
	 * @return la liste des joueurs appartenant à l'équipe idEquipe
	 */
	@Override
	public List<Joueur> getListeJoueursParId(int idEquipe) {
		List<Joueur> joueurs = new ArrayList<>();
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from joueur where idEquipe = ?");
			ps.setInt(1, idEquipe);
			
			ResultSet rs = ps.executeQuery();
			
			// Création de la liste des joueurs
			while(rs.next()) {
				joueurs.add(this.construireJoueur(rs));
			}
			
			rs.close();
			ps.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return joueurs;
	}

	/**
	 * Construit un joueur à partir d'un ResultSet
	 * @param rs : ResultSet
	 * @return le joueur construit
	 * @throws SQLException Exception SQL
	 */
	private Joueur construireJoueur(ResultSet rs) throws SQLException {
		return new Joueur(
			rs.getInt("idJoueur"),
			rs.getString("pseudo"),
			rs.getInt("idEquipe")
		);
	}
	
}