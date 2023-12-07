package modele.metier;

import java.util.List;

/**
 * Modèle métier tournoi
 */
public class Tournoi {
	
	//TODO ajouter une méthode à l'énum permettant de get le multiplicateur de pts
	public enum Notoriete {
        LOCAL("Local"),
        REGIONAL("Régional"),
        NATIONAL("National"),
        INTERNATIONAL("International"),
        INTERNATIONAL_CLASSE("International classé");

        private final String libelle;

        Notoriete(String libelle) {
            this.libelle = libelle;
        }

        public String getLibelle() {
            return libelle;
        }
    }
	
	private int idTournoi;
	private String nomTournoi;
	private Notoriete notoriete;
	private int dateDebut;
	private int dateFin;
	private boolean estCloture;
	private String identifiant;
	private String motDePasse;
	private List<Poule> poules;
	
	/**
	 * Construit un tournoi
	 * @param idTournoi
	 * @param nomTournoi
	 * @param notoriete
	 * @param dateDebut
	 * @param dateFin
	 * @param estCloture
	 * @param identifiant
	 * @param motDePasse
	 */
	public Tournoi(int idTournoi, String nomTournoi, Notoriete notoriete, int dateDebut, int dateFin, boolean estCloture,
			String identifiant, String motDePasse) {
		this.idTournoi = idTournoi;
		this.nomTournoi = nomTournoi;
		this.notoriete = notoriete;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.estCloture = estCloture;
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
	}

	/**
	 * @return Clé primaire
	 */
	public int getIdTournoi() {
		return idTournoi;
	}

	/**
	 * Modifie la clé primaire
	 * @param idEquipe clé primaire
	 */
	public void setIdTournoi(int idTournoi) {
		this.idTournoi = idTournoi;
	}

	/**
	 * @return NomTournoi
	 */
	public String getNomTournoi() {
		return nomTournoi;
	}

	/**
	 * Modifie le nom du tournoi
	 * @param nomTournoi
	 */
	public void setNomTournoi(String nomTournoi) {
		this.nomTournoi = nomTournoi;
	}

	/**
	 * @return Notoriete
	 */
	public Notoriete getNotoriete() {
		return notoriete;
	}
	
	/**
	 * Modifie la notoriété
	 * @param notoriete
	 */
	public void setNotoriete(Notoriete notoriete) {
		this.notoriete = notoriete;
	}

	/**
	 * @return DateDebut
	 */
	public int getDateDebut() {
		return dateDebut;
	}

	/**
	 * Modifie la date de début
	 * @param dateDebut
	 */
	public void setDateDebut(int dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return DateFin
	 */
	public int getDateFin() {
		return dateFin;
	}

	/**
	 * Modifie la date de fin
	 * @param dateFin
	 */
	public void setDateFin(int dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return EstCloturee
	 */
	public boolean isEstCloture() {
		return estCloture;
	}

	/**
	 * Modifie si le tournoi est cloturé
	 * @param estCloture
	 */
	public void setEstCloture(boolean estCloture) {
		this.estCloture = estCloture;
	}

	/**
	 * @return Identifiant
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
	 * @return MotDePasse
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
	 * @return Liste des poules
	 */
	public List<Poule> getPoules() {
		return poules;
	}

	/**
	 * Modifier la liste des poules
	 * @param poules
	 */
	public void setPoules(List<Poule> poules) {
		this.poules = poules;
	}
	
	/**
	 * Retourne true si un Object o est égal à Tournoi (this), faux sinon
	 */
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Tournoi)) {
	        return false;
	    }
	    Tournoi tournoi = (Tournoi) o;
	    return this.idTournoi == tournoi.getIdTournoi()
	            && this.dateDebut == tournoi.getDateDebut()
	            && this.dateFin == tournoi.getDateFin()
	            && this.nomTournoi.equals(tournoi.getNomTournoi())
	     	    && this.notoriete.equals(tournoi.getNotoriete())
	     	    && this.identifiant.equals(tournoi.getIdentifiant())
	     	    && this.motDePasse.equals(tournoi.getMotDePasse())
	     	    && this.estCloture == tournoi.isEstCloture();
	}
	
	@Override
	public String toString() {
		return "Tournoi [idTournoi=" + idTournoi 
				+ ", nomTournoi=" + nomTournoi 
				+ ", notoriete=" + notoriete 
				+ ", dateDebut=" + dateDebut 
				+ ", dateFin=" + dateFin 
				+ ", estCloture=" + estCloture
				+ ", identifiant=" + identifiant
				+ ", motDePasse=" + motDePasse + "]";
	}
	
}
