package controleur;

import java.util.List;

import modele.ModeleEquipe;
import modele.metier.Equipe;
import vue.VuePalmares;

public class ControleurPalmares {
    
    private VuePalmares vue;
    private ModeleEquipe modeleEquipe;

    public ControleurPalmares(VuePalmares vue) {
        this.vue = vue;
        this.modeleEquipe = new ModeleEquipe();
    }

    public List<Equipe> getClassement() {
        try {
            return modeleEquipe.getClassement();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
