package modele;

import java.sql.SQLException;

import modele.DAO.DAOAdministrateur;
import modele.DAO.DAOAdministrateurImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Tournoi;
import modele.metier.Utilisateur;

/**
 * Modèle utilisateur
 */
public class ModeleUtilisateur {

	private static Utilisateur compteCourant;
	private DAOAdministrateur daoAdministrateur;
	private DAOTournoi daoTournoi;
	
	/**
	 * Construit un modèle utilisateur
	 */
	public ModeleUtilisateur() {
		this.daoAdministrateur = new DAOAdministrateurImpl();
		this.daoTournoi = new DAOTournoiImpl();
	}

	/**
	 * Chiffre un mot de passe
	 * @param motDePasse Mot de passe à chiffrer
	 * @return Mot de passe chiffré
	 */
	public static String chiffrerMotDePasse(String motDePasse) {
        // Génération d'un sel aléatoire
        String salt = BCrypt.gensalt(12);

        // Hashage du mot de passe avec le sel
        String motDePasseChiffre = BCrypt.hashpw(motDePasse, salt);

        return motDePasseChiffre;
    }
	
	/**
	 * Connecte un utilisateur avec son couple identifiant/mot de passe s'il existe
	 * @param identifiant Identifiant de l'utilisateur
	 * @param motDePasse Mot de passe de l'utilisateur
	 * @throws IllegalArgumentException si l'identifiant et/ou le mot de passe sont incorrects
	 * @throws IllegalStateException si un utilisateur est déjà connecté
	 */
	public void connecter(String identifiant, String motDePasse) throws IllegalArgumentException, IllegalStateException {
	    if (compteCourant != null) {
            throw new IllegalStateException("Un utilisateur est déjà connecté");
        }

	    Utilisateur utilisateur = null;
	    try {
			// Chercher l'utilisateur dans la base de données
	        utilisateur = chercherUtilisateur(identifiant);
	    } catch (SQLException e) {
	        throw new IllegalStateException("Erreur lors de la connexion", e);
	    }

		// Vérifier que l'utilisateur existe et que le mot de passe est correct
	    validerUtilisateur(utilisateur, motDePasse);
	    compteCourant = utilisateur;
	}
	
	/**
	 * Déconnecter l'utilisateur
	 * @throws IllegalArgumentException si l'administrateur est déjà déconnecté
	 */
	public void deconnecter() throws IllegalStateException {
		if(compteCourant == null) {
			throw new IllegalStateException("Vous êtes déjà déconnecté.");
		}
		compteCourant = null;
	}
	
	/**
	 * Retourne l'utilisateur actuellement connecté
	 * @return l'utilisateur actuellement connecté
	 */
	public static Utilisateur getCompteCourant() {
		return compteCourant;
	}
	
	/**
	 * Vérifier si le mot de passe fourni correspond au mot de passe chiffré
	 * @param motDePasse Mot de passe à vérifier
	 * @param motDePasseChiffre Mot de passe chiffré à comparer
	 * @return true si le mot de passe correspond, false sinon
	 */
    private boolean verifierMotDePasse(String motDePasse, String motDePasseChiffre) {
        // Vérification du mot de passe fourni par l'utilisateur avec le mot de passe chiffré enregistré
        return BCrypt.checkpw(motDePasse, motDePasseChiffre);
    }

	/**
	 * Chercher un utilisateur par son identifiant
	 * @param identifiant Identifiant de l'utilisateur
	 * @return Utilisateur trouvé
	 * @throws SQLException Erreur SQL
	 */
    private Utilisateur chercherUtilisateur(String identifiant) throws SQLException {
        Utilisateur utilisateur = this.daoAdministrateur.getParIdentifiant(identifiant).orElse(null);
		// Si l'utilisateur n'est pas un administrateur, on cherche dans les tournois
        if (utilisateur == null) {
            utilisateur = daoTournoi.getParIdentifiant(identifiant).orElse(null);
        }
        return utilisateur;
    }

	/**
	 * Valider un utilisateur
	 * @param utilisateur Utilisateur à valider
	 * @param motDePasse Mot de passe de l'utilisateur
	 * @throws IllegalArgumentException si l'utilisateur n'existe pas ou si le mot de passe est incorrect ou si l'utilisateur est un arbitre et que le tournoi est clôturé
	 */
	private void validerUtilisateur(Utilisateur utilisateur, String motDePasse) throws IllegalArgumentException {
		if (utilisateur == null || !this.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse())) {
			throw new IllegalArgumentException("Identifiant et/ou mot de passe incorrects.");
		}
		if (utilisateur.getRole() == Utilisateur.Role.ARBITRE && ((Tournoi) utilisateur).getEstCloture() == true) {
			throw new IllegalArgumentException("Vous ne pouvez pas vous connecter sur un tournoi en phase d'inscriptions ou clôturé.");
		}
	}
	
}
