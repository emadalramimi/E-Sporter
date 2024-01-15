package modele.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import modele.metier.Administrateur;

/**
 * Modèle administrateur
 */
public class DAOAdministrateurImpl implements DAOAdministrateur {
	
	@Override
	public List<Administrateur> getTout() throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

	@Override
	public Optional<Administrateur> getParId(Integer... id) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

	@Override
	public boolean ajouter(Administrateur admin) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

	@Override
	public boolean modifier(Administrateur admin) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

	@Override
	public boolean supprimer(Administrateur admin) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

    /**
	 * Récupère un administrateur par son identifiant
	 * @param identifiant Identifiant de l'administrateur
	 * @return Administrateur s'il existe
	 * @throws SQLException Exception SQL
	 */
	@Override
    public Optional<Administrateur> getParIdentifiant(String identifiant) throws SQLException {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from administrateur where identifiant = ?");
		ps.setString(1, identifiant);

		ResultSet rs = ps.executeQuery();
		
		// Création de l'administrateur s'il existe
		Administrateur administrateur = null;
		if(rs.next()) {
			administrateur = new Administrateur(
	    		rs.getInt("idAdministrateur"),
	    		rs.getString("nom"),
	    		rs.getString("prenom"),
	    		rs.getString("identifiant"),
	    		rs.getString("motDePasse")
            );
		}

		rs.close();
		ps.close();
		return Optional.ofNullable(administrateur);
	}

}
