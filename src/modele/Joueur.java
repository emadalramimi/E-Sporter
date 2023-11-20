package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Modèle joueur
 * @author Nassim Khoujane
 */
public class Joueur implements DAO<Joueur, Integer> {

	private int idJoueur;
	private String pseudo;
	private int idEquipe;
	
	/**
	 * Construit un joueur
	 * @param idJoueur	Clé primaire
	 * @param pseudo	Pseudo
	 */
	public Joueur(int idJoueur, String pseudo, int idEquipe) {
		this.idJoueur = idJoueur;
		this.pseudo = pseudo;
		this.idEquipe = idEquipe;
	}

	/**
	 * @return Clé primaire
	 */
	public int getIdJoueur() {
		return idJoueur;
	}
	
	/**
	 * Modifie la clé primaire
	 * @param idJoueur clé primaire
	 */
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	/**
	 * @return Pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Modifie le pseudo
	 * @param pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	/**
	 * @return Clé étrangère équipe
	 */
	public int getIdEquipe() {
		return idEquipe;
	}
	
	/**
	 * Modifie la clé étrangère équipe
	 * @param idEquipe
	 */
	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
	}

	/**
	 * @return Liste de tous les joueurs
	 */
	@Override
	public List<Joueur> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from joueur");
		
		// Parcours les joueurs dans la base de données et les formate dans une liste
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
	 * @return Retourne un joueur depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Joueur> getParId(Integer... idJoueur) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from joueur where idJoueur = ?");
		ps.setInt(1, idJoueur[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création du joueur s'il existe
		Joueur joueur = null;
		if(rs.first()) {
			joueur = new Joueur(
	    		rs.getInt("idJoueur"),
	    		rs.getString("pseudo"),
        		rs.getInt("idEquipe")
            );
		}
		
		return Optional.ofNullable(joueur);
	}

	/**
	 * Ajoute le joueur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Joueur joueur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into joueur values (?, ?, ?)");
			ps.setInt(1, joueur.idJoueur);
			ps.setString(2, joueur.pseudo);
			ps.setInt(3, joueur.idEquipe);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifie le joueur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Joueur joueur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update joueur set pseudo = ? where idJoueur = ?");
			ps.setString(1, joueur.pseudo);
			ps.setInt(2, joueur.idJoueur);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Supprime le joueur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Joueur joueur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from joueur where idJoueur = ?");
			ps.setInt(1, joueur.idJoueur);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String toString() {
		return "Joueur [idJoueur=" + idJoueur + ", pseudo=" + pseudo + ", idEquipe=" + idEquipe + "]";
	}

}
