package modele.metier;

public class Palmares {
    
    private Equipe equipe;
    private float points;

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

    public Equipe getEquipe() {
        return this.equipe;
    }

    public float getPoints() {
        return this.points;
    }

}
