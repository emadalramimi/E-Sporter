package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Pays;

public class TestPays {

	Pays pays;

	@Before
	public void setUp() {
		this.pays = Pays.FRANCE;
	}

	@Test
	public void testGetNomPays() {
		assertEquals(this.pays.getNomPays(), "France");
	}

	@Test
	public void testGetCodePays() {
		assertEquals(this.pays.getCodePays(), "fr");
	}

	@Test
	public void testGetDrapeauPays() {
		assertEquals(this.pays.getDrapeauPays().toString(), Pays.class.getResource("/images/pays/fr.png").toString());
	}

	@Test
	public void testGetTout() {
		String[] paysGetTout = Pays.getTout();
		Pays[] toutPays = Pays.values();
		for (int i = 0; i < toutPays.length; i++) {
			assertEquals(toutPays[i].getNomPays(), paysGetTout[i]);
		}
	}

	@Test
	public void testValueOfNom() {
		assertEquals(Pays.valueOfNom("Algérie"), Pays.ALGERIE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValueOfNomInexistant() {
		Pays.valueOfNom("Disneyland");
	}
}
