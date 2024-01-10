package modele.test;

import static org.junit.Assert.*;

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
        tournoi = new Tournoi("Tournoi", Notoriete.INTERNATIONAL, 101, 1001, "id", "mdp",new ArrayList<>());
    }
    
    // Test constructeurs
    @Test
    public void testConstructeurAvecId() {
        int idHistoriquePoints = 1;
        float points = 10.0f;
        int idEquipe = 2;

        historique = new HistoriquePoints(idHistoriquePoints, points, tournoi, idEquipe);

        assertEquals(idHistoriquePoints, historique.getIdHistoriquePoints());
        assertEquals(points, historique.getPoints(), 0.0);
        assertEquals(tournoi, historique.getTournoi());
        assertEquals(idEquipe, historique.getIdEquipe());
    }

    @Test
    public void testConstructeurSansId() {
        float points = 20.0f;
        int idEquipe = 3;

        historique = new HistoriquePoints(points, tournoi, idEquipe);

        assertEquals(0, historique.getIdHistoriquePoints()); 
        assertEquals(points, historique.getPoints(), 0.0);
        assertEquals(tournoi, historique.getTournoi());
        assertEquals(idEquipe, historique.getIdEquipe());
    }

    // Test getters
    @Test
    public void testGetIdHistoriquePoints() {
        historique = new HistoriquePoints(1, 10.0f, tournoi, 2);
        assertEquals(1, historique.getIdHistoriquePoints());
    }

    @Test
    public void testGetPoints() {
        historique = new HistoriquePoints(1, 10.0f, tournoi, 2);
        assertEquals(10.0f, historique.getPoints(), 0.0);
    }

    @Test
    public void testGetTournoi() {
        historique = new HistoriquePoints(1, 10.0f, tournoi, 2);
        assertEquals(tournoi, historique.getTournoi());
    }

    @Test
    public void testGetIdEquipe() {
        historique = new HistoriquePoints(1, 10.0f, tournoi, 2);
        assertEquals(2, historique.getIdEquipe());
    }

    // Test setters
    @Test
    public void testSetIdHistoriquePoints() {
        historique = new HistoriquePoints(30.0f, tournoi, 4);
        historique.setIdHistoriquePoints(5);
        assertEquals(5, historique.getIdHistoriquePoints());
    }

    @Test
    public void testSetPoints() {
        historique = new HistoriquePoints(30.0f, tournoi, 4);
        historique.setPoints(40.0f);
        assertEquals(40.0f, historique.getPoints(), 0.0);
    }

    @Test
    public void testSetTournoi() {
        historique = new HistoriquePoints(30.0f, tournoi, 4);
        Tournoi newTournoi = new Tournoi("Tournoi2", Notoriete.INTERNATIONAL, 101, 1001, "id", "mdp", new ArrayList<>());
        historique.setTournoi(newTournoi);
        assertEquals(newTournoi, historique.getTournoi());
    }

    @Test
    public void testSetIdEquipe() {
        historique = new HistoriquePoints(30.0f, tournoi, 4);
        historique.setIdEquipe(6);
        assertEquals(6, historique.getIdEquipe());
    }
}
