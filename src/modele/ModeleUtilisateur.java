package modele;

import java.sql.SQLException;

import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Utilisateur;

public class ModeleUtilisateur {

	private static Utilisateur compteCourant;
	private ModeleAdministrateur modeleAdministrateur;
	private ModeleTournoi modeleTournoi;
	
	public ModeleUtilisateur() {
		this.modeleAdministrateur = new ModeleAdministrateur();
		this.modeleTournoi = new ModeleTournoi();
	}
	
	public static String chiffrerMotDePasse(String motDePasse) {
        // Génération d'un sel aléatoire
        String salt = BCrypt.gensalt(12);

        // Hashage du mot de passe avec le sel
        String motDePasseChiffre = BCrypt.hashpw(motDePasse, salt);

        return motDePasseChiffre;
    }
	
	/**
	 * Connecte un utilisateur avec son couple identifiant/mot de passe s'il existe
	 * @param identifiant
	 * @param motDePasse
	 * @throws IdentifiantOuMdpIncorrectsException
	 * @throws RuntimeException
	 */
	public void connecter(String identifiant, String motDePasse) throws IdentifiantOuMdpIncorrectsException, IllegalStateException {
	    verifierUtilisateurDejaConnecte();

	    Utilisateur utilisateur = null;
	    try {
	        utilisateur = chercherUtilisateur(identifiant);
	    } catch (SQLException e) {
	        throw new IllegalStateException("Erreur lors de la connexion", e);
	    }

	    validerUtilisateur(utilisateur, motDePasse);
	    compteCourant = utilisateur;
	}
	
	/**
	 * Déconnecte l'utilisateur
	 * @throws IllegalArgumentException si l'administrateur est déjà déconnecté
	 */
	public void deconnecter() throws IllegalStateException {
		if(compteCourant == null) {
			throw new IllegalStateException("Vous êtes déjà déconnecté.");
		}
		compteCourant = null;
	}
	
	/**
	 * @return l'utilisateur actuellement connecté
	 */
	public static Utilisateur getCompteCourant() {
		return compteCourant;
	}
	
    private boolean verifierMotDePasse(String motDePasse, String motDePasseChiffre) {
        // Vérification du mot de passe fourni par l'utilisateur avec le mot de passe chiffré enregistré
        return BCrypt.checkpw(motDePasse, motDePasseChiffre);
    }
    
    private void verifierUtilisateurDejaConnecte() {
        if (compteCourant != null) {
            throw new IllegalStateException("Un utilisateur est déjà connecté");
        }
    }

    private Utilisateur chercherUtilisateur(String identifiant) throws SQLException {
        Utilisateur utilisateur = modeleAdministrateur.getParIdentifiant(identifiant).orElse(null);
        if (utilisateur == null) {
            utilisateur = modeleTournoi.getParIdentifiant(identifiant).orElse(null);
        }
        return utilisateur;
    }

    private void validerUtilisateur(Utilisateur utilisateur, String motDePasse) throws IdentifiantOuMdpIncorrectsException {
        if (utilisateur == null || !this.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse())) {
            throw new IdentifiantOuMdpIncorrectsException("Identifiant ou mot de passe incorrects");
        }
    }
	
}
