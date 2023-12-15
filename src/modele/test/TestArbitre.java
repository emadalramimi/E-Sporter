package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Arbitre;

public class TestArbitre {

	private Arbitre arbitre;
	
	@Before
	public void setUp() {
		arbitre = new Arbitre(50, "Mortier", "Damien");
	}
	
	@Test
	public void testGetIdArbitre() {
		assertEquals(arbitre.getIdArbitre(), 50);
	}
	
	@Test
	public void testSetIdArbitre() {
		arbitre.setIdArbitre(51);
		assertEquals(arbitre.getIdArbitre(), 51);
	}
	
	@Test
	public void testGetNom() {
		assertEquals(arbitre.getNom(), "Mortier");
	}
	
	@Test
	public void testSetNom() {
		arbitre.setNom("Pillon");
		assertEquals(arbitre.getNom(),"Pillon");
	}
	
	@Test
	public void testGetPrenom() {
		assertEquals(arbitre.getPrenom(),"Damien");
	}
	
	@Test
	public void testSetPrenom() {
		arbitre.setPrenom("Katherine");
		assertEquals(arbitre.getPrenom(),"Katherine");
	}
}
