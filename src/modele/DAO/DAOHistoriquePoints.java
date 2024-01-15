package modele.DAO;

import java.util.List;
import java.util.Map;

import modele.metier.Equipe;
import modele.metier.HistoriquePoints;

public interface DAOHistoriquePoints extends DAO<HistoriquePoints, Integer> {
    
    public List<HistoriquePoints> getParEquipe(int idEquipe) throws Exception;

    public Map<Equipe, Integer> getClassementParEquipe() throws Exception;

}
