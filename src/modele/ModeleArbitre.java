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
	 * @return Retourne un arbitre depuis la BDD par sa clé primaire
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
	 * Ajoute le arbitre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Arbitre arbitre) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into arbitre values (?, ?, ?)");
			ps.setInt(1, arbitre.getIdArbitre());
			ps.setString(2, arbitre.getNom());
			ps.setString(3, arbitre.getPrenom());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Modifie le arbitre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Arbitre arbitre) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update arbitre set nomArbitre = ?, notoriete = ?, dateDebut = ?, dateFin = ?, estCloture = ?, identifiant = ?, motDePasse = ? where idArbitre = ?");
			ps.setString(1, arbitre.getNom());
			ps.setString(2, arbitre.getPrenom());
			ps.setInt(3, arbitre.getIdArbitre());
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Supprime le arbitre dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Arbitre arbitre) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from arbitre where idArbitre = ?");
			ps.setInt(1, arbitre.getIdArbitre());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	public Arbitre[] getTableauArbitres() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from arbitre");

		// Parcourt les équipes dans la base de données et les formate dans une liste
		Stream<Arbitre> stream = StreamSupport.stream(
				new Spliterators.AbstractSpliterator<Arbitre>(Long.MAX_VALUE, Spliterator.ORDERED) {
					@Override
					public boolean tryAdvance(Consumer<? super Arbitre> action) {
						try {
							if (!rs.next()) {
								return false;
							}
							action.accept(new Arbitre(
									rs.getInt("idArbitre"),
									rs.getString("nom"),
									rs.getString("prenom")));
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

		return stream.toArray(Arbitre[]::new);
	}
	
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
	
}
