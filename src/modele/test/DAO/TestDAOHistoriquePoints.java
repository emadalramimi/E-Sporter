package modele.test.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOHistoriquePoints;
import modele.DAO.DAOHistoriquePointsImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.HistoriquePoints;

public class TestDAOHistoriquePoints {
	
	private DAOHistoriquePoints daoHistoriquePoints;
	private HistoriquePoints historiquePoints;

	@Before
	public void setUp() throws Exception {
		daoHistoriquePoints = new DAOHistoriquePointsImpl();
		DAOTournoi daoTournoi = new DAOTournoiImpl(); 
		historiquePoints = new HistoriquePoints(1200, daoTournoi.getParId(1).get(), 1);
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testGetTout()  throws Exception {
		daoHistoriquePoints.getTout();
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void getParId()  throws Exception {
		daoHistoriquePoints.getParId();
	}
	
	@Test
	public void testAjouter() throws Exception {
		assertTrue(daoHistoriquePoints.ajouter(historiquePoints));
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void getModifier()  throws Exception {
		daoHistoriquePoints.modifier(historiquePoints);
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void getSupprimer()  throws Exception {
		daoHistoriquePoints.supprimer(historiquePoints);
	}
	
	@Test
	public void testGetParEquipe() throws Exception {
		assertEquals(daoHistoriquePoints.getParEquipe(2).size(),6);
	}
	
	@Test
	public void testGetClassementParEquipe() throws Exception {
		daoHistoriquePoints.getClassementParEquipe();
	}
	
	@After
	public void tearsDown() {
	}
}
