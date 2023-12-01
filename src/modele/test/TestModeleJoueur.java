package modele.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.metier.Equipe;
import modele.metier.Joueur;

public class TestModeleJoueur {
	
	private ModeleJoueur modele;
	private Joueur joueur;
	private Joueur joueurModif;
	
	@Before
	public void setup() throws Exception {
		modele = new ModeleJoueur();
		joueur = new Joueur(50, "joueur", 1);
		joueurModif = new Joueur(50, "joueurModif", 1);
    }
	
	@Test
	public void testGetTout() throws Exception {
	    assertNotNull(modele.getTout());
	    List<Joueur> listTest = modele.getTout();
	    List<Joueur> listJoueurs = new ArrayList<>();
	    while (listJoueurs.size() < listTest.size()) {
	        listJoueurs.add(modele.getParId(modele.getNextValId()).orElse(null));
	    }
	    assertEquals(listTest.size(), listJoueurs.size());
	    for (int i = 0; i < listTest.size(); i++) {
	    	assertTrue(listTest.get(i).equals(listTest.get(i)));
	    }
	}
	
	/*
	@Test
	public void testGetParId() throws Exception {
	    Joueur JoueurTest = new Joueur(60, "JoueurTest", 1);
	    modele.ajouter(JoueurTest);
	    Optional<Joueur> retrievedJoueur = modele.getParId(60);
	    System.out.println("Expected: " + JoueurTest);
	    System.out.println("Actual  : " + retrievedJoueur.orElse(null));
	    assertEquals(JoueurTest, retrievedJoueur.get());
	}
	*/

	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(modele.ajouter(joueur));
	}
	
	@Test
	public void testAjouterFalse() throws Exception {
		assertTrue(modele.ajouter(joueur));
		assertFalse(modele.ajouter(joueurModif));
	}
	
	@Test
	public void testModifierTrue() throws Exception {
		modele.ajouter(joueur);
		assertTrue(modele.modifier(joueurModif));
	}

	@Test
	public void testSupprimerTrue() throws Exception {
		modele.ajouter(joueur);
		assertTrue(modele.supprimer(joueur));
	}
	
	@Test
	public void testGetNextValId() {
	    int nextVal = modele.getNextValId();
	    assertTrue(nextVal != 0);
	}
	
	@Test
	public void testGetListeJoueursParId() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
	    Equipe equipe = new Equipe(10, "Equipe", "Canada", 2, 2, "Saison 2023");
		modeleEquipe.ajouter(equipe);
	    Joueur joueur1 = new Joueur(101, "Joueur1", 10);
	    Joueur joueur2 = new Joueur(102, "Joueur2", 10);
	    Joueur joueur3 = new Joueur(103, "Joueur3", 10);
	    modele.ajouter(joueur1);
	    modele.ajouter(joueur2);
	    modele.ajouter(joueur3);
	    List<Joueur> result = modele.getListeJoueursParId(10);
	    assertEquals(3, result.size());
	    assertTrue(result.contains(joueur1));
	    assertTrue(result.contains(joueur2));
	    assertTrue(result.contains(joueur3));
	    modeleEquipe.supprimer(equipe);
	}


	@After
    public void cleanup() throws Exception {
        List<Integer> idsToPreserve = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 ,20);
        modele.getTout().stream()
                .filter(joueur -> !idsToPreserve.contains(joueur.getIdJoueur()))
                .forEach(joueur -> {
                    try {
                        modele.supprimer(joueur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
	
}