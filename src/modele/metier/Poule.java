package modele.metier;

import java.util.List;

/**
 * Modèle métier poule
 */
public class Poule {
	
	private int idPoule;
	private boolean estCloturee;
	private boolean estFinale;
	private int idTournoi;
	private List<Rencontre> rencontres;
	
	/**
	 * Construit une poule
	 * @param idPoule 		Clé primaire
	 * @param estCloturee 	Si clôturée
	 * @param estFinale 	Si finale
	 * @param idTournoi 	Clé étrangère tournoi
	 * @param rencontres 	Liste des rencontres
	 */
	public Poule(int idPoule, boolean estCloturee, boolean estFinale, int idTournoi, List<Rencontre> rencontres) {
		this.idPoule = idPoule;
		this.estCloturee = estCloturee;
		this.estFinale = estFinale;
		this.idTournoi = idTournoi;
		this.rencontres = rencontres;
	}
	
	/**
	 * Construit une poule pour l'insertion en BDD
	 * @param estCloturee 	Si clôturée
	 * @param estFinale 	Si finale
	 * @param idTournoi 	Clé étrangère tournoi
	 * @param rencontres 	Liste des rencontres
	 */
	public Poule(boolean estCloturee, boolean estFinale, int idTournoi, List<Rencontre> rencontres) {
		this.estCloturee = estCloturee;
		this.estFinale = estFinale;
		this.idTournoi = idTournoi;
		this.rencontres = rencontres;
	}
	
	/**
	 * Retourne la clé primaire
	 * @return Clé primaire
	 */
	public int getIdPoule() {
		return idPoule;
	}

	/**
	 * Modifie la clé primaire
	 * @param idPoule clé primaire
	 */
	public void setIdPoule(int idPoule) {
		this.idPoule = idPoule;
	}

	/**
	 * Vérifie si la poule est cloturée
	 * @return EstCloturee
	 */
	public boolean getEstCloturee() {
		return estCloturee;
	}

	/**
	 * Modifie si la poule est cloturée
	 * @param estCloturee
	 */
	public void setEstCloturee(boolean estCloturee) {
		this.estCloturee = estCloturee;
	}

	/**
	 * Vérifie si la poule est une finale
	 * @return EstFinale
	 */
	public boolean getEstFinale() {
		return estFinale;
	}

	/**
	 * Modifie si la poule est une finale
	 * @param estFinale
	 */
	public void setEstFinale(boolean estFinale) {
		this.estFinale = estFinale;
	}

	/**
	 * Retourne la clé étrangère tournoi associée
	 * @return Clé étrangère tournoi
	 */
	public int getIdTournoi() {
		return idTournoi;
	}

	/**
	 * Modifie la clé étrangère tournoi
	 * @param idTournoi Clé étrangère tournoi associée
	 */
	public void setIdTournoi(int idTournoi) {
		this.idTournoi = idTournoi;
	}

	/**
	 * Retourne la liste des rencontres
	 * @return Liste des rencontres
	 */
	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	/**
	 * Modifier la liste des rencontres
	 * @param rencontres Liste des rencontres
	 */
	public void setRencontres(List<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}
	
	/**
	 * Méthode equals pour comparer deux poules
	 * @param o Objet à comparer
	 * @return Vrai si les deux poules sont égales, faux sinon
	 */
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Poule)) {
	        return false;
	    }
	    Poule poule = (Poule) o;
	    return this.idPoule == poule.getIdPoule()
	            && this.idTournoi == poule.getIdTournoi()
	            && this.estCloturee == poule.getEstCloturee()
	            && this.estFinale == poule.getEstFinale();
	}
	
	/**
	 * Retourne une représentation textuelle de la poule
	 * @return Représentation textuelle de la poule
	 */
	@Override
	public String toString() {
		return "Poule [idPoule=" + idPoule 
				+ ", estCloturée=" + estCloturee 
				+ ", estFinale=" + estFinale 
				+ ", idTournoi=" + idTournoi + "]";
	}
}
