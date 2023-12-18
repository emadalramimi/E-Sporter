package modele.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.metier.Utilisateur.Role;

public class TestTournoi {
	
	private Tournoi tournoi1;
	private Tournoi tournoi2;
	private Equipe equipeA;
	private Equipe equipeB;
	private List<Poule> poules;
	private List<Joueur> joueurs;
	private List<Equipe> equipes;
	private List<Rencontre> rencontres;
	private List<Arbitre> arbitres;
	
	@Before
    public void setUp() {
		
		joueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
    	
        equipeA = new Equipe(50, "Equipe A", "France", 5, 5, "2020", joueurs);
        equipeB = new Equipe(51, "Equipe B", "Maroc", 5, 5, "2020", joueurs);
        Equipe[] equipeArray = {equipeA, equipeB};
        equipes = new ArrayList<>(Arrays.asList(
        		equipeA,
        		equipeB
        ));
        rencontres = new ArrayList<>(Arrays.asList(
        		new Rencontre(50, 1, 50, equipeArray),
        		new Rencontre(51, 1, 51, equipeArray)
        ));
        
        arbitres = new ArrayList<>(Arrays.asList(
			    new Arbitre(1, "Nom1", "Prenom1"),
			    new Arbitre(2, "Nom2", "Prenom2")
			));
        poules = new ArrayList<>(Arrays.asList(
				new Poule(1, true, true, 11, rencontres),
				new Poule(2, false, false, 10, rencontres)
			));
		tournoi1 = new Tournoi(10, "Tournoi1", Notoriete.INTERNATIONAL, 100, 1000, false, "id", "mdp", poules, equipes, arbitres);
		tournoi2 = new Tournoi("Tournoi2", Notoriete.INTERNATIONAL, 101, 1001, "id", "mdp", arbitres);
    }
	
	@Test
	public void testGetIdTournoi() {
		assertEquals(tournoi1.getIdTournoi(), 10);
	}
	
	@Test
	public void testSetIdEquipe() {
		tournoi1.setIdTournoi(12);
		assertEquals(tournoi1.getIdTournoi(), 12);
	}
	
	@Test
	public void testGetNomTournoi() {
		assertEquals(tournoi1.getNomTournoi(), "Tournoi1");
	}
	
	@Test
	public void testSetNomTournoi() {
		tournoi1.setNomTournoi("Tournoi3");
		assertEquals(tournoi1.getNomTournoi(), "Tournoi3");
	}
	
	@Test
	public void testGetNotoriete() {
		assertEquals(tournoi1.getNotoriete(), Notoriete.INTERNATIONAL);
	}
	
	@Test
	public void testSetNotoriete() {
		tournoi1.setNotoriete(Notoriete.INTERNATIONAL_CLASSE);
		assertEquals(tournoi1.getNotoriete(), Notoriete.INTERNATIONAL_CLASSE);
	}
	
	@Test
	public void testGetDateTimeDebut() {
		assertEquals(tournoi1.getDateTimeDebut(), 100);
	}
	
	@Test
	public void testSetDateTimeDebut() {
		tournoi1.setDateTimeDebut(200);
		assertEquals(tournoi1.getDateTimeDebut(), 200);
	}
	
	@Test
	public void testGetDateTimeFin() {
		assertEquals(tournoi1.getDateTimeFin(), 1000);
	}
	
	@Test
	public void testSetDateTimeFin() {
		tournoi1.setDateTimeFin(2000);
		assertEquals(tournoi1.getDateTimeFin(), 2000);
	}

	@Test
	public void testIsEstCloture() {
		assertEquals(tournoi1.getEstCloture(), false);
	}
	
	@Test
	public void testSetEstCloture() {
		tournoi1.setEstCloture(true);
		assertEquals(tournoi1.getEstCloture(), true);
	}
	
	@Test
	public void testGetIdentifiant() {
		assertEquals(tournoi1.getIdentifiant(), "id");
	}
	
	@Test
	public void testSetIdentifiant() {
		tournoi1.setIdentifiant("identifiant");
		assertEquals(tournoi1.getIdentifiant(), "identifiant");
	}
	
	@Test
	public void testGetMotDePasse() {
		assertEquals(tournoi1.getMotDePasse(), "mdp");
	}
	
	@Test
	public void testSetMotDePasse() {
		tournoi1.setMotDePasse("motdpasse");
		assertEquals(tournoi1.getMotDePasse(), "motdpasse");
	}
	
