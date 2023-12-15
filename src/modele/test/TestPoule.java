package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Poule;
import modele.metier.Rencontre;

public class TestPoule {

    private Poule poule1;
    private Poule poule2;
    private List<Rencontre> rencontres;

    @Before
    public void setUp() {
        rencontres = new ArrayList<>(Arrays.asList(
        		new Rencontre(1, 1000, 10000, 1),
        		new Rencontre(2, 1000, 10000, 1)
        ));
        poule1 = new Poule(1, false, false, 10, rencontres);
        poule2 = new Poule(2, true, true, 11);
    }

    @Test
    public void testGetIdPoule() {
        assertEquals(poule1.getIdPoule(), 1);
        assertEquals(poule2.getIdPoule(), 2);
        assertNotEquals(poule1.getIdPoule(), poule2.getIdPoule());
    }

    @Test
    public void testSetIdPoule() {
        poule1.setIdPoule(3);
        poule2.setIdPoule(4);
        assertEquals(poule1.getIdPoule(), 3);
        assertEquals(poule2.getIdPoule(), 4);
        assertNotEquals(poule1.getIdPoule(), poule2.getIdPoule());
    }

    @Test
    public void testIsEstCloturee() {
        assertEquals(poule1.isEstCloturee(), false);
        assertEquals(poule2.isEstCloturee(), true);
        assertNotEquals(poule1.isEstCloturee(), poule2.isEstCloturee());
    }

    @Test
    public void testSetEstCloturee() {
        poule1.setEstCloturee(true);
        poule2.setEstCloturee(false);
        assertEquals(poule1.isEstCloturee(), true);
        assertEquals(poule2.isEstCloturee(), false);
        assertNotEquals(poule1.isEstCloturee(), poule2.isEstCloturee());
    }

    @Test
    public void testIsEstFinale() {
        assertEquals(poule1.isEstFinale(), false);
        assertEquals(poule2.isEstFinale(), true);
        assertNotEquals(poule1.isEstFinale(), poule2.isEstFinale());
    }

    @Test
    public void testSetEstFinale() {
        poule1.setEstFinale(true);
        poule2.setEstFinale(false);
        assertEquals(poule1.isEstFinale(), true);
        assertEquals(poule2.isEstFinale(), false);
        assertNotEquals(poule1.isEstFinale(), poule2.isEstFinale());
    }

    @Test
    public void testGetIdTournoi() {
        assertEquals(poule1.getIdTournoi(), 10);
        assertEquals(poule2.getIdTournoi(), 11);
        assertNotEquals(poule1.getIdTournoi(), poule2.getIdTournoi());
    }

    @Test
    public void testSetIdTournoi() {
        poule1.setIdTournoi(12);
        poule2.setIdTournoi(13);
        assertEquals(poule1.getIdTournoi(), 12);
        assertEquals(poule2.getIdTournoi(), 13);
        assertNotEquals(poule1.getIdTournoi(), poule2.getIdTournoi());
    }

    @Test
    public void testGetRencontres() {
        assertEquals(poule1.getRencontres(), rencontres);
    }

    @Test
    public void testSetRencontres() {
        List<Rencontre> newRencontres = new ArrayList<>(Arrays.asList(
        		new Rencontre(3, 100, 1000, 1)
        ));
        poule1.setRencontres(newRencontres);
        assertEquals(poule1.getRencontres(), newRencontres);

        poule2.setRencontres(newRencontres);
        assertEquals(poule2.getRencontres(), newRencontres);
    }

}
