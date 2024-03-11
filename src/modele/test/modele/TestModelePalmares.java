package modele.test.modele;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.ModelePalmares;
import modele.DAO.DAOEquipeImpl;
import modele.test.SuperTest;

/**
 * Classe de test pour le modèle Palmares
 * @see ModelePalmares
 */
public class TestModelePalmares extends SuperTest {

	private ModelePalmares modele;
	private DAOEquipeImpl daoEquipe;
	
	/**
	 * Teste la méthode getClassement() de la classe ModelePalmares
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModelePalmares#getClassement()
	 */
	@Test
	public void testGetClassement() throws Exception {
		modele = new ModelePalmares();
		daoEquipe = new DAOEquipeImpl();
		assertEquals(modele.getParNom("CFO Academy").get(0).getEquipe(), 
		daoEquipe.getTout().stream().filter(equipe -> equipe.getNom().equals("CFO Academy")).findFirst().get());
	}

}
