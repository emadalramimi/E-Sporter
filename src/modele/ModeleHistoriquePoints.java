package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.HistoriquePoints;

public class ModeleHistoriquePoints extends DAO<HistoriquePoints, Integer> {
    
    private ModeleTournoi modeleTournoi;

    public ModeleHistoriquePoints() {
        this.modeleTournoi = new ModeleTournoi();
    }

    public List<HistoriquePoints> getParEquipe(int idEquipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from historiquePoints where idEquipe = ?");
        ps.setInt(1, idEquipe);

        ResultSet rs = ps.executeQuery();
		
		// Parcourt les historiquePoints dans la base de données et les formate dans une liste
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
                                ModeleHistoriquePoints.this.modeleTournoi.getParId(rs.getInt("idTournoi")).orElse(null),
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
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		return stream.collect(Collectors.toList());
    }

    @Override
    public boolean ajouter(HistoriquePoints historiquePoints) throws Exception {
        try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into historiquePoints values (next value for idHistoriquePoints, ?, ?, ?)");
            ps.setFloat(1, historiquePoints.getPoints());
            ps.setInt(2, historiquePoints.getTournoi().getIdTournoi());
            ps.setInt(3, historiquePoints.getIdEquipe());
			ps.execute();
			ps.close();
			
			BDD.getConnexion().commit();
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

}
