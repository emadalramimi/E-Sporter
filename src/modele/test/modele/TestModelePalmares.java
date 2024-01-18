package modele.test.modele;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.ModelePalmares;
import modele.DAO.DAOPalmares;
import modele.DAO.DAOPalmaresImpl;
import modele.test.SuperTest;

/**
 * Classe de test pour le modèle Palmares
 * @see ModelePalmares
 */
public class TestModelePalmares extends SuperTest {

	private ModelePalmares modele;
	
	/**
	 * Teste la méthode getClassement() de la classe ModelePalmares
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModelePalmares#getClassement()
	 */
	@Test
	public void testGetClassement() throws Exception {
		modele = new ModelePalmares();
		DAOPalmares daoPalmares = new DAOPalmaresImpl();
		assertEquals(daoPalmares.getClassement().get(0).getEquipe(), modele.getParNom("CFO").get(0).getEquipe());
	}

}
