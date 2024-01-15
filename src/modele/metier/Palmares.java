package modele.metier;

/**
 * Modèle métier Palmares
 */
public class Palmares {
    
    private Equipe equipe;
    private float points;

    /**
     * Construit un palmares
     * @param equipe Equipe
     * @param points Points
     * @throws IllegalArgumentException Si l'équipe est nulle ou si les points sont négatifs
     */
    public Palmares(Equipe equipe, float points) throws IllegalArgumentException {
        if(equipe == null) {
            throw new IllegalArgumentException("L'équipe ne peut pas être nulle");
        }
        if(points < 0) {
            throw new IllegalArgumentException("Les points ne peuvent pas être négatifs");
        }
        this.equipe = equipe;
        this.points = points;
    }

    /**
     * Retourne l'équipe
     * @return Equipe
     */
    public Equipe getEquipe() {
        return this.equipe;
    }

    /**
     * Retourne les points
     * @return Points
     */
    public float getPoints() {
        return this.points;
    }

}
