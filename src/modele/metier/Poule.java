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
	 * @param idPoule
	 * @param estCloturee
	 * @param estFinale
	 * @param idTournoi
	 * @param rencontres
	 */
	public Poule(int idPoule, boolean estCloturee, boolean estFinale, int idTournoi, List<Rencontre> rencontres) {
		this.idPoule = idPoule;
		this.estCloturee = estCloturee;
		this.estFinale = estFinale;
		this.idTournoi = idTournoi;
		this.rencontres = rencontres;
	}
	
	/**
	 * Construit une poule
	 * @param idPoule
	 * @param estCloturee
	 * @param estFinale
	 * @param idTournoi
	 */
	public Poule(int idPoule, boolean estCloturee, boolean estFinale, int idTournoi) {
		this.idPoule = idPoule;
		this.estCloturee = estCloturee;
		this.estFinale = estFinale;
		this.idTournoi = idTournoi;
	}
	
	/**
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
	 * @return EstCloturee
	 */
	public boolean isEstCloturee() {
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
	 * @return EstFinale
	 */
	public boolean isEstFinale() {
		return estFinale;
	}

	/**
	 * Modifie si la poule est une finale
	 * @param estCloturee
	 */
	public void setEstFinale(boolean estFinale) {
		this.estFinale = estFinale;
	}

	/**
	 * @return Clé étrangère tournoi
	 */
	public int getIdTournoi() {
		return idTournoi;
	}

	/**
	 * Modifie la clé étrangère tournoi
	 * @param idTournoi
	 */
	public void setIdTournoi(int idTournoi) {
		this.idTournoi = idTournoi;
	}

	/**
	 * @return Liste des rencontres
	 */
	public List<Rencontre> getRencontres() {
		return rencontres;
	}

	/**
	 * Modifier la liste des rencontres
	 * @param rencontres
	 */
	public void setRencontres(List<Rencontre> rencontres) {
		this.rencontres = rencontres;
	}
	
	/**
	 * Retourne true si un Object o est égal à Poule (this), faux sinon
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
	            && this.estCloturee == poule.isEstCloturee()
	            && this.estFinale == poule.isEstFinale()
	     	    && this.rencontres == poule.getRencontres();
	}
	
	@Override
	public String toString() {
		return "Poule [idPoule=" + idPoule 
				+ ", estCloturée=" + estCloturee 
				+ ", estFinale=" + estFinale 
				+ ", idTournoi=" + idTournoi + "]";
	}
}
