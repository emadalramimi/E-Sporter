package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
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

import controleur.ControleurTournois;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Modèle tournoi
 */
public class ModeleTournoi extends DAO<Tournoi, Integer> {

	private ModeleArbitre modeleArbitre;
	private ModeleEquipe modeleEquipes;
	private ModelePoule modelePoule;

	/**
	 * Construit un modèle tournoi
	 */
	public ModeleTournoi() {
		this.modeleArbitre = new ModeleArbitre();
		this.modeleEquipes = new ModeleEquipe();
		this.modelePoule = new ModelePoule();
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
	 * Récupère un tournoi par son identifiant
	 * @return Retourne un tournoi depuis la BDD par sa clé primaire
	 * @throws Exception Exception SQL
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
				ModeleTournoi.this.modelePoule.getPoulesTournoi(rs.getInt("idTournoi")),
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
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
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
				this.modelePoule.supprimer(poule);
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
			} catch (SQLException e1) {
				e1.printStackTrace();
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
	 * Ouvre un tournoi
	 * @param tournoi Tournoi à ouvrir
	 * @throws Exception Exception SQL et IllegalArgumentException si le tournoi est déjà ouvert, si la date de fin du tournoi est passée, si le nombre d'équipes inscrites n'est pas compris entre 4 et 8 équipes, si le tournoi est cloturé ou si un tournoi est déjà ouvert
	 */
	public void ouvrirTournoi(Tournoi tournoi) throws Exception {
		if (tournoi.getEstCloture() == false) {
			throw new IllegalArgumentException("Le tournoi est déjà ouvert");
		}
		if (tournoi.getDateTimeFin() <= System.currentTimeMillis() / 1000) {
			throw new IllegalArgumentException("La date de fin du tournoi est passée");
		}

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

		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = false where idTournoi = ?");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.execute();
			ps.close();
			
			// Génération des poules
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
	 * Méthode pour récupérer un tournoi par son identifiant
	 * @param identifiant Identifiant du tournoi
	 * @return Retourne un tournoi depuis la BDD par son identifiant
	 * @throws SQLException Exception SQL
	 */
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

	/**
	 * Méthode pour récupérer un tournoi par identifiant d'une rencontre
	 * @param idRencontre Identifiant de la rencontre
	 * @return Retourne un tournoi depuis la BDD par identifiant d'une rencontre
	 */
	public Optional<Tournoi> getTournoiRencontre(int idRencontre) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from tournoi, poule, rencontre where rencontre.idPoule = poule.idPoule and poule.idTournoi = tournoi.idTournoi and rencontre.idRencontre = ?");
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

	/**
	 * Méthode pour récupérer les résultats d'un tournoi
	 * @param tournoi Tournoi dont on veut récupérer les résultats
	 * @return Retourne les résultats d'un tournoi
	 */
	public List<StatistiquesEquipe> getResultatsTournoi(Tournoi tournoi) {
		List<StatistiquesEquipe> statistiques = new LinkedList<>();
		for (Equipe equipe : tournoi.getEquipes()) {
			int nbMatchsJoues = 0;
			int nbMatchsGagnes = 0;

			// Parcourt les poules du tournoi
			for(Poule poule : this.modelePoule.getPoulesTournoi(tournoi.getIdTournoi())) {
				for(Rencontre rencontre : poule.getRencontres()) {
					// 0 => valeur nulle
					if(Arrays.asList(rencontre.getEquipes()).contains(equipe) && rencontre.getIdEquipeGagnante() != 0) {
						nbMatchsJoues++;
					}
					// Vérifie si l'équipe est gagnante
					if(Arrays.asList(rencontre.getEquipes()).contains(equipe) && rencontre.getIdEquipeGagnante() == equipe.getIdEquipe()) {
						nbMatchsGagnes++;
					}
				}
			}

			statistiques.add(new StatistiquesEquipe(equipe, nbMatchsJoues, nbMatchsGagnes));
		}

		return statistiques.stream().sorted().collect(Collectors.toList());
	}
	
	/**
	 * Méthode de recherche de tournois par nom
	 * @param nom Nom du tournoi
	 * @return Retourne les tournois par nom
	 * @throws Exception Exception SQL
	 */
	public List<Tournoi> getParNom(String nom) throws Exception {
        return this.getTout().stream()
                .filter(t -> t.getNomTournoi().toLowerCase().contains(nom.toLowerCase()))
                .collect(Collectors.toList());
    }

	/**
	 * Méthode de recherche de tournois par filtrage
	 * @param notoriete Notoriété du tournoi
	 * @param statut Statut du tournoi
	 * @return Retourne les tournois par filtrage
	 * @throws Exception Exception SQL
	 */
	public List<Tournoi> getParFiltrage(Notoriete notoriete, ControleurTournois.Statut statut) throws Exception {
		List<Tournoi> tournois = this.getTout();

		if (notoriete != null) {
			tournois = tournois.stream()
					.filter(t -> t.getNotoriete().equals(notoriete))
					.collect(Collectors.toList());
		}

		if (statut != null) {
			switch (statut) {
				case PHASE_INSCRIPTIONS:
					tournois = tournois.stream()
							.filter(t -> t.getEstCloture() == true && System.currentTimeMillis() / 1000 < t.getDateTimeFin())
							.collect(Collectors.toList());
					break;
				case OUVERT:
					tournois = tournois.stream()
							.filter(t -> t.getEstCloture() == false)
							.collect(Collectors.toList());
					break;
				case CLOTURE:
					tournois = tournois.stream()
							.filter(t -> t.getEstCloture() == true && System.currentTimeMillis() / 1000 > t.getDateTimeFin())
							.collect(Collectors.toList());
					break;
			}
		}

		return tournois;
	}

}
