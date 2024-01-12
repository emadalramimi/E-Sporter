package modele.test;

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

import modele.ModeleEquipe;
import modele.ModeleTournoi;
import modele.ModeleTournoiOuverture;
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
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private Equipe equipe;
	private Equipe equipeAModif;
	private List<Joueur> listJoueurs;

	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleEquipe();
		this.modeleTournoi = new ModeleTournoi();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();

		this.listJoueurs = new ArrayList<>(Arrays.asList(new Joueur(1, "Joueur1", 2), new Joueur(2, "Joueur2", 2),
				new Joueur(3, "Joueur3", 2), new Joueur(4, "Joueur4", 2), new Joueur(5, "Joueur5", 2)));

		this.equipe = new Equipe(10, "Equipe", Pays.CANADA, 2, 2, "Saison 2023", this.listJoueurs);
		this.equipeAModif = new Equipe(10, "EquipeModif", Pays.FRANCE, 3, 3, "Saison 2024", this.listJoueurs);
	}

	@Test
	public void testGetTout() throws Exception {
		assertNotNull(this.modele.getTout());
		Equipe equipeTest = this.modele.getParId(1).orElse(null);
		assertNotNull(equipeTest);
		List<Equipe> result = this.modele.getTout();
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(equipeTest, result.get(0));
	}

	@Test
	public void testGetParId() throws Exception {
		Equipe equipeTest = this.modele.getParId(1).orElse(null);
		assertNotNull(equipeTest);
		Optional<Equipe> equipe = this.modele.getParId(equipeTest.getIdEquipe());
		assertTrue(equipe.isPresent());
		assertNotNull(this.modele.getParId(1).orElse(null));
		assertEquals(equipeTest, equipe.get());
	}

	@Test
	public void testAjouterTrue() throws Exception {
		assertTrue(this.modele.ajouter(this.equipe));
		assertEquals(this.equipe.getWorldRanking(), 1000);
	}

	@Test
	public void testAjouterSaisonDerniere() throws Exception {
		Equipe equipeOM = this.modele.getParId(5).orElse(null);
		Equipe equipeAjouter = new Equipe(equipeOM.getNom(), equipeOM.getPays(), equipeOM.getJoueurs());
		this.modele.ajouter(equipeAjouter);
		Equipe equipeTest = this.modele.getTout().get(this.modele.getTout().size() - 1);
		assertEquals(equipeTest.getWorldRanking(), equipeOM.getClassement());
	}

	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testModifierException() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
		Tournoi tournoiTest = modeleTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600,
				System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modeleTournoi.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			this.modele.inscrireEquipe(this.modele.getTout().get(i), tournoi);
		}

		this.modeleTournoiOuverture.ouvrirTournoi(tournoi);
		Equipe equipeAModif = this.modele.getParId(1).orElse(null);
		equipeAModif.setNom("Coucou");
		this.modele.modifier(equipeAModif);
	}

	@Test
	public void testModifierTrue() throws Exception {
		this.modele.ajouter(this.equipe);
		assertTrue(this.modele.modifier(this.equipeAModif));
	}

	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testSupprimerException() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
		Tournoi tournoiTest = modeleTournoi.getParId(1).orElse(null);
		Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, System.currentTimeMillis() / 1000 + 3600,
				System.currentTimeMillis() / 1000 + 7200, true, "arbitre", "password", tournoiTest.getPoules(),
				tournoiTest.getEquipes(), tournoiTest.getArbitres());
		modeleTournoi.ajouter(tournoi);

		for (int i = 0; i < 4; i++) {
			this.modele.inscrireEquipe(this.modele.getTout().get(i), tournoi);
		}

		this.modeleTournoiOuverture.ouvrirTournoi(tournoi);
		this.modele.supprimer(this.modele.getParId(1).orElse(null));
	}

	@Test
	public void testSupprimerTrue() throws Exception {
		this.modele.ajouter(this.equipe);
		assertTrue(this.modele.supprimer(this.equipe));
	}

	@Test
	public void testGetEquipesTournoi() throws Exception {
		List<Equipe> equipes = new ArrayList<>(Arrays.asList(this.modele.getParId(1).get(),
				this.modele.getParId(2).get(), this.modele.getParId(3).get(), this.modele.getParId(4).get()));

		assertEquals(equipes, this.modele.getEquipesTournoi(1));
	}

	@Test
	public void testEstEquipeInscriteUnTournoi() throws Exception {
		this.modele.desinscrireEquipe(this.modele.getParId(1).orElse(null),
				this.modeleTournoi.getParId(1).orElse(null));
		this.modele.inscrireEquipe(this.modele.getParId(1).orElse(null), this.modeleTournoi.getParId(1).orElse(null));
		assertTrue(this.modele.estEquipeInscriteUnTournoi(this.modele.getParId(1).orElse(null)));
	}

	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testInscrireDejaInscrite() throws Exception {
		this.modele.inscrireEquipe(this.modele.getParId(1).orElse(null), this.modeleTournoi.getParId(1).orElse(null));
		this.modele.inscrireEquipe(this.modele.getParId(1).orElse(null), this.modeleTournoi.getParId(1).orElse(null));
	}

	@Test(expected = SaisonException.class)
	public void testInscrireSaisonAnterieure() throws Exception {
		this.modele.inscrireEquipe(this.modele.getParId(5).orElse(null), this.modeleTournoi.getParId(1).orElse(null));
	}

	@Test
	public void testInscrire() throws Exception {
		this.modele.ajouter(this.equipe);
		this.modele.inscrireEquipe(this.equipe, this.modeleTournoi.getParId(1).orElse(null));
	}

	@Test(expected = InscriptionEquipeTournoiException.class)
	public void testDesinscrireNonInscrite() throws Exception {
		this.modele.desinscrireEquipe(this.equipe, this.modeleTournoi.getParId(1).orElse(null));
	}

	@Test
	public void testDesinscrire() throws Exception {
		this.modele.ajouter(this.equipe);
		this.modele.inscrireEquipe(this.equipe, this.modeleTournoi.getParId(1).orElse(null));
		this.modele.desinscrireEquipe(this.equipe, this.modeleTournoi.getParId(1).orElse(null));
	}

	@Test
	public void testGetParNom() throws Exception {
		this.modele.ajouter(this.equipe);
		assertNotNull(this.modele.getParNom(this.equipe.getNom()));
		assertTrue(this.modele.getParNom(this.equipe.getNom()).contains(this.equipe));
	}

	@Test
	public void testGetEquipesSaison() throws Exception {
		assertNotNull(this.modele.getEquipesSaison());

		for (int i = 1; i <= 4; i++) {
			assertTrue(this.modele.getEquipesSaison().contains(this.modele.getParId(i).orElse(null)));
		}
	}

	@Test
	public void testGetTableauEquipes() throws Exception {
		List<Equipe> equipes = this.modele.getTout();

		List<Equipe> equipesInscrites = new ArrayList<>(Arrays.asList(this.modele.getParId(1).orElse(null)));

		Equipe[] equipesEligibles = this.modele.getTableauEquipes(equipesInscrites);
		assertNotNull(equipes);
		assertNotNull(equipesInscrites);
		assertTrue(equipesEligibles.length != 0);

		for (int i = 0; i < equipesEligibles.length; i++) {
			assertTrue(!equipesInscrites.contains(equipesEligibles[i]));
		}
	}

	@Test
	public void testGetParFiltrage() throws Exception {
		Equipe equipeATester = this.modele.getParId(1).orElse(null);
		equipeATester.setIdEquipe(6);
		equipeATester.setPays(Pays.ALLEMAGNE);
		this.modele.ajouter(equipeATester);
		List<Equipe> equipes = new ArrayList<>();
		equipes.add(equipeATester);
		assertEquals(equipes.size(), this.modele.getParFiltrage(Pays.ALLEMAGNE).size());

		for (int i = 0; i < equipes.size(); i++) {
			assertEquals(equipes.get(i), this.modele.getParFiltrage(Pays.ALLEMAGNE).get(i));
		}
	}

	@Test
	public void testGetParFiltrageNull() throws Exception {
		Equipe equipeATester = this.modele.getParId(1).orElse(null);
		equipeATester.setIdEquipe(6);
		equipeATester.setPays(Pays.ALLEMAGNE);
		this.modele.ajouter(equipeATester);
		assertEquals(this.modele.getEquipesSaison().size(), this.modele.getParFiltrage(null).size());

		for (int i = 0; i < this.modele.getEquipesSaison().size(); i++) {
			assertEquals(this.modele.getEquipesSaison().get(i), this.modele.getParFiltrage(null).get(i));
		}
	}

	@After
	public void tearsDown() throws Exception {
		ModeleTournoi modeleTournoi = new ModeleTournoi();
		List<Integer> idsAGarder = Arrays.asList(1, 2, 3, 4, 5);

		for (Equipe equipe : this.modele.getTout()) {
			if (!idsAGarder.contains(equipe.getIdEquipe())) {
				if (this.modele.estEquipeInscriteUnTournoi(equipe)) {
					for (Tournoi tournoi : modeleTournoi.getTout()) {
						if (this.modele.getEquipesTournoi(tournoi.getIdTournoi()).contains(equipe)) {
							this.modele.desinscrireEquipe(equipe, tournoi);
						}
					}
				}

				this.modele.supprimer(equipe);
			}
		}

		List<Integer> idsAGarderTournoi = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));

		for (Tournoi tournoi : modeleTournoi.getTout()) {
			if (!idsAGarderTournoi.contains(tournoi.getIdTournoi())) {
				modeleTournoi.supprimer(tournoi);
			}
		}
	}
}