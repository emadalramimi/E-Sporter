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

import modele.metier.Poule;
import modele.metier.Rencontre;

/**
 * Modèle poule
 */
public class ModelePoule extends DAO<Poule, Integer> {
	
	private ModeleRencontre modeleRencontre;

	/**
	 * Construit un modèle poule
	 */
	public ModelePoule() {
		this.modeleRencontre = new ModeleRencontre();
	}

	/**
	 * Récupère toutes les poules
	 * @return Liste de toutes les poules
	 * @throws Exception Exception SQL
	 */
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
                			ModelePoule.this.modeleRencontre.getRencontresPoules(rs.getInt("idPoule"))
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
	 * Supprime la poule dans la BDD
	 * @param poule Poule à supprimer
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean supprimer(Poule poule) throws Exception {
		try {
			// Supprime les rencontres de la poule
			for (Rencontre rencontre : poule.getRencontres()) {
				this.modeleRencontre.supprimer(rencontre);
			}
			
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from poule where idPoule = ?");
			ps.setInt(1, poule.getIdPoule());
			ps.execute();
			ps.close();
			
			return true;
		} catch (SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Ajoute la poule dans la BDD
	 * @param poule Poule à ajouter
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean ajouter(Poule poule) throws Exception {
		try {
			int idPoule = this.getNextValId();
			poule.setIdPoule(idPoule);

			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into poule values (?, ?, ?, ?)");
			ps.setInt(1, poule.getIdPoule());
			ps.setBoolean(2, poule.getEstCloturee());
			ps.setBoolean(3, poule.getEstFinale());
			ps.setInt(4, poule.getIdTournoi());
			ps.execute();
			ps.close();

			for (Rencontre rencontre : poule.getRencontres()) {
				rencontre.setIdPoule(idPoule);
				this.modeleRencontre.ajouter(rencontre);
			}
			
			return true;
		} catch (SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Génère le prochain identifiant unique de poule
	 * @return le prochain identifiant unique de poule
	 * @throws Exception Exception SQL
	 */
	private int getNextValId() {
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

	/**
	 * Retourne les poules d'un tournoi
	 * @param idTournoi Identifiant du tournoi
	 * @return Liste des poules du tournoi
	 */
	public List<Poule> getPoulesTournoi(int idTournoi) {
		try {
			List<Poule> poules = this.getTout().stream()
				.filter(poule -> poule.getIdTournoi() == idTournoi)
				.collect(Collectors.toList());
			return poules;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
