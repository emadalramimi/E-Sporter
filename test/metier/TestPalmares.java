package metier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Equipe;
import modele.metier.Palmares;
import modele.metier.Pays;

/**
 * Classe de tests de la classe Palmares.
 * @see modele.metier.Palmares
 */
public class TestPalmares {

	private Equipe equipe;

	@Before
	public void setUp() {
		this.equipe = new Equipe("Equipe", Pays.CANADA, new ArrayList<>());
	}

	@Test
	public void testConstructorValidInput() {
		float points = 10.0f;

		Palmares palmares = new Palmares(this.equipe, points);

		assertEquals(this.equipe, palmares.getEquipe());
		assertEquals(points, palmares.getPoints(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullEquipe() {
		new Palmares(null, 10.0f);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNegativePoints() {
		new Palmares(this.equipe, -10.0f);
	}

	@Test
	public void testGetEquipe() {
		Palmares palmares = new Palmares(this.equipe, 10.0f);

		assertEquals(this.equipe, palmares.getEquipe());
	}

	@Test
	public void testGetPoints() {
		float points = 10.0f;
		Palmares palmares = new Palmares(this.equipe, points);

		assertEquals(points, palmares.getPoints(), 0.0);
	}
}
