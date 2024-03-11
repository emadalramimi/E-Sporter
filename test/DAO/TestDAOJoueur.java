package DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOJoueur;
import modele.DAO.DAOJoueurImpl;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;

/**
 * Classe de tests de la classe DAOEquipe.
 * @see modele.DAO.DAOEquipe
 */
public class TestDAOJoueur {

	private DAOJoueur daoJoueur;

	private Joueur joueur;
	private Equipe equipe;

	private List<Joueur> joueurs;

	@Before
	public void setUp() throws Exception {
		this.daoJoueur = new DAOJoueurImpl();
		this.joueur = new Joueur(54, "Kévin", 1);
		this.joueurs = new ArrayList<>(Arrays.asList(new Joueur(38, "joueur", 6), new Joueur(39, "joueur", 6),
				new Joueur(40, "joueur", 6), new Joueur(41, "joueur", 6), new Joueur(42, "joueur", 6)));
		this.equipe = new Equipe("Equipe", Pays.CANADA, this.joueurs);
	}

	/**
	 * Teste la récupération de tous les joueurs.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#getTout()
	 */
	@Test
	public void testGetTout() throws Exception {
		assertNotNull(this.daoJoueur.getTout());
		List<Joueur> listTest = this.daoJoueur.getTout();
		assertEquals(listTest.size(), this.daoJoueur.getTout().size());
		List<Joueur> listJoueurs = new ArrayList<>();

		for (int i = 0; i < listTest.size(); i++) {
			assertNotNull(listTest.get(i));
			listJoueurs.add(listTest.get(i));
		}

		assertEquals(listTest.size(), listJoueurs.size());
		for (int i = 0; i < listTest.size(); i++) {
			assertTrue(listTest.get(i).equals(listJoueurs.get(i)));
		}
	}

	/**
	 * Teste la récupération d'un joueur par son id.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#getParId(int)
	 */
	@Test
	public void testGetParId() throws Exception {
		this.daoJoueur.ajouter(this.joueur);
		assertNotNull(this.joueur);
		Optional<Joueur> retrievedJoueur = this.daoJoueur.getParId(this.joueur.getIdJoueur());
		assertEquals(this.joueur, retrievedJoueur.get());
	}

	/**
	 * Teste l'ajout d'un joueur.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#ajouter(modele.metier.Joueur)
	 */
	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(this.daoJoueur.ajouter(this.joueur));
	}

	/**
	 * Teste la modification d'un joueur.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#modifier(modele.metier.Joueur)
	 */
	@Test
	public void testModifierTrue() throws Exception {
		this.daoJoueur.ajouter(this.joueur);
		assertTrue(this.daoJoueur.modifier(new Joueur(50, "joueurModif", 1)));
	}

	/**
	 * Teste la suppression des joueurs d'une équipe.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#supprimerJoueursEquipe(modele.metier.Joueur)
	 */
	@Test
	public void testSupprimerJoueursParEquipe() throws Exception {
		assertTrue(this.daoJoueur.supprimerJoueursEquipe(this.equipe.getIdEquipe()));
	}
	
	/**
	 * Teste la levée d'exception lors de la suppression d'un joueur.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#supprimer(modele.metier.Joueur)
	 */
	@Test (expected = UnsupportedOperationException.class)
	public void testSupprimer() throws Exception {
		daoJoueur.supprimer(joueur);
	}

	/**
	 * Teste la récupération de la liste des joueurs d'une équipe.
	 * @throws Exception
	 * @see modele.DAO.DAOJoueur#getListeJoueursParId(int)
	 */
	@Test
	public void testGetListeJoueursParId() throws Exception {
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		daoEquipe.ajouter(this.equipe);
		assertEquals(5, this.daoJoueur.getListeJoueursParId(this.equipe.getIdEquipe()).size());
		assertEquals(this.daoJoueur.getListeJoueursParId(this.equipe.getIdEquipe()).toString(), this.joueurs.toString());
	}

	@After
	public void tearsDown() throws Exception {
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for (Equipe equipe : daoEquipe.getTout()) {
			if (!idAGarder.contains(equipe.getIdEquipe())) {
				this.daoJoueur.supprimerJoueursEquipe(6);
				daoEquipe.supprimer(equipe);
			}
		}
	}
}