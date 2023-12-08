package modele.metier;

/**
 * Modèle joueur
 */
public class Joueur {

	private int idJoueur;
	private String pseudo;
	private int idEquipe;
	
	/**
	 * Construit un joueur
	 * @param idJoueur	Clé primaire
	 * @param pseudo	Pseudo
	 */
	public Joueur(int idJoueur, String pseudo, int idEquipe) {
		this.idJoueur = idJoueur;
		this.pseudo = pseudo;
		this.idEquipe = idEquipe;
	}
	
	/**
	 * Construit un joueur sans identifiant, ni clé étrangère (pour l'insertion en BDD)
	 * @param pseudo Pseudo
	 */
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Retourne la clé primaire
	 * @return Clé primaire
	 */
	public int getIdJoueur() {
		return idJoueur;
	}
	
	/**
	 * Modifie la clé primaire
	 * @param idJoueur Clé primaire
	 */
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	/**
	 * Retourne le pseudo
	 * @return Pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Modifie le pseudo
	 * @param pseudo
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	/**
	 * Retourne la clé étrangère équipe
	 * @return Clé étrangère équipe
	 */
	public int getIdEquipe() {
		return this.idEquipe;
	}
	
	/**
	 * Modifie la clé étrangère équipe
	 * @param idEquipe
	 */
	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
	}
	
	/**
	 * Retourne true si un Object o est égal à Joueur (this), faux sinon
	 */
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Joueur)) {
	        return false;
	    }
	    Joueur joueur = (Joueur) o;
	    return this.idJoueur == joueur.getIdJoueur()
	            && this.pseudo.equals(joueur.getPseudo())
	            && this.idEquipe == joueur.getIdEquipe();
	}

	@Override
	public String toString() {
		return "Joueur [idJoueur=" + idJoueur + ", pseudo=" + pseudo + ", idEquipe=" + idEquipe + "]";
	}

}
