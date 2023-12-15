package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Poule;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.metier.Utilisateur.Role;

public class TestTournoi {
	
	private Tournoi tournoi1;
	private Tournoi tournoi2;
	private List<Poule> poules;
	private List<Joueur> joueurs;
	private List<Equipe> equipes;
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
    	
        Equipe equipeA = new Equipe("Equipe1", "Canada", joueurs);
        Equipe equipeB = new Equipe("Equipe2", "France", joueurs);
        equipes = new ArrayList<>(Arrays.asList(equipeA, equipeB));
        
        arbitres = new ArrayList<>(Arrays.asList(
			    new Arbitre(1, "Nom1", "Prenom1"),
			    new Arbitre(2, "Nom2", "Prenom2")
			));
		
		tournoi1 = new Tournoi(10, "Tournoi1", Notoriete.INTERNATIONAL, 100, 1000, false, "id", "mdp", equipes, arbitres);
		poules = new ArrayList<>(Arrays.asList(
				new Poule(10, false, false, 10),
				new Poule(11, false, true, 10)
			));
		tournoi1.setPoules(poules);
		tournoi2 = new Tournoi("Tournoi2", Notoriete.INTERNATIONAL, 101, 1001, false, "id", "mdp", equipes, arbitres);
    }
	
	@Test
	public void testGetIdTournoi() {
		assertEquals(tournoi1.getIdTournoi(), 10);
		assertEquals(tournoi2.getIdTournoi(), 0);
		assertNotEquals(tournoi1.getIdTournoi(), tournoi2.getIdTournoi());
	}
	
	@Test
	public void testSetIdEquipe() {
		tournoi1.setIdTournoi(12);
		tournoi2.setIdTournoi(13);
		assertEquals(tournoi1.getIdTournoi(), 12);
		assertEquals(tournoi2.getIdTournoi(), 13);
		assertNotEquals(tournoi1.getIdTournoi(), tournoi2.getIdTournoi());
	}
	
	@Test
	public void testGetNomTournoi() {
		assertEquals(tournoi1.getNomTournoi(), "Tournoi1");
		assertEquals(tournoi2.getNomTournoi(), "Tournoi2");
		assertNotEquals(tournoi1.getNomTournoi(), tournoi2.getNomTournoi());
	}
	
	@Test
	public void testSetNomTournoi() {
		tournoi1.setNomTournoi("Tournoi3");
		tournoi2.setNomTournoi("Tournoi4");
		assertEquals(tournoi1.getNomTournoi(), "Tournoi3");
		assertEquals(tournoi2.getNomTournoi(), "Tournoi4");
		assertNotEquals(tournoi1.getNomTournoi(), tournoi2.getNomTournoi());
	}
	
	@Test
	public void testGetNotoriete() {
		assertEquals(tournoi1.getNotoriete(), Notoriete.INTERNATIONAL);
		assertEquals(tournoi2.getNotoriete(), Notoriete.INTERNATIONAL);
		assertEquals(tournoi1.getNotoriete(), tournoi2.getNotoriete());
	}
	
	@Test
	public void testSetNotoriete() {
		tournoi1.setNotoriete(Notoriete.INTERNATIONAL_CLASSE);
		tournoi2.setNotoriete(Notoriete.REGIONAL);
		assertEquals(tournoi1.getNotoriete(), Notoriete.INTERNATIONAL_CLASSE);
		assertEquals(tournoi2.getNotoriete(), Notoriete.REGIONAL);
		assertNotEquals(tournoi1.getNotoriete(), tournoi2.getNotoriete());
	}
	
	@Test
	public void testGetDateDebut() {
		assertEquals(tournoi1.getDateDebut(), 100);
		assertEquals(tournoi2.getDateDebut(), 101);
		assertNotEquals(tournoi1.getDateDebut(), tournoi2.getDateDebut());
	}
	
	@Test
	public void testSetDateDebut() {
		tournoi1.setDateDebut(200);
		tournoi2.setDateDebut(200);
		assertEquals(tournoi1.getDateDebut(), 200);
		assertEquals(tournoi2.getDateDebut(), 200);
		assertEquals(tournoi1.getDateDebut(), tournoi2.getDateDebut());
	}
	
	@Test
	public void testGetDateFin() {
		assertEquals(tournoi1.getDateFin(), 1000);
		assertEquals(tournoi2.getDateFin(), 1001);
		assertNotEquals(tournoi1.getDateFin(), tournoi2.getDateFin());
	}
	
	@Test
	public void testSetDateFin() {
		tournoi1.setDateFin(2000);
		tournoi2.setDateFin(2001);
		assertEquals(tournoi1.getDateFin(), 2000);
		assertEquals(tournoi2.getDateFin(), 2001);
		assertNotEquals(tournoi1.getDateFin(), tournoi2.getDateFin());
	}

	@Test
	public void testIsEstCloture() {
		assertEquals(tournoi1.getEstCloture(), false);
		assertEquals(tournoi2.getEstCloture(), false);
		assertEquals(tournoi1.getEstCloture(), tournoi2.getEstCloture());
	}
	
	@Test
	public void testSetEstCloture() {
		tournoi1.setEstCloture(true);
		tournoi2.setEstCloture(true);
		assertEquals(tournoi1.getEstCloture(), true);
		assertEquals(tournoi2.getEstCloture(), true);
		assertEquals(tournoi1.getEstCloture(), tournoi2.getEstCloture());
	}
	
	@Test
	public void testGetIdentifiant() {
		assertEquals(tournoi1.getIdentifiant(), "id");
		assertEquals(tournoi2.getIdentifiant(), "id");
		assertEquals(tournoi1.getIdentifiant(), tournoi2.getIdentifiant());
	}
	
	@Test
	public void testSetIdentifiant() {
		tournoi1.setIdentifiant("identifiant");
		tournoi2.setIdentifiant("iden");
		assertEquals(tournoi1.getIdentifiant(), "identifiant");
		assertEquals(tournoi2.getIdentifiant(), "iden");
		assertNotEquals(tournoi1.getIdentifiant(), tournoi2.getIdentifiant());
	}
	
	@Test
	public void testGetMotDePasse() {
		assertEquals(tournoi1.getMotDePasse(), "mdp");
		assertEquals(tournoi2.getMotDePasse(), "mdp");
		assertEquals(tournoi1.getMotDePasse(), tournoi2.getMotDePasse());
	}
	
	@Test
	public void testSetMotDePasse() {
		tournoi1.setMotDePasse("motdpasse");
		tournoi2.setMotDePasse("motdepasse");
		assertEquals(tournoi1.getMotDePasse(), "motdpasse");
		assertEquals(tournoi2.getMotDePasse(), "motdepasse");
		assertNotEquals(tournoi1.getMotDePasse(), tournoi2.getMotDePasse());
	}
	
	@Test
	public void testGetPoules() {
		assertEquals(tournoi1.getPoules(), poules);
	}
	
	@Test
	public void testSetPoules() {
		List<Poule> listPoules = new ArrayList<Poule>(Arrays.asList(
				new Poule(10, false, false, 10)
			));
		tournoi2.setPoules(listPoules);
		assertEquals(tournoi2.getPoules(), listPoules);
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
}
