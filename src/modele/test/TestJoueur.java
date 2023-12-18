package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Joueur;


public class TestJoueur {
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	@Before
    public void setUp() {
		joueur1 = new Joueur(1, "joueur1", 2);
		joueur2 = new Joueur("joueur2");
	}
	
	@Test
	public void testGetIdJoueur() {
		assertEquals(joueur1.getIdJoueur(), 1);
	}
	
	@Test
	public void testSetIdJoueur() {
		joueur1.setIdJoueur(3);
		assertEquals(joueur1.getIdJoueur(), 3);
	}
	
	@Test
	public void testGetPseudo() {
		assertEquals(joueur1.getPseudo(), "joueur1");
		assertEquals(joueur2.getPseudo(), "joueur2");
	}
	
	@Test
	public void testSetPseudo() {
		joueur1.setPseudo("tarik");
		assertEquals(joueur1.getPseudo(), "tarik");
	}
	
	@Test
	public void testGetIdEquipe() {
		assertEquals(joueur1.getIdEquipe(), 2);
	}
	
	@Test
	public void testSetIdEquipe() {
		joueur1.setIdEquipe(3);
		assertEquals(joueur1.getIdEquipe(), 3);
	}
	
}
