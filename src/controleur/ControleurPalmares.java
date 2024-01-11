package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modele.ModelePalmares;
import modele.metier.Palmares;
import vue.VuePalmares;

public class ControleurPalmares extends ControleurRecherche<Palmares> implements ActionListener {
    
    private ModelePalmares modelePalmares;

    public ControleurPalmares(VuePalmares vue) {
        super(new ModelePalmares(), vue);
        this.modelePalmares = (ModelePalmares) super.getModele();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.traitementClicBoutonRecherche(e);
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
