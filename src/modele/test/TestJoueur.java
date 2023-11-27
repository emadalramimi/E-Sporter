package modele.test;

import static org.junit.Assert.*;
import org.junit.Test;

import modele.Joueur;

public class TestJoueur {
	@Test
	void testGetters() {
		Joueur j1 = new Joueur(1, "yay", 3);
		Joueur j2 = new Joueur(2, "s0m", 3);
		assertNotEquals(j1.getIdJoueur(),j2.getIdJoueur());
		assertNotEquals(j1.getPseudo(),j2.getPseudo());
		assertEquals(j1.getPseudo(),"yay");
		assertEquals(j1.getIdEquipe(), j2.getIdEquipe());
	}
	@Test
	void testSetters() {
		Joueur j1 = new Joueur(2, "yay", 3);
		Joueur j2 = new Joueur(2, "s0m", 3);
		j1.setIdJoueur(3);
		j2.setPseudo("yayy");
		j2.setIdEquipe(4);
		assertNotEquals(j1.getIdJoueur(),j2.getIdJoueur());
		assertNotEquals(j2.getPseudo(),"s0m");
		assertEquals(j2.getPseudo(),"yayy");
		assertEquals(j2.getIdEquipe(), 4);
		assertNotEquals(j2.getIdEquipe(), j1.getIdEquipe());
	}
}
