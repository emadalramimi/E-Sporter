package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controleur.ControleurTournois;
import modele.ModeleEquipe;
import modele.ModeleTournoi;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Poule;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleTournoi {

	private ModeleTournoi modele;
	
	@Before
	public void setUp() {
	    modele = new ModeleTournoi();
	}
	
	@Test
	public void testGetTout() throws Exception {
		assertNotNull(modele.getTout());
		assertEquals(modele.getTout().size(), 6);
	}
	
	@Test
	public void testGetParId() throws Exception {
		Tournoi tournoi = modele.getParId(1).orElse(null);
		assertNotNull(tournoi);
		assertTrue(modele.getTout().contains(tournoi));
	}
	
	@Test
	public void testAjouter() throws Exception {
		List<Arbitre> arbitres = new ArrayList<>();
		Tournoi tournoi = new Tournoi("Tournoi", Notoriete.INTERNATIONAL, 10000, 10001, "Iden", "mdp", arbitres);
		assertTrue(modele.ajouter(tournoi));
	}
	
	@Test
	public void testModifier() throws Exception {
		List<Arbitre> arbitres = new ArrayList<>();
		List<Poule> poules = new ArrayList<>();
		List<Equipe> equipes = new ArrayList<>();
		Tournoi tournoi = new Tournoi(7, "Tournoi", Notoriete.INTERNATIONAL, System.currentTimeMillis() / 1000 - 3600, System.currentTimeMillis() / 1000 + 3600, false, "Iden", "mdp", poules, equipes, arbitres);
		modele.ajouter(tournoi);
		tournoi.setNomTournoi("Peu Importe");
		assertTrue(modele.modifier(tournoi));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testModifierException() throws Exception {
		Tournoi tournoi = new Tournoi(1, "Tournoi", Notoriete.INTERNATIONAL, System.currentTimeMillis() / 1000 - 3600, System.currentTimeMillis() / 1000 + 3600, false, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.modifier(tournoi);
	}
	
	@Test
	public void testSupprimer() throws Exception {
		Tournoi tournoi = new Tournoi(7, "Tournoi", Notoriete.INTERNATIONAL, System.currentTimeMillis() / 1000 - 3600, System.currentTimeMillis() / 1000 + 3600, false, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.ajouter(tournoi);
		tournoi.setEstCloture(true);
		modele.supprimer(tournoi);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSupprimerException() throws Exception {
		Tournoi tournoi = modele.getParId(6).orElse(null);
		tournoi.setIdTournoi(7);
		modele.ajouter(tournoi);
		tournoi.setEstCloture(false);
		tournoi.setDateTimeDebut(System.currentTimeMillis() / 1000 - 5000);
		tournoi.setDateTimeFin(System.currentTimeMillis() / 1000 - 4000);
		modele.supprimer(tournoi);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiNombreEquipesInf4() throws Exception {
	    List<Equipe> equipes = new ArrayList<>(Arrays.asList(
			new Equipe("Name1", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name2", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name3", Pays.FRANCE, new ArrayList<>())
		));
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre3", "password", new ArrayList<>(), equipes, new ArrayList<>());
	    modele.ouvrirTournoi(tournoi);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiNombreEquipesSup8() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(
			new Equipe("Name1", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name2", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name3", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name4", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name5", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name6", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name7", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name8", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Name9", Pays.FRANCE, new ArrayList<>())
		));
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre3", "password", new ArrayList<>(), equipes, new ArrayList<>());
		modele.ajouter(tournoi);
	    modele.ouvrirTournoi(tournoi);
	}
	@After
	public void tearsDown() throws Exception {
		
		//Réinitialise les tournoi
		List<Integer> IdAGarder = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for(Tournoi tournoi : modele.getTout()) {
			if(!IdAGarder.contains(tournoi.getIdTournoi())) {
				modele.supprimer(tournoi);
			}
		}
	}
}
