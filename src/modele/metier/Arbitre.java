package modele.metier;

/**
 * Modèle métier Arbitre
 */
public class Arbitre {

	private int idArbitre;
	private String nom;
	private String prenom;
	
	/**
	 * Construit un Arbitre
	 * @param idArbitre	Clé primaire 
	 * @param nom		Nom de l'arbitre
	 * @param prenom	Prénom de l'arbitre
	 */
	public Arbitre(int idArbitre, String nom, String prenom) {
		this.idArbitre = idArbitre;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	/**
	 * Retourne la clé primaire de l'arbitre
	 * @return Clé primaire
	 */
	public int getIdArbitre() {
		return this.idArbitre;
	}

	/**
	 * Modifie la clé primaire de l'arbitre
	 * @param idArbitre Clé primaire
	 */
	public void setIdArbitre(int idArbitre) {
		this.idArbitre = idArbitre;
	}

	/**
	 * Retourne le nom de l'arbitre
	 * @return Nom 
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Modifie le nom de l'arbitre
	 * @param nom Nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'arbitre
	 * @return Prénom 
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Modifie le prénom de l'arbitre
	 * @param prenom Prénom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Retourne vrai si l'arbitre est égal à l'objet passé en paramètre
	 * @param o Objet à comparer
	 * @return Vrai si l'arbitre est égal à l'objet passé en paramètre, faux sinon
	 */
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Arbitre)) {
	        return false;
	    }
	    Arbitre arbitre = (Arbitre) o;
	    return arbitre.getIdArbitre() == this.getIdArbitre();
	}

	/**
	 * Retourne une représentation textuelle de l'arbitre
	 * @return Chaine de caractère représentant l'arbitre
	 */
	@Override
	public String toString() {
		return "Arbitre [idArbitre=" + idArbitre + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
	
}
