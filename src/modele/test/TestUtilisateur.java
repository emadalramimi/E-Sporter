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
    
    /*
     * Test si la methode ajouter renvoie false lorsqu'il y a une erreur
     */
    @Test
    public void testAjouterFalse() throws Exception, SQLException {
    	 Administrateur adminToAdd = new Administrateur(1, "John", "Doe", "john.doe", "password");
    	 assertFalse(modele.ajouter(adminToAdd));
    }
    
    /*
     * Test si la methode modifier renvoie true
     */
    @Test
    public void testModifierAdministrateur() throws Exception {
    	Administrateur adminToAdd = new Administrateur(7, "John", "Doe", "john.doe", "password");
   	 	modele.ajouter(adminToAdd);
   	 	assertTrue(modele.modifier(new Administrateur(2, "Admin", "Admin", "admin", "mdpadmin")));
   	 	modele.supprimer(new Administrateur(2, "Admin", "Admin", "admin", "mdpadmin"));
    }
    
    /*
     * Test si la methode supprimer renvoie true
     */
    @Test
    public void testSupprimerTrue() throws Exception {
    	 Administrateur adminToAdd = new Administrateur(7, "John", "Doe", "john.doe", "password");
    	 modele.ajouter(adminToAdd);
    	 assertTrue(modele.supprimer(adminToAdd));
    }
    
    /*
     * Test l'erreur IllegalArgumentException lorsqu'on essaie de se connecter si l'on l'est déjà
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConnexionDejaConnecte() throws IllegalArgumentException, IdentifiantOuMdpIncorrectsException, RuntimeException {
    	modele.connecter("admin", "mdpadmin");
        modele.connecter("admin", "mdpadmin");
    }
    
    /*
     * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se connecter et que l'identifiant est invalide
     */
    @Test(expected = IdentifiantOuMdpIncorrectsException.class)
    public void testConnexionIdentifiantInvalide() throws IllegalArgumentException, IdentifiantOuMdpIncorrectsException, RuntimeException {
        modele.connecter("fauxadmin", "mdpadmin");
    }
    
    /*
     * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se connecter et que le mot de passe est invalide
     */
    @Test(expected = IdentifiantOuMdpIncorrectsException.class)
    public void testConnexionMotDePasseInvalide() throws IllegalArgumentException, IdentifiantOuMdpIncorrectsException, RuntimeException {
        modele.connecter("admin", "*admin");
    }
    
}
