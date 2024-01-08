package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import modele.metier.EnumPoints;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;

/**
 * Modèle poule
 */
public class ModelePoule extends DAO<Poule, Integer> {
	
	private ModeleRencontre modeleRencontre;

	/**
	 * Construit un modèle poule
	 */
	public ModelePoule() {
		this.modeleRencontre = new ModeleRencontre();
	}

	/**
	 * Récupère toutes les poules
	 * @return Liste de toutes les poules
	 * @throws Exception Exception SQL
	 */
	@Override
	public List<Poule> getTout() throws Exception {
		Statement st = BDD.getConnexion().createStatement();
		ResultSet rs = st.executeQuery("select * from poule");
		
		// Parcourt les poules dans la base de données et les formate dans une liste
		Stream<Poule> stream = StreamSupport.stream(
    		new Spliterators.AbstractSpliterator<Poule>(Long.MAX_VALUE, Spliterator.ORDERED) {
                @Override
                public boolean tryAdvance(Consumer <? super Poule> action) {
                    try {
                        if (!rs.next()) {
                            return false;
                        }
                        action.accept(new Poule(
                    		rs.getInt("idPoule"),
                			rs.getBoolean("estCloturee"),
                			rs.getBoolean("estFinale"),
                			rs.getInt("idTournoi"),
                			ModelePoule.this.modeleRencontre.getRencontresPoules(rs.getInt("idPoule"))
                        ));
                        return true;
                    } catch (Exception e) {
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
	 * Supprime la poule dans la BDD
	 * @param poule Poule à supprimer
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean supprimer(Poule poule) throws Exception {
		try {
			// Supprime les rencontres de la poule
			for (Rencontre rencontre : poule.getRencontres()) {
				this.modeleRencontre.supprimer(rencontre);
			}
			
			PreparedStatement ps = BDD.getConnexion().prepareStatement("delete from poule where idPoule = ?");
			ps.setInt(1, poule.getIdPoule());
			ps.execute();
			ps.close();
			
			return true;
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
	 * Ajoute la poule dans la BDD
	 * @param poule Poule à ajouter
	 * @return true si l'opération s'est bien déroulée, false sinon
	 * @throws Exception Exception SQL
	 */
	@Override
	public boolean ajouter(Poule poule) throws Exception {
		try {
			int idPoule = this.getNextValId();
			poule.setIdPoule(idPoule);

			PreparedStatement ps = BDD.getConnexion().prepareStatement("insert into poule values (?, ?, ?, ?)");
			ps.setInt(1, poule.getIdPoule());
			ps.setBoolean(2, poule.getEstCloturee());
			ps.setBoolean(3, poule.getEstFinale());
			ps.setInt(4, poule.getIdTournoi());
			ps.execute();
			ps.close();

			for (Rencontre rencontre : poule.getRencontres()) {
				rencontre.setIdPoule(idPoule);
				this.modeleRencontre.ajouter(rencontre);
			}
			
			return true;
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
	 * Génère le prochain identifiant unique de poule
	 * @return le prochain identifiant unique de poule
	 * @throws Exception Exception SQL
	 */
	private int getNextValId() {
		int nextVal = 0;
		try {
			PreparedStatement ps = BDD.getConnexion().prepareStatement("values next value for idPoule");

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
	 * Retourne les poules d'un tournoi
	 * @param idTournoi Identifiant du tournoi
	 * @return Liste des poules du tournoi
	 */
	public List<Poule> getPoulesTournoi(int idTournoi) {
		try {
			List<Poule> poules = this.getTout().stream()
				.filter(poule -> poule.getIdTournoi() == idTournoi)
				.collect(Collectors.toList());
			return poules;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// peut etre bouger ce code dans le modèle tournoi
	public void cloturerPoule(Poule poule) throws Exception {
		Map<Equipe, Integer> nbPointsParEquipe = new HashMap<>();

		// Calcul des points par équipe
		for(Rencontre rencontre : poule.getRencontres()) {
			// Vérification que tous les matchs ont été joués (0 = aucun gagnant)
			if(rencontre.getIdEquipeGagnante() == 0) {
				throw new IllegalArgumentException("Tous les matchs de la poule n'ont pas été joués");
			}

			for(Equipe equipe : rencontre.getEquipes()) {
				// Si l'équipe n'est pas dans la map, on l'ajoute
				nbPointsParEquipe.putIfAbsent(equipe, 0);

				// Si l'équipe a gagné, on ajoute 3 points, sinon 1 point
				if (equipe.getIdEquipe() == rencontre.getIdEquipeGagnante()) {
					nbPointsParEquipe.put(equipe, nbPointsParEquipe.get(equipe) + EnumPoints.POULE_MATCH_VICTOIRE.getPoints());
				} else {
					nbPointsParEquipe.put(equipe, nbPointsParEquipe.get(equipe) + EnumPoints.POULE_MATCH_PERDU.getPoints());
				}
			}
		}
		
		List<Equipe> equipesSelectionnees = poule.getRencontres().stream()
			.flatMap(rencontre -> Arrays.stream(rencontre.getEquipes()))
			.collect(Collectors.groupingBy(
				Function.identity(),
				Collectors.summingInt(equipe -> nbPointsParEquipe.getOrDefault(equipe, 0))
			))
			.entrySet().stream()
			.sorted(Comparator.<Map.Entry<Equipe, Integer>>comparingInt(entry -> entry.getValue())
				.thenComparing((entry1, entry2) -> Integer.compare(entry2.getKey().getWorldRanking(), entry1.getKey().getWorldRanking()))
				.reversed())
			.map(Map.Entry::getKey)
			.collect(Collectors.collectingAndThen(
				Collectors.toList(),
				equipes -> selectionnerMeilleuresEquipes(equipes, nbPointsParEquipe)
			));
			
		// Nous sommes sûrs qu'equipesSelectionnees contient 2 équipes

		// Clôture de la poule
		PreparedStatement ps = BDD.getConnexion().prepareStatement("update poule set estCloturee = true where idPoule = ?");
		ps.setInt(1, poule.getIdPoule());
		ps.execute();
		ps.close();

		// Création de la finale
		List<Rencontre> rencontres = new LinkedList<>();
		rencontres.add(new Rencontre(equipesSelectionnees.toArray(new Equipe[2])));
		Poule finale = new Poule(false, true, poule.getIdTournoi(), rencontres);
		this.ajouter(finale);

	}

	private List<Equipe> selectionnerMeilleuresEquipes(List<Equipe> equipes, Map<Equipe, Integer> nbPointsParEquipe) {
		int pointsMin = equipes.stream().limit(2).mapToInt(equipe -> nbPointsParEquipe.getOrDefault(equipe, 0)).min().orElse(0);
		List<Equipe> equipesSelectionnees = equipes.stream()
			.filter(equipe -> nbPointsParEquipe.getOrDefault(equipe, 0) >= pointsMin)
			.collect(Collectors.toList());
	
		if (equipesSelectionnees.size() > 2) {
			equipesSelectionnees.sort(Comparator.comparingInt(Equipe::getWorldRanking));
		}
	
		if (equipesSelectionnees.size() > 2) {
			Collections.shuffle(equipesSelectionnees);
			equipesSelectionnees = equipesSelectionnees.subList(0, 2);
		}
	
		return equipesSelectionnees;
	}
	
}
