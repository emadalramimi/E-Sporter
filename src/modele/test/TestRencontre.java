package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Rencontre;

public class TestRencontre {

    private Rencontre rencontre1;
    private Rencontre rencontre2;
    private List<Joueur> listJoueurs;

    @Before
    public void setUp() {
    	listJoueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
    	
        Equipe equipeA = new Equipe("Equipe1", "Canada", listJoueurs);
        Equipe equipeB = new Equipe("Equipe2", "France", listJoueurs);
        Equipe[] equipes1 = {equipeA, equipeB};

        rencontre1 = new Rencontre(1, 100, 200, 10, equipes1);
        rencontre2 = new Rencontre(2, 150, 250, 11);
    }

    @Test
    public void testGetIdRencontre() {
        assertEquals(rencontre1.getIdRencontre(), 1);
        assertEquals(rencontre2.getIdRencontre(), 2);
        assertNotEquals(rencontre1.getIdRencontre(), rencontre2.getIdRencontre());
    }

    @Test
    public void testSetIdRencontre() {
        rencontre1.setIdRencontre(3);
        rencontre2.setIdRencontre(4);
        assertEquals(rencontre1.getIdRencontre(), 3);
        assertEquals(rencontre2.getIdRencontre(), 4);
        assertNotEquals(rencontre1.getIdRencontre(), rencontre2.getIdRencontre());
    }

    @Test
    public void testGetDateHeureDebut() {
        assertEquals(rencontre1.getDateHeureDebut(), 100);
        assertEquals(rencontre2.getDateHeureDebut(), 150);
        assertNotEquals(rencontre1.getDateHeureDebut(), rencontre2.getDateHeureDebut());
    }

    @Test
    public void testSetDateHeureDebut() {
        rencontre1.setDateHeureDebut(120);
        rencontre2.setDateHeureDebut(180);
        assertEquals(rencontre1.getDateHeureDebut(), 120);
        assertEquals(rencontre2.getDateHeureDebut(), 180);
        assertNotEquals(rencontre1.getDateHeureDebut(), rencontre2.getDateHeureDebut());
    }

    @Test
    public void testGetDateHeureFin() {
        assertEquals(rencontre1.getDateHeureFin(), 200);
        assertEquals(rencontre2.getDateHeureFin(), 250);
        assertNotEquals(rencontre1.getDateHeureFin(), rencontre2.getDateHeureFin());
    }

    @Test
    public void testSetDateHeureFin() {
        rencontre1.setDateHeureFin(220);
        rencontre2.setDateHeureFin(280);
        assertEquals(rencontre1.getDateHeureFin(), 220);
        assertEquals(rencontre2.getDateHeureFin(), 280);
        assertNotEquals(rencontre1.getDateHeureFin(), rencontre2.getDateHeureFin());
    }

    @Test
    public void testGetIdPoule() {
        assertEquals(rencontre1.getIdPoule(), 10);
        assertEquals(rencontre2.getIdPoule(), 11);
        assertNotEquals(rencontre1.getIdPoule(), rencontre2.getIdPoule());
    }

    @Test
    public void testSetIdPoule() {
        rencontre1.setIdPoule(12);
        rencontre2.setIdPoule(13);
        assertEquals(rencontre1.getIdPoule(), 12);
        assertEquals(rencontre2.getIdPoule(), 13);
        assertNotEquals(rencontre1.getIdPoule(), rencontre2.getIdPoule());
    }

    @Test
    public void testGetEquipes() {
        assertNotNull(rencontre1.getEquipes());
        assertNotNull(rencontre2.getEquipes());
        assertEquals(rencontre1.getEquipes().length, 2);
        assertEquals(rencontre2.getEquipes().length, 2);
    }

    @Test
    public void testSetEquipes() {
        Equipe equipeC = new Equipe(3, "Equipe1", "Canada", 2, 2, "Saison 2023",listJoueurs);
        Equipe equipeD = new Equipe(4, "Equipe1", "Canada", 2, 2, "Saison 2023",listJoueurs);
        Equipe[] equipes2 = {equipeC, equipeD};
        rencontre1.setEquipes(equipes2);
        assertArrayEquals(rencontre1.getEquipes(), equipes2);
        rencontre2.setEquipes(equipes2);
        assertArrayEquals(rencontre2.getEquipes(), equipes2);
    }
}
