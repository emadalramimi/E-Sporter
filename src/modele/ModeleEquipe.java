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

public class ModeleEquipe implements DAO<Equipe, Integer> {
	
	private ModeleJoueur modeleJoueur;
	
	public ModeleEquipe() {
		this.modeleJoueur = new ModeleJoueur();
	}

	/**
	 * @return Liste de tous les équipes
	 */
	@Override
	public List<Equipe> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from equipe");
		
		// Parcours les équipes dans la base de données et les formate dans une liste
		Stream<Equipe> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Equipe>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Equipe> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Equipe(
                    		rs.getInt("idEquipe"),
                    		rs.getString("nom"),
                    		rs.getString("pays"),
                    		rs.getInt("classement"),
                    		rs.getInt("worldRanking"),
                    		rs.getString("saison"),
                    		ModeleEquipe.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))
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
	 * @return Retourne une équipe depuis la BDD par sa clé primaire
	 */
	@Override
	public Optional<Equipe> getParId(Integer... idEquipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from equipe where idEquipe = ?");
		ps.setInt(1, idEquipe[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création d'équipe si elle existe
		Equipe equipe = null;
		if(rs.next()) {
			equipe = new Equipe(
	    		rs.getInt("idEquipe"),
	    		rs.getString("nom"),
	    		rs.getString("pays"),
	    		rs.getInt("classement"),
	    		rs.getInt("worldRanking"),
	    		rs.getString("saison"),
	    		ModeleEquipe.this.modeleJoueur.getListeJoueursParId(rs.getInt("idEquipe"))
            );
		}
		
		return Optional.ofNullable(equipe);
	}

	/**
	 * Ajoute l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Equipe equipe) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into equipe values (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, equipe.getIdEquipe());
			ps.setString(2, equipe.getNom());
			ps.setString(3, equipe.getPays());
			ps.setInt(4, equipe.getClassement());
			ps.setInt(5, equipe.getWorldRanking());
			ps.setString(6, equipe.getSaison());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Modifie l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Equipe equipe) {
		// TODO SPRINT 2 : Si l'équipe est inscrite à un tournoi, ne pas la modifier.
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update equipe set nom = ?, pays = ?, worldRanking = ? where idEquipe = ?");
			// On ne peut pas modifier le classement et la saison d'une équipe ?
			ps.setString(1, equipe.getNom());
			ps.setString(2, equipe.getPays());
			ps.setInt(3, equipe.getWorldRanking());
			ps.setInt(4, equipe.getIdEquipe());
			ps.execute();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Supprime l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Equipe equipe) {
		try {
			// TODO SPRINT 2 : Si l'équipe est inscrite à un tournoi, ne pas la supprimer.
			this.modeleJoueur.supprimerJoueursEquipe(equipe.getIdEquipe());
			PreparedStatement psEquipe = BDD.getConnexion().prepareStatement("delete from equipe where idEquipe = ?");
			psEquipe.setInt(1, equipe.getIdEquipe());
			psEquipe.execute();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idEquipe");

            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                nextVal = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return nextVal;
    }
	
	public List<Equipe> getParNom(String nom) throws Exception {
		return this.getTout().stream()
				.filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
				.collect(Collectors.toList());
	}
	
}