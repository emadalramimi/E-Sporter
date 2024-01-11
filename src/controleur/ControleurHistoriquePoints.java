package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleEquipe;
import modele.ModeleHistoriquePoints;
import modele.metier.Equipe;
import vue.VueHistoriquePoints;

public class ControleurHistoriquePoints extends ControleurRecherche<Equipe> implements ActionListener, ListSelectionListener {
    
    private VueHistoriquePoints vue;
    private ModeleEquipe modeleEquipe;
    private ModeleHistoriquePoints modeleHistoriquePoints;

    public ControleurHistoriquePoints(VueHistoriquePoints vue) {
		super(new ModeleEquipe(), vue);
		this.vue = vue;
		this.modeleEquipe = (ModeleEquipe) super.getModele();
        this.modeleHistoriquePoints = new ModeleHistoriquePoints();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JTable tableEquipes = this.vue.getTableEquipes();
        if(e.getSource() == tableEquipes.getSelectionModel() && !e.getValueIsAdjusting()) {
            int idEquipe = (int) tableEquipes.getValueAt(tableEquipes.getSelectedRow(), 0);
            try {
                this.vue.remplirTableauHistoriquePoints(this.modeleHistoriquePoints.getParEquipe(idEquipe));
            } catch(Exception err) {
                this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération des historiques de points de l'équipe");
                throw new RuntimeException(err);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
		super.traitementClicBoutonRecherche(e);
    }

    /**
	 * Retourne la liste de toutes les équipes de la saison pour VueEquipes
	 * @return La liste de toutes les équipes de la siaosn
	 */
    public List<Equipe> getEquipes() {
        try {
            return this.modeleEquipe.getEquipesSaison();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

}
