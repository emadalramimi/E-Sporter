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
	 * @param nom		Nom
	 * @param prenom	Prénom
	 */
	public Arbitre(int idArbitre, String nom, String prenom) {
		this.idArbitre = idArbitre;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	/**
	 * Retourne la clé primaire
	 * @return Clé primaire
	 */
	public int getIdArbitre() {
		return this.idArbitre;
	}

	/**
	 * Modifie la clé primaire
	 * @param idArbitre Clé primaire
	 */
	public void setIdArbitre(int idArbitre) {
		this.idArbitre = idArbitre;
	}

	/**
	 * Retourne le nom
	 * @return Nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Modifie le nom
	 * @param nom Nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Retourne le prénom
	 * @return Prénom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Modifie le prénom
	 * @param prenom Prénom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * Retourne vrai si l'arbitre est égal à l'objet passé en paramètre
	 * @param o Objet à comparer
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

	@Override
	public String toString() {
		return "Arbitre [idArbitre=" + idArbitre + ", nom=" + nom + ", prenom=" + prenom + "]";
	}
	
}
