package modele;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.Recherchable;
import modele.exception.InscriptionEquipeTournoiException;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;

/**
 * Modèle équipe
 */
public class ModeleEquipe implements Recherchable<Equipe> {

    private DAOEquipe daoEquipe;

    public ModeleEquipe() {
        this.daoEquipe = new DAOEquipeImpl();
    }
	
	/**
	 * Modifie une équipe en traitant les exceptions
	 * @param equipe : équipe à modifier
	 * @param nom : nouveau nom de l'équipe
	 * @param pays : nouveau pays de l'équipe
	 * @param nomsJoueurs : nouveaux noms des joueurs de l'équipe
	 * @return true si la modification a été effectuée, false sinon
	 * @throws Exception Erreur SQL
	 */
	public boolean modifier(Equipe equipe, String nom, Pays pays, List<String> nomsJoueurs) throws Exception {
		if (this.daoEquipe.estEquipeInscriteUnTournoiOuvert(equipe)) {
			throw new InscriptionEquipeTournoiException("Cette équipe est inscrite à un tournoi actuellement ouvert.");
		}
	
		// Modification des champs
		equipe.setNom(nom);
		equipe.setPays(pays);
		
		// Récupération des joueurs saisis et mise à jour des joueurs de l'équipe
		List<Joueur> joueursEquipe = equipe.getJoueurs();
		for(int i = 0; i < nomsJoueurs.size(); i++) {
			joueursEquipe.get(i).setPseudo(nomsJoueurs.get(i));
		}

		return this.daoEquipe.modifier(equipe);
	}

	/**
	 * Créer liste des joueurs d'une équipe
	 * @param nomsJoueurs : noms des joueurs de l'équipe
	 * @return la liste des joueurs de l'équipe
	 */
	public List<Joueur> creerJoueurs(List<String> nomsJoueurs) {
		List<Joueur> joueurs = new ArrayList<>();
		for (String nomJoueur : nomsJoueurs) {
			joueurs.add(new Joueur(nomJoueur));
		}
		return joueurs;
	}

	/**
	 * Retourne la liste des équipes par nom
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
