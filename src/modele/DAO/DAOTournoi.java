package modele.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import controleur.ControleurTournois;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public interface DAOTournoi extends DAO<Tournoi, Integer>, Recherchable<Tournoi> {
    
    public Optional<Tournoi> getParIdentifiant(String identifiant) throws SQLException;

    public Optional<Tournoi> getTournoiRencontre(int idRencontre);

    public List<Tournoi> getParFiltrage(Notoriete notoriete, ControleurTournois.Statut statut) throws Exception;

    public void cloturerTournoi(Tournoi tournoi) throws SQLException;

    public void ouvrirTournoi(Tournoi tournoi) throws SQLException;

}
