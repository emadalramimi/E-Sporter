package modele.metier;

import java.util.List;

/**
 * Modèle métier équipe
 * @author Nassim Khoujane
 */
public class Equipe {

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
	 * @return Nom
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * Modifie le nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return Pays
	 */
	public String getPays() {
		return this.pays;
	}
	
	/**
	 * Modifie le pays
	 * @param pays
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * @return Classement
	 */
	public int getClassement() {
		return this.classement;
	}
	
	/**
	 * Modifie le classement
	 * @param classement
	 */
	public void setClassement(int classement) {
		this.classement = classement;
	}

	/**
	 * @return WorldRanking
	 */
	public int getWorldRanking() {
		return this.worldRanking;
	}
	
	/**
	 * Modifie le worldRanking
	 * @param worldRanking
	 */
	public void setWorldRanking(int worldRanking) {
		this.worldRanking = worldRanking;
	}

	/**
	 * @return Saison
	 */
	public String getSaison() {
		return this.saison;
	}
	
	/**
	 * Modifie la saison
	 * @param saison
	 */
	public void setSaison(String saison) {
		this.saison = saison;
	}
	
	/**
	 * @return Liste des joueurs
	 */
	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Modifier la liste des joueurs
	 * @param joueurs
	 */
	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	@Override
	public String toString() {
		return "Equipe [idEquipe=" + idEquipe + ", nom=" + nom + ", pays=" + pays + ", classement=" + classement
				+ ", worldRanking=" + worldRanking + ", saison=" + saison + "]";
	}

}
