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

import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Administrateur;

public class ModeleAdministrateur implements DAO<Administrateur, Integer> {
	
	private static Administrateur compteCourant;

	/**
	 * @return Liste de tous les administrateurs
	 */
	@Override
	public List<Administrateur> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from administrateur");
		
		// Parcours les administrateurs dans la base de données et les formate dans une liste
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
			ps.setString(5, administrateur.getMotDePasse());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifie l'administrateur dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Administrateur administrateur) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update administrateur set nom = ?, prenom = ?, identifiant = ?, motDePasse = ? where idAdministrateur = ?");
			ps.setString(1, administrateur.getNom());
			ps.setString(2, administrateur.getPrenom());
			ps.setString(3, administrateur.getIdentifiant());
			ps.setString(4, administrateur.getMotDePasse());
			ps.setInt(5, administrateur.getIdAdministrateur());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
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
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Connecte un administrateur avec son couple identifiant/mot de passe s'il existe
	 * @param identifiant
	 * @param motDePasse
	 * @throws IdentifiantOuMdpIncorrectsException
	 * @throws RuntimeException
	 */
	public void connecter(String identifiant, String motDePasse) throws IdentifiantOuMdpIncorrectsException, IllegalArgumentException, RuntimeException {
		if(compteCourant != null) {
			throw new IllegalArgumentException("Un administrateur est déjà connecté");
		}
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from administrateur where identifiant = ? and motDePasse = ?");
			ps.setString(1, identifiant);
			ps.setString(2, motDePasse);
			
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {
				throw new IdentifiantOuMdpIncorrectsException("Identifiant ou mot de passe incorrects");
			}
		
			Administrateur administrateur = new Administrateur(
	    		rs.getInt("idAdministrateur"),
	    		rs.getString("nom"),
	    		rs.getString("prenom"),
	    		rs.getString("identifiant"),
	    		rs.getString("motDePasse")
	        );
			
			compteCourant = administrateur;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deconnecter() throws IllegalArgumentException {
		if(compteCourant == null) {
			throw new IllegalArgumentException("Vous êtes déjà déconnecté.");
		}
		
		compteCourant = null;
	}
	
	public static Administrateur getCompteCourant() {
		return compteCourant;
	}
	
}
