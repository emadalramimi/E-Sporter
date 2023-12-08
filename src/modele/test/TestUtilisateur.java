package modele.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleAdministrateur;
import modele.ModeleUtilisateur;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Administrateur;

public class TestUtilisateur {

	private ModeleUtilisateur modeleUtilisateur;
	private ModeleAdministrateur modeleAdministrateur;
	
    @Before
    public void setUp() {
        this.modeleUtilisateur = new ModeleUtilisateur();
        this.modeleAdministrateur = new ModeleAdministrateur();
        if (ModeleUtilisateur.getCompteCourant() != null)
            this.modeleUtilisateur.deconnecter();
    }
    
    /*
     * Test si la methode ajouter renvoie true
     */
    @Test
    public void testAjouterTrue() throws Exception {
    	Administrateur admini = new Administrateur(7, "John", "Doe", "john.doe", "password");
    	modeleAdministrateur.supprimer(admini);
    	assertTrue(modeleAdministrateur.ajouter(admini));
    }
    
    /*
     * Test si la methode ajouter renvoie false lorsqu'il y a une erreur
     */
    @Test
    public void testAjouterFalse() throws Exception, SQLException {
    	Administrateur adminToAdd = new Administrateur(1, "John", "Doe", "john.doe", "password");
    	assertFalse(modeleAdministrateur.ajouter(adminToAdd));
    }
    
    /*
     * Test si la methode modifier renvoie true
     */
    @Test
    public void testModifierAdministrateur() throws Exception {
    	Administrateur adminToAdd = new Administrateur(7, "John", "Doe", "john.doe", "password");
    	modeleAdministrateur.ajouter(adminToAdd);
   	 	assertTrue(modeleAdministrateur.modifier(new Administrateur(2, "Admin", "Admin", "admin", "mdpadmin")));
   	 	modeleAdministrateur.supprimer(new Administrateur(2, "Admin", "Admin", "admin", "mdpadmin"));
    }
    
    /*
     * Test si la methode supprimer renvoie true
     */
    @Test
    public void testSupprimerTrue() throws Exception {
    	 Administrateur adminToAdd = new Administrateur(7, "John", "Doe", "john.doe", "password");
    	 modeleAdministrateur.ajouter(adminToAdd);
    	 assertTrue(modeleAdministrateur.supprimer(adminToAdd));
    }
    
    /*
     * Test l'erreur IllegalArgumentException lorsqu'on essaie de se connecter si l'on l'est déjà
     */
    @Test(expected = IllegalStateException.class)
    public void testConnexionDejaConnecte() throws Exception {
    	modeleUtilisateur.connecter("admin", "mdp");
    	modeleUtilisateur.connecter("admin", "mdp");
    }
    
    /*
     * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se connecter et que l'identifiant est invalide
     */
    @Test(expected = IdentifiantOuMdpIncorrectsException.class)
    public void testConnexionIdentifiantInvalide() throws Exception {
    	modeleUtilisateur.connecter("fauxadmin", "mdp");
    }
    
    /*
     * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se connecter et que le mot de passe est invalide
     */
    @Test(expected = IdentifiantOuMdpIncorrectsException.class)
    public void testConnexionMotDePasseInvalide() throws Exception {
    	modeleUtilisateur.connecter("admin", "*admin");
    }
    
    /*
     * Test si la connexion fonctionne lorsqu'on rentre un identifiant et mot de passe valide
     */
    @Test
    public void testConnexionValide() throws Exception {
    	modeleUtilisateur.connecter("admin", "mdp");
        assertTrue(modeleUtilisateur!=null);
    }
    
    /*
     * Test si le compte courant de l'admin est bien null lorsqu'il est déconnecté
     */
    @Test
    public void testDeconnexionValide() throws Exception {
    	modeleUtilisateur.connecter("admin", "mdp");
    	modeleUtilisateur.deconnecter();
    	assertTrue(ModeleUtilisateur.getCompteCourant() == null);
    }
    
    /*
     * Test l'erreur IllegalArgumentException lorsqu'on essaie de se déconnecter alors que l'on n'était pas connecté
     */
    @Test(expected = IllegalStateException.class)
    public void testDeconnexionInvalide() throws IllegalStateException {
    	modeleUtilisateur.deconnecter();
    }
    
    /*
     * Test du getter getCompteCourant
     */
    @Test
    public void testGetCompteCourant() {
    	assertEquals(ModeleUtilisateur.getCompteCourant(), null);
    }
}
