package DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Poule;
import modele.metier.Rencontre;

/**
 * Classe de tests de la classe DAOPoule.
 * @see modele.DAO.DAOPoule
 */
public class TestDAOPoule {

	private DAOPoule daoPoule;
	private Poule poule;
	private List<Rencontre> rencontres;

	@Before
	public void setUp() throws Exception {
		this.daoPoule = new DAOPouleImpl();

		List<Joueur> joueurs = new ArrayList<>(Arrays.asList(
			new Joueur(1, "Joueur1", 2), 
			new Joueur(2, "Joueur2", 2),
			new Joueur(3, "Joueur3", 2), 
			new Joueur(4, "Joueur4", 2), 
			new Joueur(5, "Joueur5", 2)
		));

		Equipe equipeA = new Equipe(1, "Equipe A", Pays.FRANCE, 5, 5, "2020", joueurs);
		Equipe equipeB = new Equipe(2, "Equipe B", Pays.MAROC, 5, 5, "2020", joueurs);
		Equipe[] equipes = { equipeA, equipeB };

		this.rencontres = new ArrayList<>(Arrays.asList(
			new Rencontre(1, 1000, 10000, equipes), 
			new Rencontre(2, 1050, 10050, equipes)
		));

		this.poule = new Poule(false, false, 2, rencontres);
	}

	/**
	 * Teste la récupération de toutes les poules.
	 * @throws Exception
	 * @see modele.DAO.DAOPoule#getTout()
	 */
	@Test
	public void testGetTout() throws Exception {
		assertNotNull(this.daoPoule.getTout());
		assertEquals(this.daoPoule.getTout().size(), 12);
		List<Poule> poules = this.daoPoule.getTout();
		assertEquals(this.daoPoule.getTout().size(), poules.size());
	}

	/**
	 * Teste l'ajout d'une poule.
	 * @throws Exception
	 * @see modele.DAO.DAOPoule#ajouter(Poule)
	 */
	@Test
	public void testAjouter() throws Exception {
		assertTrue(this.daoPoule.ajouter(this.poule));
	}

	/**
	 * Teste la suppression d'une poule.
	 * @throws Exception
	 * @see modele.DAO.DAOPoule#supprimer(Poule)
	 */
	@Test
	public void testSupprimer() throws Exception {
		this.daoPoule.ajouter(this.poule);
		assertTrue(this.daoPoule.supprimer(this.poule));
	}

	/**
	 * Teste la récupération des poules d'un tournoi.
	 * @throws Exception
	 * @see modele.DAO.DAOPoule#getPoulesTournoi(int)
	 */
	@Test
	public void testGetPoulesTournoi() throws Exception {
		List<Poule> listPoules = new ArrayList<>(Arrays.asList(
			new Poule(1, true, false, 1, this.rencontres),
			new Poule(2, true, true, 1, this.rencontres)
		));

		assertEquals(this.daoPoule.getPoulesTournoi(1), listPoules);
	}

	@After
	public void tearsDown() throws Exception {
		List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
		for (Poule poule : this.daoPoule.getTout()) {
			if (!idAGarder.contains(poule.getIdPoule())) {
				this.daoPoule.supprimer(poule);
			}
		}
	}

}