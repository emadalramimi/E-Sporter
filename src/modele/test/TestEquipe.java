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
		equipeSansJoueurs = new Equipe(1, "Equipe1", "France", 1, 1, "2023", listJoueurs);
		listJoueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
		equipeAvecJoueurs = new Equipe("Equipe2", "Canada",listJoueurs);
    }
	
	@Test
	public void testGetIdEquipe() {
		assertEquals(equipeSansJoueurs.getIdEquipe(), 1);
	}
	
	@Test
	public void testSetIdEquipe() {
		equipeSansJoueurs.setIdEquipe(3);
		equipeAvecJoueurs.setIdEquipe(4);
		assertEquals(equipeSansJoueurs.getIdEquipe(), 3);
		assertEquals(equipeAvecJoueurs.getIdEquipe(), 4);
		assertNotEquals(equipeAvecJoueurs.getIdEquipe(), equipeSansJoueurs.getIdEquipe());
	}
	
	@Test
	public void testGetNom() {
		assertEquals(equipeSansJoueurs.getNom(), "Equipe1");
		assertEquals(equipeAvecJoueurs.getNom(), "Equipe2");
		assertNotEquals(equipeAvecJoueurs.getNom(), equipeSansJoueurs.getNom());
	}
	
	@Test
	public void testSetNom() {
		equipeSansJoueurs.setNom("Equipe3");
		equipeAvecJoueurs.setNom("Equipe4");
		assertEquals(equipeSansJoueurs.getNom(), "Equipe3");
		assertEquals(equipeAvecJoueurs.getNom(), "Equipe4");
		assertNotEquals(equipeAvecJoueurs.getNom(), equipeSansJoueurs.getNom());
	}
	
	@Test
	public void testGetPays() {
		assertEquals(equipeSansJoueurs.getPays(), "France");
		assertEquals(equipeAvecJoueurs.getPays(), "Canada");
		assertNotEquals(equipeAvecJoueurs.getPays(), equipeSansJoueurs.getPays());
	}
	
	@Test
	public void testSetPays() {
		equipeSansJoueurs.setPays("Allemagne");
		equipeAvecJoueurs.setPays("Espagne");
		assertEquals(equipeSansJoueurs.getPays(), "Allemagne");
		assertEquals(equipeAvecJoueurs.getPays(), "Espagne");
			assertNotEquals(equipeAvecJoueurs.getPays(), equipeSansJoueurs.getPays());
	}
	
	@Test
	public void testGetClassement() {
		assertEquals(equipeSansJoueurs.getClassement(), 1);
	}
	
	@Test
	public void testSetClassement() {
		equipeSansJoueurs.setClassement(3);
		equipeAvecJoueurs.setClassement(4);
		assertEquals(equipeSansJoueurs.getClassement(), 3);
		assertEquals(equipeAvecJoueurs.getClassement(), 4);
		assertNotEquals(equipeAvecJoueurs.getClassement(), equipeSansJoueurs.getClassement());
	}
	
	@Test
	public void testGetWorldRanking() {
		assertEquals(equipeSansJoueurs.getWorldRanking(), 1);
	}
	
	@Test
	public void testSetWorldRankin() {
		equipeSansJoueurs.setWorldRanking(3);
		equipeAvecJoueurs.setWorldRanking(4);
		assertEquals(equipeSansJoueurs.getWorldRanking(), 3);
		assertEquals(equipeAvecJoueurs.getWorldRanking(), 4);
		assertNotEquals(equipeAvecJoueurs.getWorldRanking(), equipeSansJoueurs.getWorldRanking());
	}

	@Test
	public void testGetSaison() {
		assertEquals(equipeSansJoueurs.getSaison(), "2023");
	}
	
	@Test
	public void testSetSaison() {
		equipeSansJoueurs.setSaison("Saison 2024");
		equipeAvecJoueurs.setSaison("Saison 2025");
		assertEquals(equipeSansJoueurs.getSaison(), "Saison 2024");
		assertEquals(equipeAvecJoueurs.getSaison(), "Saison 2025");
		assertNotEquals(equipeAvecJoueurs.getSaison(), equipeSansJoueurs.getSaison());
	}
	
	@Test
	public void testGetJoueurs() {
		assertEquals(equipeAvecJoueurs.getJoueurs(), listJoueurs);
	}
	
	@Test
	public void testSetJoueurs() {
		List<Joueur> listJoueurs2 = new ArrayList<Joueur>(Arrays.asList(
			    new Joueur(1, "Joueur6", 2),
			    new Joueur(2, "Joueur7", 2),
			    new Joueur(3, "Joueur8", 2),
			    new Joueur(4, "Joueur9", 2),
			    new Joueur(5, "Joueur10", 2)
			));
		equipeAvecJoueurs.setJoueurs(listJoueurs2);
		assertEquals(equipeAvecJoueurs.getJoueurs(), listJoueurs2);
	}
	
}
