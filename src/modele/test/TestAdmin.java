package modele.test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import modele.metier.Administrateur;
import modele.metier.Utilisateur.Role;

public class TestAdmin {
	
	private Administrateur admin;

	@Before
    public void setUp() {
		admin = new Administrateur(50, "LeNoir", "Kévin", "LNK", "1234");
    }
	
	@Test
	public void testSetIdAdministrateur() {
		admin.setIdAdministrateur(51);
		assertEquals(admin.getIdAdministrateur(), 51);
	}
	
	@Test
	public void testSetIdentifiant() {
		admin.setIdentifiant("KNL");
		assertEquals(admin.getIdentifiant(), "KNL");
	}
	
	@Test
	public void testSetMotDePasse() {
		admin.setMotDePasse("0000");
		assertEquals(admin.getMotDePasse(), "0000");
	}
	
	@Test
	public void testSetNom() {
		admin.setNom("LeBlanc");
		assertEquals(admin.getNom(), "LeBlanc");
	}
	
	@Test
	public void testSetPrenom() {
		admin.setPrenom("Max");
		assertEquals(admin.getPrenom(),"Max");
	}
	
	@Test
	public void testGetRole() {
		assertEquals(admin.getRole(), Role.ADMINISTRATEUR);
	}
}
