package modele;

import java.util.List;
import java.util.stream.Collectors;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.Recherchable;
import modele.exception.InscriptionEquipeTournoiException;
import modele.metier.Equipe;

public class ModeleEquipe implements Recherchable<Equipe> {

    private DAOEquipe daoEquipe;

    public ModeleEquipe() {
        this.daoEquipe = new DAOEquipeImpl();
    }

	public boolean modifier(Equipe equipe) throws Exception {
		if (this.daoEquipe.estEquipeInscriteUnTournoiOuvert(equipe)) {
			throw new InscriptionEquipeTournoiException("Cette équipe est inscrite à un tournoi actuellement ouvert.");
		}

		return this.daoEquipe.modifier(equipe);
	}

	/**
	 * Méthode qui récupère les équipes contenant la variable nom dans leur nom d'équipe
	 * @param nom : contenu dans le nom d'une équipe
	 * @return la liste des équipes contenant la variable nom dans leur nom d'équipe
	 * @throws Exception Erreur SQL
	 */
	@Override
	public List<Equipe> getParNom(String nom) throws Exception {
		return this.daoEquipe.getEquipesSaison().stream()
            .filter(e -> e.getNom().toLowerCase().contains(nom.toLowerCase()))
            .collect(Collectors.toList());
	}

}
