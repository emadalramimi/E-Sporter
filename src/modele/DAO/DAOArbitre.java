package modele.DAO;

import java.util.List;

import modele.metier.Arbitre;

public interface DAOArbitre extends DAO<Arbitre, Integer> {
    
    public List<Arbitre> getArbitresTournoi(int idTournoi);

    public Arbitre[] getTableauArbitres(List<Arbitre> arbitresNonEligibles) throws Exception;

    

}
