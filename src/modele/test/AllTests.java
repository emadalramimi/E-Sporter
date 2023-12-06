package modele.test;

import org.junit.runner.JUnitCore;

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
        JUnitCore.runClasses(AllTests.class);
    }
}
