package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controleur.ControleurTournois;
import modele.ModeleEquipe;
import modele.ModelePoule;
import modele.ModeleTournoi;
import modele.exception.DatesTournoiException;
import modele.exception.OuvertureTournoiException;
import modele.exception.TournoiDejaOuvertException;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleTournoi {

	private ModeleTournoi modele;
	private ModelePoule modelePoule;
	private ModeleEquipe modeleEquipe;
	private Poule poule;
	private Tournoi tournoi;
	private Equipe[] equipes;
	private List<Poule> poules;
	private Rencontre rencontreCloture;
	
	/**
	 * Créée une nouveau ModeleTournoi
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
	    modele = new ModeleTournoi();
	    modelePoule = new ModelePoule();
	    modeleEquipe = new ModeleEquipe();
        Arbitre arbitre = new Arbitre(1, "Willem", "Miled");
        
        List<Arbitre> arbitres = new ArrayList<>(Arrays.asList(
        	arbitre
		));
    	tournoi = new Tournoi(1, "Tournoi1", Notoriete.INTERNATIONAL, this.getDateCourante() + 500, this.getDateCourante() + 1000, false, "Identifiant", "mdp", modele.getParId(1).orElse(null).getPoules(), modele.getParId(1).orElse(null).getEquipes(), arbitres);
    	tournoi.getPoules();
	}
	
	/**
	 * Renvoie la date courante en secondes
	 */
	private long getDateCourante() {
		return (System.currentTimeMillis() / 1000);
	}
	
	/**
	 * Teste la récupération de tous les tournois dans la base de données
	 */
	@Test
	public void testGetTout() throws Exception {
		assertNotNull(modele.getTout());
		assertEquals(modele.getTout().size(), 6);
	}
	
	/**
	 * Teste la récupération du tournoi dont l'idTournoi est spécifié
	 */
	@Test
	public void testGetParId() throws Exception {
		Tournoi tournoi = modele.getParId(1).orElse(null);
		assertNotNull(tournoi);
		assertTrue(modele.getTout().contains(tournoi));
	}
	
	/**
	 * Teste l'ajout d'un tournoi dans la base de données
	 */
	@Test
	public void testAjouter() throws Exception {
		List<Arbitre> arbitres = new ArrayList<>();
		Tournoi tournoi = new Tournoi("Tournoi", Notoriete.INTERNATIONAL, 10000, 10001, "Iden", "mdp", arbitres);
		assertTrue(modele.ajouter(tournoi));
	}
	
	/**
	 * Teste la modification d'un tournoi dans la base de données
	 */
	@Test
	public void testModifier() throws Exception {
		Tournoi tournoi = new Tournoi(7, "Tournoi", Notoriete.INTERNATIONAL, getDateCourante() - 3600, getDateCourante() + 3600, true, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.ajouter(tournoi);
		tournoi.setNomTournoi("Peu Importe");
		assertTrue(modele.modifier(tournoi));
	}
	
	/**
	 * Teste l'erreur lors de la modification d'un tournoi déja cloturé
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testModifierClotureException() throws Exception {
		Tournoi tournoi = new Tournoi(1, "Tournoi", Notoriete.INTERNATIONAL, getDateCourante() - 3600, getDateCourante() + 3600, false, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.modifier(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de la modification d'un tournoi avec une date de fin inférieure à la courante
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testModifierDateFinException() throws Exception {
		Tournoi tournoi = new Tournoi(1, "Tournoi", Notoriete.INTERNATIONAL, getDateCourante() - 3600, getDateCourante() - 3500, true, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.modifier(tournoi);
	}
	
	/**
	 * Teste la suppression d'un tournoi dans la base de données
	 */
	@Test
	public void testSupprimer() throws Exception {
		Tournoi tournoi = new Tournoi(7, "Tournoi", Notoriete.INTERNATIONAL, getDateCourante() - 3600, getDateCourante() + 3600, false, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.ajouter(tournoi);
		tournoi.setEstCloture(true);
		modele.supprimer(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de la suppression d'un tournoi cloturé avec une date de fin inférieure à la courante
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSupprimerException() throws Exception {
		Tournoi tournoi = modele.getParId(6).orElse(null);
		tournoi.setIdTournoi(7);
		modele.ajouter(tournoi);
		tournoi.setEstCloture(false);
		tournoi.setDateTimeDebut(getDateCourante() - 5000);
		tournoi.setDateTimeFin(getDateCourante() - 4000);
		modele.supprimer(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi cloturé
	 * @throws OuvertureTournoiException
	 */
	@Test(expected = OuvertureTournoiException.class)
	public void testOuvrirTournoiTournoiOuvert() throws Exception {
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() - 3600, getDateCourante() + 3600, false, "arbitre1", "password", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	    modele.ouvrirTournoi(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi avec une date de fin inférieure à la courante
	 * @throws DatesTournoiException
	 */
	@Test(expected = DatesTournoiException.class)
	public void testOuvrirTournoiDateFinPassee() throws Exception {
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() - 7200, getDateCourante() - 3600, true, "arbitre2", "password", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		modele.ouvrirTournoi(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi avec un nombre d'équipes inférieur à 4
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiNombreEquipesInf4() throws Exception {
	    List<Equipe> equipes = new ArrayList<>(Arrays.asList(
			new Equipe("Nom1", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom2", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom3", Pays.FRANCE, new ArrayList<>())
		));
	    
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3600, getDateCourante() + 7200, true, "arbitre3", "password", new ArrayList<>(), equipes, new ArrayList<>());
	    modele.ouvrirTournoi(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi avec un nombre d'équipes supérieur à 8
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiNombreEquipesSup8() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(
			new Equipe("Nom1", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom2", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom3", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom4", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom5", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom6", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom7", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom8", Pays.FRANCE, new ArrayList<>()),
			new Equipe("Nom9", Pays.FRANCE, new ArrayList<>())
		));
		
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3600, getDateCourante() + 7200, true, "arbitre3", "password", new ArrayList<>(), equipes, new ArrayList<>());
		modele.ajouter(tournoi);
	    modele.ouvrirTournoi(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi lorsqu'un autre tournoi est déjà ouvert
	 * @throws TournoiDejaOuvertException
	 */
	@Test(expected = TournoiDejaOuvertException.class)
	public void testOuvrirTournoiTournoiEnCours() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi1 = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3200, getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi1);
		
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi1);
		}
		
		modele.ajouter(tournoi1);
		Tournoi tournoi2 = new Tournoi(8, "TournoiTest2", Notoriete.NATIONAL, getDateCourante() + 3300, getDateCourante() + 7600, true, "arbitre2", "password2", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi2);
		
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi2);
		}
		
		modele.ajouter(tournoi2);
	    modele.ouvrirTournoi(tournoi1);
	    modele.ouvrirTournoi(tournoi2);
	}
	
	/**
	 * Teste l'ouverture d'un tournoi
	 */
	@Test
	public void testOuvrirTournoi() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3600, getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi);
		
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi);
		}
		
		modele.ouvrirTournoi(tournoi);
	}
	
	/**
	 * Teste le filtrage par identifiant
	 */
	@Test
	public void testGetParIdentifiant() throws Exception {
		assertEquals(modele.getParIdentifiant("AsiaStar"), modele.getParId(2));
	}
	
	/**
	 * Teste le filtrage par rencontre
	 */
	@Test
	public void testGetTournoiRencontre() throws Exception {
		assertEquals(modele.getTournoiRencontre(1), modele.getParId(1));
	}
	
	/**
	 * Teste la récupération des résultats d'un tournoi
	 */
	@Test
    public void testGetResultatsTournoi() throws Exception {
        ModeleEquipe modeleEquipe = new ModeleEquipe();
        Tournoi tournoiTest = modele.getParId(1).orElse(null);
        Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3600, getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
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
        assertEquals(statistiquesEquipes.size(), modele.getResultatsTournoi(tournoi).size());
        
        for(int i = 1; i < statistiquesEquipes.size(); i++){
        	assertEquals(statistiquesEquipes.get(i), modele.getResultatsTournoi(tournoi).get(i));
        }
    }
	
	/**
	 * Teste le filtrage par nom
	 */
	@Test
	public void testGetParNom() throws Exception {
		assertEquals(modele.getParNom("Asia Star Challengers Invitational 2023").size(), 1);
		assertEquals(modele.getTout().get(0), modele.getParNom("Asia Star Challengers Invitational 2023").get(0));
	}
	
	/**
	 * Teste le filtrage avec une notoriété null et un statut non null
	 */
	@Test
	public void testGetParFiltrageNotorieteNull() throws Exception {
		List<Tournoi> tournois = modele.getTout();
		assertEquals(tournois.size(), modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).size());
		
		for(int i = 0; i < tournois.size(); i++){
			assertEquals(tournois.get(i), modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).get(i));
		}
	}
	
	/**
	 * Teste le filtrage avec une notoriété non null et un statut null
	 */
	@Test
	public void testGetParFiltrageStatutNull() throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		tournois.add(modele.getParId(3).orElse(null));
		assertEquals(tournois.size(), modele.getParFiltrage(Notoriete.NATIONAL, null).size());
		
		for(int i = 0; i < tournois.size(); i++){
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.NATIONAL, null).get(i));
		}
	}
	
	/**
	 * Teste le filtrage avec une notoriété et un statut cloturé
	 */
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
	
	/**
	 * Teste le filtrage avec une notoriété et un statut ouvert
	 */
	@Test
	public void testGetParFiltrageOuvert() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3600, getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi);
		
		for(int i = 0; i < 4; i++){
			modeleEquipe.inscrireEquipe(modeleEquipe.getTout().get(i), tournoi);
		}
		
		modele.ouvrirTournoi(tournoi);
		List<Tournoi> tournois = modele.getParNom(tournoi.getNomTournoi());
		assertEquals(tournois.size(), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.OUVERT).size());
		
		for(int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.OUVERT).get(i));
		}
	}
	
	/**
	 * Teste le filtrage avec une notoriété et un statut en phase d'inscriptions
	 */
	@Test
	public void testGetParFiltrageInscription() throws Exception {
		Tournoi tournoiTest = modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, getDateCourante() + 3600, getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modele.ajouter(tournoi);
		List<Tournoi> tournois = new ArrayList<>();
		tournois.add(tournoi);
		assertEquals(tournois.size(), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).size());
		for(int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).get(i));
		}
	}
	
	/**
     * Teste la levée d'exception si la poule est déjà cloturée
     */
    @Test (expected = IllegalArgumentException.class)
    public void testCloturerPouleCloturee() throws Exception {
        List<Poule> poulesTest = new ArrayList<>();
        
        for(Poule p : tournoi.getPoules()) {
            p.setEstCloturee(true);
            poulesTest.add(p);
        }
        
        tournoi.setPoules(poulesTest);
        
        System.out.println(tournoi);
        System.out.println(tournoi.getPoules());
        modele.cloturerPoule(tournoi);
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
