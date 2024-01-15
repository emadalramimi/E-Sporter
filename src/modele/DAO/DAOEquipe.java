package modele.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Tournoi;

/**
 * Interface DAO pour la classe Equipe
 */
public interface DAOEquipe extends DAO<Equipe, Integer> {

    public List<Equipe> getEquipesTournoi(int idTournoi);

    public boolean estEquipeInscriteUnTournoi(Equipe equipe) throws Exception;

    public boolean estEquipeInscriteUnTournoiOuvert(Equipe equipe) throws Exception;

    public void inscrireEquipe(Equipe equipe, Tournoi tournoi) throws Exception;

    public void desinscrireEquipe(Equipe equipe, Tournoi tournoi) throws Exception;

    public List<Equipe> getEquipesSaison() throws Exception;

    public Equipe[] getTableauEquipes(List<Equipe> equipesNonEligibles) throws Exception;

    public List<Equipe> getParFiltrage(Pays pays) throws Exception;
 
    public Equipe construireEquipe(ResultSet rs) throws SQLException;

}
