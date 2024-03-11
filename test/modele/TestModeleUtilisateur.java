package modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import global.SuperTest;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Classe de test pour la classe modèle ModeleUtilisateur
 * @see ModeleUtilisateur
 */
public class TestModeleUtilisateur extends SuperTest {

	private ModeleUtilisateur modeleUtilisateur;

	@Before
	public void setUp() {
		this.modeleUtilisateur = new ModeleUtilisateur();
	}
	
	/**
	 * Teste le chiffrement du mot de passe d'un utilisateur
	 * @see ModeleUtilisateur#chiffrerMotDePasse(String)
	 */
	@Test
	public void testChiffrerMotDePasse() {
		String motDePasse = "mdp";
		assertNotEquals(ModeleUtilisateur.chiffrerMotDePasse(motDePasse), motDePasse);
	}

	/**
	 * Test la connexion multiple d'un même utilisateur
	 * @throws Exception si une erreur se produit pendant la connexion
	 * @see ModeleUtilisateur#connecter(String, String)
	 */
	@Test(expected = IllegalStateException.class)
	public void testConnexionDejaConnecte() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleUtilisateur.connecter("admin", "mdp");
	}

	/**
	 * Teste la connection avec un login invalide
	 * @throws IdentifiantOuMdpIncorrectsException si l'identifiant ou le mot de passe est incorrect
	 * @see ModeleUtilisateur#connecter(String, String)
	 */
	@Test(expected = IdentifiantOuMdpIncorrectsException.class)
	public void testConnexionIdentifiantInvalide() throws Exception {
		this.modeleUtilisateur.connecter("fauxadmin", "mdp");
	}

	/**
	 * Teste la connection avec un mot de passe invalide
	 * @throws IdentifiantOuMdpIncorrectsException si l'identifiant ou le mot de passe est incorrect
	 * @see ModeleUtilisateur#connecter(String, String)
	 */
	@Test(expected = IdentifiantOuMdpIncorrectsException.class)
	public void testConnexionMotDePasseInvalide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "*admin");
	}

	/**
	 * Test si la connexion fonctionne lorsqu'on rentre un identifiant et mot de passe valide
	 * @throws Exception si une erreur se produit pendant la connexion
	 * @see ModeleUtilisateur#connecter(String, String)
	 */
	@Test
	public void testConnexionValide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		assertTrue(this.modeleUtilisateur != null);
	}

	/**
	 * Test si le compte courant de l'admin est bien null lorsqu'il est déconnecté
	 * @throws Exception si une erreur se produit pendant la connexion
	 * @see ModeleUtilisateur#connecter(String, String)
	 * @see ModeleUtilisateur#deconnecter()
	 */
	@Test
	public void testDeconnexionValide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleUtilisateur.deconnecter();
		assertTrue(ModeleUtilisateur.getCompteCourant() == null);
	}

	/**
	 * Test l'erreur IllegalArgumentException lorsqu'on essaie de se déconnecter alors que l'on n'était pas connecté
	 * @see ModeleUtilisateur#deconnecter()
	 */
	@Test(expected = IllegalStateException.class)
	public void testDeconnexionInvalide() throws IllegalStateException {
		this.modeleUtilisateur.deconnecter();
	}

	/**
	 * Test du getter getCompteCourant
	 * @see ModeleUtilisateur#getCompteCourant()
	 */
	@Test
	public void testGetCompteCourant() {
		assertEquals(ModeleUtilisateur.getCompteCourant(), null);
	}
	
	/**
	 * Teste la connexion d'un utilisateur arbitre tournoi cloture
	 * @throws Exception si une erreur se produit pendant la connexion
	 * @see ModeleUtilisateur#connecter(String, String)
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testValiderUtilisateurArbitreCloture() throws Exception {
		this.modeleUtilisateur.connecter("Pcl2023", "$Pcl2023");
	}
	
	/**
	 * Teste la connexion d'un utilisateur arbitre tournoi ouvert
	 * @throws Exception si une erreur se produit pendant la connexion
	 * @see ModeleUtilisateur#connecter(String, String)
	 */
	@Test
	public void testValiderUtilisateurArbitre() throws Exception {
		ModeleTournoiOuverture modeleTournoiOuverture = new ModeleTournoiOuverture();
		DAOTournoi daoTournoi = new DAOTournoiImpl();
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		
		Tournoi tournoiTest = daoTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(
			7, 
			"TournoiTest", 
			Notoriete.NATIONAL, 
			this.getDateCourante() + 3600,
			this.getDateCourante() + 7200, 
			true, 
			"arbitre", 
			"password", 
			tournoiTest.getPoules(),
			tournoiTest.getEquipes(), 
			tournoiTest.getArbitres()
		);
		
		daoTournoi.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getTout().get(i), tournoi);
		}

		modeleTournoiOuverture.ouvrirTournoi(tournoi);
		this.modeleUtilisateur.connecter("arbitre", "password");
	}
	
	/**
	 * Déconnecte l'utilisateur et nettoie les tournois
	 * @throws Exception si une erreur se produit pendant le nettoyage
	 */
	@After
	public void tearsDown() throws Exception {
		if (ModeleUtilisateur.getCompteCourant() != null) {
			this.modeleUtilisateur.deconnecter();
		}
		
		this.nettoyerTournois();
	}

}
