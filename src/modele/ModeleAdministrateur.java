package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.Administrateur;

public class ModeleAdministrateur implements DAO<Administrateur, Integer> {

	/**
	 * @return Liste de tous les administrateurs
	 */
	@Override
	public List<Administrateur> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from administrateur");
		
		// Parcourt les administrateurs dans la base de données et les formate dans une liste
		Stream<Administrateur> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Administrateur>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Administrateur> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Administrateur(
                    		rs.getInt("idAdministrateur"),
                    		rs.getString("nom"),
                    		rs.getString("prenom"),
                    		rs.getString("identifiant"),
                    		rs.getString("motDePasse")
                        ));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
	        }, false).onClose(() -> {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		return stream.collect(Collectors.toList());
	}

	/**
	 * @return Retourne un administrateur depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Administrateur> getParId(Integer... idAdministrateur) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from administrateur where idAdministrateur = ?");
		ps.setInt(1, idAdministrateur[0]);
		
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

	/**
	 * Ajoute l'administrateur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Administrateur administrateur) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into administrateur values (?, ?, ?, ?, ?)");
			ps.setInt(1, administrateur.getIdAdministrateur());
			ps.setString(2, administrateur.getNom());
			ps.setString(3, administrateur.getPrenom());
			ps.setString(4, administrateur.getIdentifiant());
			ps.setString(5, ModeleUtilisateur.chiffrerMotDePasse(administrateur.getMotDePasse()));
			ps.execute();
			
			BDD.getConnexion().commit();
			ps.close();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Modifie l'administrateur dans la BDD
	 * Cette méthode ne peut pas changer le mot de passe de l'administrateur
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Administrateur administrateur) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update administrateur set nom = ?, prenom = ?, identifiant = ? where idAdministrateur = ?");
			ps.setString(1, administrateur.getNom());
			ps.setString(2, administrateur.getPrenom());
			ps.setString(3, administrateur.getIdentifiant());
			ps.setInt(4, administrateur.getIdAdministrateur());
			ps.execute();
			
			BDD.getConnexion().commit();
			ps.close();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Supprime l'administrateur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Administrateur administrateur) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from administrateur where idAdministrateur = ?");
			ps.setInt(1, administrateur.getIdAdministrateur());
			ps.execute();
			
			BDD.getConnexion().commit();
			ps.close();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}
	
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