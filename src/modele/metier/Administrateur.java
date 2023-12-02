package modele.metier;

/**
 * Modèle métier Administrateur
 */
public class Administrateur {

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
	 * Retourne true si un Object o est égal à Administrateur (this), faux sinon
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) { 
			return false;
		}
		if (!(o instanceof Administrateur)) {
			return false;
		}
		Administrateur admin = (Administrateur) o;
		return this.idAdministrateur == admin.idAdministrateur 
				&& this.nom == admin.nom
				&& this.prenom == admin.prenom
				&& this.identifiant == admin.identifiant
				&& this.motDePasse == admin.motDePasse;
	}
	
	@Override
	public String toString() {
		return "Administrateur [idAdministrateur=" + idAdministrateur + ", nom=" + nom + ", prenom=" + prenom
				+ ", identifiant=" + identifiant + ", motDePasse=" + motDePasse + "]";
	}
	
}
