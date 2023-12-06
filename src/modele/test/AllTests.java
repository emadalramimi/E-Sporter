package modele.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import modele.BDD;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestEquipe.class,
    TestJoueur.class,
    TestUtilisateur.class,
    TestModeleEquipe.class,
    TestModeleJoueur.class
})

public class AllTests {
	
    public static void main(String[] args) {
    	BDD.main(args);
        Result result = JUnitCore.runClasses(AllTests.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("Tous les tests ont réussi.");
        } else {
            System.out.println("Certains tests ont échoué.");
        }
    }
}
