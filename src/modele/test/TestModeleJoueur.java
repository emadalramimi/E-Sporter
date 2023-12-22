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
import modele.metier.Pays;

public class TestModeleJoueur {
	
private ModeleJoueur modele;
	
	private Joueur joueur;
	private Equipe equipe;
	
	private List<Joueur> joueurs;
	
	@Before
	public void setUp() throws Exception {
		modele = new ModeleJoueur();
		joueur = new Joueur(54, "Kévin", 1);
		joueurs = new ArrayList<>(Arrays.asList(
				new Joueur(38, "joueur", 6),
				new Joueur(39, "joueur", 6),
				new Joueur(40, "joueur", 6),
				new Joueur(41, "joueur", 6),
				new Joueur(42, "joueur", 6)
				));
		equipe = new Equipe("Equipe", Pays.CANADA, joueurs);
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
		assertTrue(modele.modifier(new Joueur(50, "joueurModif", 1)));
	}
	
	@Test
	public void testSupprimerJoueursParEquipe() throws Exception {
		assertTrue(modele.supprimerJoueursEquipe(equipe.getIdEquipe()));
	}

	@Test
	public void testGetListeJoueursParId() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		modeleEquipe.ajouter(equipe);
	    assertEquals(5, modele.getListeJoueursParId(equipe.getIdEquipe()).size());
	    assertEquals(modele.getListeJoueursParId(equipe.getIdEquipe()).toString(), joueurs.toString());
	}

	@After
    public void tearsDown() throws Exception {
		modele.supprimerJoueursEquipe(6);
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		modeleEquipe.supprimer(equipe);
    }
}