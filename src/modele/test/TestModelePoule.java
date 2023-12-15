package modele.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModelePoule;
import modele.metier.Poule;

public class TestModelePoule {
	
	private ModelePoule modele;
	private Poule poule;
	
	@Before
	public void setUp() throws Exception {
		modele = new ModelePoule();
		poule = new Poule(10, false, false, 1);
		}
	
	@Test
	public void testGetTout() throws Exception {
	    assertNotNull(modele.getTout());
	    assertEquals(modele.getTout().size(), 2);
	    List<Poule> poules = modele.getTout();
	    assertEquals(modele.getTout().size(), poules.size());
	}
	
	@Test
	public void testGetParId() throws Exception {
		assertTrue(modele.getParId(1).isPresent());
		assertNotNull(modele.getParId(1).orElse(null));
	}
	
	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(modele.ajouter(poule));
	}
	
	@Test
	public void testSupprimerTrue() throws Exception {
		modele.ajouter(poule);
		assertTrue(modele.supprimer(poule));
	}
	
	@Test
	public void testGetNextValId() {
	    int nextVal = modele.getNextValId();
	    assertTrue(nextVal != 0);
	}
	
	@After
    public void tearsDown() throws Exception {
        List<Integer> idAGarder = Arrays.asList(1, 2);
        modele.getTout().stream()
                .filter(poule -> !idAGarder.contains(poule.getIdPoule()))
                .forEach(poule -> {
                    try {
                        modele.supprimer(poule);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
	
}