	@Test
	public void testGetPoules() {
		assertEquals(tournoi1.getPoules(), poules);
	}
	
	@Test
	public void testSetPoules() {
		List<Poule> listPoules = new ArrayList<>(Arrays.asList(
				new Poule(true, true, 11, rencontres),
				new Poule(1, true, false, 10, rencontres)
			));
		tournoi2.setPoules(listPoules);
		assertEquals(tournoi2.getPoules(), listPoules);
	}
	
	@Test
	public void testGetPouleActuelle() {
		Poule pouleTest = new Poule(2, false, false, 10, rencontres);
		assertEquals(tournoi1.getPouleActuelle(),pouleTest);
	}
	
	@Test
	public void testGetPouleActuelleNull() {
		List<Poule> listPoules = new ArrayList<>(Arrays.asList(
				new Poule(true, true, 11, rencontres),
				new Poule(1, true, false, 10, rencontres)
			));
		tournoi2.setPoules(listPoules);
		assertEquals(tournoi2.getPouleActuelle(),null);
	}
	
	@Test
	public void testGetRole() {
		assertEquals(tournoi1.getRole(),Role.ARBITRE);
	}
	
	@Test
	public void testGetEquipes() {
		assertEquals(tournoi1.getEquipes(), equipes);
	}
	
	@Test
	public void testSetEquipes() {
		
		Equipe equipe1 = new Equipe("AutreEquipe1", "Algérie", joueurs);
        Equipe equipe2 = new Equipe("AutreEquipe2", "Maroc", joueurs);
        List<Equipe> autreEquipes = new ArrayList<>(Arrays.asList(equipe1, equipe2));
		
		tournoi1.setEquipes(autreEquipes);
		assertEquals(tournoi1.getEquipes(),autreEquipes);
	}
	
	@Test
	public void testGetArbitres() {
		assertEquals(tournoi1.getArbitres(), arbitres);
	}
	
	@Test
	public void testSetArbitres() {
		
		Arbitre arbitre1 = new Arbitre(60, "Ribéri", "Frank");
        Arbitre arbitre2 = new Arbitre(61, "Ribéro", "Fred");
        List<Arbitre> autreArbitres = new ArrayList<>(Arrays.asList(arbitre1, arbitre2));
		
		tournoi1.setArbitres(autreArbitres);
		assertEquals(tournoi1.getArbitres(),autreArbitres);
	}
	
	@Test
	public void testNotorieteGetLibelle() {
		Notoriete notoriete = Notoriete.INTERNATIONAL;
		assertEquals(notoriete.getLibelle(), "International");
	}
	
	@Test
	public void testNotorieteValueOfLibelle() {
		assertEquals(Notoriete.valueOfLibelle("International"), Notoriete.INTERNATIONAL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotorieteValueOfLibelleInconnu() {
		Notoriete.valueOfLibelle("Inconnu");
	}
	
	@Test
	public void testAddEquipe() {
		Equipe equipeTest = new Equipe(50, "Equipe C", "France", 5, 5, "2020", joueurs);
		tournoi1.addEquipe(equipeTest);
		equipes.add(equipeTest);
		assertEquals(tournoi1.getEquipes(),equipes);
	}
	
	@Test
	public void testRemoveEquipe() {
		tournoi1.removeEquipe(equipeB);
		equipes.remove(equipeB);
		assertEquals(tournoi1.getEquipes(),equipes);
	}
	
	@Test
	public void testGetNbMatchsJoues() {
		assertEquals(tournoi1.getNbMatchsJoues(equipeA),4);
	}
	
	@Test
	public void testGetNbMatchsJouesEquipeInvalide() {
		tournoi1.getPouleActuelle().getRencontres().get(0).setIdEquipeGagnante(0);
		Equipe equipeC = new Equipe(55, "Equipe C", "France", 5, 5, "2020", joueurs);
		assertEquals(tournoi1.getNbMatchsJoues(equipeC),0);
	}
	
	@Test
	public void testGetNbMatchsGagnes() {
		assertEquals(tournoi1.getNbMatchsGagnes(equipeA),2);
	}
	
	@Test
	public void testGetNbMatchsGagnesEquipeInvalide() {
		Equipe equipeC = new Equipe(55, "Equipe C", "France", 5, 5, "2020", joueurs);
		assertEquals(tournoi1.getNbMatchsGagnes(equipeC),0);
	}
}
