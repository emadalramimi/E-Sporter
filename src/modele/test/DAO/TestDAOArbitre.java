package modele.test.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOArbitre;
import modele.DAO.DAOArbitreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Arbitre;
import modele.metier.Tournoi;

/**
 * Classe de tests de la classe DAOArbitre.
 * @see modele.DAO.DAOArbitre
 */
public class TestDAOArbitre {
	private DAOArbitre daoArbitre;
	private DAOTournoi daoTournoi;
	private Optional<Tournoi> tournoi;

	@Before
	public void setUp() throws Exception {
		this.daoArbitre = new DAOArbitreImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.tournoi = this.daoTournoi.getParId(1);
	}

	/**
	 * Teste la récupération de tous les arbitres.
	 * @throws Exception
	 * @see DAOArbitre#getTout()
	 */
	@Test
	public void testGetTout() throws Exception {
		List<Arbitre> resultat = this.daoArbitre.getTout();

		assertNotNull(resultat);
		assertFalse(resultat.isEmpty());
		assertNotNull(this.daoArbitre.getTout());

		Arbitre arbitreBDD = this.daoArbitre.getParId(1).orElse(null);
		
		assertNotNull(arbitreBDD);
		assertEquals(arbitreBDD, resultat.get(0));
	}

	/**
	 * Teste la récupération d'un arbitre par son id.
	 * @throws Exception
	 * @see DAOArbitre#getParId(int)
	 */
	@Test
	public void testGetParId() throws Exception {
		Arbitre arbitreDBB = this.daoArbitre.getParId(1).orElse(null);
		assertNotNull(arbitreDBB);
		Optional<Arbitre> resultat = this.daoArbitre.getParId(arbitreDBB.getIdArbitre());
		assertTrue(resultat.isPresent());
		assertNotNull(this.daoArbitre.getParId(1).orElse(null));
		assertEquals(arbitreDBB, resultat.get());
	}

	/**
	 * Teste la levée d'exception lors de l'ajout d'un arbitre.
	 * @throws Exception
	 * @see DAOArbitre#ajouter(Arbitre)
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testAjouter() throws Exception {
		this.daoArbitre.ajouter(new Arbitre(5, "Willem", "Leboss"));
	}

	/**
	 * Teste la levée d'exception lors de la modification d'un arbitre.
	 * @throws Exception
	 * @see DAOArbitre#modifier(Arbitre)
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testModifier() throws Exception {
		this.daoArbitre.modifier(new Arbitre(1, "Willem", "Leboss"));
	}

	/**
	 * Teste la levée d'exception lors de la suppression d'un arbitre.
	 * @throws Exception
	 * @see DAOArbitre#supprimer(Arbitre)
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testSupprimer() throws Exception {
		this.daoArbitre.supprimer(this.daoArbitre.getParId(1).orElse(null));
	}

	/**
	 * Teste la récupération des arbitres d'un tournoi.
	 * @throws Exception
	 * @see DAOArbitre#getArbitresTournoi(int)
	 */
	@Test
	public void testGetArbitresTournoi() throws Exception {
		List<Arbitre> resultat = this.daoArbitre.getArbitresTournoi(1);
		assertNotNull(resultat);
		assertFalse(resultat.isEmpty());
		assertEquals(resultat, this.tournoi.get().getArbitres());
	}

	/**
	 * Teste la récupération des arbitres non affectés à un tournoi.
	 * @throws Exception
	 * @see DAOArbitre#getTableauArbitres(List)
	 */
	@Test
	public void testGetTableauArbitres() throws Exception {
		List<Arbitre> arbitresNonEligibles = this.tournoi.get().getArbitres();
		Arbitre[] resultat = this.daoArbitre.getTableauArbitres(arbitresNonEligibles);

		for (Arbitre arbitre : arbitresNonEligibles) {
			assertFalse(Arrays.asList(resultat).contains(arbitre));
		}

		assertNotNull(resultat);
	}
}
