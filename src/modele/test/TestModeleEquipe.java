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
import modele.ModeleTournoi;
import modele.exception.InscriptionEquipeTournoiException;
import modele.exception.SaisonException;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleEquipe {

	private ModeleEquipe modele;
	private ModeleTournoi modeleTournoi;
	private Equipe equipe;
	private Equipe equipeAModif;
	private List<Joueur> listJoueurs;

	@Before
	public void setUp() throws Exception {
		modele = new ModeleEquipe();
		modeleTournoi = new ModeleTournoi();
		
		listJoueurs = new ArrayList<>(Arrays.asList(
				new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
		
		equipe = new Equipe(10, "Equipe", Pays.CANADA, 2, 2, "Saison 2023", listJoueurs);
		equipeAModif = new Equipe(10, "EquipeModif", Pays.FRANCE, 3, 3, "Saison 2024", listJoueurs);
    }

	@Test
	public void testGetTout() throws Exception {
	    assertNotNull(modele.getTout());
	    Equipe equipeTest = modele.getParId(1).orElse(null);
	    assertNotNull(equipeTest);
	    List<Equipe> result = modele.getTout();
	    assertNotNull(result);
	    assertFalse(result.isEmpty());
	    assertEquals(equipeTest, result.get(0));
	}
	
	@Test
	public void testGetParId() throws Exception {
	    Equipe equipeTest = modele.getParId(1).orElse(null);
	    assertNotNull(equipeTest);
	    Optional<Equipe> equipe = modele.getParId(equipeTest.getIdEquipe());
	    assertTrue(equipe.isPresent());
		assertNotNull(modele.getParId(1).orElse(null));
	    assertEquals(equipeTest, equipe.get());
	}

	@Test
	public void testAjouterTrue() throws Exception{
		assertTrue(modele.ajouter(equipe));
		assertEquals(equipe.getWorldRanking(), 1000);
	}

	@Test
	public void testAjouterSaisonDerniere() throws Exception {
		Equipe equipeOM = modele.getParId(5).orElse(null);
		Equipe equipeAjouter = new Equipe(equipeOM.getNom(), equipeOM.getPays(), equipeOM.getJoueurs());
		modele.ajouter(equipeAjouter);
		Equipe equipeTest = modele.getTout().get(modele.getTout().size()-1);
		assertEquals(equipeTest.getWorldRanking(), equipeOM.getClassement());
	}
	
	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testModifierException() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
		Tournoi tournoiTest = modeleTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modeleTournoi.ajouter(tournoi);
		
		for(int i = 0; i < 4; i++){
			modele.inscrireEquipe(modele.getTout().get(i), tournoi);
		}
		
		modeleTournoi.ouvrirTournoi(tournoi);
		Equipe equipeAModif = modele.getParId(1).orElse(null);
		equipeAModif.setNom("Coucou");
		modele.modifier(equipeAModif);
	}

	@Test
	public void testModifierTrue() throws Exception {
		modele.ajouter(equipe);
		assertTrue(modele.modifier(equipeAModif));
	}
	
	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testSupprimerException() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
		Tournoi tournoiTest = modeleTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600, System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modeleTournoi.ajouter(tournoi);
		
		for(int i = 0; i < 4; i++){
			modele.inscrireEquipe(modele.getTout().get(i), tournoi);
		}
		
		modeleTournoi.ouvrirTournoi(tournoi);
		modele.supprimer(modele.getParId(1).orElse(null));
	}

	@Test
	public void testSupprimerTrue() throws Exception {
		modele.ajouter(equipe);
		assertTrue(modele.supprimer(equipe));
	}

	@Test
	public void testGetEquipesTournoi() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(
			modele.getParId(1).get(),
			modele.getParId(2).get(),
			modele.getParId(3).get(),
			modele.getParId(4).get()
		));
		
		assertEquals(equipes, modele.getEquipesTournoi(1));
	}

	@Test
	public void testEstEquipeInscriteUnTournoi() throws Exception {
		modele.desinscrireEquipe(modele.getParId(1).orElse(null), modeleTournoi.getParId(1).orElse(null));
		modele.inscrireEquipe(modele.getParId(1).orElse(null), modeleTournoi.getParId(1).orElse(null));
		assertTrue(modele.estEquipeInscriteUnTournoi(modele.getParId(1).orElse(null)));
	}

	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testInscrireDejaInscrite() throws Exception{
		modele.inscrireEquipe(modele.getParId(1).orElse(null), modeleTournoi.getParId(1).orElse(null));
		modele.inscrireEquipe(modele.getParId(1).orElse(null), modeleTournoi.getParId(1).orElse(null));
	}

	@Test(expected = SaisonException.class)
	public void testInscrireSaisonAnterieure() throws Exception{
		modele.inscrireEquipe(modele.getParId(5).orElse(null), modeleTournoi.getParId(1).orElse(null));
	}

	@Test
	public void testInscrire() throws Exception{
		modele.ajouter(equipe);
		modele.inscrireEquipe(equipe, modeleTournoi.getParId(1).orElse(null));
	}

	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testDesinscrireNonInscrite() throws Exception{
		modele.desinscrireEquipe(equipe, modeleTournoi.getParId(1).orElse(null));
	}

	@Test
	public void testDesinscrire() throws Exception{
		modele.ajouter(equipe);
		modele.inscrireEquipe(equipe, modeleTournoi.getParId(1).orElse(null));
		modele.desinscrireEquipe(equipe, modeleTournoi.getParId(1).orElse(null));
	}

	@Test
	public void testGetParNom() throws Exception {
	    modele.ajouter(equipe);
	    assertNotNull(modele.getParNom(equipe.getNom()));
		assertTrue(modele.getParNom(equipe.getNom()).contains(equipe));
	}
	
	@Test
	public void testGetEquipesSaison() throws Exception {
		assertNotNull(modele.getEquipesSaison());
		
		for(int i = 1; i <= 4; i++){
			assertTrue(modele.getEquipesSaison().contains(modele.getParId(i).orElse(null)));
		}
	}
	
	@Test
	public void testGetTableauEquipes() throws Exception {
		List<Equipe> equipes = modele.getTout();
		
		List<Equipe> equipesInscrites = new ArrayList<>(Arrays.asList(
			modele.getParId(1).orElse(null)
		));
		
		Equipe[] equipesEligibles = modele.getTableauEquipes(equipesInscrites);
		assertNotNull(equipes);
		assertNotNull(equipesInscrites);
		assertTrue(equipesEligibles.length != 0);
		
		for(int i = 0; i < equipesEligibles.length; i++){
			assertTrue(!equipesInscrites.contains(equipesEligibles[i]));
		}
	}
	
	@Test
	public void testGetParFiltrage() throws Exception {
		Equipe equipeATester = modele.getParId(1).orElse(null);
		equipeATester.setIdEquipe(6);
		equipeATester.setPays(Pays.ALLEMAGNE);
		modele.ajouter(equipeATester);
		List<Equipe> equipes = new ArrayList<>();
		equipes.add(equipeATester);
		assertEquals(equipes.size(), modele.getParFiltrage(Pays.ALLEMAGNE).size());
		
		for (int i = 0; i < equipes.size(); i++) {
			assertEquals(equipes.get(i), modele.getParFiltrage(Pays.ALLEMAGNE).get(i));
		}
	}
	
	@Test
	public void testGetParFiltrageNull() throws Exception {
		Equipe equipeATester = modele.getParId(1).orElse(null);
		equipeATester.setIdEquipe(6);
		equipeATester.setPays(Pays.ALLEMAGNE);
		modele.ajouter(equipeATester);
		assertEquals(modele.getEquipesSaison().size(), modele.getParFiltrage(null).size());
		
		for (int i = 0; i < modele.getTout().size(); i++) {
			assertEquals(modele.getTout().get(i), modele.getParFiltrage(null).get(i));
		}
	}

	@After
    public void tearsDown() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
        List<Integer> idsAGarder = Arrays.asList(1, 2, 3, 4, 5);
        
        for(Equipe equipe : modele.getTout()) {
        	if(!idsAGarder.contains(equipe.getIdEquipe())) {
        		if(modele.estEquipeInscriteUnTournoi(equipe)) {
        			for(Tournoi tournoi : modeleTournoi.getTout()) {
        				if(modele.getEquipesTournoi(tournoi.getIdTournoi()).contains(equipe)) {
        					modele.desinscrireEquipe(equipe, tournoi);
        				}
        			}
        		}
        		
        		modele.supprimer(equipe);
        	}
        }
        
        List<Integer> idsAGarderTournoi = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        
        for(Tournoi tournoi : modeleTournoi.getTout()) {
        	if (!idsAGarderTournoi.contains(tournoi.getIdTournoi())) {
        		modeleTournoi.supprimer(tournoi);
        	}
        }
    }
}