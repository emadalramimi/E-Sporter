package global;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;

import org.junit.runners.Suite;

import DAO.TestDAOAdministrateur;
import DAO.TestDAOArbitre;
import DAO.TestDAOEquipe;
import DAO.TestDAOHistoriquePoints;
import DAO.TestDAOJoueur;
import DAO.TestDAOPalmares;
import DAO.TestDAOPoule;
import DAO.TestDAORencontre;
import DAO.TestDAOTournoi;

import metier.TestAdministrateur;
import metier.TestArbitre;
import metier.TestEquipe;
import metier.TestJoueur;
import metier.TestPalmares;
import metier.TestPays;
import metier.TestPoule;
import metier.TestRencontre;
import metier.TestSatistiquesEquipe;
import metier.TestTournoi;

import modele.TestModeleEquipe;
import modele.TestModeleHistoriquePoints;
import modele.TestModelePalmares;
import modele.TestModeleRencontre;
import modele.TestModeleTournoi;
import modele.TestModeleTournoiCloture;
import modele.TestModeleTournoiOuverture;
import modele.TestModeleUtilisateur;

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
        JUnitCore.runClasses(AllTests.class);
    }

}
