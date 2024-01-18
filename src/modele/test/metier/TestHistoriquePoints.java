package modele.test.metier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import modele.metier.HistoriquePoints;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestHistoriquePoints {

	private HistoriquePoints historique;
	private Tournoi tournoi;

	@Before
	public void setUp() {
		this.tournoi = new Tournoi("Tournoi", Notoriete.INTERNATIONAL, 101, 1001, "id", "mdp", new ArrayList<>());
	}

	// Test constructeurs
	@Test
	public void testConstructeurAvecId() {
		int idHistoriquePoints = 1;
		float points = 10.0f;
		int idEquipe = 2;

		this.historique = new HistoriquePoints(idHistoriquePoints, points, this.tournoi, idEquipe);

		assertEquals(idHistoriquePoints, this.historique.getIdHistoriquePoints());
		assertEquals(points, this.historique.getPoints(), 0.0);
		assertEquals(this.tournoi, this.historique.getTournoi());
		assertEquals(idEquipe, this.historique.getIdEquipe());
	}

	@Test
	public void testConstructeurSansId() {
		float points = 20.0f;
		int idEquipe = 3;

		this.historique = new HistoriquePoints(points, this.tournoi, idEquipe);

		assertEquals(0, this.historique.getIdHistoriquePoints());
		assertEquals(points, this.historique.getPoints(), 0.0);
		assertEquals(this.tournoi, this.historique.getTournoi());
		assertEquals(idEquipe, this.historique.getIdEquipe());
	}

	// Test getters
	@Test
	public void testGetIdHistoriquePoints() {
		this.historique = new HistoriquePoints(1, 10.0f, this.tournoi, 2);
		assertEquals(1, this.historique.getIdHistoriquePoints());
	}

	@Test
	public void testGetPoints() {
		this.historique = new HistoriquePoints(1, 10.0f, this.tournoi, 2);
		assertEquals(10.0f, this.historique.getPoints(), 0.0);
	}

	@Test
	public void testGetTournoi() {
		this.historique = new HistoriquePoints(1, 10.0f, this.tournoi, 2);
		assertEquals(this.tournoi, this.historique.getTournoi());
	}

	@Test
	public void testGetIdEquipe() {
		this.historique = new HistoriquePoints(1, 10.0f, this.tournoi, 2);
		assertEquals(2, this.historique.getIdEquipe());
	}

	// Test setters
	@Test
	public void testSetIdHistoriquePoints() {
		this.historique = new HistoriquePoints(30.0f, this.tournoi, 4);
		this.historique.setIdHistoriquePoints(5);
		assertEquals(5, this.historique.getIdHistoriquePoints());
	}

	@Test
	public void testSetPoints() {
		this.historique = new HistoriquePoints(30.0f, this.tournoi, 4);
		this.historique.setPoints(40.0f);
		assertEquals(40.0f, this.historique.getPoints(), 0.0);
	}

	@Test
	public void testSetTournoi() {
		this.historique = new HistoriquePoints(30.0f, this.tournoi, 4);
		Tournoi newTournoi = new Tournoi("Tournoi2", Notoriete.INTERNATIONAL, 101, 1001, "id", "mdp",
				new ArrayList<>());
		this.historique.setTournoi(newTournoi);
		assertEquals(newTournoi, this.historique.getTournoi());
	}

	@Test
	public void testSetIdEquipe() {
		this.historique = new HistoriquePoints(30.0f, this.tournoi, 4);
		this.historique.setIdEquipe(6);
		assertEquals(6, this.historique.getIdEquipe());
	}
}
