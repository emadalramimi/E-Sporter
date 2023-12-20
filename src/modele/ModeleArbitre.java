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

import modele.metier.Arbitre;

public class ModeleArbitre extends DAO<Arbitre, Integer> {
	/**
	 * Récupère tous les arbitres
	 * @return Liste des arbitres
	 */
	@Override
	public List<Arbitre> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from arbitre");
		
		// Parcourt les arbitres dans la base de données et les formate dans une liste
		Stream<Arbitre> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Arbitre>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Arbitre> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Arbitre(
                    		rs.getInt("idArbitre"),
                			rs.getString("nom"),
                			rs.getString("prenom")
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
	 * Récupère un arbitre depuis la BDD par sa clé primaire
	 * @param idJoueur : identifiant de l'arbitre
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
			arbitre = new Arbitre(
				rs.getInt("idArbitre"),
				rs.getString("nom"),
				rs.getString("prenom")
			);
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(arbitre);
	}
	
	/**
	 * Récupère les arbitres affectés à un tournoi
	 * @param idTournoi Identifiant du tournoi
	 * @return Liste des arbitres affectés
	 */
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
	                        action.accept(new Arbitre(
	                    		rs.getInt("idArbitre"),
	                			rs.getString("nom"),
	                			rs.getString("prenom")
	                        ));
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
	 * Récupère les arbitres qui ne sont pas déjà affectés à un tournoi
	 * @param arbitresNonEligibles Liste des arbitres déjà affectés
	 * @return Liste des arbitres non affectés
	 * @throws Exception Erreur SQL
	 */
	public Arbitre[] getTableauArbitres(List<Arbitre> arbitresNonEligibles) throws Exception {
		return this.getTout().stream()
				.filter(e -> !arbitresNonEligibles.contains(e))
				.toArray(Arbitre[]::new);
	}
	
}
