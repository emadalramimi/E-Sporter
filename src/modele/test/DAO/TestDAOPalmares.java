package modele.test.DAO;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoiCloture;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOPalmares;
import modele.DAO.DAOPalmaresImpl;
import modele.metier.Equipe;

/**
 * Classe de tests de la classe DAOPalmares.
 * @see modele.DAO.DAOPalmares
 */
public class TestDAOPalmares {
	
	@Before
	public void setUp() throws Exception {
		ModeleTournoiCloture modele = new ModeleTournoiCloture();
		modele.majClassements();
	}

	/**
	 * Teste la récupération du classement des équipes.
	 * @throws Exception
	 * @see modele.DAO.DAOPalmares#getClassement()
	 */
	@Test
	public void testGetClassement() throws Exception {
		DAOPalmares daoPalmares = new DAOPalmaresImpl();
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		List<Equipe> classement = daoPalmares.getClassement().stream()
				.map(palmares -> palmares.getEquipe())
				.collect(Collectors.toList());
		for (int i = 0; i < classement.size(); i++) {
			assertTrue(classement.contains(daoEquipe.getTout().get(i)));
		}
	}
}
