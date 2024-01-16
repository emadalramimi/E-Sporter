package modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOHistoriquePoints;
import modele.DAO.DAOHistoriquePointsImpl;
import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.EnumPoints;
import modele.metier.Equipe;
import modele.metier.HistoriquePoints;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import modele.metier.Utilisateur;

/**
 * Modèle servant à la clôture d'un tournoi et à la mise à jour du classement
 */
public class ModeleTournoiCloture {

    private DAOPoule daoPoule;
    private DAOEquipe daoEquipe;
    private DAOTournoi daoTournoi;
	private DAOHistoriquePoints daoHistoriquePoints;
	private ModeleHistoriquePoints modeleHistoriquePoints;
	private ModeleTournoi modeleTournoi;

    public ModeleTournoiCloture() {
        this.daoPoule = new DAOPouleImpl();
        this.daoEquipe = new DAOEquipeImpl();
        this.daoTournoi = new DAOTournoiImpl();
		this.daoHistoriquePoints = new DAOHistoriquePointsImpl();
		this.modeleHistoriquePoints = new ModeleHistoriquePoints();
		this.modeleTournoi = new ModeleTournoi();
    }

	/**
	 * Met à jour le classement des équipes
	 * @throws Exception Erreurs SQL ou de récupération d'équipes
	 */
	public void majClassements() throws Exception {
        Map<Equipe, Integer> classementParEquipe = this.modeleHistoriquePoints.getClassementParEquipe();

		// Parcourt des équipes pour mettre à jour leur classement
        for(Equipe equipe : classementParEquipe.keySet()) {
            equipe.setClassement(classementParEquipe.get(equipe));
            
            this.daoEquipe.modifier(equipe);
        }
    }
    
	/**
	 * Clôture la poule actuelle du tournoi, si la poule est la finale, le tournoi est aussi clôturé
	 * @param tournoi Le tournoi dont la poule actuelle est à clôturer
	 * @throws IllegalArgumentException Si la poule est déjà clôturée ou si tous les matchs n'ont pas été joués
	 * @throws Exception Erreurs SQL ou de récupération d'équipes
	 */
    public void cloturerPoule(Tournoi tournoi) throws Exception {
		if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ARBITRE) {
			throw new IllegalArgumentException("Seul un arbitre peut clôturer une poule/un tournoi");
		}

		Poule poule = tournoi.getPouleActuelle();
		
		/*				IMPOSSIBLE
		if(poule.getEstCloturee()) {
			throw new IllegalArgumentException("La poule est déjà cloturée");
		}
		*/
		
		// Vérification que tous les matchs ont été joués (0 = aucun gagnant)
		for(Rencontre rencontre : poule.getRencontres()) {
			if(rencontre.getIdEquipeGagnante() == 0) {
				throw new IllegalArgumentException("Tous les matchs de la poule n'ont pas été joués");
			}
		}

