package modele.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleAdministrateur;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Administrateur;

public class TestUtilisateur {

private ModeleAdministrateur modele;
    
    @Before
    public void setUp() {
        this.modele = new ModeleAdministrateur();
        if (modele.getCompteCourant() != null)
            this.modele.deconnecter();
    }
    
    /*
     * Test si la methode ajouter renvoie true
     */
    @Test
    public void testAjouterTrue() throws Exception {
    	Administrateur admini = new Administrateur(7, "John", "Doe", "john.doe", "password");
    	modele.supprimer(admini);
    	assertTrue(modele.ajouter(admini));
    }
    
}
