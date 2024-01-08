package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.HistoriquePoints;

public class ModeleHistoriquePoints extends DAO<HistoriquePoints, Integer> {
    
    // à modifier car la sélection dans la liste entraine voir doc
    @Override
    public List<HistoriquePoints> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from historiquePoints");
		
		// Parcourt les historiquePoints dans la base de données et les formate dans une liste
		Stream<HistoriquePoints> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<HistoriquePoints>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super HistoriquePoints> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new HistoriquePoints(
                    		rs.getInt("idHistoriquePoints"),
                            rs.getInt("points"),
                            rs.getInt("idTournoi"),
                            rs.getInt("idEquipe")
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

    @Override
    public boolean ajouter(HistoriquePoints historiquePoints) throws Exception {
        try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into historiquePoints values (next value for idHistoriquePoints, ?, ?, ?)");
            ps.setInt(1, historiquePoints.getPoints());
            ps.setInt(2, historiquePoints.getIdTournoi());
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
