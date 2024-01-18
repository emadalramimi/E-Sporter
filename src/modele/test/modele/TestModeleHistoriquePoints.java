package modele.test.modele;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleHistoriquePoints;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.metier.Equipe;
import modele.test.SuperTest;

/**
 * Classe de test pour le modèle HistoriquePoints
 * @see ModeleHistoriquePoints
 */
public class TestModeleHistoriquePoints extends SuperTest {
	
	private ModeleHistoriquePoints modele;
	
	/**
	 * Configure l'environnement de test avant chaque cas de test
	 */
	@Before
	public void setUp(){
		modele = new ModeleHistoriquePoints();
	}
	
	/**
	 * Teste la méthode getClassementParEquipe() de la classe ModeleHistoriquePoint
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleHistoriquePoints#getClassementParEquipe()
	 */
	@Test
	public void testGetClassementParEquipe() throws Exception {
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		Map<Equipe, Integer> classement = new HashMap<>();

		// Ajouter les équipes et leurs points correspondants au classement
		classement.put(daoEquipe.getParId(3).get(), 3);
		classement.put(daoEquipe.getParId(1).get(), 1);
		classement.put(daoEquipe.getParId(4).get(), 2);
		classement.put(daoEquipe.getParId(2).get(), 4);
		
		// Vérifier si le classement retourné par le modèle correspond au classement attendu
		assertEquals(modele.getClassementParEquipe(),classement);
	}

}
