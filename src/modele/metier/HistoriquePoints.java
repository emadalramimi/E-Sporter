package modele.metier;

/**
 * Modèle métier HistoriquePoints
 */
public class HistoriquePoints {
    
    private int idHistoriquePoints;
    private float points;
    private Tournoi tournoi;
    private int idEquipe;

    /**
     * Construit un historique de points complet
     * @param idHistoriquePoints Clé primaire
     * @param points Points
     * @param tournoi Tournoi
     * @param idEquipe Clé étrangère de l'équipe
     */
    public HistoriquePoints(int idHistoriquePoints, float points, Tournoi tournoi, int idEquipe) {
        this.idHistoriquePoints = idHistoriquePoints;
        this.points = points;
        this.tournoi = tournoi;
        this.idEquipe = idEquipe;
    }

    /**
     * Construit un historique de points sans clé primaire
     * @param points Points
     * @param tournoi Tournoi
     * @param idEquipe Clé étrangère de l'équipe
     */
    public HistoriquePoints(float points, Tournoi tournoi, int idEquipe) {
        this.points = points;
        this.tournoi = tournoi;
        this.idEquipe = idEquipe;
    }

    /**
     * Retourne la clé primaire
     * @return Clé primaire
     */
    public int getIdHistoriquePoints() {
        return this.idHistoriquePoints;
    }

    /**
     * Modifie la clé primaire
     * @param idHistoriquePoints Clé primaire
     */
    public void setIdHistoriquePoints(int idHistoriquePoints) {
        this.idHistoriquePoints = idHistoriquePoints;
    }

    /**
     * Retourne les points
     * @return Points
     */
    public float getPoints() {
        return this.points;
    }

    /**
     * Modifie les points
     * @param points Points
     */
    public void setPoints(float points) {
        this.points = points;
    }

    /**
     * Retourne le tournoi
     * @return Tournoi
     */
    public Tournoi getTournoi() {
        return this.tournoi;
    }

    /**
     * Modifie le tournoi
     * @param tournoi Tournoi
     */
    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    /**
     * Retourne l'équipe associée
     * @return Clé étrangère de l'équipe
     */
    public int getIdEquipe() {
        return this.idEquipe;
    }

    /**
     * Modifie l'équipe associée
     * @param idEquipe Clé étrangère de l'équipe
     */
    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

}
