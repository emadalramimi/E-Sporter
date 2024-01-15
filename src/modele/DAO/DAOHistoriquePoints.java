package modele.DAO;

import java.sql.ResultSet;
import java.util.List;

import modele.metier.HistoriquePoints;

public interface DAOHistoriquePoints extends DAO<HistoriquePoints, Integer> {
    
    public List<HistoriquePoints> getParEquipe(int idEquipe) throws Exception;

    public ResultSet getClassementParEquipe() throws Exception;

}
