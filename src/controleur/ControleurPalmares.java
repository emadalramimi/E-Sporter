package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.List;

import javax.swing.JButton;

import modele.ModeleImpression;
import modele.ModelePalmares;
import modele.metier.Palmares;
import vue.VuePalmares;

public class ControleurPalmares extends ControleurRecherche<Palmares> implements ActionListener {
    
    private VuePalmares vue;
    private ModelePalmares modelePalmares;
    private ModeleImpression modeleImpression;

    public ControleurPalmares(VuePalmares vue) {
        super(new ModelePalmares(), vue);
        this.vue = vue;
        this.modelePalmares = (ModelePalmares) super.getModele();
        this.modeleImpression = new ModeleImpression();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.traitementClicBoutonRecherche(e);

        JButton bouton = (JButton) e.getSource();

        if(bouton.getText()=="Imprimer") {
            try {
                this.modeleImpression.imprimerPalmares(this.vue.getTableImpression());
            } catch(PrinterException err) {
                this.vue.afficherPopupErreur("Une erreur est survenue lors du lancement de la fenêtre d'impression");
            }
        }
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
