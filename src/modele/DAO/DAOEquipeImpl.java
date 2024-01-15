package modele.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.exception.InscriptionEquipeTournoiException;
import modele.exception.SaisonException;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Tournoi;

/**
 * Modèle équipe
 */
public class DAOEquipeImpl implements DAOEquipe {
	
	private DAOJoueur daoJoueur;
	
	/**
	 * Construit un modèle équipe
	 */
	public DAOEquipeImpl() {
		this.daoJoueur = new DAOJoueurImpl();
	}

	/**
	 * Récupère toutes les équipes
	 * @return Liste de tous les équipes
	 * @throws Exception Erreur SQL
	 */
	@Override
	public List<Equipe> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from equipe");
		
		return this.collect(st, rs);
	}

	/**
	 * @return Retourne une équipe depuis la BDD par sa clé primaire
	 * @throws Exception Erreur SQL
	 */
	@Override
	public Optional<Equipe> getParId(Integer... idEquipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from equipe where idEquipe = ?");
		ps.setInt(1, idEquipe[0]);
		
		ResultSet rs = ps.executeQuery();
		
		// Création d'équipe si elle existe
		Equipe equipe = null;
		if(rs.next()) {
			equipe = this.construireEquipe(rs);
		}
		
		rs.close();
		ps.close();
		return Optional.ofNullable(equipe);
	}

	/**
	 * Ajoute l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Erreur SQL
	 */
	@Override
	public boolean ajouter(Equipe equipe) throws Exception {
		try {
			int idEquipe = this.getNextValId();
			equipe.setIdEquipe(idEquipe);
			equipe.setSaison(String.valueOf(LocalDate.now().getYear()));
			equipe.setClassement(1000);
			
			// On récupère le classement de l'équipe de la saison dernière
			Equipe equipeSaisonDerniere = this.getTout().stream()
				.filter(e -> e.equals(equipe) && e.getSaison().equals(String.valueOf(LocalDate.now().getYear() - 1)))
				.findFirst()
				.orElse(null);
			
			// Si l'équipe n'a pas été trouvée, on lui attribue le classement 1000
			if(equipeSaisonDerniere != null) {
				equipe.setWorldRanking(equipeSaisonDerniere.getClassement());
			} else {
				equipe.setWorldRanking(1000);
			}
			
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into equipe values (?, ?, ?, ?, ?, ?)");
			ps.setInt(1, equipe.getIdEquipe());
			ps.setString(2, equipe.getNom());
			ps.setString(3, equipe.getPays().getNomPays());
			ps.setInt(4, equipe.getClassement());
			ps.setInt(5, equipe.getWorldRanking());
			ps.setString(6, equipe.getSaison());
			ps.execute();
			ps.close();
			
			// On ajoute les joueurs de l'équipe
			for(Joueur joueur : equipe.getJoueurs()) {
				joueur.setIdEquipe(idEquipe);
				this.daoJoueur.ajouter(joueur);
			}
			
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
	
	/**
	 * Récupère le prochain identifiant d'équipe
	 * @return Identifiant d'équipe
	 */
	private int getNextValId() {
        int nextVal = 0;
        try {
            PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idEquipe");

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
	 * Modifie l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Erreur SQL ou si l'équipe est inscrite à un tournoi ouvert
	 */
	@Override
	public boolean modifier(Equipe equipe) throws Exception {
		if (this.estEquipeInscriteUnTournoiOuvert(equipe)) {
			throw new InscriptionEquipeTournoiException("Cette équipe est inscrite à un tournoi actuellement ouvert.");
		}
		
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update equipe set nom = ?, pays = ?, worldRanking = ?, classement = ? where idEquipe = ?");
			// On ne peut pas modifier la saison d'une équipe
			ps.setString(1, equipe.getNom());
			ps.setString(2, equipe.getPays().getNomPays());
			ps.setInt(3, equipe.getWorldRanking());
			ps.setInt(4, equipe.getClassement());
			ps.setInt(5, equipe.getIdEquipe());
			ps.executeUpdate();

			List<Joueur> joueursEquipe = equipe.getJoueurs();
			for(Joueur joueur : joueursEquipe) {
				this.daoJoueur.modifier(joueur);
			}

			BDD.getConnexion().commit();
			ps.close();
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
	 * Supprime l'équipe dans la BDD
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Erreur SQL ou si l'équipe est inscrite à un tournoi
	 */
	@Override
	public boolean supprimer(Equipe equipe) throws Exception {
		if (this.estEquipeInscriteUnTournoi(equipe)) {
			throw new InscriptionEquipeTournoiException("L'équipe est ou a été inscrite à un tournoi en cours ou clôturé.");
		}

		try {
			this.daoJoueur.supprimerJoueursEquipe(equipe.getIdEquipe());
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from equipe where idEquipe = ?");
			ps.setInt(1, equipe.getIdEquipe());
			ps.execute();
			
			BDD.getConnexion().commit();
			ps.close();
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
	 * Récupère les équipes inscrites à un tournoi
	 * @param idTournoi Identifiant du tournoi
	 * @return Liste des équipes inscrites
	 */
	@Override
	public List<Equipe> getEquipesTournoi(int idTournoi) {
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from equipe, participer where equipe.idEquipe = participer.idEquipe and participer.idTournoi = ?");
			ps.setInt(1, idTournoi);

			ResultSet rs = ps.executeQuery();

			return this.collect(ps, rs);
		} catch(Exception e) {
			try {
				BDD.getConnexion().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Méthode qui vérifie si une équipe est inscrite à un tournoi
	 * @param equipe : l'équipe à vérifier
	 * @return true si l'équipe est inscrite à un tournoi, false sinon
	 * @throws Exception Erreur SQL
	 */
	@Override
	public boolean estEquipeInscriteUnTournoi(Equipe equipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from participer where participer.idEquipe = ?");
		ps.setInt(1, equipe.getIdEquipe());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode qui vérifie si une équipe est inscrite à un tournoi ouvert
	 * @param equipe : l'équipe à vérifier
	 * @return true si l'équipe est inscrite à un tournoi ouvert, false sinon
	 * @throws Exception Erreur SQL
	 */
	@Override
	public boolean estEquipeInscriteUnTournoiOuvert(Equipe equipe) throws Exception {
		PreparedStatement ps = BDD.getConnexion().prepareStatement("select * from participer, tournoi where participer.idEquipe = ? and participer.idTournoi = tournoi.idTournoi and tournoi.estCloture = false");
		ps.setInt(1, equipe.getIdEquipe());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode qui inscrit une équipe à un tournoi
	 * @param equipe : l'équipe à inscrire
	 * @param tournoi : le tournoi auquel inscrire l'équipe
	 * @throws Exception : si l'équipe est déjà inscrite au tournoi ou si l'équipe n'est pas de la saison courante
	 */
	@Override
	public void inscrireEquipe(Equipe equipe, Tournoi tournoi) throws Exception {
		if (this.getEquipesTournoi(tournoi.getIdTournoi()).contains(equipe)) {
			throw new InscriptionEquipeTournoiException("Cette équipe est déjà inscrite à ce tournoi");
		}
		if (!equipe.getSaison().equals(String.valueOf(LocalDate.now().getYear()))) {
			throw new SaisonException("Cette équipe n'est pas de la saison courante");
		}
		
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into participer values (?, ?)");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.setInt(2, equipe.getIdEquipe());
			ps.execute();
			ps.close();

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
	 * Méthode qui désinscrit une équipe d'un tournoi
	 * @param equipe : l'équipe à désinscrire
	 * @param tournoi : le tournoi duquel désinscrire l'équipe
	 * @throws Exception : si l'équipe n'est pas inscrite au tournoi ou erreur SQL
	 */
	@Override
	public void desinscrireEquipe(Equipe equipe, Tournoi tournoi) throws Exception {
		if (!this.getEquipesTournoi(tournoi.getIdTournoi()).contains(equipe)) {
			throw new InscriptionEquipeTournoiException("Cette équipe n'est pas inscrite à ce tournoi");
		}

		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from participer where idTournoi = ? and idEquipe = ?");
			ps.setInt(1, tournoi.getIdTournoi());
			ps.setInt(2, equipe.getIdEquipe());
			ps.execute();
			ps.close();

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
	 * Méthode qui récupère les équipes de la saison courante
	 * @return la liste des équipes de la saison courante
	 * @throws Exception Erreur SQL
	 */
	@Override
	public List<Equipe> getEquipesSaison() throws Exception {
		return this.getTout().stream()
            .filter(e -> e.getSaison().equals(String.valueOf(LocalDate.now().getYear())))
            .collect(Collectors.toList());
	}

	/**
	 * Méthode qui récupère les équipes de la saison courante sauf celles passées en paramètre
	 * @param equipesNonEligibles : les équipes qui ne doivent pas être récupérées
	 * @return la liste des équipes de la saison courante
	 * @throws Exception Erreur SQL
	 */
	@Override
	public Equipe[] getTableauEquipes(List<Equipe> equipesNonEligibles) throws Exception {
		return this.getEquipesSaison().stream()
            .filter(e -> !equipesNonEligibles.contains(e))
            .sorted()
            .toArray(Equipe[]::new);
	}

	/**
	 * Méthode de recherche d'équipes par filtrage
	 * @param pays Pays de l'équipe
	 * @return Retourne la liste des équipes filtrées par pays
	 * @throws Exception Exception SQL
	 */
	@Override
	public List<Equipe> getParFiltrage(Pays pays) throws Exception {
		List<Equipe> equipes = this.getEquipesSaison();

		if (pays != null) {
			equipes = equipes.stream()
				.filter(t -> t.getPays().equals(pays))
				.collect(Collectors.toList());
		}

		return equipes;
	}
	
	/**
	 * Méthode qui récupère les équipes contenant la variable nom dans leur nom d'équipe
	 * @param nom : contenu dans le nom d'une équipe
	 * @return la liste des équipes contenant la variable nom dans leur nom d'équipe
	 * @throws Exception Erreur SQL
	 */
	@Override
	public List<Equipe> getParNom(String nom) throws Exception {
		return this.getEquipesSaison().stream()
            .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
            .collect(Collectors.toList());
	}

	/**
	 * Parcourt les équipes dans la base de données et les formate dans une liste
	 * @param st Statement
	 * @param rs ResultSet
	 * @return Liste des équipes
	 */
	private List<Equipe> collect(Statement st, ResultSet rs) throws Exception {
		Stream<Equipe> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Equipe>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Equipe> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(DAOEquipeImpl.this.construireEquipe(rs));
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
			});
		
		return stream.collect(Collectors.toList());
	}

	// Public car utilisé aussi par ModelePoule et ModeleRencontre
	@Override
	public Equipe construireEquipe(ResultSet rs) throws SQLException {
		return new Equipe(
			rs.getInt("idEquipe"),
			rs.getString("nom"),
			Pays.valueOfNom(rs.getString("pays")),
			rs.getInt("classement"),
			rs.getInt("worldRanking"),
			rs.getString("saison"),
			DAOEquipeImpl.this.daoJoueur.getListeJoueursParId(rs.getInt("idEquipe"))
		);
	}
	
}