package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import modele.DAO.DAOAdministrateur;
import modele.DAO.DAOAdministrateurImpl;
import modele.metier.Administrateur;

public class TestDAOAdministrateur {

	private DAOAdministrateur daoAdministrateur;
	private Administrateur adminToAdd;

	@Before
	public void setUp() {
		this.daoAdministrateur = new DAOAdministrateurImpl();
		this.adminToAdd = new Administrateur(1, "Istrateur", "Admin", "admin", "mdp");
	}

	@Test
	public void testGetParIdentifiant() throws Exception {
		assertEquals(this.daoAdministrateur.getParIdentifiant(this.adminToAdd.getIdentifiant()).get(),
				this.adminToAdd);
	}
}
