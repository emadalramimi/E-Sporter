package modele;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.DatesTournoiException;
import modele.exception.OuvertureTournoiException;
import modele.exception.TournoiDejaOuvertException;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;

/**
 * Modèle de l'ouverture d'un tournoi
 */
public class ModeleTournoiOuverture {

	private DAOTournoi daoTournoi;
	private DAOPoule daoPoule;

    public ModeleTournoiOuverture() {
		this.daoTournoi = new DAOTournoiImpl();
		this.daoPoule = new DAOPouleImpl();
    }

	/**
	 * Ouvrir un tournoi en vérifiant les conditions
	 * @param tournoi Tournoi à ouvrir
	 * @throws Exception Exception SQL et IllegalArgumentException si le tournoi est déjà ouvert, si la date de fin du tournoi est passée, si le nombre d'équipes inscrites n'est pas compris entre 4 et 8 équipes, si le tournoi est cloturé ou si un tournoi est déjà ouvert
	 */
	public void ouvrirTournoi(Tournoi tournoi) throws Exception {
		if (tournoi.getEstCloture() == false) {
			throw new OuvertureTournoiException("Le tournoi est déjà ouvert");
		}
		if (tournoi.getDateTimeFin() <= System.currentTimeMillis() / 1000) {
			throw new DatesTournoiException("La date de fin du tournoi est passée");
		}

		int nbEquipes = tournoi.getEquipes().size();
		if (nbEquipes < 4 || nbEquipes > 8) {
			throw new IllegalArgumentException("Le nombre d'équipes inscrites doit être compris entre 4 et 8 équipes");
		}
		if(this.daoTournoi.getTout().stream().anyMatch(t -> t.getEstCloture() == false)) {
			throw new TournoiDejaOuvertException("Il ne peut y avoir qu'un seul tournoi ouvert à la fois");
		}

		this.daoTournoi.ouvrirTournoi(tournoi);
		
		// Génération des poules
		List<Rencontre> rencontres = new LinkedList<>();
		List<Equipe> equipes = tournoi.getEquipes();
		for (int i = 0; i < equipes.size(); i++) {
			for (int j = i + 1; j < equipes.size(); j++) {
				rencontres.add(new Rencontre(new Equipe[] { equipes.get(i), equipes.get(j) }));
			}
		}
		Collections.shuffle(rencontres);
		 
		Poule poule = new Poule(false, false, tournoi.getIdTournoi(), rencontres);
		this.daoPoule.ajouter(poule);
	}

}
