package modele.metier;

/**
 * Modèle métier rencontre
 */
public class Rencontre {

	private int idRencontre;
	private int dateHeureDebut;
	private int dateHeureFin;
	private int idPoule;
	private Equipe[] equipes;
	
	/**
	 * Construit une équipe
	 * @param idRencontre
	 * @param dateHeureDebut
	 * @param dateHeureFin
	 * @param idPoule
	 * @param equipes
	 */
	public Rencontre(int idRencontre, int dateHeureDebut, int dateHeureFin, int idPoule, Equipe[] equipes) {
		this.idRencontre = idRencontre;
		this.dateHeureDebut = dateHeureDebut;
		this.dateHeureFin = dateHeureFin;
		this.idPoule = idPoule;
		this.equipes = equipes;
	}
	
	/**
	 * Construit une rencontre
	 * @param idRencontre
	 * @param dateHeureDebut
	 * @param dateHeureFin
	 * @param idPoule
	 */
	public Rencontre(int idRencontre, int dateHeureDebut, int dateHeureFin, int idPoule) {
		this.idRencontre = idRencontre;
		this.dateHeureDebut = dateHeureDebut;
		this.dateHeureFin = dateHeureFin;
		this.idPoule = idPoule;
		equipes = new Equipe[2];
	}
	
	/**
	 * @return Clé primaire
	 */
	public int getIdRencontre() {
		return idRencontre;
	}

	/**
	 * Modifie la clé primaire
	 * @param idRencontre clé primaire
	 */
	public void setIdRencontre(int idRencontre) {
		this.idRencontre = idRencontre;
	}
	
	/**
	 * @return DateHeureDebut
	 */
	public int getDateHeureDebut() {
		return dateHeureDebut;
	}

	/**
	 * Modifie la date de début
	 * @param dateHeureDebut
	 */
	public void setDateHeureDebut(int dateHeureDebut) {
		this.dateHeureDebut = dateHeureDebut;
	}

	/**
	 * @return DateHeureFin
	 */
	public int getDateHeureFin() {
		return dateHeureFin;
	}

	/**
	 * Modifie la date de fin
	 * @param dateHeureFin
	 */
	public void setDateHeureFin(int dateHeureFin) {
		this.dateHeureFin = dateHeureFin;
	}

	/**
	 * @return Clé étrangère poule
	 */
	public int getIdPoule() {
		return idPoule;
	}

	/**
	 * Modifie la clé étrangère poule
	 * @param idPoule
	 */
	public void setIdPoule(int idPoule) {
		this.idPoule = idPoule;
	}

	/**
	 * @return Clé étrangère Tableau d'équipes
	 */
	public Equipe[] getEquipes() {
		return equipes;
	}

	/**
	 * Modifie la clé étrangère tableau d'équipes
	 * @param equipes
	 */
	public void setEquipes(Equipe[] equipes) {
		this.equipes = equipes;
	}
	
	/**
	 * Retourne true si un Object o est égal à Rencontre (this), faux sinon
	 */
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Rencontre)) {
	        return false;
	    }
	    Rencontre rencontre = (Rencontre) o;
	    return this.idRencontre == rencontre.getIdPoule()
	            && this.dateHeureDebut == rencontre.getDateHeureDebut()
	            && this.dateHeureFin == rencontre.getDateHeureFin()
	            && this.idPoule == rencontre.getIdPoule()
	     	    && this.equipes == rencontre.getEquipes();
	}
	
	@Override
	public String toString() {
		return "Rencontre [idRencontre=" + idRencontre
				+ ", dateHeureDebut=" + dateHeureDebut 
				+ ", dateHeureFin=" + dateHeureFin 
				+ ", idPoule=" + idPoule + "]";
	}
	
}
