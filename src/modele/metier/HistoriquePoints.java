package modele.metier;

public class HistoriquePoints {
    
    private int idHistoriquePoints;
    private float points;
    private Tournoi tournoi;
    private int idEquipe;

    public HistoriquePoints(int idHistoriquePoints, float points, Tournoi tournoi, int idEquipe) {
        this.idHistoriquePoints = idHistoriquePoints;
        this.points = points;
        this.tournoi = tournoi;
        this.idEquipe = idEquipe;
    }

    public HistoriquePoints(float points, Tournoi tournoi, int idEquipe) {
        this.points = points;
        this.tournoi = tournoi;
        this.idEquipe = idEquipe;
    }

    public int getIdHistoriquePoints() {
        return this.idHistoriquePoints;
    }

    public void setIdHistoriquePoints(int idHistoriquePoints) {
        this.idHistoriquePoints = idHistoriquePoints;
    }

    public float getPoints() {
        return this.points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public Tournoi getTournoi() {
        return this.tournoi;
    }

    public void setTournoi(Tournoi tournoi) {
        this.tournoi = tournoi;
    }

    public int getIdEquipe() {
        return this.idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

}
