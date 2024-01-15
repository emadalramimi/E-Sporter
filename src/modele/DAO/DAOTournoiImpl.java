package modele.DAO;

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

import modele.ModeleUtilisateur;
import modele.metier.Arbitre;
import modele.metier.Poule;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Modèle tournoi
 */
public class DAOTournoiImpl implements DAOTournoi {

	private DAOArbitre daoArbitre;
	private DAOEquipe daoEquipe;
	private DAOPoule daoPoule;

	/**
	 * Construit un modèle tournoi
	 */
	public DAOTournoiImpl() {
		this.daoArbitre = new DAOArbitreImpl();
		this.daoEquipe = new DAOEquipeImpl();
		this.daoPoule = new DAOPouleImpl();
	}

	/**
	 * Récupère tous les tournois
	 * @return Liste de tous les tournois
	 * @throws Exception Exception SQL
	 */
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
                        action.accept(DAOTournoiImpl.this.construireTournoi(rs));
                        return true;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
	        }, false).onClose(() -> {
				try {
					rs.close();
                    st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}).sorted();
		
		return stream.collect(Collectors.toList());
	}

	/**
	 * Récupère un tournoi par son identifiant
	 * @return Retourne un tournoi depuis la BDD par sa clé primaire
	 * @throws Exception Exception SQL
	 */
	@Override
	public Optional<Tournoi> getParId(Integer... idTournoi) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi where idTournoi = ?");
		ps.setInt(1, idTournoi[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création de tournoi si il existe
		Tournoi tournoi = null;
		if(rs.next()) {
			tournoi = this.construireTournoi(rs);
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(tournoi);
	}

	/**
	 * Ajoute le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean ajouter(Tournoi tournoi) throws Exception {
		try {
			int idTournoi = this.getNextValId();
			tournoi.setIdTournoi(idTournoi);
			tournoi.setEstCloture(true);

			// Chiffrement du mot de passe
			tournoi.setMotDePasse(ModeleUtilisateur.chiffrerMotDePasse(tournoi.getMotDePasse()));
			
			// Ajout du tournoi
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into tournoi values (?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.setString(2, tournoi.getNomTournoi());
			ps.setString(3, tournoi.getNotoriete().getLibelle());
			ps.setLong(4, tournoi.getDateTimeDebut());
			ps.setLong(5, tournoi.getDateTimeFin());
			ps.setBoolean(6, tournoi.getEstCloture());
			ps.setString(7, tournoi.getIdentifiant());
			ps.setString(8, tournoi.getMotDePasse());
			ps.execute();
			ps.close();

			// Ajout des arbitres du tournoi
			for (Arbitre arbitre : tournoi.getArbitres()) {
				ps = BDD.getConnexion().prepareStatement("insert into arbitrer values (?, ?)");
				ps.setInt(1, tournoi.getIdTournoi());
				ps.setInt(2, arbitre.getIdArbitre());
				ps.execute();
				ps.close();
			}

			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Récupère le prochain identifiant unique de tournoi
	 * @return le prochain identifiant unique de tournoi
	 * @throws Exception Exception SQL
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

	/**
	 * Modifie le tournoi dans la BDD
	 * @param tournoi Tournoi à modifier
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL et IllegalArgumentException si le tournoi est cloturé
	 */
	@Override
	public boolean modifier(Tournoi tournoi) throws Exception {
		if (System.currentTimeMillis() / 1000 >= tournoi.getDateTimeFin() || tournoi.getEstCloture() == false) {
			throw new IllegalArgumentException("Le tournoi est cloturé");
		}

		try {
			// Chiffrement du mot de passe
			tournoi.setMotDePasse(ModeleUtilisateur.chiffrerMotDePasse(tournoi.getMotDePasse()));

			PreparedStatement ps = BDD.getConnexion().prepareStatement("update tournoi set nomTournoi = ?, notoriete = ?, dateDebut = ?, dateFin = ?, identifiant = ?, motDePasse = ? where idTournoi = ?");
			ps.setString(1, tournoi.getNomTournoi());
			ps.setString(2, tournoi.getNotoriete().getLibelle());
			ps.setLong(3, tournoi.getDateTimeDebut());
			ps.setLong(4, tournoi.getDateTimeFin());
			ps.setString(5, tournoi.getIdentifiant());
			ps.setString(6, tournoi.getMotDePasse());
			ps.setInt(7, tournoi.getIdTournoi());
			ps.executeUpdate();
			ps.close();

			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Supprime le tournoi dans la BDD
	 * @param tournoi Tournoi à supprimer
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL et IllegalArgumentException si le tournoi est cloturé
	 */
	@Override
	public boolean supprimer(Tournoi tournoi) throws Exception {
		if (System.currentTimeMillis() / 1000 >= tournoi.getDateTimeFin() && tournoi.getEstCloture() == false) {
			throw new IllegalArgumentException("Le tournoi est cloturé");
		}

		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from arbitrer where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.execute();
			ps.close();

			ps = BDD.getConnexion().prepareStatement("delete from participer where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.execute();
			ps.close();
			
			// Supprimer les poules
			for (Poule poule : tournoi.getPoules()) {
				this.daoPoule.supprimer(poule);
			}

			ps = BDD.getConnexion().prepareStatement("delete from tournoi where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.execute();
			ps.close();

			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Méthode pour récupérer un tournoi par son identifiant
	 * @param identifiant Identifiant du tournoi
	 * @return Retourne un tournoi depuis la BDD par son identifiant
	 * @throws SQLException Exception SQL
	 */
    @Override
	public Optional<Tournoi> getParIdentifiant(String identifiant) throws SQLException {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi where identifiant = ?");
		ps.setString(1, identifiant);

		ResultSet rs = ps.executeQuery();

		// Création de tournoi s'il existe
		Tournoi tournoi = null;
		if (rs.next()) {
			tournoi = this.construireTournoi(rs);
		}

		rs.close();
		ps.close();
		return Optional.ofNullable(tournoi);
	}

	/**
	 * Méthode pour récupérer un tournoi par identifiant d'une rencontre
	 * @param idRencontre Identifiant de la rencontre
	 * @return Retourne un tournoi depuis la BDD par identifiant d'une rencontre
	 */
    @Override
	public Optional<Tournoi> getTournoiRencontre(int idRencontre) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi, poule, rencontre where rencontre.idPoule = poule.idPoule and poule.idTournoi = tournoi.idTournoi and rencontre.idRencontre = ?");
			ps.setInt(1, idRencontre);
			ResultSet rs = ps.executeQuery();

			// Création de tournoi s'il existe
			Tournoi tournoi = null;
			if (rs.next()) {
				tournoi = this.construireTournoi(rs);
			}

			rs.close();
			ps.close();
			return Optional.ofNullable(tournoi);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	/**
	 * Clôture le tournoi en BDD
	 * @param tournoi Le tournoi à clôturer
	 * @throws SQLException Erreur SQL
	 */
    @Override
	public void cloturerTournoi(Tournoi tournoi) throws SQLException {
		PreparedStatement ps;

		// Si le tournoi est clôturé prématurément avant date fin, on met date fin à maintenant
		if(tournoi.getDateTimeFin() > System.currentTimeMillis() / 1000) {
			ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = true, dateFin = ? where idTournoi = ?");
			ps.setInt(1, (int) (System.currentTimeMillis() / 1000));
			ps.setInt(2, tournoi.getIdTournoi());
		} else {
			ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = true where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
		}
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void ouvrirTournoi(Tournoi tournoi) throws SQLException {
		PreparedStatement ps;
		if(tournoi.getDateTimeDebut() > System.currentTimeMillis() / 1000) {
			ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = false, dateDebut = ? where idTournoi = ?");
			ps.setInt(1, (int) (System.currentTimeMillis() / 1000));
			ps.setInt(2, tournoi.getIdTournoi());
		} else {
			ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = false where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
		}
		ps.executeUpdate();
		ps.close();
	}

	private Tournoi construireTournoi(ResultSet rs) throws SQLException {
		return new Tournoi(
			rs.getInt("idTournoi"),
			rs.getString("nomTournoi"),
			Notoriete.valueOfLibelle(rs.getString("notoriete")),
			rs.getInt("dateDebut"),
			rs.getInt("dateFin"),
			rs.getBoolean("estCloture"),
			rs.getString("identifiant"),
			rs.getString("motDePasse"),
			DAOTournoiImpl.this.daoPoule.getPoulesTournoi(rs.getInt("idTournoi")),
			DAOTournoiImpl.this.daoEquipe.getEquipesTournoi(rs.getInt("idTournoi")),
			DAOTournoiImpl.this.daoArbitre.getArbitresTournoi(rs.getInt("idTournoi"))
		);
	}

}
