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
 * Modèle équipe
 * @author Nassim Khoujane
 */
public class Equipe implements DAO<Equipe, Integer> {

	private int idEquipe;
	private String nom;
	private String pays;
	private int classement;
	private int worldRanking;
	private String saison;
	// TODO : Ajouter liste de joueurs
	
	/**
	 * Construit une équipe
	 * @param idEquipe	Clé primaire
	 * @param pseudo	Pseudo
	 */
	public Equipe(int idEquipe, String nom, String pays, int classement, int worldRanking, String saison) {
		this.idEquipe = idEquipe;
		this.nom = nom;
		this.pays = pays;
		this.classement = classement;
		this.worldRanking = worldRanking;
		this.saison = saison;
	}

	/**
	 * @return Clé primaire
	 */
	public int getIdEquipe() {
		return this.idEquipe;
	}
	
	/**
	 * Modifie la clé primaire
	 * @param idEquipe clé primaire
	 */
	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
	}

	/**
	 * @return Nom
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Modifie le nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return Pays
	 */
	public String getPays() {
		return this.pays;
	}
	
	/**
	 * Modifie le pays
	 * @param pays
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * @return Classement
	 */
	public int getClassement() {
		return this.classement;
	}
	
	/**
	 * Modifie le classement
	 * @param classement
	 */
	public void setClassement(int classement) {
		this.classement = classement;
	}

	/**
	 * @return WorldRanking
	 */
	public int getWorldRanking() {
		return this.worldRanking;
	}
	
	/**
	 * Modifie le worldRanking
	 * @param worldRanking
	 */
	public void setWorldRanking(int worldRanking) {
		this.worldRanking = worldRanking;
	}

	/**
	 * @return Saison
	 */
	public String getSaison() {
		return this.saison;
	}
	
	/**
	 * Modifie la saison
	 * @param saison
	 */
	public void setSaison(String saison) {
		this.saison = saison;
	}

	/**
	 * @return Liste de tous les équipes
	 */
	@Override
	public List<Equipe> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from equipe");
		
		// Parcours les équipes dans la base de données et les formate dans une liste
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
                    		rs.getString("saison")
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
		if(rs.first()) {
			equipe = new Equipe(
	    		rs.getInt("idEquipe"),
	    		rs.getString("nom"),
	    		rs.getString("pays"),
	    		rs.getInt("classement"),
	    		rs.getInt("worldRanking"),
	    		rs.getString("saison")
            );
		}
		
		return Optional.ofNullable(equipe);
	}

	/**
	 * Ajoute l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Equipe equipe) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into equipe values (?, ?, ?, ?, ?)");
			ps.setInt(1, equipe.idEquipe);
			ps.setString(2, equipe.nom);
			ps.setString(3, equipe.pays);
			ps.setInt(4, equipe.classement);
			ps.setInt(5, equipe.worldRanking);
			ps.setString(5, equipe.saison);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifie l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Equipe equipe) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update equipe set nom = ?, pays = ?, classement = ?, worldRanking = ?, saison = ? where idEquipe = ?");
			ps.setString(1, equipe.nom);
			ps.setString(2, equipe.pays);
			ps.setInt(3, equipe.classement);
			ps.setInt(4, equipe.worldRanking);
			ps.setString(5, equipe.saison);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Supprime l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Equipe equipe) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from equipe where idEquipe = ?");
			ps.setInt(1, equipe.idEquipe);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String toString() {
		return "Equipe [idEquipe=" + idEquipe + ", nom=" + nom + ", pays=" + pays + ", classement=" + classement
				+ ", worldRanking=" + worldRanking + ", saison=" + saison + "]";
	}

}
