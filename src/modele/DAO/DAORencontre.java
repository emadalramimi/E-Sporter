package modele.DAO;

import java.util.List;

import modele.metier.Equipe;
import modele.metier.Rencontre;

public interface DAORencontre extends DAO<Rencontre, Integer> {
    
    public List<Rencontre> getRencontresPoules(int idPoule);

    public Equipe[] getEquipesRencontre(int idRencontre);

    public void setEquipeGagnante(Rencontre rencontre, String nomEquipe) throws Exception;

    public void resetEquipeGagnante(Rencontre rencontre) throws Exception;

}
