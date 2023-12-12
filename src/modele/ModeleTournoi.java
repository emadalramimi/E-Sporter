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

import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class ModeleTournoi implements DAO<Tournoi, Integer> {

	private ModeleArbitre modeleArbitre;
	private ModeleEquipe modeleEquipes;

	public ModeleTournoi() {
		this.modeleArbitre = new ModeleArbitre();
		this.modeleEquipes = new ModeleEquipe();
	}

	@Override
	public List<Tournoi> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from tournoi");
		
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
                			Notoriete.valueOfLibelle(rs.getString("notoriete")),
                			rs.getInt("dateDebut"),
                			rs.getInt("dateFin"),
                			rs.getBoolean("estCloture"),
                			rs.getString("identifiant"),
							rs.getString("motDePasse"),
							ModeleTournoi.this.modeleEquipes.getEquipesTournoi(rs.getInt("idTournoi")),
                			ModeleTournoi.this.modeleArbitre.getArbitresTournoi(rs.getInt("idTournoi"))
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
				Notoriete.valueOfLibelle(rs.getString("notoriete")),
				rs.getInt("dateDebut"),
				rs.getInt("dateFin"),
				rs.getBoolean("estCloture"),
				rs.getString("identifiant"),
				rs.getString("motDePasse"),
				ModeleTournoi.this.modeleEquipes.getEquipesTournoi(rs.getInt("idTournoi")),
				ModeleTournoi.this.modeleArbitre.getArbitresTournoi(rs.getInt("idTournoi"))
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
			int idTournoi = this.getNextValId();
			tournoi.setIdTournoi(idTournoi);
			
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into tournoi values (?, ?, ?, ?, ?, ?, ?, ?)");
			System.out.println(tournoi.getIdTournoi());
			ps.setInt(1, tournoi.getIdTournoi());
			ps.setString(2, tournoi.getNomTournoi());
			ps.setString(3, tournoi.getNotoriete().getLibelle());
			ps.setLong(4, tournoi.getDateDebut());
			ps.setLong(5, tournoi.getDateFin());
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
			ps.setLong(3, tournoi.getDateDebut());
			ps.setLong(4, tournoi.getDateFin());
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
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from tournoi where idTournoi = ?");
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
	private int getNextValId() {
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
	
	public Optional<Tournoi> getParIdentifiant(String identifiant) throws SQLException {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi where identifiant = ?");
		ps.setString(1, identifiant);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de tournoi s'il existe
		Tournoi tournoi = null;
		if(rs.next()) {
			tournoi = new Tournoi(
				rs.getInt("idTournoi"),
    			rs.getString("nomTournoi"),
    			Notoriete.valueOfLibelle(rs.getString("notoriete")),
    			rs.getInt("dateDebut"),
    			rs.getInt("dateFin"),
    			rs.getBoolean("estCloture"),
    			rs.getString("identifiant"),
    			rs.getString("motDePasse"),
				ModeleTournoi.this.modeleEquipes.getEquipesTournoi(rs.getInt("idTournoi")),
				ModeleTournoi.this.modeleArbitre.getArbitresTournoi(rs.getInt("idTournoi"))
            );
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(tournoi);
	}

}
