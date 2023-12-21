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
import modele.metier.StatistiquesEquipe;
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
	public void testOuvrirTournoiTournoiOuvert() throws Exception {
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 - 3600, System.currentTimeMillis() / 1000 + 3600, false, "arbitre1", "password", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	    modele.ouvrirTournoi(tournoi);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiDateFinPassee() throws Exception {
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 - 7200, System.currentTimeMillis() / 1000 - 3600, true, "arbitre2", "password", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.ouvrirTournoi(tournoi);
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiTournoiEnCours() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi1 = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3200, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi1);
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi1);
		}
		modele.ajouter(tournoi1);
		Tournoi tournoi2 = new Tournoi(8, "TournoiTest2", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3300, System.currentTimeMillis() / 1000 + 7600, true, "arbitre2", "password2", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi2);
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi2);
		}
		modele.ajouter(tournoi2);
	    modele.ouvrirTournoi(tournoi1);
	    modele.ouvrirTournoi(tournoi2);
	}
	
	@Test
	public void testOuvrirTournoi() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi);
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi);
		}
		modele.ouvrirTournoi(tournoi);
	}
	
	@Test
	public void testGetParIdentifiant() throws Exception {
		assertEquals(modele.getParIdentifiant("AsiaStar"), modele.getParId(2));
	}
	
	@Test
	public void testGetTournoiRencontre() throws Exception {
		assertEquals(modele.getTournoiRencontre(1), modele.getParId(1));
	}
	
	@Test
    public void testGetResultatsTournoi() throws Exception {
        ModeleEquipe modeleEquipe = new ModeleEquipe();
        Tournoi tournoiTest = modele.getParId(1).orElse(null);
        Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
        modele.ajouter(tournoi);
        
        List<StatistiquesEquipe> statistiquesEquipes = new ArrayList<>(Arrays.asList(
                new StatistiquesEquipe(modeleEquipe.getParId(1).get(), 0, 0),
                new StatistiquesEquipe(modeleEquipe.getParId(2).get(), 0, 0),
                new StatistiquesEquipe(modeleEquipe.getParId(4).get(), 0, 0),
                new StatistiquesEquipe(modeleEquipe.getParId(3).get(), 0, 0)
            ));
        for(int i = 1; i < 5; i++){
            modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
        }
        modele.ouvrirTournoi(tournoi);
        System.out.println(statistiquesEquipes);
        System.out.println(modele.getResultatsTournoi(tournoi));
        assertEquals(statistiquesEquipes.size(), modele.getResultatsTournoi(tournoi).size());
        for(int i = 1; i < statistiquesEquipes.size(); i++){
        	assertEquals(statistiquesEquipes.get(i), modele.getResultatsTournoi(tournoi).get(i));
        }
    }
	
	@Test
	public void testGetParNom() throws Exception {
		assertEquals(modele.getParNom("Asia Star Challengers Invitational 2023").size(), 1);
		assertEquals(modele.getTout().get(0), modele.getParNom("Asia Star Challengers Invitational 2023").get(0));
	}
	
	@Test
	public void testGetParFiltrageNotorieteNull() throws Exception {
		List<Tournoi> tournois = modele.getTout();
		assertEquals(tournois.size(), modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).size());
		for(int i = 0; i < tournois.size(); i++){
			assertEquals(tournois.get(i), modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).get(i));
		}
	}
	
	@Test
	public void testGetParFiltrageStatutNull() throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		tournois.add(modele.getParId(3).orElse(null));
		assertEquals(tournois.size(), modele.getParFiltrage(Notoriete.NATIONAL, null).size());
		for(int i = 0; i < tournois.size(); i++){
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.NATIONAL, null).get(i));
		}
	}
	
	@Test
	public void testGetParFiltrageCloture() throws Exception {
		List<Tournoi> tournois = modele.getTout();
		tournois.remove(modele.getParId(6).orElse(null));
		tournois.remove(modele.getParId(3).orElse(null));
		tournois.remove(modele.getParId(4).orElse(null));
		for(int i = 0; i < tournois.size(); i++){
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.INTERNATIONAL, ControleurTournois.Statut.CLOTURE).get(i));
		}
	}
	
	@Test
	public void testGetParFiltrageOuvert() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi);
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi);
		}
		modele.ouvrirTournoi(tournoi);
		
		//Ici getParNom() car un seul tournoi ne peut être ouvert à la fois donc le filtre n'en retournera que 1, celui qu'on vient d'ouvrir
		List<Tournoi> tournois = modele.getParNom(tournoi.getNomTournoi());
		assertEquals(tournois.size(), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.OUVERT).size());
		for(int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.OUVERT).get(i));
		}
	}
	
	@Test
	public void testGetParFiltrageInscription() throws Exception {
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi);
		List<Tournoi> tournois = new ArrayList<>();
		tournois.add(tournoi);
		assertEquals(tournois.size(), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).size());
		for(int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).get(i));
		}
	}
	
	//Réinitialise les tournoi
	@After
	public void tearsDown() throws Exception {
		List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for(Tournoi tournoi : modele.getTout()) {
			if(!idAGarder.contains(tournoi.getIdTournoi())) {
				modele.supprimer(tournoi);
			}
		}
	}
}
