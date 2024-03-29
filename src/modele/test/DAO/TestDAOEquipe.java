package modele.test.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoiOuverture;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;

import modele.exception.InscriptionEquipeTournoiException;
import modele.exception.SaisonException;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Classe de tests de la classe DAOEquipe.
 * @see modele.DAO.DAOEquipe
 */
public class TestDAOEquipe {

	private DAOEquipe daoEquipe;
	private DAOTournoi daoTournoi;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private Equipe equipe;
	private Equipe equipeAModif;
	private List<Joueur> listJoueurs;

	@Before
	public void setUp() throws Exception {
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();

		this.listJoueurs = new ArrayList<>(Arrays.asList(
			new Joueur(
				1, 
				"Joueur1", 
				2
			), 
			new Joueur(
				2, 
				"Joueur2", 
				2
			),
			new Joueur(
				3, "Joueur3", 2), new Joueur(4, "Joueur4", 2), new Joueur(5, "Joueur5", 2)));

		this.equipe = new Equipe(10, "Equipe", Pays.CANADA, 2, 2, "Saison 2023", this.listJoueurs);
		this.equipeAModif = new Equipe(10, "EquipeModif", Pays.FRANCE, 3, 3, "Saison 2024", this.listJoueurs);
	}

	/**
	 * Teste la récupération de toutes les équipes.
	 * @throws Exception
	 * @see DAOEquipe#getTout()
	 */
	@Test
	public void testGetTout() throws Exception {
		assertNotNull(this.daoEquipe.getTout());
		Equipe equipeTest = this.daoEquipe.getParId(1).orElse(null);
		assertNotNull(equipeTest);
		List<Equipe> result = this.daoEquipe.getTout();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(equipeTest, result.get(0));
	}

	/**
	 * Teste la récupération d'une équipe par son id.
	 * @throws Exception
	 * @see DAOEquipe#getParId(int)
	 */
	@Test
	public void testGetParId() throws Exception {
		Equipe equipeTest = this.daoEquipe.getParId(1).orElse(null);
		assertNotNull(equipeTest);
		Optional<Equipe> equipe = this.daoEquipe.getParId(equipeTest.getIdEquipe());
		assertTrue(equipe.isPresent());
		assertNotNull(this.daoEquipe.getParId(1).orElse(null));
		assertEquals(equipeTest, equipe.get());
	}

	/**
	 * Teste l'ajout d'une équipe.
	 * @throws Exception
	 * @see DAOEquipe#ajouter(Equipe)
	 */
	@Test
	public void testAjouter() throws Exception {
		assertTrue(this.daoEquipe.ajouter(this.equipe));
		assertEquals(this.equipe.getWorldRanking(), 1000);
	}

	/**
	 * Teste l'ajout d'une équipe avec une saison antérieure.
	 * @throws Exception
	 * @see DAOEquipe#ajouter(Equipe)
	 */
	@Test
	public void testAjouterSaisonDerniere() throws Exception {
		Equipe equipeOM = this.daoEquipe.getParId(5).orElse(null);
		Equipe equipeAjouter = new Equipe(equipeOM.getNom(), equipeOM.getPays(), equipeOM.getJoueurs());
		this.daoEquipe.ajouter(equipeAjouter);
		Equipe equipeTest = this.daoEquipe.getTout().get(this.daoEquipe.getTout().size() - 1);
		assertEquals(equipeTest.getWorldRanking(), equipeOM.getClassement());
	}

	/**
	 * Teste la modification d'une équipe.
	 * @throws Exception
	 * @see DAOEquipe#modifier(Equipe)
	 */
	@Test
	public void testModifier() throws Exception {
		this.daoEquipe.ajouter(this.equipe);
		assertTrue(this.daoEquipe.modifier(this.equipeAModif));
	}
	
	/**
	 * Teste la modification d'une équipe avec une saison antérieure.
	 * @throws Exception
	 * @see DAOEquipe#estEquipeInscriteUnTournoi(Equipe)
	 */
	@Test
	public void testNEstPasEquipeInscriteUnTournoiOuvert() throws Exception {
		assertFalse(this.daoEquipe.estEquipeInscriteUnTournoi(equipe));
	}
	
