package modele.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoi;
import modele.ModeleTournoiOuverture;
import modele.ModeleUtilisateur;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Classe de test pour la classe modèle ModeleUtilisateur.
 * @see ModeleUtilisateur
 */
public class TestModeleUtilisateur extends TestModele {

	private ModeleUtilisateur modeleUtilisateur;

	@Before
	public void setUp() {
		this.modeleUtilisateur = new ModeleUtilisateur();
	}
	
	/**
	 * Teste le chiffrement du mot de passe d'un utilisateur
	 */
	@Test
	public void testChiffrerMotDePasse() {
		String motDePasse = "mdp";
		assertNotEquals(ModeleUtilisateur.chiffrerMotDePasse(motDePasse), motDePasse);
	}

	/**
	 * Test la connexion multiple d'un même utilisateur
	 * @throws Exception lors de la deuxième exception
	 */
	@Test(expected = IllegalStateException.class)
	public void testConnexionDejaConnecte() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleUtilisateur.connecter("admin", "mdp");
	}

	/**
	 * Teste la connection avec un login invalide
	 * @throws IdentifiantOuMdpIncorrectsException
	 */
	@Test(expected = IdentifiantOuMdpIncorrectsException.class)
	public void testConnexionIdentifiantInvalide() throws IdentifiantOuMdpIncorrectsException {
		this.modeleUtilisateur.connecter("fauxadmin", "mdp");
	}

	/**
	 * Teste la connection avec un mot de passe invalide
	 * @throws IdentifiantOuMdpIncorrectsException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConnexionMotDePasseInvalide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "*admin");
	}

	/*
	 * Test si la connexion fonctionne lorsqu'on rentre un identifiant et mot de
	 * passe valide
	 */
	@Test
	public void testConnexionValide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		assertTrue(this.modeleUtilisateur != null);
	}

	/*
	 * Test si le compte courant de l'admin est bien null lorsqu'il est déconnecté
	 */
	@Test
	public void testDeconnexionValide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleUtilisateur.deconnecter();
		assertTrue(ModeleUtilisateur.getCompteCourant() == null);
	}

	/*
	 * Test l'erreur IllegalArgumentException lorsqu'on essaie de se déconnecter
	 * alors que l'on n'était pas connecté
	 */
	@Test(expected = IllegalStateException.class)
	public void testDeconnexionInvalide() throws IllegalStateException {
		this.modeleUtilisateur.deconnecter();
	}

	/*
	 * Test du getter getCompteCourant
	 */
	@Test
	public void testGetCompteCourant() {
		assertEquals(ModeleUtilisateur.getCompteCourant(), null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testValiderUtilisateurArbitreCloture() throws IllegalArgumentException {
		this.modeleUtilisateur.connecter("Pcl2023", "$Pcl2023");
	}
	
	@Test
	public void testValiderUtilisateurArbitre() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
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
	
	@After
	public void tearsDown() throws Exception {
		if (ModeleUtilisateur.getCompteCourant() != null) {
			this.modeleUtilisateur.deconnecter();
		}
		
		this.nettoyerTournois();
	}

}
