package modele.DAO;

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

import modele.metier.Arbitre;

/**
 * Implémentation DAO pour la classe Arbitre
 */
public class DAOArbitreImpl implements DAOArbitre {

	/**
	 * Retourne la liste de tous les arbitres
	 * @return Liste des arbitres
	 * @throws Exception Exception SQL
	 */
	@Override
	public List<Arbitre> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from arbitre");
		
		return this.collect(st, rs);
	}

	/**
	 * Recherche un arbitre par son identifiant
	 * @param idArbitre : identifiant de l'arbitre
	 * @return Retourne un arbitre depuis la BDD par sa clé primaire
	 * @throws Exception Exception SQL
	 */
	@Override
	public Optional<Arbitre> getParId(Integer... idArbitre) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from arbitre where idArbitre = ?");
		ps.setInt(1, idArbitre[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de joueur si il existe
		Arbitre arbitre = null;
		if(rs.next()) {
			arbitre = this.construireArbitre(rs);
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(arbitre);
	}
	
	@Override
	public boolean ajouter(Arbitre arbitre) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}
	
	@Override
	public boolean modifier(Arbitre arbitre) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

	@Override
	public boolean supprimer(Arbitre arbitre) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}
	
	/**
	 * Recherche une liste d'arbitres par leur tournoi
	 * @param idTournoi Identifiant du tournoi
	 * @return Liste des arbitres affectés
	 */
	@Override
	public List<Arbitre> getArbitresTournoi(int idTournoi) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from arbitre, arbitrer where arbitre.idArbitre = arbitrer.idArbitre and arbitrer.idTournoi = ?");
			ps.setInt(1, idTournoi);
			
			ResultSet rs = ps.executeQuery();
			
			// Parcourt les arbitres dans la base de données et les formate dans une liste
			Stream<Arbitre> stream = StreamSupport.stream(
	    		new Spliterators.AbstractSpliterator<Arbitre>(Long.MAX_VALUE, Spliterator.ORDERED) {
	                @Override
	                public boolean tryAdvance(Consumer <? super Arbitre> action) {
	                    try {
	                        if (!rs.next()) {
	                            return false;
	                        }
	                        action.accept(DAOArbitreImpl.this.construireArbitre(rs));
	                        return true;
	                    } catch (SQLException e) {
	                        throw new RuntimeException(e);
	                    }
	                }
		        }, false).onClose(() -> {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
				
			return stream.collect(Collectors.toList());
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retourne la liste des arbitres non affectés à un tournoi
	 * @param arbitresNonEligibles Liste des arbitres déjà affectés
	 * @return Liste des arbitres non affectés
	 * @throws Exception Erreur SQL
	 */
	@Override
	public Arbitre[] getTableauArbitres(List<Arbitre> arbitresNonEligibles) throws Exception {
		return this.getTout().stream()
				.filter(e -> !arbitresNonEligibles.contains(e))
				.toArray(Arbitre[]::new);
	}

	/**
	 * Parcourt les arbitres dans la base de données et les formate dans une liste
	 * @param st Statement
	 * @param rs ResultSet
	 * @return Liste des arbitres
	 */
	private List<Arbitre> collect(Statement st, ResultSet rs) throws Exception {
		Stream<Arbitre> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Arbitre>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Arbitre> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(DAOArbitreImpl.this.construireArbitre(rs));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
	        }, false).onClose(() -> {
				try {
					rs.close();
                    st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		return stream.collect(Collectors.toList());
	}
	
	/**
	 * Construit un arbitre depuis un ResultSet
	 * @param rs ResultSet à parcourir
	 * @return Arbitre construit
	 */
	private Arbitre construireArbitre(ResultSet rs) throws SQLException {
		return new Arbitre(
			rs.getInt("idArbitre"),
			rs.getString("nom"),
			rs.getString("prenom")
		);
	}
	
}
