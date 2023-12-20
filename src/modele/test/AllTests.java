package modele.test;

import org.junit.runner.JUnitCore;

import modele.BDD;

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
    TestModeleAdministrateur.class,
    TestModeleArbitre.class,
    TestModeleEquipe.class,
    TestModeleJoueur.class,
    TestModelePoule.class,
    TestModeleRenontre.class,
    TestModeleTournoi.class,
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
    	BDD.main(args);
        JUnitCore.runClasses(AllTests.class);
    }

}
