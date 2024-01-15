package modele.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.HistoriquePoints;

public class DAOHistoriquePointsImpl implements DAOHistoriquePoints {
    
    private DAOTournoi daoTournoi;

    public DAOHistoriquePointsImpl() {
        this.daoTournoi = new DAOTournoiImpl();
    }

    @Override
	public List<HistoriquePoints> getTout() throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

    @Override
	public Optional<HistoriquePoints> getParId(Integer... idHistoriquePoints) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

    @Override
    public boolean ajouter(HistoriquePoints historiquePoints) throws Exception {
        try {
            Connection connection = BDD.getConnexion();
            
			PreparedStatement ps = connection.prepareStatement("insert into historiquePoints values (next value for idHistoriquePoints, ?, ?, ?)");
            ps.setFloat(1, historiquePoints.getPoints());
            ps.setInt(2, historiquePoints.getTournoi().getIdTournoi());
            ps.setInt(3, historiquePoints.getIdEquipe());
			ps.execute();
			ps.close();
			
			connection.commit();
			return true;
		} catch(Exception e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
    }

    @Override
	public boolean modifier(HistoriquePoints historiquePoints) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}

    @Override
	public boolean supprimer(HistoriquePoints historiquePoints) throws Exception {
		throw new UnsupportedOperationException("Méthode non implémentée");
	}
    
    @Override
    public List<HistoriquePoints> getParEquipe(int idEquipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from historiquePoints where idEquipe = ?");
        ps.setInt(1, idEquipe);

        ResultSet rs = ps.executeQuery();
		
		return this.collect(rs, ps);
    }

    /**
	 * Retourne le classement des équipes
	 * @return Le classement des équipes
	 * @throws Exception Erreurs SQL ou de récupération d'équipes
	 */
    @Override
	public ResultSet getClassementParEquipe() throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select e.idEquipe, sum(hp.points) from equipe e, historiquePoints hp where e.idEquipe = hp.idEquipe and e.saison = ? group by e.idEquipe order by sum(hp.points) desc");
		ps.setInt(1, LocalDate.now().getYear());
        return ps.executeQuery();
	}
    
    /**
     * Parcourt les historiquePoints dans la base de données et les formate dans une liste
     * @param rs ResultSet
     * @param ps PreparedStatement
     * @return Liste des historiquePoints
     * @throws Exception Erreur de lecture de la base de données
     */
    private List<HistoriquePoints> collect(ResultSet rs, PreparedStatement ps) throws Exception {
        Stream<HistoriquePoints> stream = StreamSupport.stream(
        new Spliterators.AbstractSpliterator<HistoriquePoints>(Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer <? super HistoriquePoints> action) {
                try {
                    if (!rs.next()) {
                        return false;
                    }
                    try {
                        action.accept(new HistoriquePoints(
                            rs.getInt("idHistoriquePoints"),
                            rs.getFloat("points"),
                            DAOHistoriquePointsImpl.this.daoTournoi.getParId(rs.getInt("idTournoi")).orElse(null),
                            rs.getInt("idEquipe")
                        ));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, false).onClose(() -> {
            try {
                rs.close();
                if(ps != null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    
        return stream.collect(Collectors.toList());
    }

}
