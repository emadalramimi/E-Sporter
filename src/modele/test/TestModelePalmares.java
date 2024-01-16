package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.ModelePalmares;
import modele.DAO.DAOPalmares;
import modele.DAO.DAOPalmaresImpl;

public class TestModelePalmares {

	private ModelePalmares modele;
	
	@Test
	public void testGetParNom() throws Exception {
		modele = new ModelePalmares();
		DAOPalmares daoPalmares = new DAOPalmaresImpl();
		assertEquals(daoPalmares.getClassement().get(0).getEquipe(), modele.getParNom("DCG Academy").get(0).getEquipe());
		assertEquals(daoPalmares.getClassement().get(0).getPoints(), modele.getParNom("DCG Academy").get(0).getPoints(),0.01);
	}
}
