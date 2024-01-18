package modele.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoi;
import modele.ModeleTournoiOuverture;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Arbitre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.test.SuperTest;
import controleur.ControleurTournois;

/**
 * Classe de test pour le modèle Tournoi
 * @see ModeleTournoi
 */
public class TestModeleTournoi extends SuperTest {
	
	private ModeleTournoi modele;
	private DAOTournoi daoTournoi;
	private DAOEquipe daoEquipe;
	
	/**
	 * Configure l'environnement de test avant chaque cas de test
	 */
	@Before
	public void setUp() {
		modele = new ModeleTournoi();
		daoTournoi = new DAOTournoiImpl();
		daoEquipe = new DAOEquipeImpl();
	}
	
	/**
	 * Teste la méthode getResultatsTournoi() de la classe ModeleTournoi
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getResultatsTournoi(Tournoi)
	 */
	@Test
	public void testGetResultatsTournoi() throws Exception {
		List<StatistiquesEquipe> statistiques = new ArrayList<>(Arrays.asList(
			new StatistiquesEquipe(daoEquipe.getParId(1).get(), 4, 4),
			new StatistiquesEquipe(daoEquipe.getParId(4).get(), 4, 2),
			new StatistiquesEquipe(daoEquipe.getParId(3).get(), 3, 1),
			new StatistiquesEquipe(daoEquipe.getParId(2).get(), 3, 0))
		);
		assertEquals(modele.getResultatsTournoi(daoTournoi.getParId(1).get()), statistiques);
	}
	
	/**
	 * Teste la méthode getParNom() de la classe ModeleTournoi
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getParNom(String)
	 */
	@Test
	public void testParNom() throws Exception {
		assertEquals(modele.getParNom("PCL 2023").get(0), daoTournoi.getParId(1).get());
	}
	
	/**
	 * Teste la méthode getParFiltrage() de la classe ModeleTournoi avec notoriété null
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getParFiltrage(Notoriete, ControleurTournois.Statut)
	 */
	@Test
	public void testGetParFiltrageNotorieteNull() throws Exception {
	    List<Tournoi> tournois = daoTournoi.getTout();
	    assertEquals(tournois.size(), this.modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).size());
	    
