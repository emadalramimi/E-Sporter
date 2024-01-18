package modele.test.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Tournoi;

/**
 * Super-classe de test de modele
 */
public class TestModele {

    private DAOTournoi daoTournoi;

    public TestModele() {
        this.daoTournoi = new DAOTournoiImpl();
    }
    
	/**
	 * Renvoie la date courante en secondes
	 */
	protected long getDateCourante() {
		return (System.currentTimeMillis() / 1000);
	}

    /**
     * Nettoie la table Tournoi en supprimant tous les tournois sauf les 6 premiers (données de base)
     * @throws Exception si une erreur se produit pendant le test
     */
    protected void nettoyerTournois() throws Exception {
        List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for (Tournoi tournoi : this.daoTournoi.getTout()) {
			if (!idAGarder.contains(tournoi.getIdTournoi())) {
				this.daoTournoi.supprimer(tournoi);
			}
		}
    }

}
