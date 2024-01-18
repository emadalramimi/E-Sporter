package modele.test.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoiOuverture;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.DatesTournoiException;
import modele.exception.OuvertureTournoiException;
import modele.exception.TournoiDejaOuvertException;
import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleTournoiOuverture {

	private ModeleTournoiOuverture modele;
	private DAOTournoi daoTournoi;
	private DAOEquipe daoEquipe;

	/**
	 * Créée une nouveau ModeleTournoi
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleTournoiOuverture();
		this.daoTournoi = new DAOTournoiImpl();
		this.daoEquipe = new DAOEquipeImpl();
	}

	/**
	 * Renvoie la date courante en secondes
	 */
	private long getDateCourante() {
		return (System.currentTimeMillis() / 1000);
	}

	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi cloturé
	 * 
	 * @throws OuvertureTournoiException
	 */
	@Test(expected = OuvertureTournoiException.class)
	public void testOuvrirTournoiTournoiOuvert() throws Exception {
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() - 3600,
				this.getDateCourante() + 3600, false, "arbitre1", "password", null, null,
				null);
		this.modele.ouvrirTournoi(tournoi);
	}
	
	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi avec une date de fin
	 * inférieure à la courante
	 * 
	 * @throws DatesTournoiException
	 */
	@Test(expected = DatesTournoiException.class)
	public void testOuvrirTournoiDateFinPassee() throws Exception {
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() - 7200,
				this.getDateCourante() - 3600, true, "arbitre2", "password", null, null,
				null);
		this.modele.ouvrirTournoi(tournoi);
	}

	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi avec un nombre d'équipes
	 * inférieur à 4
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiNombreEquipesInf4() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(new Equipe("Nom1", Pays.FRANCE, new ArrayList<>()),
				new Equipe("Nom2", Pays.FRANCE, null),
				new Equipe("Nom3", Pays.FRANCE, null)));

		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600,
				this.getDateCourante() + 7200, true, "arbitre3", "password", null, equipes,
				null);
		this.modele.ouvrirTournoi(tournoi);
	}

	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi avec un nombre d'équipes
	 * supérieur à 8
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testOuvrirTournoiNombreEquipesSup8() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(new Equipe("Nom1", Pays.FRANCE, null),
				new Equipe("Nom2", Pays.FRANCE, null), new Equipe("Nom3", Pays.FRANCE, null),
				new Equipe("Nom4", Pays.FRANCE, null), new Equipe("Nom5", Pays.FRANCE, null),
				new Equipe("Nom6", Pays.FRANCE, null), new Equipe("Nom7", Pays.FRANCE, null),
				new Equipe("Nom8", Pays.FRANCE, null),
				new Equipe("Nom9", Pays.FRANCE, null)));

		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600,
				this.getDateCourante() + 7200, true, "arbitre3", "password", new ArrayList<>(), equipes,
				new ArrayList<>());
		this.daoTournoi.ajouter(tournoi);
		this.modele.ouvrirTournoi(tournoi);
	}

	/**
	 * Teste l'erreur lors de l'ouverture d'un tournoi lorsqu'un autre tournoi est
	 * déjà ouvert
	 * 
	 * @throws TournoiDejaOuvertException
	 */
	@Test(expected = TournoiDejaOuvertException.class)
	public void testOuvrirTournoiTournoiEnCours() throws Exception {
		Tournoi tournoiTest = this.daoTournoi.getParId(1).orElse(null);
		Tournoi tournoi1 = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3200,
				this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.daoTournoi.ajouter(tournoi1);

		for (int i = 0; i < 4; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getTout().get(i), tournoi1);
		}

		this.daoTournoi.ajouter(tournoi1);
		Tournoi tournoi2 = new Tournoi(8, "TournoiTest2", Notoriete.NATIONAL, this.getDateCourante() + 3300,
				this.getDateCourante() + 7600, true, "arbitre2", "password2", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.daoTournoi.ajouter(tournoi2);

		for (int i = 0; i < 4; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getTout().get(i), tournoi2);
		}

		this.daoTournoi.ajouter(tournoi2);
		this.modele.ouvrirTournoi(tournoi1);
		this.modele.ouvrirTournoi(tournoi2);
	}

	/**
	 * Teste l'ouverture d'un tournoi
	 */
	@Test
	public void testOuvrirTournoi() throws Exception {
		Tournoi tournoiTest = this.daoTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600,
				this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.daoTournoi.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getTout().get(i), tournoi);
		}

		this.modele.ouvrirTournoi(tournoi);
	}

	/**
	 * Teste l'ouverture d'un tournoi avec une date de début antérieure à celle
	 * courante
	 */
	@Test
	public void testOuvrirTournoiDatePassee() throws Exception {
		Tournoi tournoiTest = this.daoTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() - 3600,
				this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		this.daoTournoi.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getTout().get(i), tournoi);
		}

		this.modele.ouvrirTournoi(tournoi);
	}
	
	// Réinitialise les tournoi
	@After
	public void tearsDown() throws Exception {
		List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for (Tournoi tournoi : this.daoTournoi.getTout()) {
			if (!idAGarder.contains(tournoi.getIdTournoi())) {
				this.daoTournoi.supprimer(tournoi);
			}
		}
	}
}