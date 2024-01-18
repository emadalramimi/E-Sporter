package modele.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import org.junit.runners.Suite;

import modele.DAO.BDD;
import modele.test.DAO.TestDAOAdministrateur;
import modele.test.DAO.TestDAOArbitre;
import modele.test.DAO.TestDAOEquipe;
import modele.test.DAO.TestDAOHistoriquePoints;
import modele.test.DAO.TestDAOJoueur;
import modele.test.DAO.TestDAOPalmares;
import modele.test.DAO.TestDAOPoule;
import modele.test.DAO.TestDAORencontre;
import modele.test.DAO.TestDAOTournoi;

import modele.test.metier.TestAdministrateur;
import modele.test.metier.TestArbitre;
import modele.test.metier.TestEquipe;
import modele.test.metier.TestJoueur;
import modele.test.metier.TestPalmares;
import modele.test.metier.TestPays;
import modele.test.metier.TestPoule;
import modele.test.metier.TestRencontre;
import modele.test.metier.TestSatistiquesEquipe;
import modele.test.metier.TestTournoi;

import modele.test.modele.TestModeleEquipe;
import modele.test.modele.TestModeleHistoriquePoints;
import modele.test.modele.TestModelePalmares;
import modele.test.modele.TestModeleRencontre;
import modele.test.modele.TestModeleTournoi;
import modele.test.modele.TestModeleTournoiCloture;
import modele.test.modele.TestModeleTournoiOuverture;
import modele.test.modele.TestModeleUtilisateur;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestAdministrateur.class,
    TestArbitre.class,
    TestEquipe.class,
    TestJoueur.class,
    TestPays.class,
    TestPoule.class,
    TestRencontre.class,
    TestPalmares.class,
    TestSatistiquesEquipe.class,
    TestTournoi.class,

    TestDAOAdministrateur.class,
    TestDAOArbitre.class,
    TestDAOEquipe.class,
    TestDAOHistoriquePoints.class,
    TestDAOJoueur.class,
    TestDAOPalmares.class,
    TestDAOPoule.class,
    TestDAORencontre.class,
    TestDAOTournoi.class,
    
    TestModeleEquipe.class,
    TestModeleHistoriquePoints.class,
    TestModelePalmares.class,
    TestModeleRencontre.class,
    TestModeleTournoi.class,
    TestModeleTournoiCloture.class,
    TestModeleTournoiOuverture.class,
    TestModeleUtilisateur.class
})

/**
 * Classe de test pour lancer tous les tests
 */
public class AllTests {

    /**
     * Méthode principale pour lancer tous les tests
     * La base de données est remise à zéro avant de lancer les tests
     * @param args Arguments
     * @throws Exception si une erreur se produit pendant la création de la BDD
     */
    public static void main(String[] args) throws Exception {
        BDD.creerBDD();
        JUnitCore.runClasses(AllTests.class);
    }

}
