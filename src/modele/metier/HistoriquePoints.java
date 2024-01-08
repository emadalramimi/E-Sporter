package modele.metier;

public class HistoriquePoints {
    
    private int idHistoriquePoints;
    private int points;
    private int idTournoi;
    private int idEquipe;

    public HistoriquePoints(int idHistoriquePoints, int points, int idTournoi, int idEquipe) {
        this.idHistoriquePoints = idHistoriquePoints;
        this.points = points;
        this.idTournoi = idTournoi;
        this.idEquipe = idEquipe;
    }

    public HistoriquePoints(int points, int idTournoi, int idEquipe) {
        this.points = points;
        this.idTournoi = idTournoi;
        this.idEquipe = idEquipe;
    }

    public int getIdHistoriquePoints() {
        return this.idHistoriquePoints;
    }

    public void setIdHistoriquePoints(int idHistoriquePoints) {
        this.idHistoriquePoints = idHistoriquePoints;
    }

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getIdTournoi() {
        return this.idTournoi;
    }

    public void setIdTournoi(int idTournoi) {
        this.idTournoi = idTournoi;
    }

    public int getIdEquipe() {
        return this.idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

}
