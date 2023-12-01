package modele.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Joueur;


public class TestJoueur {
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	@Before
    public void setUp() {
		joueur1 = new Joueur(1, "yay", 2);
		joueur2 = new Joueur(2, "s0m", 2);
	}
	
	@Test
	public void testGetIdJoueur() {
		assertEquals(joueur1.getIdJoueur(), 1);
		assertEquals(joueur2.getIdJoueur(), 2);
		assertNotEquals(joueur1.getIdJoueur(), joueur2.getIdJoueur());
	}
	
	@Test
	public void testGetPseudo() {
		assertEquals(joueur1.getPseudo(), "yay");
		assertEquals(joueur2.getPseudo(), "s0m");
		assertNotEquals(joueur1.getPseudo(), joueur2.getPseudo());
	}
	
	@Test
	public void testGetIdEquipe() {
		assertEquals(joueur1.getIdEquipe(), 2);
		assertEquals(joueur2.getIdEquipe(), 2);
		assertEquals(joueur1.getIdEquipe(), joueur2.getIdEquipe());
	}
	
	@Test
	}
	
}
