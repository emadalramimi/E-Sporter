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
 * Modèle administrateur
 * @author Nassim Khoujane
 */
public class Administrateur implements DAO<Administrateur, Integer> {

	private int idAdministrateur;
	private String nom;
	private String prenom;
	private String identifiant;
	private String motDePasse;
	
	/**
	 * Construit un administrateur
	 * @param idAdministrateur	Clé primaire
	 * @param nom				Nom
	 * @param prenom			Prénom
	 * @param identifiant		Identifiant de connexion
	 * @param motDePasse		Mot de passe de connexion
	 */
	public Administrateur(int idAdministrateur, String nom, String prenom, String identifiant, String motDePasse) {
		this.idAdministrateur = idAdministrateur;
		this.nom = nom;
		this.prenom = prenom;
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
	}

	/**
	 * @return Clé primaire
	 */
	public int getIdAdministrateur() {
		return idAdministrateur;
	}
	
	/**
	 * Modifie la clé primaire
	 * @param idAdministrateur clé primaire
	 */
	public void setIdAdministrateur(int idAdministrateur) {
		this.idAdministrateur = idAdministrateur;
	}

	/**
	 * @return Nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Modifie le nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return Prénom
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * Modifie le prénom
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return Identifiant de connexion
	 */
	public String getIdentifiant() {
		return identifiant;
	}
	
	/**
	 * Modifie l'identifiant
	 * @param identifiant
	 */
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * @return Mot de passe de connexion
	 */
	public String getMotDePasse() {
		return motDePasse;
	}
	
	/**
	 * Modifie le mot de passe
	 * @param motDePasse
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * @return Liste de tous les administrateurs
	 */
	@Override
	public List<Administrateur> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from administrateur");
		
		// Parcours les administrateurs dans la base de données et les formate dans une liste
		Stream<Administrateur> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Administrateur>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Administrateur> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Administrateur(
                    		rs.getInt("idAdministrateur"),
                    		rs.getString("nom"),
                    		rs.getString("prenom"),
                    		rs.getString("identifiant"),
                    		rs.getString("motDePasse")
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
	 * @return Retourne un administrateur depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Administrateur> getParId(Integer... idAdministrateur) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from sujets where idSujet = ?");
		ps.setInt(1, idAdministrateur[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de l'administrateur s'il existe
		Administrateur administrateur = null;
		if(rs.first()) {
			administrateur = new Administrateur(
	    		rs.getInt("idAdministrateur"),
	    		rs.getString("nom"),
	    		rs.getString("prenom"),
	    		rs.getString("identifiant"),
	    		rs.getString("motDePasse")
            );
		}
		
		return Optional.ofNullable(administrateur);
	}

	/**
	 * Ajoute l'administrateur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Administrateur administrateur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into administrateur values (?, ?, ?, ?, ?)");
			ps.setInt(1, administrateur.idAdministrateur);
			ps.setString(2, administrateur.nom);
			ps.setString(3, administrateur.prenom);
			ps.setString(4, administrateur.identifiant);
			ps.setString(5, administrateur.motDePasse);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifie l'administrateur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Administrateur administrateur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update administrateur set nom = ?, prenom = ?, identifiant = ?, motDePasse = ? where idAdministrateur = ?");
			ps.setString(1, administrateur.nom);
			ps.setString(2, administrateur.prenom);
			ps.setString(3, administrateur.identifiant);
			ps.setString(4, administrateur.motDePasse);
			ps.setInt(5, administrateur.idAdministrateur);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Supprime l'administrateur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Administrateur administrateur) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from administrateur where idAdministrateur = ?");
			ps.setInt(1, administrateur.idAdministrateur);
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String toString() {
		return "Administrateur [idAdministrateur=" + idAdministrateur + ", nom=" + nom + ", prenom=" + prenom
				+ ", identifiant=" + identifiant + ", motDePasse=" + motDePasse + "]";
	}
	
}
