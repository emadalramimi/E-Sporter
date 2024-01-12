package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleAdministrateur;
import modele.metier.Administrateur;

public class TestModeleAdministrateur {

	private ModeleAdministrateur modeleAdministrateur;
	private Administrateur adminToAdd;

	@Before
	public void setUp() {
		this.modeleAdministrateur = new ModeleAdministrateur();
		this.adminToAdd = new Administrateur(1, "Istrateur", "Admin", "admin", "mdp");
	}

	@Test
	public void testGetParIdentifiant() throws Exception {
		assertEquals(this.modeleAdministrateur.getParIdentifiant(this.adminToAdd.getIdentifiant()).get(),
				this.adminToAdd);
	}
}
