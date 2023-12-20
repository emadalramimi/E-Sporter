package modele.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.StatistiquesEquipe;

public class TestSatistiquesEquipe {
	
	private StatistiquesEquipe statEquipe;
	private Equipe equipe;
	
	@Before
	public void setUp() throws IllegalArgumentException {
		List<Joueur> listJoueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
		equipe = new Equipe("Equipe1", Pays.CANADA, listJoueurs);
		statEquipe = new StatistiquesEquipe(equipe, 3, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMatchJoueInferieurGagne() {
		statEquipe = new StatistiquesEquipe(equipe, 3, 4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMatchJoueEtGagneNegatif() {
		statEquipe = new StatistiquesEquipe(equipe, -3, -4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMatchGagneNegatif() {
		statEquipe = new StatistiquesEquipe(equipe, 3, -4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMatchJoueNegatif() {
		statEquipe = new StatistiquesEquipe(equipe, -3, 4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMatchEquipeNull() {
		statEquipe = new StatistiquesEquipe(null, 4, 3);
	}
	
	@Test
	public void testMatchJoue0() {
		statEquipe = new StatistiquesEquipe(equipe, 0, 0);
		assertEquals(statEquipe.getRatio(), 0F,0F);
	}
	
	@Test
	public void testGetEquipe() {
		assertEquals(statEquipe.getEquipe(),equipe);
	}

	@Test
	public void testGetNbMatchsJoues() {
		assertEquals(statEquipe.getNbMatchsJoues(), 3);
	}
	
	@Test
	public void testGetNbMatchsGagnes() {
		assertEquals(statEquipe.getNbMatchsGagnes(), 1);
	}
	
	@Test
	public void testGetRatioPourcentage() {
		assertEquals(statEquipe.getRatioPourcentage(), String.format("%.2f", (float) 100/3) + " %");
	}
}
