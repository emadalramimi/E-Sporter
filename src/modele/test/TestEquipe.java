package modele.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Equipe;
import modele.metier.Joueur;

public class TestEquipe {
	
	private Equipe equipeSansJoueurs;
	private Equipe equipeAvecJoueurs;
	private List<Joueur> listJoueurs;
	
	@Before
    public void setUp() {
		equipeSansJoueurs = new Equipe(1, "Equipe1", "France", 1, 1, "Saison 2023");
		listJoueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
		equipeAvecJoueurs = new Equipe(2, "Equipe2", "Canada", 2, 2, "Saison 2023", listJoueurs);
    }
	
	@Test
	public void testGetIdEquipe() {
		assertEquals(equipeSansJoueurs.getIdEquipe(), 1);
		assertEquals(equipeAvecJoueurs.getIdEquipe(), 2);
		assertNotEquals(equipeAvecJoueurs.getIdEquipe(), equipeSansJoueurs.getIdEquipe());
	}
	
	@Test
	public void testGetNom() {
		assertEquals(equipeSansJoueurs.getNom(), "Equipe1");
		assertEquals(equipeAvecJoueurs.getNom(), "Equipe2");
		assertNotEquals(equipeAvecJoueurs.getNom(), equipeSansJoueurs.getNom());
	}
	
	@Test
	public void testGetPays() {
		assertEquals(equipeSansJoueurs.getPays(), "France");
		assertEquals(equipeAvecJoueurs.getPays(), "Canada");
		assertNotEquals(equipeAvecJoueurs.getPays(), equipeSansJoueurs.getPays());
	}
	
	@Test
	public void testGetClassement() {
		assertEquals(equipeSansJoueurs.getClassement(), 1);
		assertEquals(equipeAvecJoueurs.getClassement(), 2);
		assertNotEquals(equipeAvecJoueurs.getClassement(), equipeSansJoueurs.getClassement());
	}
	
	@Test
	public void testGetWorldRanking() {
		assertEquals(equipeSansJoueurs.getWorldRanking(), 1);
		assertEquals(equipeAvecJoueurs.getWorldRanking(), 2);
		assertNotEquals(equipeAvecJoueurs.getWorldRanking(), equipeSansJoueurs.getWorldRanking());
	}
	
	@Test
	public void testGetSaison() {
		assertEquals(equipeSansJoueurs.getSaison(), "Saison 2023");
		assertEquals(equipeAvecJoueurs.getSaison(), "Saison 2023");
		assertEquals(equipeAvecJoueurs.getSaison(), equipeSansJoueurs.getSaison());
	}
	
	@Test
	public void testGetJoueurs() {
		assertEquals(equipeAvecJoueurs.getJoueurs(), listJoueurs);
	}
	
	}
	
}
