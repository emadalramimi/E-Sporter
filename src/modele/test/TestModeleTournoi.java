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
import modele.ModeleTournoiOuverture;
import modele.metier.Arbitre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleTournoi {

	private ModeleTournoi modele;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private ModeleEquipe modeleEquipe;
	private Tournoi tournoi;

	/**
	 * Créée une nouveau ModeleTournoi
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleTournoi();
		this.modeleEquipe = new ModeleEquipe();
		Arbitre arbitre = new Arbitre(1, "Willem", "Miled");

		List<Arbitre> arbitres = new ArrayList<>(Arrays.asList(arbitre));
		this.tournoi = new Tournoi(1, "Tournoi1", Notoriete.INTERNATIONAL, this.getDateCourante() + 500,
				this.getDateCourante() + 1000, false, "Identifiant", "mdp",
				this.modele.getParId(1).orElse(null).getPoules(), this.modele.getParId(1).orElse(null).getEquipes(),
				arbitres);
		this.tournoi.getPoules();
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
		assertNotNull(this.modele.getTout());
		assertEquals(this.modele.getTout().size(), 6);
	}

	/**
	 * Teste la récupération du tournoi dont l'idTournoi est spécifié
	 */
	@Test
	public void testGetParId() throws Exception {
		Tournoi tournoi = this.modele.getParId(1).orElse(null);
		assertNotNull(tournoi);
		assertTrue(this.modele.getTout().contains(tournoi));
	}

	/**
	 * Teste l'ajout d'un tournoi dans la base de données
	 */
	@Test
	public void testAjouter() throws Exception {
		List<Arbitre> arbitres = new ArrayList<>();
		Tournoi tournoi = new Tournoi("Tournoi", Notoriete.INTERNATIONAL, 10000, 10001, "Iden", "mdp", arbitres);
		assertTrue(this.modele.ajouter(tournoi));
	}

	/**
	 * Teste la modification d'un tournoi dans la base de données
	 */
	@Test
	public void testModifier() throws Exception {
		Tournoi tournoi = new Tournoi(7, "Tournoi", Notoriete.INTERNATIONAL, this.getDateCourante() - 3600,
				this.getDateCourante() + 3600, true, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());
		this.modele.ajouter(tournoi);
		tournoi.setNomTournoi("Peu Importe");
		assertTrue(this.modele.modifier(tournoi));
	}

	/**
	 * Teste l'erreur lors de la modification d'un tournoi déja cloturé
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testModifierClotureException() throws Exception {
		Tournoi tournoi = new Tournoi(1, "Tournoi", Notoriete.INTERNATIONAL, this.getDateCourante() - 3600,
				this.getDateCourante() + 3600, false, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());
		this.modele.modifier(tournoi);
	}

	/**
	 * Teste l'erreur lors de la modification d'un tournoi avec une date de fin
	 * inférieure à la courante
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testModifierDateFinException() throws Exception {
		Tournoi tournoi = new Tournoi(1, "Tournoi", Notoriete.INTERNATIONAL, this.getDateCourante() - 3600,
				this.getDateCourante() - 3500, true, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());
		this.modele.modifier(tournoi);
	}

	/**
	 * Teste la suppression d'un tournoi dans la base de données
	 */
	@Test
	public void testSupprimer() throws Exception {
		Tournoi tournoi = new Tournoi(7, "Tournoi", Notoriete.INTERNATIONAL, this.getDateCourante() - 3600,
				this.getDateCourante() + 3600, false, "Iden", "mdp", new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());
		this.modele.ajouter(tournoi);
		tournoi.setEstCloture(true);
		this.modele.supprimer(tournoi);
	}

	/**
	 * Teste l'erreur lors de la suppression d'un tournoi cloturé avec une date de
	 * fin inférieure à la courante
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSupprimerException() throws Exception {
		Tournoi tournoi = this.modele.getParId(6).orElse(null);
		tournoi.setIdTournoi(7);
		this.modele.ajouter(tournoi);
		tournoi.setEstCloture(false);
		tournoi.setDateTimeDebut(this.getDateCourante() - 5000);
		tournoi.setDateTimeFin(this.getDateCourante() - 4000);
		this.modele.supprimer(tournoi);
	}

	/**
	 * Teste le filtrage par nom
	 */
	@Test
	public void testGetParNom() throws Exception {
		assertEquals(this.modele.getParNom("Asia Star Challengers Invitational 2023").size(), 1);
		assertEquals(this.modele.getTout().get(0),
				this.modele.getParNom("Asia Star Challengers Invitational 2023").get(0));
	}

	/**
	 * Teste le filtrage avec une notoriété null et un statut non null
	 */
	@Test
	public void testGetParFiltrageNotorieteNull() throws Exception {
		List<Tournoi> tournois = this.modele.getTout();
		assertEquals(tournois.size(), this.modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).size());

		for (int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), this.modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).get(i));
		}
	}

	/**
	 * Teste le filtrage avec une notoriété non null et un statut null
	 */
	@Test
	public void testGetParFiltrageStatutNull() throws Exception {
		List<Tournoi> tournois = new ArrayList<>();
		tournois.add(this.modele.getParId(3).orElse(null));
		assertEquals(tournois.size(), this.modele.getParFiltrage(Notoriete.NATIONAL, null).size());

		for (int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), this.modele.getParFiltrage(Notoriete.NATIONAL, null).get(i));
		}
	}

	/**
	 * Teste le filtrage avec une notoriété et un statut cloturé
	 */
	@Test
	public void testGetParFiltrageCloture() throws Exception {
		List<Tournoi> tournois = this.modele.getTout();
		tournois.remove(this.modele.getParId(6).orElse(null));
		tournois.remove(this.modele.getParId(3).orElse(null));
		tournois.remove(this.modele.getParId(4).orElse(null));
		for (int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i),
					this.modele.getParFiltrage(Notoriete.INTERNATIONAL, ControleurTournois.Statut.CLOTURE).get(i));
		}
	}

	/**
	 * Teste le filtrage avec une notoriété et un statut ouvert
	 */
	@Test
	public void testGetParFiltrageOuvert() throws Exception {
		Arbitre arbitre = new Arbitre(1, "Willem", "Miled");

		List<Arbitre> arbitres = new ArrayList<>(Arrays.asList(arbitre));

		this.tournoi = new Tournoi(1, "Tournoi1", Notoriete.INTERNATIONAL, this.getDateCourante() + 500,
				this.getDateCourante() + 1000, false, "Identifiant", "mdp",
				this.modele.getParId(1).orElse(null).getPoules(), this.modele.getParId(1).orElse(null).getEquipes(),
				arbitres);
		Tournoi tournoiTest = this.modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600,
				this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.modele.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			this.modeleEquipe.inscrireEquipe(this.modeleEquipe.getTout().get(i), tournoi);
		}

		this.modeleTournoiOuverture.ouvrirTournoi(tournoi);
	}

	/**
	 * Teste le filtrage avec une notoriété et un statut en phase d'inscriptions
	 */
	@Test
	public void testGetParFiltrageInscription() throws Exception {
		Tournoi tournoiTest = this.modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600,
				this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.modele.ajouter(tournoi);
		List<Tournoi> tournois = new ArrayList<>();
		tournois.add(tournoi);
		assertEquals(tournois.size(),
				this.modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).size());
		for (int i = 0; i < tournois.size(); i++) {
			assertEquals(tournois.get(i), this.modele
					.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).get(i));
		}
	}

	/**
	 * Teste la récupération des résultats d'un tournoi
	 */
	@Test
	public void testGetResultatsTournoi() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Tournoi tournoiTest = this.modele.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600,
				this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.modele.ajouter(tournoi);

		List<StatistiquesEquipe> statistiquesEquipes = new ArrayList<>(
				Arrays.asList(new StatistiquesEquipe(modeleEquipe.getParId(1).get(), 0, 0),
						new StatistiquesEquipe(modeleEquipe.getParId(2).get(), 0, 0),
						new StatistiquesEquipe(modeleEquipe.getParId(4).get(), 0, 0),
						new StatistiquesEquipe(modeleEquipe.getParId(3).get(), 0, 0)));

		for (int i = 1; i < 5; i++) {
			modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		}

		this.modeleTournoiOuverture.ouvrirTournoi(tournoi);

		this.modeleTournoiOuverture.ouvrirTournoi(tournoi);
		assertEquals(statistiquesEquipes.size(), this.modele.getResultatsTournoi(tournoi).size());

		for (int i = 1; i < statistiquesEquipes.size(); i++) {
			assertEquals(statistiquesEquipes.get(i), this.modele.getResultatsTournoi(tournoi).get(i));
		}
	}

	// Réinitialise les tournoi
	@After
	public void tearsDown() throws Exception {
		List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for (Tournoi tournoi : this.modele.getTout()) {
			if (!idAGarder.contains(tournoi.getIdTournoi())) {
				this.modele.supprimer(tournoi);
			}
		}
	}
}
