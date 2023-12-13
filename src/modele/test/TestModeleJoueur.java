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
	private ModeleEquipe modeleEquipe;
	
	private Joueur joueur;
	private Joueur joueurModif;
	private Equipe equipe;
	
	private List<Joueur> joueurs;
	
	@Before
	public void setUp() throws Exception {
		modele = new ModeleJoueur();
		modeleEquipe = new ModeleEquipe();
		joueur = new Joueur(50, "joueur", 1);
		joueurModif = new Joueur(50, "joueurModif", 1);
		equipe = new Equipe(50, "Equipe", "Canada", 2, 2, "Saison 2023", joueurs);
		joueurs = new ArrayList<>(Arrays.asList(
				new Joueur(55, "joueur", 50),
				new Joueur(56, "joueur", 50),
				new Joueur(57, "joueur", 50),
				new Joueur(58, "joueur", 50),
				new Joueur(59, "joueur", 50)
				));
    }
	
	@Test
	public void testGetTout() throws Exception {
	    assertNotNull(modele.getTout());
	    List<Joueur> listTest = modele.getTout();
	    assertEquals(listTest.size(), modele.getTout().size());
	    List<Joueur> listJoueurs = new ArrayList<>();
	    for(int i = 0; i < listTest.size(); i++) {
	    	assertNotNull(listTest.get(i));
	        listJoueurs.add(listTest.get(i));
	    }
	    assertEquals(listTest.size(), listJoueurs.size());
	    for (int i = 0; i < listTest.size(); i++) {
	    	assertTrue(listTest.get(i).equals(listJoueurs.get(i)));
	    }
	}
	
	@Test
	public void testGetParId() throws Exception {
	    modele.ajouter(joueur);
	    assertNotNull(joueur);
	    Optional<Joueur> retrievedJoueur = modele.getParId(joueur.getIdJoueur());
	    assertEquals(joueur, retrievedJoueur.get());
	}

	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(modele.ajouter(joueur));
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
	
	/*
	@Test
	public void testGetNextValId() {
	    int nextVal = modele.getNextValId();
	    assertTrue(nextVal != 0);
	}
	*/
	
	@Test
	public void testSupprimerJoueursParEquipe() throws Exception {
		assertTrue(modele.supprimerJoueursEquipe(equipe.getIdEquipe()));
	}
		
	
	@Test
	public void testGetListeJoueursParId() throws Exception {
	    assertEquals(5, modele.getListeJoueursParId(1).size());
	    for(int i = 0; i < 5; i++) {
	    	assertEquals(modele.getParId(i + 1).orElse(null), modele.getListeJoueursParId(1).get(i));
	    }
	}
	
	@After
    public void tearsDown() throws Exception {
        List<Integer> idsAGarderJoueurs = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 ,20);
        
        modele.getTout().stream()
                .filter(j -> !idsAGarderJoueurs.contains(j.getIdJoueur()))
                .forEach(j -> {
                    try {
                        modele.supprimer(j);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        
        List<Integer> idsAGarderEquipe = Arrays.asList(1, 2, 3, 4);
        modele.getTout().stream()
                .filter(eq -> !idsAGarderEquipe.contains(eq.getIdEquipe()))
                .forEach(eq -> {
                    try {
                        modele.supprimer(eq);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}