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
	 * Retourne la clé primaire
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
	 * Retourne la clé étrangère poule
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
	 * Retourne la clé étrangère équipe gagnante
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
	 * Retourne liste d'équipes de la rencontre
	 * @return liste d'équipes de la rencontre
	 */
	public Equipe[] getEquipes() {
		return equipes;
	}

	/**
	 * Modifie la liste d'équipes de la rencontre
	 * @param equipes liste d'équipes de la rencontre à modifier
	 */
	public void setEquipes(Equipe[] equipes) {
		this.equipes = equipes;
	}
	
	/**
	 * Méthode equals pour comparer deux rencontres
	 * @param o Objet à comparer
	 * @return Vrai si les deux rencontres sont égales, faux sinon
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
	
	/**
	 * Retourne une représentation textuelle de la rencontre
	 * @return Chaîne de caractères représentant la rencontre
	 */
	@Override
	public String toString() {
		return "Rencontre [idRencontre=" + idRencontre
				+ ", idPoule=" + idPoule + "]";
	}
	
}
