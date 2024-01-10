package controleur;

import java.util.List;

import modele.ModelePalmares;
import modele.metier.Palmares;

public class ControleurPalmares {
    
    private ModelePalmares modelePalmares;

    public ControleurPalmares() {
        this.modelePalmares = new ModelePalmares();
    }

    public List<Palmares> getClassement() {
        try {
            return modelePalmares.getClassement();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
