package modele;

import java.sql.SQLException;

import modele.metier.Tournoi;
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
	 * @throws IdentificationTournoiClotureException
	 * @throws RuntimeException
	 */
	public void connecter(String identifiant, String motDePasse) throws IllegalArgumentException, IllegalStateException {
	    if (compteCourant != null) {
            throw new IllegalStateException("Un utilisateur est déjà connecté");
        }

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

    private Utilisateur chercherUtilisateur(String identifiant) throws SQLException {
        Utilisateur utilisateur = this.modeleAdministrateur.getParIdentifiant(identifiant).orElse(null);
        if (utilisateur == null) {
            utilisateur = modeleTournoi.getParIdentifiant(identifiant).orElse(null);
        }
        return utilisateur;
    }

	private void validerUtilisateur(Utilisateur utilisateur, String motDePasse) throws IllegalArgumentException {
		if (utilisateur == null || !this.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse())) {
			throw new IllegalArgumentException("Identifiant et/ou mot de passe incorrects.");
		}
		if (utilisateur.getRole() == Utilisateur.Role.ARBITRE && ((Tournoi) utilisateur).getEstCloture() == true) {
			throw new IllegalArgumentException("Vous ne pouvez pas vous connecter sur un tournoi clôturé.");
		}
	}
	
}
