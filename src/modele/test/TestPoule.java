package modele.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Poule;
import modele.metier.Rencontre;

public class TestPoule {

	private List<Joueur> joueurs;
    private Poule poule1;
    private Poule poule2;
    private List<Rencontre> rencontres;

    @Before
    public void setUp() {
    	joueurs = new ArrayList<>(Arrays.asList(
			    new Joueur(1, "Joueur1", 2),
			    new Joueur(2, "Joueur2", 2),
			    new Joueur(3, "Joueur3", 2),
			    new Joueur(4, "Joueur4", 2),
			    new Joueur(5, "Joueur5", 2)
			));
    	
        Equipe equipeA = new Equipe(50, "Equipe A", "France", 5, 5, "2020", joueurs);
        Equipe equipeB = new Equipe(51, "Equipe B", "Maroc", 5, 5, "2020", joueurs);
        Equipe[] equipes = {equipeA, equipeB};
        rencontres = new ArrayList<>(Arrays.asList(
        		new Rencontre(50, 1000, 10000, equipes),
        		new Rencontre(51, 1050, 10050, equipes)
        ));
        poule1 = new Poule(1, false, false, 10, rencontres);
        poule2 = new Poule(true, true, 11, rencontres);
    }

    @Test
    public void testGetIdPoule() {
        assertEquals(poule1.getIdPoule(), 1);
    }

    @Test
    public void testSetIdPoule() {
        poule1.setIdPoule(3);
        assertEquals(poule1.getIdPoule(), 3);
    }

    @Test
    public void testIsEstCloturee() {
        assertEquals(poule1.getEstCloturee(), false);
    }

    @Test
    public void testSetEstCloturee() {
        poule1.setEstCloturee(true);
        assertEquals(poule1.getEstCloturee(), true);
    }

    @Test
    public void testIsEstFinale() {
        assertEquals(poule1.getEstFinale(), false);
    }

    @Test
    public void testSetEstFinale() {
        poule1.setEstFinale(true);
        assertEquals(poule1.getEstFinale(), true);
    }

    @Test
    public void testGetIdTournoi() {
        assertEquals(poule1.getIdTournoi(), 10);
    }

    @Test
    public void testSetIdTournoi() {
        poule1.setIdTournoi(12);
        assertEquals(poule1.getIdTournoi(), 12);
    }

    @Test
    public void testGetRencontres() {
        assertEquals(poule1.getRencontres(), rencontres);
    }

    @Test
    public void testSetRencontres() {
    	Equipe equipeA = new Equipe(50, "Equipe A", "France", 5, 5, "2020", joueurs);
        Equipe equipeB = new Equipe(51, "Equipe B", "Maroc", 5, 5, "2020", joueurs);
        Equipe[] equipes = {equipeA, equipeB};
        List<Rencontre> newRencontres = new ArrayList<>(Arrays.asList(
        		new Rencontre(50, 1005, 10050, equipes),
        		new Rencontre(51, 1500, 10500, equipes)
        ));
        poule1.setRencontres(newRencontres);
        assertEquals(poule1.getRencontres(), newRencontres);

        poule2.setRencontres(newRencontres);
        assertEquals(poule2.getRencontres(), newRencontres);
    }

}
