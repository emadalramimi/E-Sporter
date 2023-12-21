package modele.metier;

/**
 * Modèle métier statistiques équipe
 */
public class StatistiquesEquipe implements Comparable<StatistiquesEquipe> {
    
    private Equipe equipe;
    private int nbMatchsJoues;
    private int nbMatchsGagnes;
    private float ratio;

    /**
     * Construit les statistiques d'une équipe
     * @param equipe L'équipe
     * @param nbMatchsJoues Le nombre de matchs joués
     * @param nbMatchsGagnes Le nombre de matchs gagnés
     * @throws IllegalArgumentException Si le nombre de matchs joués est inférieur au nombre de matchs gagnés
     * @throws IllegalArgumentException Si le nombre de matchs joués ou le nombre de matchs gagnés est négatif
     * @throws IllegalArgumentException Si l'équipe est nulle
     */
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

    /**
     * Retourne l'équipe
     * @return L'équipe
     */
    public Equipe getEquipe() {
        return equipe;
    }

    /**
     * Retourne le nombre de matchs joués
     * @return Le nombre de matchs joués
     */
    public int getNbMatchsJoues() {
        return nbMatchsJoues;
    }

    /**
     * Retourne le nombre de matchs gagnés
     * @return Le nombre de matchs gagnés
     */
    public int getNbMatchsGagnes() {
        return nbMatchsGagnes;
    }

    /**
     * Retourne le ratio de l'équipe
     * @return Le ratio de l'équipe
     */
    public float getRatio() {
        return ratio;
    }

    /**
     * Retourne le ratio de l'équipe en pourcentage
     * @return Le ratio de l'équipe en pourcentage
     */
    public String getRatioPourcentage() {
        return String.format("%.2f", this.ratio*100) + " %";
    }
    
    @Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof StatistiquesEquipe)) {
	        return false;
	    }
	    StatistiquesEquipe stat = (StatistiquesEquipe) o;
	    return this.equipe.equals(stat.getEquipe())
	            && this.nbMatchsGagnes == stat.getNbMatchsGagnes()
	            && this.nbMatchsJoues == stat.getNbMatchsJoues();
	}

    /**
     * Compare deux statistiques d'équipe
     * @param statistiquesEquipes Les statistiques d'équipe à comparer
     * @return 0 si les statistiques d'équipe sont égales, 1 si les statistiques d'équipe sont différentes
     */
    @Override
    public int compareTo(StatistiquesEquipe statistiquesEquipes) {
        if (this.ratio == statistiquesEquipes.ratio) {
            return this.equipe.compareTo(statistiquesEquipes.equipe);
        }
        return Float.compare(statistiquesEquipes.ratio, this.ratio);
    }

    @Override
	public String toString() {
		return "StatistiquesEquipe [equipe=" + equipe + ", nbMatchsJoues=" + nbMatchsJoues + ", nbMatchsGagnes=" + nbMatchsGagnes + ", ratio=" + ratio + "]";
	}

}
