package modele.metier;

public class HistoriquePoints {
    
    private int idHistoriquePoints;
    private float points;
    private int idTournoi;
    private Equipe equipe;

    public HistoriquePoints(int idHistoriquePoints, float points, int idTournoi, Equipe equipe) {
        this.idHistoriquePoints = idHistoriquePoints;
        this.points = points;
        this.idTournoi = idTournoi;
        this.equipe = equipe;
    }

    public HistoriquePoints(float points, int idTournoi, Equipe equipe) {
        this.points = points;
        this.idTournoi = idTournoi;
        this.equipe = equipe;
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

    public int getIdTournoi() {
        return this.idTournoi;
    }

    public void setIdTournoi(int idTournoi) {
        this.idTournoi = idTournoi;
    }

    public Equipe getEquipe() {
        return this.equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

}
