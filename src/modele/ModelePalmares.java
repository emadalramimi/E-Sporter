package modele;

import java.util.List;
import java.util.stream.Collectors;

import modele.DAO.DAOPalmares;
import modele.DAO.DAOPalmaresImpl;
import modele.DAO.Recherchable;
import modele.metier.Palmares;

/**
 * Modèle palmarès
 */
public class ModelePalmares implements Recherchable<Palmares> {

    private DAOPalmares daoPalmares;

    public ModelePalmares() {
        this.daoPalmares = new DAOPalmaresImpl();
    }
    
    /**
     * Méthode de recherche d'un palmarès par nom d'équipe
     * @param nom : nom de l'équipe
     * @return la liste des palmarès de l'équipe
     * @throws Exception Erreur SQL
     */
    @Override
    public List<Palmares> getParNom(String nom) throws Exception {
        return this.daoPalmares.getClassement()
            .stream()
            .filter(palmares -> palmares.getEquipe().getNom().toLowerCase().contains(nom.toLowerCase()))
            .collect(Collectors.toList());
    }

}
