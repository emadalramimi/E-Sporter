package modele.DAO;

import java.util.List;

import modele.metier.Joueur;

/**
 * Interface DAO pour la classe Joueur
 */
public interface DAOJoueur extends DAO<Joueur, Integer> {
    
    public boolean supprimerJoueursEquipe(int idEquipe) throws Exception;

    public List<Joueur> getListeJoueursParId(int idEquipe);

}
