package modele.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import modele.ModeleUtilisateur;

public class TestModeleUtilisateur {

	private ModeleUtilisateur modeleUtilisateur;
	
	@Before
    public void setUp() {
        this.modeleUtilisateur = new ModeleUtilisateur();
        if (ModeleUtilisateur.getCompteCourant() != null)
            this.modeleUtilisateur.deconnecter();
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
    @Test(expected = IllegalArgumentException.class)
    public void testConnexionIdentifiantInvalide() throws Exception {
    	modeleUtilisateur.connecter("fauxadmin", "mdp");
    }
    
    /*
     * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se connecter et que le mot de passe est invalide
     */
    @Test(expected = IllegalArgumentException.class)
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
