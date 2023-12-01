package modele.metier;

/**
 * Modèle joueur
 * @author Nassim Khoujane
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
	
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @return Clé primaire
	 */
	public int getIdJoueur() {
		return idJoueur;
	}
	
	/**
	 * Modifie la clé primaire
	 * @param idJoueur clé primaire
	 */
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	/**
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

	@Override
	public String toString() {
		return "Joueur [idJoueur=" + idJoueur + ", pseudo=" + pseudo + ", idEquipe=" + idEquipe + "]";
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof Joueur)) {
	        return false;
	    }
	    Joueur joueur = (Joueur) o;
	    return this.idJoueur == joueur.idJoueur
	            && this.pseudo.equals(joueur.pseudo)
	            && this.idEquipe == joueur.idEquipe;
	}

}
