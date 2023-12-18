package modele.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModelePoule;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModelePoule {
	
	private ModelePoule modele;
	private Poule poule;
	private List<Rencontre> rencontres;
	
	@Before
	public void setUp() throws Exception {
		modele = new ModelePoule();

		List<Joueur> joueurs = new ArrayList<>(Arrays.asList(
		    new Joueur(1, "Joueur1", 2),
		    new Joueur(2, "Joueur2", 2),
		    new Joueur(3, "Joueur3", 2),
		    new Joueur(4, "Joueur4", 2),
		    new Joueur(5, "Joueur5", 2)
		));
    	
        Equipe equipeA = new Equipe(1, "Equipe A", "France", 5, 5, "2020", joueurs);
        Equipe equipeB = new Equipe(2, "Equipe B", "Maroc", 5, 5, "2020", joueurs);
        Equipe[] equipes = {equipeA, equipeB};
        rencontres = new ArrayList<>(Arrays.asList(
        	new Rencontre(1, 1000, 10000, equipes),
        	new Rencontre(2, 1050, 10050, equipes)
        ));
        poule = new Poule(1, false, false, 1, rencontres);
        }
	
	@Test
	public void testGetTout() throws Exception {
	    assertNotNull(modele.getTout());
	    assertEquals(modele.getTout().size(), 2);
	    List<Poule> poules = modele.getTout();
	    assertEquals(modele.getTout().size(), poules.size());
	}
	/*
	@Test
	public void testGetParId() throws Exception {
		assertTrue(modele.getParId(1).isPresent());
		assertNotNull(modele.getParId(1).orElse(null));
	}
	*/
	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(modele.ajouter(poule));
	}
	
	@Test
	public void testSupprimerTrue() throws Exception {
		modele.ajouter(poule);
		assertTrue(modele.supprimer(poule));
	}
	
	@Test
	public void testGetPoulesTournoi() throws Exception {
		List<Poule> listPoules = new ArrayList<>(Arrays.asList(
				new Poule(1, true, false, 1, rencontres),
			new Poule(2, true, true, 1, rencontres)
		));
		assertEquals(modele.getPoulesTournoi(poule.getIdTournoi()).toString(), listPoules.toString());
	}
	
	@After
    public void tearsDown() throws Exception {
        List<Integer> idAGarder = Arrays.asList(1, 2);
        modele.getTout().stream()
                .filter(poule -> !idAGarder.contains(poule.getIdPoule()))
                .forEach(poule -> {
                    try {
                        modele.supprimer(poule);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
	
}