		// Clôture de la poule des qualifications si la poule n'est pas la finale (détermination des finalistes)
		// Ou clôture de la poule finale si elle l'est (et donc clôture du tournoi + mise à jour classement)
		if(poule.getEstFinale() == false) {
			this.cloturerPouleQualifications(tournoi);
		} else {
			this.cloturerPouleFinale(tournoi);
		}
	}

	/**
	 * Clôturer la poule des qualifications et créer la poule finale
	 * @param tournoi Le tournoi dont la poule des qualifications (poule actuelle) est à cloturer
	 * @throws Exception Erreurs SQL ou de récupération d'équipes
	 * @throws IllegalArgumentException Si la poule qualifications est déjà clôturée
	 */
	private void cloturerPouleQualifications(Tournoi tournoi) throws Exception {
		Poule poule = tournoi.getPouleActuelle();
		
		/*			IMPOSSIBLE
		if(poule.getEstFinale()) {
			throw new IllegalArgumentException("Poule de qualifications déjà cloturée");
		}
		*/
		// Détermination des équipes pour la finale
		// Il s'agit forcément d'un tableau de 2 équipes !
		Equipe[] equipesSelectionnees = this.getEquipesSelectionneesPourFinale(tournoi);

		this.daoPoule.cloturerPoule(poule);

		// Création de la finale
		List<Rencontre> rencontres = new LinkedList<>();
		rencontres.add(new Rencontre(equipesSelectionnees));
		Poule finale = new Poule(false, true, poule.getIdTournoi(), rencontres);
		this.daoPoule.ajouter(finale);
	}

	/**
	 * Détermine les équipes qui vont jouer la finale (meilleurs points ou world ranking si égal ou au hasard si égaux)
	 * @param tournoi Le tournoi en question pour le calcul des points
	 * @return Les équipes sélectionnées pour la finale, il s'agit forcément d'un tableau de 2 équipes
	 * @throws Exception Erreurs SQL ou de récupération d'équipes
	 */
	private Equipe[] getEquipesSelectionneesPourFinale(Tournoi tournoi) throws Exception {
		Poule poule = tournoi.getPouleActuelle();

		// Récupération des points des matchs de toutes les équipes (3 gagné, 1 perdu)
		Map<Equipe, StatistiquesEquipe> mapStatistiquesEquipes = this.modeleTournoi.getResultatsTournoi(tournoi).stream()
			.collect(Collectors.toMap(StatistiquesEquipe::getEquipe, Function.identity()));

		// Tri des équipes par nombre de points décroissant puis par world ranking croissant
		List<Equipe> equipesSelectionnees = poule.getRencontres().stream()
			.flatMap(rencontre -> Arrays.stream(rencontre.getEquipes()))
			.collect(Collectors.groupingBy(
				Function.identity(),
				Collectors.summingDouble(equipe -> mapStatistiquesEquipes.get(equipe).getPoints())
			))
			.entrySet().stream()
			.map(Map.Entry::getKey)
			.sorted(Comparator.comparing((Equipe equipe) -> mapStatistiquesEquipes.get(equipe).getPoints())
				.reversed()
				.thenComparing(Equipe::getWorldRanking))
			.collect(Collectors.toList());

		// Obtention des points et le World Ranking de la deuxième équipe dans la liste triée
		float pointsEquipe2 = mapStatistiquesEquipes.get(equipesSelectionnees.get(1)).getPoints();
		int worldRankingEquipe2 = equipesSelectionnees.get(1).getWorldRanking();

		// Filtrer les équipes qui ont le même nombre de points ou plus et le même classement mondial ou mieux que la deuxième équipe
		List<Equipe> equipesFiltrees = equipesSelectionnees.stream()
			.filter(equipe -> mapStatistiquesEquipes.get(equipe).getPoints() >= pointsEquipe2 && equipe.getWorldRanking() <= worldRankingEquipe2)
			.collect(Collectors.toList());

		// S'il y a plus de 2 équipes après le filtrage, mélanger la liste et sélectionner les 2 premières équipes
		if (equipesFiltrees.size() >= 2) {
			Collections.shuffle(equipesFiltrees);
			equipesSelectionnees = equipesFiltrees.subList(0, 2);
		} else {
			// Si moins de 2 équipes restent après le filtrage, sélectionner les 2 premières équipes de la liste initiale
			equipesSelectionnees = equipesSelectionnees.subList(0, 2);
		}

		return equipesSelectionnees.toArray(new Equipe[2]);
	}

	/**
	 * Clôture la poule finale et le tournoi
	 * @param tournoi Le tournoi dont la poule finale (poule actuelle) est à cloturer
	 * @throws Exception Erreurs SQL ou de récupération d'équipes
	 * @throws IllegalArgumentException Si la poule finale est déjà clôturée
	 */
	private void cloturerPouleFinale(Tournoi tournoi) throws Exception {
		Poule poule = tournoi.getPouleActuelle();

		// Vérification que la poule n'est pas déjà clôturée
		if(poule.getEstCloturee() || tournoi.getEstCloture()) {
			throw new IllegalArgumentException("Poule finale déjà cloturée");
		}

		// Obtention du nombre de points de chaque équipe (25 match gagné, 15 perdu)
		Map<Equipe, Float> nbPointsParEquipe = this.getNombrePointsMatchsParEquipe(tournoi);

		// Ajout du nombre de points de chaque équipe selon son classement (1er, 2e, 3e, 4e) et multiplication par notoriété
		Map<Equipe, Float> nbPointsParEquipeClasse = this.getNombrePointsParClassement(nbPointsParEquipe, tournoi.getNotoriete());

		// Création des historiques de points
		for (Map.Entry<Equipe, Float> entry : nbPointsParEquipeClasse.entrySet()) {
			this.daoHistoriquePoints.ajouter(new HistoriquePoints(entry.getValue(), tournoi, entry.getKey().getIdEquipe()));
		}

		// Mise à jour du classement des équipes
		this.majClassements();

		// Fermeture de la poule et du tournoi
		this.daoPoule.cloturerPoule(poule);
		this.daoTournoi.cloturerTournoi(tournoi);
	}

	/**
	 * Retourne le nombre de points de chaque équipe selon le nombre de matchs gagnés ou perdus
	 * @param tournoi Le tournoi en question pour le calcul des points
	 * @return Le nombre de points de chaque équipe selon le nombre de matchs gagnés ou perdus
	 */
	private Map<Equipe, Float> getNombrePointsMatchsParEquipe(Tournoi tournoi) {
		Map<Equipe, Float> nbPointsParEquipe = new HashMap<>();

		// Récupération de tous les matchs du tournoi
		List<Rencontre> rencontresTournoi = tournoi.getPoules().stream()
			.flatMap(pouleTournoi -> pouleTournoi.getRencontres().stream())
			.collect(Collectors.toList());

		for(Rencontre rencontre : rencontresTournoi) {
			for(Equipe equipe : rencontre.getEquipes()) {
				// Si l'équipe n'est pas dans la map, on l'ajoute
				nbPointsParEquipe.putIfAbsent(equipe, 0F);

				// Si l'équipe a gagné, on ajoute 25 points, sinon 15 point
				if (equipe.getIdEquipe() == rencontre.getIdEquipeGagnante()) {
					nbPointsParEquipe.put(equipe, nbPointsParEquipe.get(equipe) + EnumPoints.MATCH_VICTOIRE.getPoints());
				} else {
					nbPointsParEquipe.put(equipe, nbPointsParEquipe.get(equipe) + EnumPoints.MATCH_DEFAITE.getPoints());
				}
			}
		}

		return nbPointsParEquipe;
	}

	/**
	 * Retourne le nombre de points de chaque équipe selon son classement (1er, 2e, 3e, 4e) et multiplication par notoriété
	 * @param nbPointsParEquipe Le nombre de points de chaque équipe selon le nombre de matchs gagnés ou perdus
	 * @param notoriete La notoriété du tournoi
	 * @return Le nombre de points de chaque équipe selon son classement (1er, 2e, 3e, 4e) et multiplication par notoriété
	 */
	private Map<Equipe, Float> getNombrePointsParClassement(Map<Equipe, Float> nbPointsParEquipe, Tournoi.Notoriete notoriete) {
		// On convertit la map en une liste d'entrées (clé-valeur) pour la trier
		List<Map.Entry<Equipe, Float>> listePoints = new ArrayList<>(nbPointsParEquipe.entrySet());

		// On trie la liste selon le nombre de points décroissant
		listePoints.sort(Map.Entry.<Equipe, Float>comparingByValue().reversed());

		// On parcours la liste de points (triés)
		for (int i = 0; i < listePoints.size(); i++) {
			Map.Entry<Equipe, Float> entry = listePoints.get(i);

			// On multiplie le nombre de points par 10
			entry.setValue(entry.getValue() * 10F);

			// Puis on ajoute des points selon le classement de l'équipe
			switch (i) {
				case 0:
					entry.setValue(entry.getValue() + EnumPoints.CLASSEMENT_PREMIER.getPoints());
					break;
				case 1:
					entry.setValue(entry.getValue() + EnumPoints.CLASSEMENT_DEUXIEME.getPoints());
					break;
				case 2:
					entry.setValue(entry.getValue() + EnumPoints.CLASSEMENT_TROISIEME.getPoints());
					break;
				case 3:
					entry.setValue(entry.getValue() + EnumPoints.CLASSEMENT_QUATRIEME.getPoints());
					break;
			}

			// Finalement, on multiplie selon la notoriété
			entry.setValue(entry.getValue() * notoriete.getMultiplicateur());
		}

		// On retransforme en map
		Map<Equipe, Float> nbPointsParEquipeClasse = listePoints.stream()
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (ancienneValeur, nouvelleValeur) -> ancienneValeur, LinkedHashMap::new));

		return nbPointsParEquipeClasse;
	}

}
