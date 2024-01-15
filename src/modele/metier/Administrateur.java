package modele.metier;

/**
 * Modèle métier Administrateur
 */
public class Administrateur implements Utilisateur {

	private int idAdministrateur;
	private String nom;
	private String prenom;
	private String identifiant;
	private String motDePasse;
	
	/**
	 * Construit un administrateur
	 * @param idAdministrateur	Clé primaire
	 * @param nom				Nom de l'administrateur
	 * @param prenom			Prénom de l'administrateur
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
	 * Retourne la clé primaire de l'administrateur
	 * @return Clé primaire de l'administrateur
	 */
	public int getIdAdministrateur() {
		return idAdministrateur;
	}
	
	/**
	 * Modifie la clé primaire de l'administrateur
	 * @param idAdministrateur clé primaire de l'administrateur
	 */
	public void setIdAdministrateur(int idAdministrateur) {
		this.idAdministrateur = idAdministrateur;
	}

	/**
	 * Retourne le nom de l'administrateur
	 * @return Nom de l'administrateur
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Modifie le nom de l'administrateur
	 * @param nom Nom de l'administrateur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'administrateur
	 * @return Prénom de l'administrateur
	 */
	public String getPrenom() {
		return prenom;
	}
	
	/**
	 * Modifie le prénom de l'administrateur
	 * @param prenom Prénom de l'administrateur
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Retourne l'identifiant de connexion de l'administrateur
	 * @return Identifiant de connexion de l'administrateur
	 */
	@Override
	public String getIdentifiant() {
		return identifiant;
	}
	
	/**
	 * Modifie l'identifiant de connexion
	 * @param identifiant Identifiant de connexion
	 */
	@Override
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * Retourne le mot de passe de connexion de l'administrateur
	 * @return Mot de passe de connexion
	 */
	@Override
	public String getMotDePasse() {
		return motDePasse;
	}
	
	/**
	 * Modifie le mot de passe de connexion
	 * @param motDePasse Mot de passe de connexion
	 */
	@Override
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	/**
	 * Retourne le rôle de l'administrateur
	 * @return Role.ADMINISTRATEUR (enum) de l'administrateur
	 */
	public Role getRole() {
		return Role.ADMINISTRATEUR;
	}
	
	/**
	 * Retourne true si un Object o est égal à Administrateur (this), faux sinon
	 * @param o Object à comparer
	 * @return true si un Object o est égal à Administrateur (this), faux sinon
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
		return this.idAdministrateur == admin.getIdAdministrateur();
	}
	
	/**
	 * Retourne une représentation textuelle de l'administrateur
	 * @return Représentation textuelle de l'administrateur
	 */
	@Override
	public String toString() {
		return "Administrateur [idAdministrateur=" + idAdministrateur + ", nom=" + nom + ", prenom=" + prenom
				+ ", identifiant=" + identifiant + ", motDePasse=" + motDePasse + "]";
	}
	
}
