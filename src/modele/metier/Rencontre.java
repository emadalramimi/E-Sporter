package modele.metier;

import java.util.Arrays;

/**
 * Modèle métier rencontre
 */
public class Rencontre {

	private int idRencontre;
	private int idPoule;
	private int idEquipeGagnante;
	private Equipe[] equipes;
	
	/**
	 * Construit une rencontre
	 * @param idRencontre 		Clé primaire
	 * @param idPoule 			Clé étrangère poule
	 * @param equipes 			Tableau d'équipes
	 */
	public Rencontre(int idRencontre, int idPoule, int idEquipeGagnante, Equipe[] equipes) {
		this.idRencontre = idRencontre;
		this.idPoule = idPoule;
		this.idEquipeGagnante = idEquipeGagnante;
		this.equipes = equipes;
	}
	
	/**
	 * Construit une rencontre (pour l'insertion en BDD)
	 * @param equipes Tableau d'équipes
	 */
	public Rencontre(Equipe[] equipes) {
		this.equipes = equipes;
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
	 * @return Clé étrangère équipe gagnante
	 */
	public int getIdEquipeGagnante() {
		return idEquipeGagnante;
	}

	/**
	 * Modifie la clé étrangère équipe gagnante
	 * @param idEquipeGagnante
	 */
	public void setIdEquipeGagnante(int idEquipeGagnante) {
		this.idEquipeGagnante = idEquipeGagnante;
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
	            && this.idPoule == rencontre.getIdPoule()
	     	    && Arrays.equals(this.equipes, rencontre.getEquipes());
	}
	
	@Override
	public String toString() {
		return "Rencontre [idRencontre=" + idRencontre
				+ ", idPoule=" + idPoule + "]";
	}
	
}
