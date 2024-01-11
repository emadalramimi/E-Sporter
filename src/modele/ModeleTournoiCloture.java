package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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

import modele.metier.EnumPoints;
import modele.metier.Equipe;
import modele.metier.HistoriquePoints;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;

public class ModeleTournoiCloture {

    private ModelePoule modelePoule;
    private ModeleEquipe modeleEquipe;
    private ModeleTournoi modeleTournoi;
	private ModeleHistoriquePoints modeleHistoriquePoints;

    public ModeleTournoiCloture() {
        this.modelePoule = new ModelePoule();
        this.modeleEquipe = new ModeleEquipe();
        this.modeleTournoi = new ModeleTournoi();
		this.modeleHistoriquePoints = new ModeleHistoriquePoints();
    }
    
    public void cloturerPoule(Tournoi tournoi) throws Exception {
		Poule poule = tournoi.getPouleActuelle();

		if(poule.getEstCloturee()) {
			throw new IllegalArgumentException("La poule est déjà cloturée");
		}

		// Vérification que tous les matchs ont été joués (0 = aucun gagnant)
		for(Rencontre rencontre : poule.getRencontres()) {
			if(rencontre.getIdEquipeGagnante() == 0) {
				throw new IllegalArgumentException("Tous les matchs de la poule n'ont pas été joués");
			}
		}

		if(poule.getEstFinale() == false) {
			// Récupération des points de toutes les équipes
			Map<Equipe, StatistiquesEquipe> mapStatistiquesEquipes = this.modeleTournoi.getResultatsTournoi(tournoi).stream()
				.collect(Collectors.toMap(StatistiquesEquipe::getEquipe, Function.identity()));

			List<Equipe> equipesSelectionnees = poule.getRencontres().stream()
				.flatMap(rencontre -> Arrays.stream(rencontre.getEquipes()))
				.collect(Collectors.groupingBy(
					Function.identity(),
					Collectors.summingDouble(equipe -> mapStatistiquesEquipes.get(equipe).getPoints())
				))
				.entrySet().stream()
				.sorted(Comparator.<Map.Entry<Equipe, Double>>comparingDouble(Map.Entry::getValue)
					.thenComparing((entry1, entry2) -> Integer.compare(entry2.getKey().getWorldRanking(), entry1.getKey().getWorldRanking()))
					.reversed())
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

			equipesSelectionnees = selectionnerMeilleuresEquipes(equipesSelectionnees, mapStatistiquesEquipes);
				
			// Nous sommes sûrs qu'equipesSelectionnees contient 2 équipes

			// Clôture de la poule à refactorer
			PreparedStatement ps = BDD.getConnexion().prepareStatement("update poule set estCloturee = true where idPoule = ?");
			ps.setInt(1, poule.getIdPoule());
			ps.execute();
			ps.close();

			// Création de la finale
			List<Rencontre> rencontres = new LinkedList<>();
			rencontres.add(new Rencontre(equipesSelectionnees.toArray(new Equipe[2])));
			Poule finale = new Poule(false, true, poule.getIdTournoi(), rencontres);
			this.modelePoule.ajouter(finale);
		} else {
			Map<Equipe, Float> nbPointsParEquipe = new HashMap<>();

			// Récupération de tous les matchs du tournoi
			List<Rencontre> rencontresTournoi = tournoi.getPoules().stream()
				.flatMap(pouleTournoi -> pouleTournoi.getRencontres().stream())
				.collect(Collectors.toList());

			// Nombre de points par match
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

			// Trier par classement des équipes
			// Convertir la map en une liste d'entrées (clé-valeur)
			List<Map.Entry<Equipe, Float>> listePoints = new ArrayList<>(nbPointsParEquipe.entrySet());

			// Trier la liste selon le nombre de points décroissant
			listePoints.sort(Map.Entry.<Equipe, Float>comparingByValue().reversed());

			for (int i = 0; i < listePoints.size(); i++) {
				Map.Entry<Equipe, Float> entry = listePoints.get(i);

				entry.setValue(entry.getValue() * 10F);

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

				entry.setValue(entry.getValue() * tournoi.getNotoriete().getMultiplicateur());
			}

			Map<Equipe, Float> nbPointsParEquipeClasse = listePoints.stream()
    			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (ancienneValeur, nouvelleValeur) -> ancienneValeur, LinkedHashMap::new));

