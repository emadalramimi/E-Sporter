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
	
	@After
    public void cleanup() throws Exception {
        List<Integer> idsToPreserve = Arrays.asList(1, 2, 3, 4);
        modele.getTout().stream()
                .filter(equipe -> !idsToPreserve.contains(equipe.getIdEquipe()))
                .forEach(equipe -> {
                    try {
                        modele.supprimer(equipe);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
	
}