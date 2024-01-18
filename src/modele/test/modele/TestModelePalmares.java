package modele.test.modele;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.ModelePalmares;
import modele.DAO.DAOPalmares;
import modele.DAO.DAOPalmaresImpl;

/**
 * Classe de test pour le modèle Palmares
 * @see ModelePalmares
 */
public class TestModelePalmares extends TestModele {

	private ModelePalmares modele;
	
	/**
	 * Teste la méthode getClassement() de la classe ModelePalmares
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModelePalmares#getClassement()
	 */
	@Test
	public void testGetParNom() throws Exception {
		modele = new ModelePalmares();
		DAOPalmares daoPalmares = new DAOPalmaresImpl();
		assertEquals(daoPalmares.getClassement().get(0).getEquipe(), modele.getParNom("DCG Academy").get(0).getEquipe());
		assertEquals(daoPalmares.getClassement().get(0).getPoints(), modele.getParNom("DCG Academy").get(0).getPoints(),0.01);
	}

}
