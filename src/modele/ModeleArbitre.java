package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.Arbitre;

public class ModeleArbitre {

	public Arbitre[] getTableauArbitres() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from arbitre");
		
		// Parcourt les équipes dans la base de données et les formate dans une liste
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
		
		return stream.toArray(Arbitre[]::new);
	}
	
}
