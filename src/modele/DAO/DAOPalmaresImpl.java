package modele.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import modele.metier.Palmares;

/**
 * Implémentation DAO pour la classe Palmares
 */
public class DAOPalmaresImpl implements DAOPalmares {
    
    private DAOEquipe daoEquipe;

    public DAOPalmaresImpl() {
        this.daoEquipe = new DAOEquipeImpl();
    }

	/**
	 * Récupère toutes les équipes
	 * @return Liste de tous les équipes
	 * @throws Exception Erreur SQL
	 */
    @Override
	public List<Palmares> getClassement() throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select e.*, coalesce((select sum(hp.points) from historiquePoints hp where hp.idEquipe = e.idEquipe), 0) as points from equipe e where e.saison = ? order by e.classement asc");
		ps.setInt(1, LocalDate.now().getYear());
		ResultSet rs = ps.executeQuery();

		Stream<Palmares> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Palmares>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Palmares> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        try {
                            action.accept(new Palmares(
                                DAOPalmaresImpl.this.daoEquipe.construireEquipe(rs),
                                rs.getFloat("points")
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
                    ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		return stream.collect(Collectors.toList());
	}

}
