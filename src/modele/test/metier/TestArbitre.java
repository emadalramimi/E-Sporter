package modele.test.metier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Arbitre;

/**
 * Classe de tests de la classe Arbitre.
 * @see modele.metier.Arbitre
 */
public class TestArbitre {

	private Arbitre arbitre;
	
	@Before
	public void setUp() {
		this.arbitre = new Arbitre(50, "Mortier", "Damien");
	}
	
	@Test
	public void testGetIdArbitre() {
		assertEquals(this.arbitre.getIdArbitre(), 50);
	}
	
	@Test
	public void testSetIdArbitre() {
		this.arbitre.setIdArbitre(51);
		assertEquals(this.arbitre.getIdArbitre(), 51);
	}
	
	@Test
	public void testGetNom() {
		assertEquals(this.arbitre.getNom(), "Mortier");
	}
	
	@Test
	public void testSetNom() {
		this.arbitre.setNom("Pillon");
		assertEquals(this.arbitre.getNom(),"Pillon");
	}
	
	@Test
	public void testGetPrenom() {
		assertEquals(this.arbitre.getPrenom(),"Damien");
	}
	
	@Test
	public void testSetPrenom() {
		this.arbitre.setPrenom("Katherine");
		assertEquals(this.arbitre.getPrenom(),"Katherine");
	}
}
