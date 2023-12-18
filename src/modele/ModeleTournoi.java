package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class ModeleTournoi extends DAO<Tournoi, Integer> {

	private ModeleArbitre modeleArbitre;
	private ModeleEquipe modeleEquipes;
	private ModelePoule modelePoule;

	public ModeleTournoi() {
		this.modeleArbitre = new ModeleArbitre();
		this.modeleEquipes = new ModeleEquipe();
		this.modelePoule = new ModelePoule();
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
							ModeleTournoi.this.modelePoule.getPoulesTournoi(rs.getInt("idTournoi")),
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
			}).sorted();
		
		return stream.collect(Collectors.toList());
	}

	/**
	 * @return Retourne un tournoi depuis la BDD par sa clé primaire
	 */
//	@Override
//	public Optional<Tournoi> getParId(Integer... idTournoi) throws Exception {
//		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi where idTournoi = ?");
//		ps.setInt(1, idTournoi[0]);
//		
//		ResultSet rs = ps.executeQuery();
//		
//		// Création de joueur si il existe
//		Tournoi tournoi = null;
//		if(rs.next()) {
//			tournoi = new Tournoi(
//				rs.getInt("idTournoi"),
//				rs.getString("nomTournoi"),
//				Notoriete.valueOfLibelle(rs.getString("notoriete")),
//				rs.getInt("dateDebut"),
//				rs.getInt("dateFin"),
//				rs.getBoolean("estCloture"),
//				rs.getString("identifiant"),
//				rs.getString("motDePasse"),
//				ModeleTournoi.this.modelePoule.getPoulesTournoi(rs.getInt("idTournoi")),
//				ModeleTournoi.this.modeleEquipes.getEquipesTournoi(rs.getInt("idTournoi")),
//				ModeleTournoi.this.modeleArbitre.getArbitresTournoi(rs.getInt("idTournoi"))
//            );
//		}
//		
//		rs.close();
//		ps.close();
//		return Optional.ofNullable(tournoi);
//	}

	/**
	 * Ajoute le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean ajouter(Tournoi tournoi) throws Exception {
		try {
			int idTournoi = this.getNextValId();
			tournoi.setIdTournoi(idTournoi);
			tournoi.setEstCloture(true);

			// Chiffrement du mot de passe
			tournoi.setMotDePasse(ModeleUtilisateur.chiffrerMotDePasse(tournoi.getMotDePasse()));
			
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
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Modifie le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean modifier(Tournoi tournoi) throws Exception {
		if (System.currentTimeMillis() / 1000 >= tournoi.getDateTimeFin() || tournoi.getEstCloture() == false) {
			throw new IllegalArgumentException("Le tournoi est cloturé");
		}

		try {
			tournoi.setMotDePasse(ModeleUtilisateur.chiffrerMotDePasse(tournoi.getMotDePasse()));

			PreparedStatement ps = BDD.getConnexion().prepareStatement("update tournoi set nomTournoi = ?, notoriete = ?, dateDebut = ?, dateFin = ?, identifiant = ?, motDePasse = ? where idTournoi = ?");
			ps.setString(1, tournoi.getNomTournoi());
			ps.setString(2, tournoi.getNotoriete().getLibelle());
			ps.setLong(3, tournoi.getDateTimeDebut());
			ps.setLong(4, tournoi.getDateTimeFin());
			ps.setString(5, tournoi.getIdentifiant());
			ps.setString(6, tournoi.getMotDePasse());
			ps.setInt(7, tournoi.getIdTournoi());
			ps.execute();
			ps.close();

			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * Supprime le tournoi dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 */
	@Override
	public boolean supprimer(Tournoi tournoi) throws Exception {
		if (System.currentTimeMillis() / 1000 >= tournoi.getDateTimeFin() || tournoi.getEstCloture() == false) {
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

			ps = BDD.getConnexion().prepareStatement("delete from tournoi where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.execute();
			
			ps.close();
			BDD.getConnexion().commit();
			return true;
		} catch(SQLException e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
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

	public void ouvrirTournoi(Tournoi tournoi) throws Exception {
		// TODO TRY CATCH AVEC ROLLBACK
		int nbEquipes = modeleEquipes.getEquipesTournoi(tournoi.getIdTournoi()).size();
		if (nbEquipes < 4 || nbEquipes > 8) {
			throw new IllegalArgumentException("Le nombre d'équipes inscrites doit être compris entre 4 et 8 équipes");
		}
		if (tournoi.getDateTimeFin() <= System.currentTimeMillis() / 1000) {
			throw new IllegalArgumentException("La date de fin du tournoi est passée");
		}
		if(tournoi.getDateTimeFin() <= System.currentTimeMillis() / 1000 && tournoi.getEstCloture()) {
			throw new IllegalArgumentException("Le tournoi est cloturé");
		}
		if(this.getTout().stream().anyMatch(t -> t.getEstCloture() == false)) {
			throw new IllegalArgumentException("Il ne peut y avoir qu'un seul tournoi ouvert à la fois");
		}
	
		PreparedStatement ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = false where idTournoi = ?");
		ps.setInt(1, tournoi.getIdTournoi());
		ps.execute();
		ps.close();

		List<Rencontre> rencontres = new LinkedList<>();
		List<Equipe> equipes = tournoi.getEquipes();
		for (int i = 0; i < equipes.size(); i++) {
			for (int j = i + 1; j < equipes.size(); j++) {
				rencontres.add(new Rencontre(new Equipe[] { equipes.get(i), equipes.get(j) }));
			}
		}
		Collections.shuffle(rencontres);

		ModelePoule modelePoule = new ModelePoule();
		Poule poule = new Poule(false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);

		BDD.getConnexion().commit();
	}
	
	public Optional<Tournoi> getParIdentifiant(String identifiant) throws SQLException {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi where identifiant = ?");
		ps.setString(1, identifiant);

		ResultSet rs = ps.executeQuery();

		// Création de tournoi s'il existe
		Tournoi tournoi = null;
		if (rs.next()) {
			tournoi = new Tournoi(
					rs.getInt("idTournoi"),
					rs.getString("nomTournoi"),
					Notoriete.valueOfLibelle(rs.getString("notoriete")),
					rs.getInt("dateDebut"),
					rs.getInt("dateFin"),
					rs.getBoolean("estCloture"),
					rs.getString("identifiant"),
					rs.getString("motDePasse"),
					ModeleTournoi.this.modelePoule.getPoulesTournoi(rs.getInt("idTournoi")),
					ModeleTournoi.this.modeleEquipes.getEquipesTournoi(rs.getInt("idTournoi")),
					ModeleTournoi.this.modeleArbitre.getArbitresTournoi(rs.getInt("idTournoi")));
		}

		rs.close();
		ps.close();
		return Optional.ofNullable(tournoi);
	}

	public Optional<Tournoi> getTournoiRencontre(int idRencontre) {
		PreparedStatement ps;
		try {
			ps = BDD.getConnexion().prepareStatement("select * from tournoi, poule, rencontre where rencontre.idPoule = poule.idPoule and poule.idTournoi = tournoi.idTournoi and rencontre.idRencontre = ?");
			ps.setInt(1, idRencontre);
			ResultSet rs = ps.executeQuery();

			// Création de tournoi s'il existe
			Tournoi tournoi = null;
			if (rs.next()) {
				tournoi = new Tournoi(
					rs.getInt("idTournoi"),
					rs.getString("nomTournoi"),
					Notoriete.valueOfLibelle(rs.getString("notoriete")),
					rs.getInt("dateDebut"),
					rs.getInt("dateFin"),
					rs.getBoolean("estCloture"),
					rs.getString("identifiant"),
					rs.getString("motDePasse"),
					ModeleTournoi.this.modelePoule.getPoulesTournoi(rs.getInt("idTournoi")),
					ModeleTournoi.this.modeleEquipes.getEquipesTournoi(rs.getInt("idTournoi")),
					ModeleTournoi.this.modeleArbitre.getArbitresTournoi(rs.getInt("idTournoi"))
				);
			}

			rs.close();
			ps.close();
			return Optional.ofNullable(tournoi);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	public List<StatistiquesEquipe> getResultatsTournoi(Tournoi tournoi) {
		List<StatistiquesEquipe> statistiques = new LinkedList<>();
		for (Equipe equipe : tournoi.getEquipes()) {
			statistiques.add(new StatistiquesEquipe(equipe, tournoi.getNbMatchsJoues(equipe), tournoi.getNbMatchsGagnes(equipe)));
		}
		return statistiques.stream().sorted().collect(Collectors.toList());
	}
	
	public List<Tournoi> getParNom(String nom) throws Exception {
        return this.getTout().stream()
                .filter(t -> t.getNomTournoi().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Tournoi> getParNotoriete(Notoriete notoriete) throws Exception {
        return this.getTout().stream()
                .filter(t -> t.getNotoriete().equals(notoriete))
                .collect(Collectors.toList());
    }

    public List<Tournoi> getParPhaseInscription() throws Exception {
        return this.getTout().stream()
                .filter(t -> t.getEstCloture() == true && System.currentTimeMillis() / 1000 < t.getDateTimeFin())
                .collect(Collectors.toList());
    }

	public List<Tournoi> getParOuvert() throws Exception {
		return this.getTout().stream()
				.filter(t -> t.getEstCloture() == false)
				.collect(Collectors.toList());
	}
	
    public List<Tournoi> getParCloture() throws Exception {
        return this.getTout().stream()
                .filter(t -> t.getEstCloture() == true && System.currentTimeMillis() / 1000 > t.getDateTimeFin())
                .collect(Collectors.toList());
    }

}
