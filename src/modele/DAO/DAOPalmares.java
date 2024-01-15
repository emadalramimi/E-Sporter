package modele.DAO;

import java.util.List;

import modele.metier.Palmares;

public interface DAOPalmares  {
    
    public List<Palmares> getClassement() throws Exception;

}
