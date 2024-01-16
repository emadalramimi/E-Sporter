package modele.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modele.ModeleRencontre;
import modele.DAO.DAORencontre;
import modele.DAO.DAORencontreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;

public class TestModeleRencontre {

	private ModeleRencontre modele;
	@Test
	public void testGetRencontreInMemory() throws Exception {
		modele = new ModeleRencontre();
		DAOTournoi daoTournoi = new DAOTournoiImpl();
		DAORencontre daoRencontre = new DAORencontreImpl();
		assertEquals(modele.getRencontreInMemory(daoTournoi.getParId(1).get(), 1), daoRencontre.getParId(1).get());
	}
}
