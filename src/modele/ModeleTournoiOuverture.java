package modele;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import modele.exception.DatesTournoiException;
import modele.exception.OuvertureTournoiException;
import modele.exception.TournoiDejaOuvertException;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;

public class ModeleTournoiOuverture {

    private ModeleTournoi modeleTournoi;

    public ModeleTournoiOuverture() {
        this.modeleTournoi = new ModeleTournoi();
    }

	/**
	 * Ouvre un tournoi
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
		if(this.modeleTournoi.getTout().stream().anyMatch(t -> t.getEstCloture() == false)) {
			throw new TournoiDejaOuvertException("Il ne peut y avoir qu'un seul tournoi ouvert à la fois");
		}

		try {
			PreparedStatement ps;
			if(tournoi.getDateTimeDebut() > System.currentTimeMillis() / 1000) {
				ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = false, dateDebut = ? where idTournoi = ?");
				ps.setInt(1, (int) (System.currentTimeMillis() / 1000));
				ps.setInt(2, tournoi.getIdTournoi());
			} else {
				ps = BDD.getConnexion().prepareStatement("update tournoi set estCloture = false where idTournoi = ?");
				ps.setInt(1, tournoi.getIdTournoi());
			}
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
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}

}