			// Création des historiques de points
			for (Map.Entry<Equipe, Float> entry : nbPointsParEquipeClasse.entrySet()) {
				this.modeleHistoriquePoints.ajouter(new HistoriquePoints(entry.getValue(), tournoi, entry.getKey().getIdEquipe()));
			}

			this.majClassements();

			// Fermeture de la poule et du tournoi
			PreparedStatement psCloturePoule = BDD.getConnexion().prepareStatement("update poule set estCloturee = true where idPoule = ?");
			psCloturePoule.setInt(1, poule.getIdPoule());
			psCloturePoule.execute();
			psCloturePoule.close();

			PreparedStatement psClotureTournoi;
			if(tournoi.getDateTimeFin() > System.currentTimeMillis() / 1000) {
				psClotureTournoi = BDD.getConnexion().prepareStatement("update tournoi set estCloture = true, dateFin = ? where idTournoi = ?");
				psClotureTournoi.setInt(1, (int) (System.currentTimeMillis() / 1000));
				psClotureTournoi.setInt(2, tournoi.getIdTournoi());
			} else {
				psClotureTournoi = BDD.getConnexion().prepareStatement("update tournoi set estCloture = true where idTournoi = ?");
				psClotureTournoi.setInt(1, tournoi.getIdTournoi());
			}
			psClotureTournoi.execute();
			psClotureTournoi.close();
		}
	}

	private List<Equipe> selectionnerMeilleuresEquipes(List<Equipe> equipes, Map<Equipe, StatistiquesEquipe> mapStatistiquesEquipes) {
		List<Equipe> equipesSelectionnees = equipes.stream()
			.sorted(Comparator.comparing((Equipe equipe) -> mapStatistiquesEquipes.get(equipe).getPoints())
				.reversed()
				.thenComparing(Equipe::getWorldRanking))
			.collect(Collectors.toList());

		int minWorldRanking = equipesSelectionnees.stream()
			.limit(2)
			.mapToInt(Equipe::getWorldRanking)
			.max()
			.orElse(Integer.MAX_VALUE);

		equipesSelectionnees = equipesSelectionnees.stream()
			.filter(equipe -> equipe.getWorldRanking() <= minWorldRanking)
			.collect(Collectors.toList());
	
		if (equipesSelectionnees.size() > 2) {
			Collections.shuffle(equipesSelectionnees);
			equipesSelectionnees = equipesSelectionnees.subList(0, 2);
		}
	
		return equipesSelectionnees;
	}

	public void majClassements() throws Exception {
        Map<Equipe, Integer> classementParEquipe = new HashMap<>();
        for (Equipe equipe : this.modeleEquipe.getEquipesSaison()) {
            classementParEquipe.put(equipe, 1000);
        }

        PreparedStatement psTotalPointsParEquipe = BDD.getConnexion().prepareStatement("select e.idEquipe, sum(hp.points) from equipe e, historiquePoints hp where e.idEquipe = hp.idEquipe and e.saison = ? group by e.idEquipe order by sum(hp.points) desc");
		psTotalPointsParEquipe.setInt(1, LocalDate.now().getYear());
		ResultSet rs = psTotalPointsParEquipe.executeQuery();

        int classement = 0;
        int pointsPrecedents = -1;
        while (rs.next()) {
            Equipe equipe = this.modeleEquipe.getParId(rs.getInt(1)).orElse(null);
            int points = rs.getInt(2);

            if (points != pointsPrecedents) {
                classement++;
            }

            classementParEquipe.put(equipe, classement);

            pointsPrecedents = points;
        }

        rs.close();

        for(Equipe equipe : classementParEquipe.keySet()) {
            equipe.setClassement(classementParEquipe.get(equipe));
            
            PreparedStatement psClassement = BDD.getConnexion().prepareStatement("update equipe set classement = ? where idEquipe = ?");
            psClassement.setInt(1, classementParEquipe.get(equipe));
            psClassement.setInt(2, equipe.getIdEquipe());
            psClassement.executeUpdate();
            psClassement.close();
        }
    }

}
