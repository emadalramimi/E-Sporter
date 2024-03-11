package modele;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import global.SuperTest;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.InscriptionEquipeTournoiException;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Classe de test du modele Equipe
 * @see ModeleEquipe
 */
public class TestModeleEquipe extends SuperTest {
	
	private ModeleEquipe modele;
	private DAOEquipe daoEquipe;
	private DAOTournoi daoTournoi;

	/**
	 * Configure l'environnement de test avant chaque cas de test.
	 */
	@Before
	public void setUp() {
		this.modele = new ModeleEquipe();
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
	}
	
	/**
	 * Teste la méthode modifier() de la classe ModeleEquipe.
	 * @throws Exception si une erreur se produit pendant le test
	 */
	@Test
	public void testModifier() throws Exception {
		// Cas de test 1
		Equipe equipeInit = daoEquipe.getParId(1).get();
		List<String> nomsEquipe = new ArrayList<>();
		for (Joueur joueur:equipeInit.getJoueurs()) {
			nomsEquipe.add(joueur.getPseudo());
		}
		Equipe equipeTest = new Equipe(1, "Autre", Pays.FRANCE, 3, 414, "2024", equipeInit.getJoueurs());
		
		modele.modifier(daoEquipe.getParId(1).get(), "Autre", Pays.FRANCE, nomsEquipe);
		assertEquals(daoEquipe.getParId(1).get(), equipeTest);
		
		// Cas de test 2
		modele.modifier(daoEquipe.getParId(1).get(), "CFO Academy", Pays.TAIWAN, nomsEquipe);
	}
	
	/**
	 * Teste la méthode modifier() de la classe ModeleEquipe lorsque l'équipe est déjà inscrite à un tournoi.
	 * @throws Exception si une erreur se produit pendant le test
	 */
	@Test (expected = InscriptionEquipeTournoiException.class)
	public void testModifierEquipeInscrite() throws Exception {
		Tournoi tournoi = new Tournoi(
			"Test",
			Notoriete.LOCAL,
			this.getDateCourante() + 3600,
			this.getDateCourante() + 6600,
			"Arbitre",
			"mdp",
			new ArrayList<>()
		);
		daoTournoi.ajouter(tournoi);
		daoEquipe.inscrireEquipe(daoEquipe.getParId(1).get(), tournoi);
		
		Equipe equipeInit = daoEquipe.getParId(1).get();
		List<String> nomsEquipe = new ArrayList<>();
		for (Joueur joueur:equipeInit.getJoueurs()) {
			nomsEquipe.add(joueur.getPseudo());
		}
		daoTournoi.ouvrirTournoi(tournoi);
		modele.modifier(daoEquipe.getParId(1).get(), "Nom", Pays.AFRIQUE_DU_SUD, nomsEquipe);
	}
	
	/**
	 * Teste la méthode creerJoueurs() de la classe ModeleEquipe.
	 * @throws Exception si une erreur se produit pendant le test
	 */
	@Test
	public void testCreerJoueurs() throws Exception {
		Equipe equipeInit = daoEquipe.getParId(1).get();
		List<String> nomsEquipe = new ArrayList<>();
		for (Joueur joueur : equipeInit.getJoueurs()) {
			nomsEquipe.add(joueur.getPseudo());
		}
		for (int i=1; i<6;i++) {
			assertEquals(modele.creerJoueurs(nomsEquipe).get(i-1).getPseudo(),daoEquipe.getParId(1).get().getJoueurs().get(i-1).getPseudo());
		}
	}
	
	/**
	 * Teste la méthode getParNom() de la classe ModeleEquipe.
	 * @throws Exception si une erreur se produit pendant le test
	 */
	@Test
	public void testGetParNom() throws Exception {
		assertEquals(daoEquipe.getParId(1).get(), modele.getParNom("CFO Academy").get(0));
	}

	/**
	 * Effectue les opérations de nettoyage après chaque cas de test.
	 * @throws Exception si une erreur se produit pendant le nettoyage
	 */
	@After
	public void tearDown() throws Exception {
		this.nettoyerTournois();
	}

}
