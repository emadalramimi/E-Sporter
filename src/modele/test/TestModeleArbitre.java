package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleArbitre;
import modele.ModeleTournoi;
import modele.metier.Arbitre;
import modele.metier.Tournoi;

public class TestModeleArbitre {
	private ModeleArbitre modele;
	private ModeleTournoi modeleTournoi;
	private Optional<Tournoi> tournoi;
	
	@Before
	public void setUp() throws Exception {
		modele = new ModeleArbitre();
		modeleTournoi = new ModeleTournoi();
		tournoi = modeleTournoi.getParId(1);
    }
	
	@Test
    public void testGetTout() throws Exception {
        List<Arbitre> resultat = modele.getTout();
        assertNotNull(resultat);
        assertFalse(resultat.isEmpty());
		assertNotNull(modele.getTout());
	    Arbitre arbitreBDD = modele.getParId(1).orElse(null);
	    assertNotNull(arbitreBDD);
	    assertEquals(arbitreBDD, resultat.get(0));
    }
	
	@Test
	public void testGetParId() throws Exception {
	    Arbitre arbitreDBB = modele.getParId(1).orElse(null);
	    assertNotNull(arbitreDBB);
	    Optional<Arbitre> resultat = modele.getParId(arbitreDBB.getIdArbitre());
	    assertTrue(resultat.isPresent());
		assertNotNull(modele.getParId(1).orElse(null));
	    assertEquals(arbitreDBB, resultat.get());
	}
	
	@Test
    public void testGetArbitresTournoi() throws Exception {
		List<Arbitre> resultat = modele.getArbitresTournoi(1);
		assertNotNull(resultat);
		assertFalse(resultat.isEmpty());
		assertEquals(resultat, tournoi.get().getArbitres());
    }
	
	@Test
    public void testGetTableauArbitres() throws Exception {
		List<Arbitre> arbitresNonEligibles = tournoi.get().getArbitres();
	    Arbitre[] resultat = modele.getTableauArbitres(arbitresNonEligibles);
	    for (Arbitre arbitre : arbitresNonEligibles) {
	        assertFalse(Arrays.asList(resultat).contains(arbitre));
	    }
	    
	    assertNotNull(resultat);
    }
}
