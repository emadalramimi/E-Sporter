package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Pays;

public class testPays {

	Pays pays;
	
	@Before
	public void setUp() {
		pays = Pays.FRANCE;
	}
	
	@Test
	public void testGetNomPays() {
		assertEquals(pays.getNomPays(),"France");
	}
	
	@Test
	public void testGetCodePays() {
		assertEquals(pays.getCodePays(), "fr");
	}
	
	@Test
	public void testGetDrapeauPays() {
		assertEquals(pays.getDrapeauPays().toString(), Pays.class.getResource("/images/pays/fr.png").toString());
	}
	
	@Test
	public void testGetTout() {
		String[] paysGetTout = pays.getTout();
		Pays[] toutPays = Pays.values();
		for (int i = 0; i < toutPays.length; i++) {
            assertEquals(toutPays[i].getNomPays(), paysGetTout[i]);
        }
	}
	
	@Test
	public void testValueOfNom() {
		assertEquals(pays.valueOfNom("Algérie"),Pays.ALGERIE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testValueOfNomInexistant() {
		pays.valueOfNom("Disneyland");
	}
}
