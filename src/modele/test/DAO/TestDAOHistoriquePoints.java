package modele.test.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOHistoriquePoints;
import modele.DAO.DAOHistoriquePointsImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.HistoriquePoints;

/**
 * Classe de tests de la classe DAOHistoriquePoints.
 * @see modele.DAO.DAOHistoriquePoints
 */
public class TestDAOHistoriquePoints {
	
	private DAOHistoriquePoints daoHistoriquePoints;
	private HistoriquePoints historiquePoints;

	@Before
	public void setUp() throws Exception {
		daoHistoriquePoints = new DAOHistoriquePointsImpl();
		DAOTournoi daoTournoi = new DAOTournoiImpl(); 
		historiquePoints = new HistoriquePoints(1200, daoTournoi.getParId(1).get(), 1);
	}
	
	/**
	 * Teste l'ajout d'un historique de points
	 * @throws Exception
	 * @see DAOHistoriquePoints#ajouter(HistoriquePoints)
	 */
	@Test
	public void testAjouter() throws Exception {
		assertTrue(daoHistoriquePoints.ajouter(historiquePoints));
	}
	
	/**
	 * Teste la récupération d'un historique de points d'une équipe par son id
	 * @throws Exception
	 * @see DAOHistoriquePoints#getParId(int)
	 */
	@Test
	public void testGetParEquipe() throws Exception {
		assertEquals(daoHistoriquePoints.getParEquipe(2).size(),6);
	}
	
	/**
	 * Teste la récupération du classement des équipes
	 * @throws Exception
	 * @see DAOHistoriquePoints#getClassementParEquipe()
	 */
	@Test
	public void testGetClassementParEquipe() throws Exception {
		daoHistoriquePoints.getClassementParEquipe();
	}
	
}
