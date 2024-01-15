package modele.metier;

import java.util.List;
import java.util.Objects;

/**
 * Modèle métier Équipe
 */
public class Equipe implements Comparable<Equipe> {

	private int idEquipe;
	private String nom;
	private Pays pays;
	private int classement;
	private int worldRanking;
	private String saison;
	private List<Joueur> joueurs;
	
	/**
	 * Construit une équipe complète
	 * @param idEquipe 		Clé primaire
	 * @param nom 			Nom 
	 * @param pays 			Pays
	 * @param classement 	Classement
	 * @param worldRanking 	World Ranking
	 * @param saison 		Saison
	 * @param joueurs 		Liste des joueurs
	 */
	public Equipe(int idEquipe, String nom, Pays pays, int classement, int worldRanking, String saison, List<Joueur> joueurs) {
		this.idEquipe = idEquipe;
		this.nom = nom;
		this.pays = pays;
		this.classement = classement;
		this.worldRanking = worldRanking;
		this.saison = saison;
		this.joueurs = joueurs;
	}
	
	/**
	 * Construit une équipe sans clé primaire
	 * @param nom 		Nom
	 * @param pays 		Pays
	 * @param joueurs 	Liste des joueurs
	 */
	public Equipe(String nom, Pays pays, List<Joueur> joueurs) {
		this.nom = nom;
		this.pays = pays;
		this.joueurs = joueurs;
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
	public Pays getPays() {
		return this.pays;
	}
	
	/**
	 * Modifie le pays
	 * @param pays Pays
	 */
	public void setPays(Pays pays) {
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
	 * Méthode de comparaison d'une équipe avec un autre objet
	 * @param o Objet à comparer
	 * @return true si un Object o est égal à Equipe (this), faux sinon
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
	    return this.nom.equals(equipe.nom) && this.pays.equals(equipe.pays);
	}

	/**
	 * Retourne le hashcode de l'équipe
	 * @return Hashcode de l'équipe
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nom, pays);
	}

	/**
	 * Compare l'équipe avec une autre équipe
	 * @param equipe Équipe à comparer
	 * @return 0 si les équipes sont égales, 1 si l'équipe est supérieure à l'équipe passée en paramètre, -1 sinon
	 */
	@Override
	public int compareTo(Equipe equipe) {
		return this.nom.compareTo(equipe.nom);
	}

	/**
	 * Retourne une représentation textuelle de l'équipe
	 * @return Représentation textuelle de l'équipe
	 */
	@Override
	public String toString() {
		return "Equipe [idEquipe=" + idEquipe + ", nom=" + nom + ", pays=" + pays + ", classement=" + classement
				+ ", worldRanking=" + worldRanking + ", saison=" + saison + "]";
	}

}
