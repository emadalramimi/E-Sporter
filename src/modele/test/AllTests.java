package modele.test;

import org.junit.runner.JUnitCore;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestAdmin.class,
    TestArbitre.class,
    TestEquipe.class,
    TestJoueur.class,
    TestPays.class,
    TestPoule.class,
    TestRencontre.class,
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
     * @param args Arguments
     */
    public static void main(String[] args) {
        JUnitCore.runClasses(AllTests.class);
    }

}
