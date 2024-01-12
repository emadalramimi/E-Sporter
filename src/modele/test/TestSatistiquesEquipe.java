package modele.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.EnumPoints;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.StatistiquesEquipe;

public class TestSatistiquesEquipe {

	private StatistiquesEquipe statEquipe;
	private Equipe equipe;

	@Before
	public void setUp() throws IllegalArgumentException {
		List<Joueur> listJoueurs = new ArrayList<>(
				Arrays.asList(new Joueur(1, "Joueur1", 2), new Joueur(2, "Joueur2", 2), new Joueur(3, "Joueur3", 2),
						new Joueur(4, "Joueur4", 2), new Joueur(5, "Joueur5", 2)));
		this.equipe = new Equipe("Equipe1", Pays.CANADA, listJoueurs);
		this.statEquipe = new StatistiquesEquipe(this.equipe, 3, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatchJoueInferieurGagne() {
		this.statEquipe = new StatistiquesEquipe(this.equipe, 3, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatchJoueEtGagneNegatif() {
		this.statEquipe = new StatistiquesEquipe(this.equipe, -3, -4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatchGagneNegatif() {
		this.statEquipe = new StatistiquesEquipe(this.equipe, 3, -4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatchJoueNegatif() {
		this.statEquipe = new StatistiquesEquipe(this.equipe, -3, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMatchEquipeNull() {
		this.statEquipe = new StatistiquesEquipe(null, 4, 3);
	}

	@Test
	public void testMatchJoue0() {
		this.statEquipe = new StatistiquesEquipe(this.equipe, 0, 0);
		assertEquals(this.statEquipe.getRatio(), 0F, 0F);
	}

	@Test
	public void testGetEquipe() {
		assertEquals(this.statEquipe.getEquipe(), this.equipe);
	}

	@Test
	public void testGetNbMatchsJoues() {
		assertEquals(this.statEquipe.getNbMatchsJoues(), 3);
	}

	@Test
	public void testGetNbMatchsGagnes() {
		assertEquals(this.statEquipe.getNbMatchsGagnes(), 1);
	}

	@Test
	public void testGetRatioPourcentage() {
		assertEquals(this.statEquipe.getRatioPourcentage(), String.format("%.2f", (float) 100 / 3) + " %");
	}

	@Test
	public void testGetPoints() {
		float expectedPoints = EnumPoints.POULE_MATCH_VICTOIRE.getPoints() * this.statEquipe.getNbMatchsGagnes()
				+ EnumPoints.POULE_MATCH_PERDU.getPoints()
						* (this.statEquipe.getNbMatchsJoues() - this.statEquipe.getNbMatchsGagnes());

		assertEquals(expectedPoints, this.statEquipe.getPoints(), 0.01);
	}
}
