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

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Tournoi;

public class ModeleTournoi implements DAO<Tournoi, Integer> {

	@Override
	public List<Tournoi> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from joueur");
		
		// Parcourt les tournois dans la base de données et les formate dans une liste
		Stream<Tournoi> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Tournoi>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Tournoi> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Tournoi(
                        		rs.getInt("idTournoi"),
                    			rs.getString("nomTournoi"),
                    			//je sais pas comment get Notoriete ici
                    			rs.get("notoriete"),
                    			rs.getInt("dateDebut"),
                    			rs.getInt("dateFin"),
                    			rs.getBoolean("estCloture"),
                    			rs.getString("identifiant"),
                    			rs.getString("motDePasse")
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
	 * @return Retourne un tournoi depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Tournoi> getParId(Integer... idTournoi) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi where idTournoi = ?");
		ps.setInt(1, idTournoi[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de joueur si il existe
		Tournoi tournoi = null;
		if(rs.next()) {
			tournoi = new Tournoi(
					rs.getInt("idTournoi"),
        			rs.getString("nomTournoi"),
        			//ici je ne sais pas
        			rs.get("notoriete"),
        			rs.getInt("dateDebut"),
        			rs.getInt("dateFin"),
        			rs.getBoolean("estCloture"),
        			rs.getString("identifiant"),
        			rs.getString("motDePasse")
            );
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(tournoi);
	}

	/**
	 * Ajoute le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Tournoi tournoi) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into tournoi values (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.setString(2, tournoi.getNomTournoi());
			ps.setString(3, tournoi.getNotoriete().getLibelle());
			ps.setInt(4, tournoi.getDateDebut());
			ps.setInt(5, tournoi.getDateFin());
			ps.setBoolean(6, tournoi.isEstCloture());
			ps.setString(7, tournoi.getIdentifiant());
			ps.setString(8, tournoi.getMotDePasse());
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
	 * Modifie le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Tournoi tournoi) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update tournoi set nomTournoi = ?, notoriete = ?, dateDebut = ?, dateFin = ?, estCloture = ?, identifiant = ?, motDePasse = ? where idTournoi = ?");
			ps.setString(1, tournoi.getNomTournoi());
			//je sais pas comment set Notoriete ici
			ps.setString(2, tournoi.getNotoriete().getLibelle());
			ps.setInt(3, tournoi.getDateDebut());
			ps.setInt(4, tournoi.getDateFin());
			ps.setBoolean(5, tournoi.isEstCloture());
			ps.setString(6, tournoi.getIdentifiant());
			ps.setString(7, tournoi.getMotDePasse());
			ps.setInt(8, tournoi.getIdTournoi());
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
	 * Supprime le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Tournoi tournoi) throws Exception {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from joueur where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
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
	 * @return le prochain identifiant unique de tournoi
	 */
	public int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idTournoi");

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
