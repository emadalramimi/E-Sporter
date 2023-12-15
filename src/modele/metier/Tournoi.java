package modele.metier;

import java.util.List;

/**
 * Modèle métier Tournoi
 */
public class Tournoi implements Utilisateur, Comparable<Tournoi> {
	
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
        
        public static Notoriete valueOfLibelle(String libelle) {
            for (Notoriete notoriete : values()) {
                if (notoriete.getLibelle().equalsIgnoreCase(libelle)) {
                    return notoriete;
                }
            }
            throw new IllegalArgumentException("Notoriété avec le libellé '" + libelle + "' non trouvé.");
        }
    }
	
	private int idTournoi;
	private String nomTournoi;
	private Notoriete notoriete;
	private long dateDebut;
	private long dateFin;
	private boolean estCloture;
	private String identifiant;
	private String motDePasse;
	private List<Poule> poules;
	private List<Equipe> equipes;
	private List<Arbitre> arbitres;
	
	/**
	 * Construit un tournoi
	 * @param idTournoi
	 * @param nomTournoi
	 * @param notoriete
	 * @param dateDebut
	 * @param dateFin
	 * @param identifiant
	 * @param motDePasse
	 * @param equipes
	 * @param arbitres
	 */
	public Tournoi(int idTournoi, String nomTournoi, Notoriete notoriete, long dateDebut, long dateFin, boolean estCloture, String identifiant, String motDePasse, List<Equipe> equipes, List<Arbitre> arbitres) {
		this.idTournoi = idTournoi;
		this.nomTournoi = nomTournoi;
		this.notoriete = notoriete;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.estCloture = estCloture;
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.equipes = equipes;
		this.arbitres = arbitres;
	}
	
	public Tournoi(String nomTournoi, Notoriete notoriete, long dateDebut, long dateFin, String identifiant, String motDePasse, List<Arbitre> arbitres) {
		this.nomTournoi = nomTournoi;
		this.notoriete = notoriete;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.arbitres = arbitres;
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
	public long getDateDebut() {
		return dateDebut;
	}

	/**
	 * Modifie la date de début
	 * @param dateDebut
	 */
	public void setDateDebut(long dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return DateFin
	 */
	public long getDateFin() {
		return dateFin;
	}

	/**
	 * Modifie la date de fin
	 * @param dateFin
	 */
	public void setDateFin(long dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return EstCloturee
	 */
	public boolean getEstCloture() {
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
	@Override
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * Modifie l'identifiant
	 * @param identifiant
	 */
	@Override
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	/**
	 * @return MotDePasse
	 */
	@Override
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Modifie le mot de passe
	 * @param motDePasse
	 */
	@Override
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	
	public Role getRole() {
		return Role.ARBITRE;
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
	
	public List<Equipe> getEquipes() {
		return this.equipes;
	}
	
	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}
	
	public List<Arbitre> getArbitres() {
		return this.arbitres;
	}
	
	public void setArbitres(List<Arbitre> arbitres) {
		this.arbitres = arbitres;
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
	 	    && this.estCloture == tournoi.getEstCloture()
	 	    && this.equipes.equals(tournoi.getEquipes())
	 	    && this.arbitres.equals(tournoi.getArbitres());
	}

	@Override
	public String toString() {
		return "Tournoi [idTournoi=" + idTournoi + ", nomTournoi=" + nomTournoi + ", notoriete=" + notoriete
				+ ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", estCloture=" + estCloture + ", identifiant="
				+ identifiant + ", motDePasse=" + motDePasse + ", poules=" + poules + ", equipes=" + equipes
				+ ", arbitres=" + arbitres + "]";
	}

	@Override
	public int compareTo(Tournoi tournoi) {
		// Vérifie si le tournoi actuel est clos et que la date de fin est passée
		boolean thisClosEtTermine = System.currentTimeMillis() / 1000 < this.getDateFin() && this.getEstCloture();

		// Vérifie si le tournoi fourni est clos et terminé
		boolean autreClosEtTermine = System.currentTimeMillis() / 1000 < tournoi.getDateFin() && tournoi.getEstCloture();

		// Tout d'abord, comparaison basée sur le statut clos et terminé des tournois
		if (thisClosEtTermine && autreClosEtTermine) {
			// Si les deux tournois sont clos et terminés, comparer par date de début et nom
			if (this.getDateDebut() < tournoi.getDateDebut()) {
				return -1;
			} else if (this.getDateDebut() == tournoi.getDateDebut()) {
				return this.getNomTournoi().compareTo(tournoi.getNomTournoi());
			} else {
				return 1;
			}
		} else if (thisClosEtTermine) {
			// Si seul le tournoi actuel est clos et terminé, il devrait venir en premier
			return -1;
		} else if (autreClosEtTermine) {
			// Si seul le tournoi fourni est clos et terminé, il devrait venir en premier
			return 1;
		} else {
			// Si aucun des tournois n'est clos et terminé, comparer par date de début et nom
			if (this.getDateDebut() < tournoi.getDateDebut()) {
				return -1;
			} else if (this.getDateDebut() == tournoi.getDateDebut()) {
				return this.getNomTournoi().compareTo(tournoi.getNomTournoi());
			} else {
				return 1;
			}
		}
	}
	
}
