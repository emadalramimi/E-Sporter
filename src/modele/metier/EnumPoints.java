package modele.metier;

/**
 * Enumération des points
 */
public enum EnumPoints {

    POULE_MATCH_VICTOIRE(3),
    POULE_MATCH_PERDU(1),

    MATCH_VICTOIRE(25),
    MATCH_DEFAITE(15),

    CLASSEMENT_PREMIER(200),
    CLASSEMENT_DEUXIEME(100),
    CLASSEMENT_TROISIEME(30),
    CLASSEMENT_QUATRIEME(15);

    private int points;

    /**
     * Construit une énumération de points
     * @param points Nombre de points
     */
    EnumPoints(int points) {
        this.points = points;
    }

    /**
     * Retourne le nombre de points
     * @return Nombre de points
     */
    public int getPoints() {
        return points;
    }

}
