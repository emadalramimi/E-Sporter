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
		joueur1 = new Joueur(1, "joueur1", 2);
		joueur2 = new Joueur(2, "joueur2", 2);
	}
	
	@Test
	public void testGetIdJoueur() {
		assertEquals(joueur1.getIdJoueur(), 1);
		assertEquals(joueur2.getIdJoueur(), 2);
		assertNotEquals(joueur1.getIdJoueur(), joueur2.getIdJoueur());
	}
	
	@Test
	public void testSetIdJoueur() {
		joueur1.setIdJoueur(3);
		joueur2.setIdJoueur(4);
		assertEquals(joueur1.getIdJoueur(), 3);
		assertEquals(joueur2.getIdJoueur(), 4);
		assertNotEquals(joueur1.getIdJoueur(), joueur2.getIdJoueur());
	}
	
	@Test
	public void testGetPseudo() {
		assertEquals(joueur1.getPseudo(), "joueur1");
		assertEquals(joueur2.getPseudo(), "joueur2");
		assertNotEquals(joueur1.getPseudo(), joueur2.getPseudo());
	}
	
	@Test
	public void testSetPseudo() {
		joueur1.setPseudo("tarik");
		joueur2.setPseudo("jollz");
		assertEquals(joueur1.getPseudo(), "tarik");
		assertEquals(joueur2.getPseudo(), "jollz");
		assertNotEquals(joueur1.getPseudo(), joueur2.getPseudo());
	}
	
	@Test
	public void testGetIdEquipe() {
		assertEquals(joueur1.getIdEquipe(), 2);
		assertEquals(joueur2.getIdEquipe(), 2);
		assertEquals(joueur1.getIdEquipe(), joueur2.getIdEquipe());
	}
	
	@Test
	public void testSetIdEquipe() {
		joueur1.setIdEquipe(3);
		joueur2.setIdEquipe(4);
		assertEquals(joueur1.getIdEquipe(), 3);
		assertEquals(joueur2.getIdEquipe(), 4);
		assertNotEquals(joueur1.getIdEquipe(), joueur2.getIdEquipe());
	}
	
}
