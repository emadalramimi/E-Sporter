package modele;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import modele.DAO.DAOPouleImpl;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;

/**
 * Modèle tournoi
 */
public class ModeleTournoi {

	private DAOPouleImpl modelePoule;

	/**
	 * Construit un modèle tournoi
	 */
	public ModeleTournoi() {
		this.modelePoule = new DAOPouleImpl();
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

}
