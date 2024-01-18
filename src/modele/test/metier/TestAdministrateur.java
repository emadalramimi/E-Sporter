package modele.test.metier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Administrateur;
import modele.metier.Utilisateur.Role;

public class TestAdministrateur {

	private Administrateur admin;

	@Before
	public void setUp() {
		this.admin = new Administrateur(50, "LeNoir", "Kévin", "LNK", "1234");
	}

	@Test
	public void testGetIdAdministrateur() {
		assertEquals(this.admin.getIdAdministrateur(), 50);
	}

	@Test
	public void testSetIdAdministrateur() {
		this.admin.setIdAdministrateur(51);
		assertEquals(this.admin.getIdAdministrateur(), 51);
	}

	@Test
	public void testGetIdentifiant() {
		assertEquals(this.admin.getIdentifiant(), "LNK");
	}

	@Test
	public void testSetIdentifiant() {
		this.admin.setIdentifiant("KNL");
		assertEquals(this.admin.getIdentifiant(), "KNL");
	}

	@Test
	public void testGetMotDePasse() {
		assertEquals(this.admin.getMotDePasse(), "1234");
	}

	@Test
	public void testSetMotDePasse() {
		this.admin.setMotDePasse("0000");
		assertEquals(this.admin.getMotDePasse(), "0000");
	}

	@Test
	public void testGetNom() {
		assertEquals(this.admin.getNom(), "LeNoir");
	}

	@Test
	public void testSetNom() {
		this.admin.setNom("LeBlanc");
		assertEquals(this.admin.getNom(), "LeBlanc");
	}

	@Test
	public void testGetPrenom() {
		assertEquals(this.admin.getPrenom(), "Kévin");
	}

	@Test
	public void testSetPrenom() {
		this.admin.setPrenom("Max");
		assertEquals(this.admin.getPrenom(), "Max");
	}

	@Test
	public void testGetRole() {
		assertEquals(this.admin.getRole(), Role.ADMINISTRATEUR);
	}
}
