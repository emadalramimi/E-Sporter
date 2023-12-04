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
import modele.metier.Equipe;
import modele.metier.Joueur;

public class TestModeleEquipe {
	
	private ModeleEquipe modele;
	private Equipe equipe;
	private Equipe equipeAModif;
	private List<Joueur> listJoueurs;
	
	@Before
	public void setup() throws Exception {
		modele = new ModeleEquipe();
		listJoueurs = new ArrayList<>(Arrays.asList(
				new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
		equipe = new Equipe(10, "Equipe", "Canada", 2, 2, "Saison 2023", listJoueurs);
		equipeAModif = new Equipe(10, "EquipeModif", "France", 3, 3, "Saison 2024", listJoueurs);
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
	public void testAjouterTrue() {
		assertTrue(modele.ajouter(equipe));
	}

	@Test
	public void testModifierTrue() {
		modele.ajouter(equipe);
		assertTrue(modele.modifier(equipeAModif));
	}
	
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
}