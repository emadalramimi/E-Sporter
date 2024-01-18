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

public class TestModeleHistoriquePoints {
	
	private ModeleHistoriquePoints modele;
	
	@Before
	public void setUp(){
		modele = new ModeleHistoriquePoints();
	}
	
	@Test
	public void testGetClassementParEquipe() throws Exception {
		DAOEquipe daoEquipe = new DAOEquipeImpl();
		Map<Equipe, Integer> classement = new HashMap<>();
        classement.put(daoEquipe.getParId(3).get(), 3);
        classement.put(daoEquipe.getParId(1).get(), 1);
        classement.put(daoEquipe.getParId(4).get(), 2);
        classement.put(daoEquipe.getParId(2).get(), 4);
		assertEquals(modele.getClassementParEquipe(),classement);
	}
}
