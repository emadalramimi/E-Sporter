package modele.metier;

import java.util.List;

/**
 * Modèle métier Équipe
 */
public class Equipe implements Comparable<Equipe> {

	private int idEquipe;
	private String nom;
	private String pays;
	private int classement;
	private int worldRanking;
	private String saison;
	private List<Joueur> joueurs;
	
	/**
	 * Construit une équipe
	 * @param idEquipe
	 * @param nom
	 * @param pays
	 * @param classement
	 * @param worldRanking
	 * @param saison
	 * @param joueurs
	 */
	public Equipe(int idEquipe, String nom, String pays, int classement, int worldRanking, String saison, List<Joueur> joueurs) {
		this.idEquipe = idEquipe;
		this.nom = nom;
		this.pays = pays;
		this.classement = classement;
		this.worldRanking = worldRanking;
		this.saison = saison;
		this.joueurs = joueurs;
	}
	
	/**
	 * Construit une équipe sans la liste des joueurs (pour l'insertion en BDD)
	 * @param idEquipe
	 * @param nom
	 * @param pays
	 * @param classement
	 * @param worldRanking
	 * @param saison
	 */
	public Equipe(int idEquipe, String nom, String pays, int classement, int worldRanking, String saison) {
		this.idEquipe = idEquipe;
		this.nom = nom;
		this.pays = pays;
		this.classement = classement;
		this.worldRanking = worldRanking;
		this.saison = saison;
	}

	/**
	 * Retourne la clé priamire
	 * @return Clé primaire
	 */
	public int getIdEquipe() {
		return this.idEquipe;
	}
	
	/**
	 * Modifie la clé primaire
	 * @param idEquipe clé primaire
	 */
	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
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
	 * Retourne le pays
	 * @return Pays
	 */
	public String getPays() {
		return this.pays;
	}
	
	/**
	 * Modifie le pays
	 * @param pays Pays
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * Retourne le classement
	 * @return Classement
	 */
	public int getClassement() {
		return this.classement;
	}
	
	/**
	 * Modifie le classement
	 * @param classement Classement
	 */
	public void setClassement(int classement) {
		this.classement = classement;
	}

	/**
	 * Retourne le World Ranking
	 * @return World Ranking
	 */
	public int getWorldRanking() {
		return this.worldRanking;
	}
	
	/**
	 * Modifie le World Ranking
	 * @param worldRanking World Ranking
	 */
	public void setWorldRanking(int worldRanking) {
		this.worldRanking = worldRanking;
	}

	/**
	 * Retourne la saison
	 * @return Saison
	 */
	public String getSaison() {
		return this.saison;
	}
	
	/**
	 * Modifie la saison
	 * @param saison Saison
	 */
	public void setSaison(String saison) {
		this.saison = saison;
	}
	
	/**
	 * Retourne la liste des joueurs
	 * @return Liste des joueurs
	 */
	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Modifie la liste des joueurs
	 * @param joueurs Liste des joueurs
	 */
	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	
	/**
	 * Retourne true si un Object o est égal à Equipe (this), faux sinon
	 */
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Equipe)) {
	        return false;
	    }
	    Equipe equipe = (Equipe) o;
	    return this.idEquipe == equipe.idEquipe
	            && this.nom.equals(equipe.nom)
	            && this.pays.equals(equipe.pays)
	            && this.classement == equipe.classement
	            && this.worldRanking == equipe.worldRanking
	            && this.saison.equals(equipe.saison);
	}

	@Override
	public String toString() {
		return "Equipe [idEquipe=" + idEquipe + ", nom=" + nom + ", pays=" + pays + ", classement=" + classement
				+ ", worldRanking=" + worldRanking + ", saison=" + saison + "]";
	}

	@Override
	public int compareTo(Equipe equipe) {
		return this.nom.compareTo(equipe.nom);
	}

}
