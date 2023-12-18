package modele.metier;

public class StatistiquesEquipe implements Comparable<StatistiquesEquipe> {
    
    private Equipe equipe;
    private int nbMatchsJoues;
    private int nbMatchsGagnes;
    private float ratio;

    public StatistiquesEquipe(Equipe equipe, int nbMatchsJoues, int nbMatchsGagnes) throws IllegalArgumentException {
        if(nbMatchsJoues < nbMatchsGagnes) {
            throw new IllegalArgumentException("Le nombre de matchs joués ne peut pas être inférieur au nombre de matchs gagnés");
        }
        if(nbMatchsJoues < 0 || nbMatchsGagnes < 0) {
            throw new IllegalArgumentException("Le nombre de matchs joués et le nombre de matchs gagnés ne peuvent pas être négatifs");
        }
        if(equipe == null) {
            throw new IllegalArgumentException("L'équipe ne peut pas être nulle");
        }

        this.equipe = equipe;
        this.nbMatchsJoues = nbMatchsJoues;
        this.nbMatchsGagnes = nbMatchsGagnes;
        if(nbMatchsJoues == 0) {
            this.ratio = 0F;
        } else {
            this.ratio = (float) nbMatchsGagnes / nbMatchsJoues;
        }
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public int getNbMatchsJoues() {
        return nbMatchsJoues;
    }

    public int getNbMatchsGagnes() {
        return nbMatchsGagnes;
    }

    public float getRatio() {
        return ratio;
    }

    public String getRatioPourcentage() {
        return String.format("%.2f", this.ratio*100) + " %";
    }

    @Override
	public String toString() {
		return "StatistiquesEquipe [equipe=" + equipe + ", nbMatchsJoues=" + nbMatchsJoues + ", nbMatchsGagnes=" + nbMatchsGagnes + ", ratio=" + ratio + "]";
	}

	@Override
    public int compareTo(StatistiquesEquipe statistiquesEquipes) {
        if (this.ratio == statistiquesEquipes.ratio) {
            return this.equipe.compareTo(statistiquesEquipes.equipe);
        }
        return Float.compare(statistiquesEquipes.ratio, this.ratio);
    }

}
