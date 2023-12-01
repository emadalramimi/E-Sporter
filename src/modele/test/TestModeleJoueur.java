package modele.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleJoueur;
import modele.metier.Joueur;

public class TestModeleJoueur {
	
	private ModeleJoueur modele;
	private Joueur joueur;
	private Joueur joueurModif;
	
	@Before
	public void setup() throws Exception {
		modele = new ModeleJoueur();
		joueur = new Joueur(50, "joueur", 1);
		joueurModif = new Joueur(51, "joueurModif", 1);
    }
	
	@Test
	public void testGetTout() throws Exception {
	    assertNotNull(modele.getTout());
	    Equipe equipeFromDb = modele.getParId(1).orElse(null);
	    assertNotNull(equipeFromDb);
	    List<Equipe> result = modele.getTout();
	    assertNotNull(result);
	    assertFalse(result.isEmpty());
	    assertEquals(equipeFromDb, result.get(0));
	}
	
	@Test
	public void testGetParId() throws Exception {
	    Equipe equipeFromDb = modele.getParId(1).orElse(null);
	    assertNotNull(equipeFromDb);
	    Optional<Equipe> result = modele.getParId(equipeFromDb.getIdEquipe());
	    assertTrue(result.isPresent());
	    assertEquals(equipeFromDb, result.get());
	}
	
	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(modele.ajouter(equipe));
	}
	
	@Test
	public void testAjouterFalse() throws Exception {
		assertTrue(modele.ajouter(equipe));
		assertFalse(modele.ajouter(equipeAModif));
	}
	
	@Test
	public void testModifierTrue() throws Exception {
		modele.ajouter(equipe);
		assertTrue(modele.modifier(equipeAModif));
	}
	
	/*
	@Test
	public void testModifierFalse() throws Exception {
	    Equipe equipeToModify = new Equipe(15, "EquipeModif", "France", 3, 3, "Saison 2024", listJoueurs);
	    assertFalse(modele.modifier(equipeToModify));
	}
	*/

	@Test
	public void testSupprimerTrue() throws Exception {
		modele.ajouter(equipe);
		assertTrue(modele.supprimer(equipe));
	}
	
	@Test
	public void testGetNextValId() {
	    int nextVal = modele.getNextValId();
	    assertTrue(nextVal != 0);
	}
	
	@Test
	public void testGetParNom() throws Exception {
	    modele.ajouter(equipe);
	    List<Equipe> listTest = new ArrayList<>();
	    listTest.add(equipe);
	    List<Equipe> result = modele.getParNom(equipe.getNom());
	    assertEquals(listTest.size(), result.size());
	    for (int i = 0; i < listTest.size(); i++) {
	        assertTrue(listTest.get(i).equals(result.get(i)));
	    }
	}

	@After
    public void cleanup() throws Exception {
        List<Integer> idsToPreserve = Arrays.asList(50, 51);
        modele.getTout().stream()
                .filter(joueur -> !idsToPreserve.contains(joueur.getIdEquipe()))
                .forEach(joueur -> {
                    try {
                        modele.supprimer(joueur);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
	
}