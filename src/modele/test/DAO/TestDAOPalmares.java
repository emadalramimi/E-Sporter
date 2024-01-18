package modele.test.DAO;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOPalmares;
import modele.DAO.DAOPalmaresImpl;
import modele.metier.Palmares;

/**
 * Classe de tests de la classe DAOPalmares.
 * @see modele.DAO.DAOPalmares
 */
public class TestDAOPalmares {
	
	/**
	 * Teste la récupération du classement des équipes.
	 * @throws Exception
	 * @see modele.DAO.DAOPalmares#getClassement()
	 */
	@Test
	public void testGetClassement() throws Exception {
		DAOPalmares daoPalmares = new DAOPalmaresImpl();
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		List<Palmares> listePalmares = new ArrayList<>(Arrays.asList(
				new Palmares(daoEquipe.getParId(2).get(), 5462F),
				new Palmares(daoEquipe.getParId(4).get(), 10575F),
				new Palmares(daoEquipe.getParId(1).get(), 15300F),
				new Palmares(daoEquipe.getParId(3).get(), 6815F)));
		List<Palmares> testPalmares = daoPalmares.getClassement();
		for (int i = 0; i < 4; i++) {
			assertEquals(listePalmares.get(i).getEquipe(),testPalmares.get(i).getEquipe());
			assertEquals(listePalmares.get(i).getPoints(),testPalmares.get(i).getPoints(),0.1);
		}
	}
}
