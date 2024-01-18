package modele.test.metier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.metier.Utilisateur.Role;

/**
 * Classe de tests de la classe Tournoi.
 * @see modele.metier.Tournoi
 */
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

		this.joueurs = new ArrayList<>(Arrays.asList(new Joueur(1, "Joueur1", 2), new Joueur(2, "Joueur2", 2),
				new Joueur(3, "Joueur3", 2), new Joueur(4, "Joueur4", 2), new Joueur(5, "Joueur5", 2)));

		this.equipeA = new Equipe(50, "Equipe A", Pays.FRANCE, 5, 5, "2020", this.joueurs);
		this.equipeB = new Equipe(51, "Equipe B", Pays.MAROC, 5, 5, "2020", this.joueurs);
		Equipe[] equipeArray = { this.equipeA, this.equipeB };
		this.equipes = new ArrayList<>(Arrays.asList(this.equipeA, this.equipeB));
		this.rencontres = new ArrayList<>(
				Arrays.asList(new Rencontre(50, 1, 50, equipeArray), new Rencontre(51, 1, 51, equipeArray)));

		this.arbitres = new ArrayList<>(
				Arrays.asList(new Arbitre(1, "Nom1", "Prenom1"), new Arbitre(2, "Nom2", "Prenom2")));
		this.poules = new ArrayList<>(Arrays.asList(new Poule(1, true, true, 11, this.rencontres),
				new Poule(2, false, false, 10, this.rencontres)));
		this.tournoi1 = new Tournoi(10, "Tournoi1", Notoriete.INTERNATIONAL, 100, 1000, false, "id", "mdp", this.poules,
				this.equipes, this.arbitres);
		this.tournoi2 = new Tournoi("Tournoi2", Notoriete.INTERNATIONAL, 101, 1001, "id", "mdp", this.arbitres);
	}

	@Test
	public void testGetIdTournoi() {
		assertEquals(this.tournoi1.getIdTournoi(), 10);
	}

	@Test
	public void testSetIdEquipe() {
		this.tournoi1.setIdTournoi(12);
		assertEquals(this.tournoi1.getIdTournoi(), 12);
	}

	@Test
	public void testGetNomTournoi() {
		assertEquals(this.tournoi1.getNomTournoi(), "Tournoi1");
	}

	@Test
	public void testSetNomTournoi() {
		this.tournoi1.setNomTournoi("Tournoi3");
		assertEquals(this.tournoi1.getNomTournoi(), "Tournoi3");
	}

	@Test
	public void testGetNotoriete() {
		assertEquals(this.tournoi1.getNotoriete(), Notoriete.INTERNATIONAL);
	}

	@Test
	public void testSetNotoriete() {
		this.tournoi1.setNotoriete(Notoriete.INTERNATIONAL_CLASSE);
		assertEquals(this.tournoi1.getNotoriete(), Notoriete.INTERNATIONAL_CLASSE);
	}

	@Test
	public void testGetDateTimeDebut() {
		assertEquals(this.tournoi1.getDateTimeDebut(), 100);
	}

	@Test
	public void testSetDateTimeDebut() {
		this.tournoi1.setDateTimeDebut(200);
		assertEquals(this.tournoi1.getDateTimeDebut(), 200);
	}

	@Test
	public void testGetDateTimeFin() {
		assertEquals(this.tournoi1.getDateTimeFin(), 1000);
	}

	@Test
	public void testSetDateTimeFin() {
		this.tournoi1.setDateTimeFin(2000);
		assertEquals(this.tournoi1.getDateTimeFin(), 2000);
	}

	@Test
	public void testIsEstCloture() {
		assertEquals(this.tournoi1.getEstCloture(), false);
	}

	@Test
	public void testSetEstCloture() {
		this.tournoi1.setEstCloture(true);
		assertEquals(this.tournoi1.getEstCloture(), true);
	}

	@Test
	public void testGetIdentifiant() {
		assertEquals(this.tournoi1.getIdentifiant(), "id");
	}

	@Test
	public void testSetIdentifiant() {
		this.tournoi1.setIdentifiant("identifiant");
		assertEquals(this.tournoi1.getIdentifiant(), "identifiant");
	}

	@Test
	public void testGetMotDePasse() {
		assertEquals(this.tournoi1.getMotDePasse(), "mdp");
	}

	@Test
	public void testSetMotDePasse() {
		this.tournoi1.setMotDePasse("motdpasse");
		assertEquals(this.tournoi1.getMotDePasse(), "motdpasse");
	}

	@Test
	public void testGetPoules() {
		assertEquals(this.tournoi1.getPoules(), this.poules);
	}

	@Test
	public void testSetPoules() {
		List<Poule> listPoules = new ArrayList<>(Arrays.asList(new Poule(true, true, 11, this.rencontres),
				new Poule(1, true, false, 10, this.rencontres)));
		this.tournoi2.setPoules(listPoules);
		assertEquals(this.tournoi2.getPoules(), listPoules);
	}

	@Test
	public void testGetPouleActuelle() {
		Poule pouleTest = new Poule(2, false, false, 10, this.rencontres);
		assertEquals(this.tournoi1.getPouleActuelle(), pouleTest);
	}

	@Test
	public void testGetPouleActuelleNull() {
		List<Poule> listPoules = new ArrayList<>(Arrays.asList(new Poule(true, true, 11, this.rencontres),
				new Poule(1, true, false, 10, this.rencontres)));
		this.tournoi2.setPoules(listPoules);
		assertEquals(this.tournoi2.getPouleActuelle(), null);
	}

	@Test
	public void testGetRole() {
		assertEquals(this.tournoi1.getRole(), Role.ARBITRE);
	}

	@Test
	public void testGetEquipes() {
		assertEquals(this.tournoi1.getEquipes(), this.equipes);
	}

	@Test
	public void testSetEquipes() {

		Equipe equipe1 = new Equipe("AutreEquipe1", Pays.ALGERIE, this.joueurs);
		Equipe equipe2 = new Equipe("AutreEquipe2", Pays.MAROC, this.joueurs);
		List<Equipe> autreEquipes = new ArrayList<>(Arrays.asList(equipe1, equipe2));

		this.tournoi1.setEquipes(autreEquipes);
		assertEquals(this.tournoi1.getEquipes(), autreEquipes);
	}

	@Test
	public void testGetArbitres() {
		assertEquals(this.tournoi1.getArbitres(), this.arbitres);
	}

	@Test
	public void testSetArbitres() {

		Arbitre arbitre1 = new Arbitre(60, "Ribéri", "Frank");
		Arbitre arbitre2 = new Arbitre(61, "Ribéro", "Fred");
		List<Arbitre> autreArbitres = new ArrayList<>(Arrays.asList(arbitre1, arbitre2));

		this.tournoi1.setArbitres(autreArbitres);
		assertEquals(this.tournoi1.getArbitres(), autreArbitres);
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
		Equipe equipeTest = new Equipe(50, "Equipe C", Pays.FRANCE, 5, 5, "2020", this.joueurs);
		this.tournoi1.addEquipe(equipeTest);
		this.equipes.add(equipeTest);
		assertEquals(this.tournoi1.getEquipes(), this.equipes);
	}

	@Test
	public void testRemoveEquipe() {
		this.tournoi1.removeEquipe(this.equipeB);
		this.equipes.remove(this.equipeB);
		assertEquals(this.tournoi1.getEquipes(), this.equipes);
	}
}
