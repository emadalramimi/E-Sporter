package modele.DAO;

import java.sql.SQLException;
import java.util.Optional;

import modele.metier.Administrateur;

/** 
 * Interface DAO pour la classe Administrateur
 */
public interface DAOAdministrateur extends DAO<Administrateur, Integer> {

    public Optional<Administrateur> getParIdentifiant(String identifiant) throws SQLException;
    
}
