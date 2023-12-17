package modele.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import modele.ModeleAdministrateur;
import modele.ModeleUtilisateur;
import modele.metier.Administrateur;
import modele.metier.Joueur;

public class TestModeleAdministrateur {

	private ModeleUtilisateur modeleUtilisateur;
	private ModeleAdministrateur modeleAdministrateur;
	private Administrateur adminToAdd;
	
    @Before
    public void setUp() {
        this.modeleAdministrateur = new ModeleAdministrateur();
        this.adminToAdd = new Administrateur(7, "John", "Doe", "john.doe", "password");
    }
    
    /*
     * Test si la methode ajouter renvoie true
     */
    @Test
    public void testAjouterTrue() throws Exception {
    	assertTrue(modeleAdministrateur.ajouter(adminToAdd));
    }
    
    /*
     * Test si la methode supprimer renvoie true
     */
    @Test
    public void testSupprimerTrue() throws Exception {
    	 assertTrue(modeleAdministrateur.supprimer(adminToAdd));
    }
    
    /*
     * Test si la methode ajouter renvoie false lorsqu'il y a une erreur
     */
    @Test(expected = RuntimeException.class)
    public void testAjouterFalse() throws Exception, SQLException {
    	modeleAdministrateur.ajouter(adminToAdd);
    	modeleAdministrateur.ajouter(adminToAdd);
    }
    
    /*
     * Test si la methode modifier renvoie true
     */
    @Test
    public void testModifierAdministrateur() throws Exception {
    	modeleAdministrateur.ajouter(adminToAdd);
   	 	assertTrue(modeleAdministrateur.modifier(adminToAdd));
    }
    
    @Test
	public void testGetTout() throws Exception {
    	int j=1;
	    assertNotNull(modeleAdministrateur.getTout());
	    List<Administrateur> listTest = modeleAdministrateur.getTout();
	    List<Administrateur> listAdmin = new ArrayList<>();
	    while (listAdmin.size() < listTest.size()) {
	    	listAdmin.add(modeleAdministrateur.getParId(j).get());
	    }
	    assertEquals(listTest.size(), listAdmin.size());
	    for (int i = 0; i < listTest.size(); i++) {
	    	assertTrue(listTest.get(i).equals(listTest.get(i)));
	    }
	}
	
	@Test
	public void testGetParId() throws Exception {
		modeleAdministrateur.ajouter(adminToAdd);
	    Administrateur retrievedAdmin= modeleAdministrateur.getParId(adminToAdd.getIdAdministrateur()).get();
	    assertEquals(adminToAdd.getIdAdministrateur(),retrievedAdmin.getIdAdministrateur());
	    assertEquals(adminToAdd.getIdentifiant(),retrievedAdmin.getIdentifiant());
	    assertEquals(adminToAdd.getNom(),retrievedAdmin.getNom());
	    assertEquals(adminToAdd.getPrenom(),retrievedAdmin.getPrenom());
	    assertEquals(adminToAdd.getRole(),retrievedAdmin.getRole());
	}
	
	@Test
	public void testGetParIdentifiant() throws Exception {
		modeleAdministrateur.ajouter(adminToAdd);
	    Administrateur retrievedAdmin= modeleAdministrateur.getParIdentifiant(adminToAdd.getIdentifiant()).get();
	    assertEquals(adminToAdd.getIdAdministrateur(),retrievedAdmin.getIdAdministrateur());
	    assertEquals(adminToAdd.getIdentifiant(),retrievedAdmin.getIdentifiant());
	    assertEquals(adminToAdd.getNom(),retrievedAdmin.getNom());
	    assertEquals(adminToAdd.getPrenom(),retrievedAdmin.getPrenom());
	    assertEquals(adminToAdd.getRole(),retrievedAdmin.getRole());
	}
    
    @After
    public void tearDown() throws Exception {
    	modeleAdministrateur.supprimer(adminToAdd);
    }
}
