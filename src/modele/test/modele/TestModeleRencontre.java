package modele.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import modele.ModeleRencontre;
import modele.DAO.DAORencontre;
import modele.DAO.DAORencontreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;

/**
 * Classe de test pour le modèle Rencontre
 * @see ModeleRencontre
 */
public class TestModeleRencontre extends TestModele {

	private ModeleRencontre modele;

	/**
	 * Teste la méthode getRencontreInMemory() de la classe ModeleRencontre
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleRencontre#getRencontreInMemory()
	 */
	@Test
	public void testGetRencontreInMemory() throws Exception {
		modele = new ModeleRencontre();
		DAOTournoi daoTournoi = new DAOTournoiImpl();
		DAORencontre daoRencontre = new DAORencontreImpl();
		assertEquals(modele.getRencontreInMemory(daoTournoi.getParId(1).get(), 1), daoRencontre.getParId(1).get());
		assertNotEquals(modele.getRencontreInMemory(daoTournoi.getParId(1).get(), 100), daoRencontre.getParId(1).get());
	}

}
