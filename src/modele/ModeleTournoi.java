package modele;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import controleur.ControleurTournois;
import modele.DAO.DAOAdministrateur;
import modele.DAO.DAOAdministrateurImpl;
import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.DAO.Recherchable;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * Modèle tournoi
 */
public class ModeleTournoi implements Recherchable<Tournoi> {

	private DAOTournoi daoTournoi;
	private DAOPoule daoPoule;
	private DAOAdministrateur daoAdministrateur;

	/**
	 * Construit un modèle tournoi
	 */
	public ModeleTournoi() {
		this.daoTournoi = new DAOTournoiImpl();
		this.daoPoule = new DAOPouleImpl();
		this.daoAdministrateur = new DAOAdministrateurImpl();
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
			for(Poule poule : this.daoPoule.getPoulesTournoi(tournoi.getIdTournoi())) {
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
	@Override
	public List<Tournoi> getParNom(String nom) throws Exception {
        return this.daoTournoi.getTout().stream()
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
		List<Tournoi> tournois = this.daoTournoi.getTout();

		// Si une notoriété est renseignée pour filtrer
		if (notoriete != null) {
			tournois = tournois.stream()
				.filter(t -> t.getNotoriete().equals(notoriete))
				.collect(Collectors.toList());
		}

		// Si un statut est renseigné pour filtrer
		if (statut != null) {
			switch (statut) {
				case PHASE_INSCRIPTIONS:
					// Récupération des tournois en phase d'inscriptions
					tournois = tournois.stream()
						.filter(t -> t.getEstCloture() == true && System.currentTimeMillis() / 1000 < t.getDateTimeFin())
						.collect(Collectors.toList());
					break;
				case OUVERT:
					// Récupération des tournois ouverts
					tournois = tournois.stream()
						.filter(t -> t.getEstCloture() == false)
						.collect(Collectors.toList());
					break;
				case CLOTURE:
					// Récupération des tournois cloturés	
					tournois = tournois.stream()
						.filter(t -> t.getEstCloture() == true && System.currentTimeMillis() / 1000 > t.getDateTimeFin())
						.collect(Collectors.toList());
					break;
			}
		}

		return tournois;
	}

	public boolean verifierUniciteIdentifiant(String identifiant) throws SQLException {
		return this.daoTournoi.getParIdentifiant(identifiant).isPresent()
				|| this.daoAdministrateur.getParIdentifiant(identifiant).isPresent();
	}

	public boolean estTournoiCloture(Tournoi tournoi) {
		return System.currentTimeMillis() / 1000 >= tournoi.getDateTimeDebut() && tournoi.getEstCloture();
	}

}
