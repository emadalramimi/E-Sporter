package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
		this.modele = new ModeleJoueur();
		this.joueur = new Joueur(54, "Kévin", 1);
		this.joueurs = new ArrayList<>(Arrays.asList(new Joueur(38, "joueur", 6), new Joueur(39, "joueur", 6),
				new Joueur(40, "joueur", 6), new Joueur(41, "joueur", 6), new Joueur(42, "joueur", 6)));
		this.equipe = new Equipe("Equipe", Pays.CANADA, this.joueurs);
	}

	@Test
	public void testGetTout() throws Exception {
		assertNotNull(this.modele.getTout());
		List<Joueur> listTest = this.modele.getTout();
		assertEquals(listTest.size(), this.modele.getTout().size());
		List<Joueur> listJoueurs = new ArrayList<>();

		for (int i = 0; i < listTest.size(); i++) {
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
		this.modele.ajouter(this.joueur);
		assertNotNull(this.joueur);
		Optional<Joueur> retrievedJoueur = this.modele.getParId(this.joueur.getIdJoueur());
		assertEquals(this.joueur, retrievedJoueur.get());
	}

	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(this.modele.ajouter(this.joueur));
	}

	@Test
	public void testModifierTrue() throws Exception {
		this.modele.ajouter(this.joueur);
		assertTrue(this.modele.modifier(new Joueur(50, "joueurModif", 1)));
	}

	@Test
	public void testSupprimerJoueursParEquipe() throws Exception {
		assertTrue(this.modele.supprimerJoueursEquipe(this.equipe.getIdEquipe()));
	}

	@Test
	public void testGetListeJoueursParId() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		modeleEquipe.ajouter(this.equipe);
		assertEquals(5, this.modele.getListeJoueursParId(this.equipe.getIdEquipe()).size());
		assertEquals(this.modele.getListeJoueursParId(this.equipe.getIdEquipe()).toString(), this.joueurs.toString());
	}

	@After
	public void tearsDown() throws Exception {
		this.modele.supprimerJoueursEquipe(6);
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		modeleEquipe.supprimer(this.equipe);
	}
}