package modele.metier;

/**
 * Interface Utilisateur (englobant les administrateurs et arbitres (Tournoi))
 */
public interface Utilisateur {
	
	public enum Role {
		ADMINISTRATEUR,
		ARBITRE
	}
	
	public String getIdentifiant();
	
	public void setIdentifiant(String identifiant);
	
	public String getMotDePasse();
	
	public void setMotDePasse(String motDePasse);
	
	public Role getRole();
	
}
