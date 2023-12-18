package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import modele.metier.Administrateur;

public class ModeleAdministrateur {
    
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