	    for (int i = 0; i < tournois.size(); i++) {
	        assertEquals(tournois.get(i), this.modele.getParFiltrage(null, ControleurTournois.Statut.CLOTURE).get(i));
	    }
	}

	/**
	 * Teste le filtrage avec une notoriété non null et un statut null
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getParFiltrage(Notoriete, ControleurTournois.Statut)
	 */
	@Test
	public void testGetParFiltrageStatutNull() throws Exception {
	    List<Tournoi> tournois = new ArrayList<>();
	    tournois.add(this.daoTournoi.getParId(3).orElse(null));
	    assertEquals(tournois.size(), this.modele.getParFiltrage(Notoriete.NATIONAL, null).size());
	    for (int i = 0; i < tournois.size(); i++) {
	        assertEquals(tournois.get(i), this.modele.getParFiltrage(Notoriete.NATIONAL, null).get(i));
	    }
	}

	/**
	 * Teste le filtrage avec une notoriété et un statut cloturé
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getParFiltrage(Notoriete, ControleurTournois.Statut)
	 */
	@Test
	public void testGetParFiltrageCloture() throws Exception {
	    List<Tournoi> tournois = this.daoTournoi.getTout();
	    tournois.remove(this.daoTournoi.getParId(6).orElse(null));
	    tournois.remove(this.daoTournoi.getParId(3).orElse(null));
	    tournois.remove(this.daoTournoi.getParId(4).orElse(null));
	    for (int i = 0; i < tournois.size(); i++) {
	        assertEquals(tournois.get(i), this.modele.getParFiltrage(Notoriete.INTERNATIONAL, ControleurTournois.Statut.CLOTURE).get(i));
	    }
	}
	
	/**
	 * Teste le filtrage avec une notoriété et un statut ouvert
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getParFiltrage(Notoriete, ControleurTournois.Statut)
	 */
	@Test
	public void testGetParFiltrageOuvert() throws Exception {
	    Arbitre arbitre = new Arbitre(1, "Willem", "Miled");
	    List<Arbitre> arbitres = new ArrayList<>(Arrays.asList(arbitre));
	    Tournoi tournoi = new Tournoi(
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

	    this.daoTournoi.ajouter(tournoi);

	    for (int i = 0; i < 4; i++) {
	        this.daoEquipe.inscrireEquipe(this.daoEquipe.getTout().get(i), tournoi);
	    }

	    ModeleTournoiOuverture modeleTournoiOuverture = new ModeleTournoiOuverture();
	    modeleTournoiOuverture.ouvrirTournoi(tournoi);

	    List<Tournoi> tournois = new ArrayList<>();
	    tournois.add(tournoi);

	    assertEquals(tournois.size(), this.modele.getParFiltrage(Notoriete.INTERNATIONAL, ControleurTournois.Statut.OUVERT).size());
        assertEquals(tournois.get(0), this.modele.getParFiltrage(Notoriete.INTERNATIONAL, ControleurTournois.Statut.OUVERT).get(0));
	}
	
	/**
	 * Teste le filtrage avec une notoriété et un statut en phase d'inscriptions
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#getParFiltrage(Notoriete, ControleurTournois.Statut)
	 */
	@Test
	public void testGetParFiltrageInscription() throws Exception {
	    Tournoi tournoiTest = this.daoTournoi.getParId(1).orElse(null);
	    Tournoi tournoi = new Tournoi(7, "TournoiTest", Notoriete.NATIONAL, this.getDateCourante() + 3600, this.getDateCourante() + 7200, true, "arbitre", "password", tournoiTest.getPoules(), tournoiTest.getEquipes(), tournoiTest.getArbitres());
	    
		this.daoTournoi.ajouter(tournoi);
	    
		List<Tournoi> tournois = new ArrayList<>();
	    tournois.add(tournoi);
	    
		assertEquals(tournois.size(), this.modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).size());
	    for (int i = 0; i < tournois.size(); i++) {
	        assertEquals(tournois.get(i), this.modele.getParFiltrage(Notoriete.NATIONAL, ControleurTournois.Statut.PHASE_INSCRIPTIONS).get(i));
	    }
	}
	
	/**
	 * Teste la méthode verifierUniciteIdentifiant() de la classe ModeleTournoi
	 * @throws SQLException si une erreur se produit pendant le test
	 * @see ModeleTournoi#verifierUniciteIdentifiant(String)
	 */
	@Test
	public void TestVerifierUniciteIdentifiant() throws SQLException {
		assertTrue(modele.verifierUniciteIdentifiant("Pcl2023"));
		assertTrue(modele.verifierUniciteIdentifiant("admin"));
		assertFalse(modele.verifierUniciteIdentifiant("Inconnu"));
	}
	
	/**
	 * Teste la méthode estTournoiEnCoursOuCloture() de la classe ModeleTournoi
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#estTournoiEnCoursOuCloture(Tournoi)
	 */
	@Test
	public void TestEstTournoiEnCoursOuCloture() throws Exception {
		Tournoi tournoiTest = daoTournoi.getParId(1).orElse(null);
		
		// Teste si les deux prédicats sont vérifiés
		tournoiTest.setEstCloture(true);
		tournoiTest.setDateTimeFin(getDateCourante() + 500);
		assertTrue(modele.estTournoiEnCoursOuCloture(tournoiTest));
		
		// Teste si le premier prédicat est faux et le deuxième vrai
		tournoiTest.setDateTimeFin(getDateCourante() - 500);
		assertFalse(modele.estTournoiEnCoursOuCloture(tournoiTest));
		
		// Teste si les deux prédicats sont faux
		tournoiTest.setEstCloture(false);
		assertFalse(modele.estTournoiEnCoursOuCloture(tournoiTest));
		
		// Teste si le premier prédicat est vrai et le deuxième faux
		tournoiTest.setDateTimeFin(getDateCourante() + 500);
		assertFalse(modele.estTournoiEnCoursOuCloture(tournoiTest));
	}
	
	/**
	 * Teste la méthode estTournoiCloture() de la classe ModeleTournoi
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoi#estTournoiCloture(Tournoi)
	 */
	@Test
	public void TestEstTournoiCloture() throws Exception {
		Tournoi tournoiTest = daoTournoi.getParId(1).orElse(null);
		
		// Teste si les deux prédicats sont vérifiés
		tournoiTest.setEstCloture(true);
		tournoiTest.setDateTimeDebut(getDateCourante() + 500);
		assertFalse(modele.estTournoiCloture(tournoiTest));
		
		// Teste si le premier prédicat est faux et le deuxième vrai
		tournoiTest.setDateTimeDebut(getDateCourante() - 500);
		assertTrue(modele.estTournoiCloture(tournoiTest));
		
		// Teste si les deux prédicats sont faux
		tournoiTest.setEstCloture(false);
		assertFalse(modele.estTournoiCloture(tournoiTest));
		
		// Teste si le premier prédicat est vrai et le deuxième faux
		tournoiTest.setDateTimeDebut(getDateCourante() + 500);
		assertFalse(modele.estTournoiCloture(tournoiTest));
	}
	
	/**
	 * Nettoie les tournois
	 * @throws Exception si le nettoyage se passe mal
	 */
	@After
	public void tearDown() throws Exception {
		this.nettoyerTournois();
	}

}
