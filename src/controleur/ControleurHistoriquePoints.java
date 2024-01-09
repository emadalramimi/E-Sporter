package controleur;

import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleEquipe;
import modele.ModeleHistoriquePoints;
import modele.metier.Equipe;
import vue.VueHistoriquePoints;

public class ControleurHistoriquePoints implements ListSelectionListener {
    
    private VueHistoriquePoints vue;
    private ModeleEquipe modeleEquipe;
    private ModeleHistoriquePoints modeleHistoriquePoints;

    public ControleurHistoriquePoints(VueHistoriquePoints vue) {
        this.vue = vue;
        this.modeleEquipe = new ModeleEquipe();
        this.modeleHistoriquePoints = new ModeleHistoriquePoints();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JTable tableEquipes = this.vue.getTableEquipes();
        if(e.getSource() == tableEquipes.getSelectionModel() && !e.getValueIsAdjusting()) {
            int idEquipe = (int) tableEquipes.getValueAt(tableEquipes.getSelectedRow(), 0);
            try {
                this.vue.remplirTableauHistoriquePoints(this.modeleHistoriquePoints.getHistoriquePointsEquipe(idEquipe));
            } catch(Exception err) {
                this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
                throw new RuntimeException("Equipe inexistante");
            }
        }
    }

    public List<Equipe> getEquipes() {
        try {
            return this.modeleEquipe.getEquipesSaison();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

}
