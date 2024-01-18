package modele.test.DAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Arbitre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.test.SuperTest;

/**
 * Classe de tests de la classe DAOTournoi.
 * @see modele.DAO.DAOTournoi
 */
public class TestDAOTournoi extends SuperTest {

	private DAOTournoi daoTournoi;
	private Tournoi tournoi;

	@Before
	public void setUp() throws Exception {
		this.daoTournoi = new DAOTournoiImpl();
		Arbitre arbitre = new Arbitre(1, "Willem", "Miled");

		List<Arbitre> arbitres = new ArrayList<>(Arrays.asList(arbitre));
		this.tournoi = new Tournoi(
			1, 
			"Tournoi1", 
			Notoriete.INTERNATIONAL, 
			this.getDateCourante() + 500,
			this.getDateCourante() + 1000, 
			false, 
			"Identifiant", 
			"mdp",
			this.daoTournoi.getParId(1).orElse(null).getPoules(), 
			this.daoTournoi.getParId(1).orElse(null).getEquipes(),
			arbitres
		);

		this.tournoi.getPoules();
	}

	/**
	 * Teste la récupération de tous les tournois
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#getTout()
	 */
	@Test
	public void testGetTout() throws Exception {
		assertNotNull(this.daoTournoi.getTout());
		assertEquals(this.daoTournoi.getTout().size(), 6);
	}

	/**
	 * Teste la récupération d'un tournoi par son identifiant
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#getParId(int)
	 */
	@Test
	public void testGetParId() throws Exception {
		Tournoi tournoi = this.daoTournoi.getParId(1).orElse(null);
		assertNotNull(tournoi);
		assertTrue(this.daoTournoi.getTout().contains(tournoi));
	}

	/**
	 * Teste l'ajout d'un tournoi dans la base de données
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#ajouter(Tournoi)
	 */
	@Test
	public void testAjouter() throws Exception {
		List<Arbitre> arbitres = new ArrayList<>();

		Tournoi tournoi = new Tournoi(
			"Tournoi", 
			Notoriete.INTERNATIONAL,
			10000, 
			10001, 
			"Iden", 
			"mdp", 
			arbitres
		);

		assertTrue(this.daoTournoi.ajouter(tournoi));
	}

	/**
	 * Teste la modification d'un tournoi dans la base de données
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#modifier(Tournoi)
	 */
	@Test
	public void testModifier() throws Exception {
		Tournoi tournoi = new Tournoi(
			7, 
			"Tournoi", 
			Notoriete.INTERNATIONAL, 
			this.getDateCourante() - 3600,
			this.getDateCourante() + 3600, 
			true, 
			"Iden", 
			"mdp", 
			new ArrayList<>(), 
			new ArrayList<>(),
			new ArrayList<>()
		);

		this.daoTournoi.ajouter(tournoi);
		tournoi.setNomTournoi("Peu Importe");
		assertTrue(this.daoTournoi.modifier(tournoi));
	}

	/**
	 * Teste l'erreur lors de la modification d'un tournoi déja cloturé
	 * @throws IllegalArgumentException
	 * @see modele.DAO.DAOTournoi#modifier(Tournoi)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testModifierClotureException() throws Exception {
		Tournoi tournoi = new Tournoi(
			1, 
			"Tournoi", 
			Notoriete.INTERNATIONAL, 
			this.getDateCourante() - 3600,
			this.getDateCourante() + 3600, 
			false, 
			"Iden", 
			"mdp", 
			new ArrayList<>(), 
			new ArrayList<>(),
			new ArrayList<>()
		);

		this.daoTournoi.modifier(tournoi);
	}

	/**
	 * Teste l'erreur lors de la modification d'un tournoi avec une date de fin inférieure à la courante
	 * @throws IllegalArgumentException
	 * @see modele.DAO.DAOTournoi#modifier(Tournoi)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testModifierDateFinException() throws Exception {
		Tournoi tournoi = new Tournoi(
			1, 
			"Tournoi", 
			Notoriete.INTERNATIONAL, 
			this.getDateCourante() - 3600,
			this.getDateCourante() - 3500, 
			true, 
			"Iden", 
			"mdp", 
			new ArrayList<>(), 
			new ArrayList<>(),
			new ArrayList<>()
		);

		this.daoTournoi.modifier(tournoi);
	}

	/**
	 * Teste la suppression d'un tournoi dans la base de données
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#supprimer(Tournoi)
	 */
	@Test
	public void testSupprimer() throws Exception {
		Tournoi tournoi = new Tournoi(
			7, 
			"Tournoi", 
			Notoriete.INTERNATIONAL, 
			this.getDateCourante() - 3600,
			this.getDateCourante() + 3600, 
			false, 
			"Iden", 
			"mdp", 
			new ArrayList<>(), 
			new ArrayList<>(),
			new ArrayList<>()
		);

		this.daoTournoi.ajouter(tournoi);
		tournoi.setEstCloture(true);
		this.daoTournoi.supprimer(tournoi);
	}

	/**
	 * Teste l'erreur lors de la suppression d'un tournoi cloturé avec une date de fin inférieure à la courante
	 * @throws IllegalArgumentException
	 * @see modele.DAO.DAOTournoi#supprimer(Tournoi)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSupprimerException() throws Exception {
		Tournoi tournoi = this.daoTournoi.getParId(6).orElse(null);
		tournoi.setIdTournoi(7);
		this.daoTournoi.ajouter(tournoi);
		tournoi.setEstCloture(false);
		tournoi.setDateTimeDebut(this.getDateCourante() - 5000);
		tournoi.setDateTimeFin(this.getDateCourante() - 4000);
		this.daoTournoi.supprimer(tournoi);
	}
	
	/**
	 * Teste la récupération d'un tournoi par son identifiant
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#getParIdentifiant(String)
	 */
	@Test
	public void testGetParIdentifiant() throws Exception {
		daoTournoi.ajouter(tournoi);
		assertEquals(tournoi, daoTournoi.getParIdentifiant("Identifiant").get());
	}
	
	/**
	 * Teste la récupération du tournoi d'une rencontre
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#getParIdentifiant(String)
	 */
	@Test
	public void testGetTournoiRencontre() throws Exception {
		assertEquals(daoTournoi.getTournoiRencontre(1),daoTournoi.getParId(1));
	}
	
	/**
	 * Teste la récupération du tournoi d'une rencontre vide
	 * @throws Exception
	 * @see modele.DAO.DAOTournoi#getTournoiRencontre(String)
	 */
	@Test
	public void testGetTOurnoiRencontreEmpty() {
		assertEquals(daoTournoi.getTournoiRencontre(0),Optional.empty());
	}

	
	@After
	public void tearsDown() throws Exception {
		this.nettoyerTournois();
	}
}