	/**
	 * Teste la modification d'une équipe avec une saison antérieure.
	 * @throws Exception
	 * @see DAOEquipe#estEquipeInscriteUnTournoi(Equipe)
	 */
	@Test
	public void testEstEquipeInscriteUnTournoiOuvert() throws Exception {
		Tournoi tournoiTest = daoTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600,
				System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		daoTournoi.ajouter(tournoi);
		this.daoEquipe.inscrireEquipe(this.daoEquipe.getTout().get(0), tournoi);
		assertTrue(this.daoEquipe.estEquipeInscriteUnTournoi(this.daoEquipe.getTout().get(0)));
	}

	/**
	 * Teste la levée d'exception lors de la suppression d'une équipe.
	 * @throws Exception
	 * @see DAOEquipe#supprimer(Equipe)
	 */
	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testSupprimerException() throws Exception {
		Tournoi tournoiTest = daoTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600,
				System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		daoTournoi.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			this.daoEquipe.inscrireEquipe(this.daoEquipe.getTout().get(i), tournoi);
		}

		this.modeleTournoiOuverture.ouvrirTournoi(tournoi);
		this.daoEquipe.supprimer(this.daoEquipe.getParId(1).orElse(null));
	}

	/**
	 * Teste la suppression d'une équipe.
	 * @throws Exception
	 * @see DAOEquipe#supprimer(Equipe)
	 */
	@Test
	public void testSupprimer() throws Exception {
		this.daoEquipe.ajouter(this.equipe);
		assertTrue(this.daoEquipe.supprimer(this.equipe));
	}

	/**
	 * Teste la récupération des équipes d'un tournoi.
	 * @throws Exception
	 * @see DAOEquipe#getEquipesTournoi(int)
	 */
	@Test
	public void testGetEquipesTournoi() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(this.daoEquipe.getParId(1).get(),
				this.daoEquipe.getParId(2).get(), this.daoEquipe.getParId(3).get(), this.daoEquipe.getParId(4).get()));

		assertEquals(equipes, this.daoEquipe.getEquipesTournoi(1));
	}

	/**
	 * Teste la récupération des équipes d'un tournoi.
	 * @throws Exception
	 * @see DAOEquipe#estEquipeInscriteUnTournoi(int)
	 */
	@Test
	public void testEstEquipeInscriteUnTournoi() throws Exception {
		this.daoEquipe.desinscrireEquipe(this.daoEquipe.getParId(1).orElse(null),
				this.daoTournoi.getParId(1).orElse(null));
		this.daoEquipe.inscrireEquipe(this.daoEquipe.getParId(1).orElse(null), this.daoTournoi.getParId(1).orElse(null));
		assertTrue(this.daoEquipe.estEquipeInscriteUnTournoi(this.daoEquipe.getParId(1).orElse(null)));
	}

	/**
	 * Teste la levée d'exception lors de l'inscription d'une équipe déjà inscrite.
	 * @throws Exception
	 * @see DAOEquipe#inscrireEquipe(int)
	 */
	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testInscrireDejaInscrite() throws Exception {
		this.daoEquipe.inscrireEquipe(this.daoEquipe.getParId(1).orElse(null), this.daoTournoi.getParId(1).orElse(null));
		this.daoEquipe.inscrireEquipe(this.daoEquipe.getParId(1).orElse(null), this.daoTournoi.getParId(1).orElse(null));
	}

	/**
	 * Teste la levée d'exception lors de l'inscription d'une équipe d'une saison antérieure.
	 * @throws Exception
	 * @see DAOEquipe#inscrireEquipe(int)
	 */
	@Test(expected = SaisonException.class)
	public void testInscrireSaisonAnterieure() throws Exception {
		this.daoEquipe.inscrireEquipe(this.daoEquipe.getParId(5).orElse(null), this.daoTournoi.getParId(1).orElse(null));
	}

	/**
	 * Teste l'inscription d'une équipe.
	 * @throws Exception
	 * @see DAOEquipe#inscrireEquipe(int)
	 */
	@Test
	public void testInscrire() throws Exception {
		this.daoEquipe.ajouter(this.equipe);
		this.daoEquipe.inscrireEquipe(this.equipe, this.daoTournoi.getParId(1).orElse(null));
	}

	/**
	 * Teste la levée d'exception lors de la désinscription d'une équipe non inscrite.
	 * @throws Exception
	 * @see DAOEquipe#desinscrireEquipe(int)
	 */
	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testDesinscrireNonInscrite() throws Exception {
		this.daoEquipe.desinscrireEquipe(this.equipe, this.daoTournoi.getParId(1).orElse(null));
	}

	/**
	 * Teste la désinscription d'une équipe.
	 * @throws Exception
	 * @see DAOEquipe#desinscrireEquipe(int)
	 */
	@Test
	public void testDesinscrire() throws Exception {
		this.daoEquipe.ajouter(this.equipe);
		this.daoEquipe.inscrireEquipe(this.equipe, this.daoTournoi.getParId(1).orElse(null));
		this.daoEquipe.desinscrireEquipe(this.equipe, this.daoTournoi.getParId(1).orElse(null));
	}

	/**
	 * Teste la récupération des équipes de la saison.
	 * @throws Exception
	 * @see DAOEquipe#getEquipesSaison(int)
	 */
	@Test
	public void testGetEquipesSaison() throws Exception {
		assertNotNull(this.daoEquipe.getEquipesSaison());

		for (int i = 1; i <= 4; i++) {
			assertTrue(this.daoEquipe.getEquipesSaison().contains(this.daoEquipe.getParId(i).orElse(null)));
		}
	}

	/**
	 * Teste la récupération des équipes dans un tableau.
	 * @throws Exception
	 * @see DAOEquipe#getTableauEquipes()
	 */
	@Test
	public void testGetTableauEquipes() throws Exception {
		List<Equipe> equipes = this.daoEquipe.getTout();

		List<Equipe> equipesInscrites = new ArrayList<>(Arrays.asList(this.daoEquipe.getParId(1).orElse(null)));

		Equipe[] equipesEligibles = this.daoEquipe.getTableauEquipes(equipesInscrites);
		assertNotNull(equipes);
		assertNotNull(equipesInscrites);
		assertTrue(equipesEligibles.length != 0);

		for (int i = 0; i < equipesEligibles.length; i++) {
			assertTrue(!equipesInscrites.contains(equipesEligibles[i]));
		}
	}

	/**
	 * Teste le filtrage des équipes.
	 * @throws Exception
	 * @see DAOEquipe#getParFiltrage()
	 */
	@Test
	public void testGetParFiltrage() throws Exception {
		Equipe equipeATester = this.daoEquipe.getParId(1).orElse(null);
		equipeATester.setIdEquipe(6);
		equipeATester.setPays(Pays.ALLEMAGNE);
		this.daoEquipe.ajouter(equipeATester);
		List<Equipe> equipes = new ArrayList<>();
		equipes.add(equipeATester);
		assertEquals(equipes.size(), this.daoEquipe.getParFiltrage(Pays.ALLEMAGNE).size());

		for (int i = 0; i < equipes.size(); i++) {
			assertEquals(equipes.get(i), this.daoEquipe.getParFiltrage(Pays.ALLEMAGNE).get(i));
		}
	}

	/**
	 * Teste le filtrage des équipes.
	 * @throws Exception
	 * @see DAOEquipe#getParFiltrage()
	 */
	@Test
	public void testGetParFiltrageNull() throws Exception {
		Equipe equipeATester = this.daoEquipe.getParId(1).orElse(null);
		equipeATester.setIdEquipe(6);
		equipeATester.setPays(Pays.ALLEMAGNE);
		this.daoEquipe.ajouter(equipeATester);
		assertEquals(this.daoEquipe.getEquipesSaison().size(), this.daoEquipe.getParFiltrage(null).size());

		for (int i = 0; i < this.daoEquipe.getEquipesSaison().size(); i++) {
			assertEquals(this.daoEquipe.getEquipesSaison().get(i), this.daoEquipe.getParFiltrage(null).get(i));
		}
	}

	@After
	public void tearsDown() throws Exception {
		List<Integer> idsAGarder = Arrays.asList(1, 2, 3, 4, 5);

		for (Equipe equipe : this.daoEquipe.getTout()) {
			if (!idsAGarder.contains(equipe.getIdEquipe())) {
				if (this.daoEquipe.estEquipeInscriteUnTournoi(equipe)) {
					for (Tournoi tournoi : daoTournoi.getTout()) {
						if (this.daoEquipe.getEquipesTournoi(tournoi.getIdTournoi()).contains(equipe)) {
							this.daoEquipe.desinscrireEquipe(equipe, tournoi);
						}
					}
				}

				this.daoEquipe.supprimer(equipe);
			}
		}

		List<Integer> idsAGarderTournoi = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

		for (Tournoi tournoi : daoTournoi.getTout()) {
			if (!idsAGarderTournoi.contains(tournoi.getIdTournoi())) {
				daoTournoi.supprimer(tournoi);
			}
		}
	}
}