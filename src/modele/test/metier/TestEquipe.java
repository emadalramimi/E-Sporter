package modele.test.metier;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;

public class TestEquipe {
	
	private Equipe equipeSansJoueurs;
	private Equipe equipeAvecJoueurs;
	private List<Joueur> listJoueurs;
	
	@Before
    public void setUp() {
		listJoueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
		this.equipeSansJoueurs = new Equipe(1, "Equipe1", Pays.FRANCE, 1, 1, "2023", listJoueurs);
		this.equipeAvecJoueurs = new Equipe("Equipe2", Pays.CANADA, listJoueurs);
    }
	
	@Test
	public void testGetIdEquipe() {
		assertEquals(this.equipeSansJoueurs.getIdEquipe(), 1);
	}
	
	@Test
	public void testSetIdEquipe() {
		this.equipeSansJoueurs.setIdEquipe(3);
		assertEquals(this.equipeSansJoueurs.getIdEquipe(), 3);
	}
	
	@Test
	public void testGetNom() {
		assertEquals(this.equipeSansJoueurs.getNom(), "Equipe1");
		assertEquals(this.equipeAvecJoueurs.getNom(), "Equipe2");
	}
	
	@Test
	public void testSetNom() {
		this.equipeSansJoueurs.setNom("Equipe3");
		assertEquals(this.equipeSansJoueurs.getNom(), "Equipe3");
	}
	
	@Test
	public void testGetPays() {
		assertEquals(this.equipeSansJoueurs.getPays(), Pays.FRANCE);
	}
	
	@Test
	public void testSetPays() {
		this.equipeSansJoueurs.setPays(Pays.ALLEMAGNE);
		assertEquals(this.equipeSansJoueurs.getPays(), Pays.ALLEMAGNE);
	}
	
	@Test
	public void testGetClassement() {
		assertEquals(this.equipeSansJoueurs.getClassement(), 1);
	}
	
	@Test
	public void testSetClassement() {
		this.equipeSansJoueurs.setClassement(3);
		assertEquals(this.equipeSansJoueurs.getClassement(), 3);
	}
	
	@Test
	public void testGetWorldRanking() {
		assertEquals(this.equipeSansJoueurs.getWorldRanking(), 1);
	}
	
	@Test
	public void testSetWorldRankin() {
		this.equipeSansJoueurs.setWorldRanking(3);
		assertEquals(this.equipeSansJoueurs.getWorldRanking(), 3);
	}

	@Test
	public void testGetSaison() {
		assertEquals(this.equipeSansJoueurs.getSaison(), "2023");
	}
	
	@Test
	public void testSetSaison() {
		this.equipeSansJoueurs.setSaison("Saison 2024");
		assertEquals(this.equipeSansJoueurs.getSaison(), "Saison 2024");
	}
	
	@Test
	public void testGetJoueurs() {
		assertEquals(this.equipeAvecJoueurs.getJoueurs(), listJoueurs);
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
		this.equipeAvecJoueurs.setJoueurs(listJoueurs2);
		assertEquals(this.equipeAvecJoueurs.getJoueurs(), listJoueurs2);
	}
	
}
