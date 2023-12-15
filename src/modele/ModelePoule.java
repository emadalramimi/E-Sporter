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

import modele.metier.Poule;

public class ModelePoule extends DAO<Poule, Integer> {
	
	private ModeleRencontre modeleRencontre;
	
	public ModelePoule() {
		this.modeleRencontre = new ModeleRencontre();
	}
	
	@Override
	public List<Poule> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from poule");
		
		// Parcourt les poules dans la base de données et les formate dans une liste
		Stream<Poule> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Poule>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Poule> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Poule(
                    		rs.getInt("idPoule"),
                			rs.getBoolean("estCloturee"),
                			rs.getBoolean("estFinale"),
                			rs.getInt("idTournoi"),
                			ModelePoule.this.modeleRencontre.getListeRencontresParId(rs.getInt("idPoule"))
                        ));
                        return true;
                    } catch (Exception e) {
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
	 * @return Retourne une poule depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Poule> getParId(Integer... idPoule) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from poule where idPoule = ?");
		ps.setInt(1, idPoule[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de poule si il existe
		Poule poule = null;
		if(rs.next()) {
			poule = new Poule(
				rs.getInt("idPoule"),
				rs.getBoolean("estCloturee"),
				rs.getBoolean("estFinale"),
				rs.getInt("idTournoi"),
				ModelePoule.this.modeleRencontre.getListeRencontresParId(rs.getInt("idPoule"))
            );
		}	
		rs.close();
		ps.close();
		return Optional.ofNullable(poule);
	}

	/**
	 * Ajoute la poule dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Poule poule) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into poule values (?, ?, ?, ?)");
			ps.setInt(1, poule.getIdPoule());
			ps.setBoolean(2, poule.isEstCloturee());
			ps.setBoolean(3, poule.isEstFinale());
			ps.setInt(4, poule.getIdTournoi());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Supprime la poule dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Poule poule) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from poule where idPoule = ?");
			ps.setInt(1, poule.getIdPoule());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @return le prochain identifiant unique de poule
	 */
	public int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idPoule");

            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                nextVal = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nextVal;
    }
	
}
