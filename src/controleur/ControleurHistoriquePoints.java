package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleEquipe;
import modele.ModeleImpression;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOHistoriquePoints;
import modele.DAO.DAOHistoriquePointsImpl;
import modele.metier.Equipe;
import vue.VueHistoriquePoints;
import vue.theme.JTableThemeImpression;

public class ControleurHistoriquePoints extends ControleurRecherche<Equipe> implements ActionListener, ListSelectionListener {
    
    private VueHistoriquePoints vue;
    private DAOEquipe daoEquipe;
    private DAOHistoriquePoints daoHistoriquePoints;
    private ModeleImpression modeleImpression;
    private Equipe equipeSelectionnee;

    public ControleurHistoriquePoints(VueHistoriquePoints vue) {
		super(new ModeleEquipe(), vue);
		this.vue = vue;
		this.daoEquipe = new DAOEquipeImpl();
        this.daoHistoriquePoints = new DAOHistoriquePointsImpl();
        this.modeleImpression = new ModeleImpression();
        this.equipeSelectionnee = null;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JTable tableEquipes = this.vue.getTableEquipes();
        if(e.getSource() == tableEquipes.getSelectionModel() && !e.getValueIsAdjusting()) {
            int idEquipe = (int) tableEquipes.getValueAt(tableEquipes.getSelectedRow(), 0);
            try {
                this.equipeSelectionnee = this.daoEquipe.getParId(idEquipe).orElse(null);
            } catch(Exception err) {
                this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération de l'équipe sélectionnée");
                throw new RuntimeException(err);
            }
            try {
                this.vue.remplirTableauHistoriquePoints(this.daoHistoriquePoints.getParEquipe(idEquipe));
            } catch(Exception err) {
                this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération des historiques de points de l'équipe");
                throw new RuntimeException(err);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
		super.traitementClicBoutonRecherche(e);

        if(e.getSource() instanceof JButton) {
            JButton bouton = (JButton) e.getSource();
            if(bouton.getText() == "Imprimer l'historique sélectionné") {
                JTableThemeImpression table = null;
                try {
                    table = this.vue.getTableImpression();
                } catch(IllegalArgumentException err) {
                    this.vue.afficherPopupErreur(err.getMessage());
                }
                if(table != null) {
                    try {
                        this.modeleImpression.imprimerHistoriquePoints(table, this.equipeSelectionnee);
                    } catch(Exception err) {
                        this.vue.afficherPopupErreur("Une erreur est survenue lors de l'impression de l'historique des points de l'équipe");
                        throw new RuntimeException(err);
                    }
                }
            }
        }
    }

    /**
	 * Retourne la liste de toutes les équipes de la saison pour VueEquipes
	 * @return La liste de toutes les équipes de la siaosn
	 */
    public List<Equipe> getEquipes() {
        try {
            return this.daoEquipe.getEquipesSaison();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

}
