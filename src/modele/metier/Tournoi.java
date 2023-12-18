package modele.metier;

import java.util.Arrays;
import java.util.List;

/**
 * Modèle métier Tournoi
 */
public class Tournoi implements Utilisateur, Comparable<Tournoi> {
	
	// TODO ajouter une méthode à l'énum permettant de get le multiplicateur de pts
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
	private long dateTimeDebut;
	private long dateTimeFin;
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
	 * @param dateTimeDebut
	 * @param dateTimeFin
	 * @param identifiant
	 * @param motDePasse
	 * @param poules
	 * @param equipes
	 * @param arbitres
	 */
	public Tournoi(int idTournoi, String nomTournoi, Notoriete notoriete, long dateTimeDebut, long dateTimeFin, boolean estCloture, String identifiant, String motDePasse, List<Poule> poules, List<Equipe> equipes, List<Arbitre> arbitres) {
		this.idTournoi = idTournoi;
		this.nomTournoi = nomTournoi;
		this.notoriete = notoriete;
		this.dateTimeDebut = dateTimeDebut;
		this.dateTimeFin = dateTimeFin;
		this.estCloture = estCloture;
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.poules = poules;
		this.equipes = equipes;
		this.arbitres = arbitres;
	}
	
	// Pas d'équipe et poule car inscription
	public Tournoi(String nomTournoi, Notoriete notoriete, long dateTimeDebut, long dateTimeFin, String identifiant, String motDePasse, List<Arbitre> arbitres) {
		this.nomTournoi = nomTournoi;
		this.notoriete = notoriete;
		this.dateTimeDebut = dateTimeDebut;
		this.dateTimeFin = dateTimeFin;
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
	 * @return dateTimeDebut
	 */
	public long getDateTimeDebut() {
		return dateTimeDebut;
	}

	/**
	 * Modifie la date de début
	 * @param dateTimeDebut
	 */
	public void setDateTimeDebut(long dateTimeDebut) {
		this.dateTimeDebut = dateTimeDebut;
	}

	/**
	 * @return dateTimeFin
	 */
	public long getDateTimeFin() {
		return dateTimeFin;
	}

	/**
	 * Modifie la date de fin
	 * @param dateTimeFin
	 */
	public void setDateTimeFin(long dateTimeFin) {
		this.dateTimeFin = dateTimeFin;
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

	public Poule getPouleActuelle() {
		for (Poule poule : this.poules) {
			if (poule.getEstCloturee() == false) {
				return poule;
			}
		}
		return null;
	}
	
	public List<Equipe> getEquipes() {
		return this.equipes;
	}
	
	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public void addEquipe(Equipe equipe) {
		this.equipes.add(equipe);
	}

	public void removeEquipe(Equipe equipe) {
		this.equipes.remove(equipe);
	}
	
	public List<Arbitre> getArbitres() {
		return this.arbitres;
	}
	
	public void setArbitres(List<Arbitre> arbitres) {
		this.arbitres = arbitres;
	}

	public int getNbMatchsJoues(Equipe equipe) {
		int nbMatchsJoues = 0;
		for(Poule poule : this.poules) {
			for(Rencontre rencontre : poule.getRencontres()) {
				// 0 => valeur nulle
				if(Arrays.asList(rencontre.getEquipes()).contains(equipe) && rencontre.getIdEquipeGagnante() != 0) {
					nbMatchsJoues++;
				}
			}
		}
		return nbMatchsJoues;
	}

	public int getNbMatchsGagnes(Equipe equipe) {
		int nbMatchsGagnes = 0;
		for(Poule poule : this.poules) {
			for(Rencontre rencontre : poule.getRencontres()) {
				if(Arrays.asList(rencontre.getEquipes()).contains(equipe) && rencontre.getIdEquipeGagnante() == equipe.getIdEquipe()) {
					nbMatchsGagnes++;
				}
			}
		}
		return nbMatchsGagnes;
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
	    return this.idTournoi == tournoi.getIdTournoi();
	}

	@Override
	public String toString() {
		return "Tournoi [idTournoi=" + idTournoi + ", nomTournoi=" + nomTournoi + ", notoriete=" + notoriete
				+ ", dateTimeDebut=" + dateTimeDebut + ", dateTimeFin=" + dateTimeFin + ", estCloture=" + estCloture + ", identifiant="
				+ identifiant + ", motDePasse=" + motDePasse + ", poules=" + poules + ", equipes=" + equipes
				+ ", arbitres=" + arbitres + "]";
	}

	@Override
	public int compareTo(Tournoi tournoi) {
		// Vérifie si le tournoi actuel est clos, ouvert ou en cours de création
		int thisStatus = this.getEstCloture() ? (System.currentTimeMillis() / 1000 < this.getDateTimeFin() ? 1 : 2) : 0;

		// Vérifie si le tournoi fourni est clos, ouvert ou en cours de création
		int tournoiStatus = tournoi.getEstCloture() ? (System.currentTimeMillis() / 1000 < tournoi.getDateTimeFin() ? 1 : 2) : 0;

		// D'abord, comparaison basée sur le statut des tournois
		if (thisStatus < tournoiStatus) {
			// Si le statut du tournoi actuel est inférieur à celui du tournoi fourni, le tournoi actuel vient en premier
			return -1;
		} else if (thisStatus > tournoiStatus) {
			// Si le statut du tournoi actuel est supérieur à celui du tournoi fourni, le tournoi fourni vient en premier
			return 1;
		} else {
			// Si les deux tournois ont le même statut, comparer par date de début et nom
			if (this.getDateTimeDebut() < tournoi.getDateTimeDebut()) {
				return -1; // Le tournoi actuel commence plus tôt, donc il vient en premier
			} else if (this.getDateTimeDebut() == tournoi.getDateTimeDebut()) {
				return this.getNomTournoi().compareTo(tournoi.getNomTournoi()); // Les tournois commencent en même temps, donc on les compare par nom
			} else {
				return 1; // Le tournoi fourni commence plus tôt, donc il vient en premier
			}
		}
	}
	
}
