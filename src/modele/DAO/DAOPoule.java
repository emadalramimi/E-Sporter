package modele.DAO;

import java.sql.SQLException;
import java.util.List;

import modele.metier.Poule;

/**
 * Interface DAO pour la classe Poule
 */
public interface DAOPoule extends DAO<Poule, Integer> {
    
    public List<Poule> getPoulesTournoi(int idTournoi);

    public void cloturerPoule(Poule poule) throws SQLException;

}
