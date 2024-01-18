package modele.test.metier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Joueur;

/**
 * Classe de tests de la classe Joueur.
 * @see modele.metier.Joueur
 */
public class TestJoueur {

	private Joueur joueur1;
	private Joueur joueur2;

	@Before
	public void setUp() {
		this.joueur1 = new Joueur(1, "joueur1", 2);
		this.joueur2 = new Joueur("joueur2");
	}

	@Test
	public void testGetIdJoueur() {
		assertEquals(this.joueur1.getIdJoueur(), 1);
	}

	@Test
	public void testSetIdJoueur() {
		this.joueur1.setIdJoueur(3);
		assertEquals(this.joueur1.getIdJoueur(), 3);
	}

	@Test
	public void testGetPseudo() {
		assertEquals(this.joueur1.getPseudo(), "joueur1");
		assertEquals(this.joueur2.getPseudo(), "joueur2");
	}

	@Test
	public void testSetPseudo() {
		this.joueur1.setPseudo("tarik");
		assertEquals(this.joueur1.getPseudo(), "tarik");
	}

	@Test
	public void testGetIdEquipe() {
		assertEquals(this.joueur1.getIdEquipe(), 2);
	}

	@Test
	public void testSetIdEquipe() {
		this.joueur1.setIdEquipe(3);
		assertEquals(this.joueur1.getIdEquipe(), 3);
	}

}
