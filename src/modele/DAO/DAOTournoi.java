package modele.DAO;

import java.sql.SQLException;
import java.util.Optional;

import modele.metier.Tournoi;

public interface DAOTournoi extends DAO<Tournoi, Integer> {
    
    public Optional<Tournoi> getParIdentifiant(String identifiant) throws SQLException;

    public Optional<Tournoi> getTournoiRencontre(int idRencontre);

    public void cloturerTournoi(Tournoi tournoi) throws SQLException;

    public void ouvrirTournoi(Tournoi tournoi) throws SQLException;

}
