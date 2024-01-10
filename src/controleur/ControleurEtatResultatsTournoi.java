package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import modele.ModeleImpression;
import modele.ModeleTournoi;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import vue.VueEtatResultatsTournoi;

/**
 * Contrôleur de la vue des résultats d'un tournoi
 * @see VueEtatResultatsTournoi
 */
public class ControleurEtatResultatsTournoi implements ActionListener {
    
    private VueEtatResultatsTournoi vue;
    private Tournoi tournoi;
    private ModeleTournoi modeleTournoi;
    private ModeleImpression modeleImpression;

    /**
     * Constructeur du controleur de VueEtatResultatsTournoi
     * @param vue : vueEtatResultatsTournoi
     */
    public ControleurEtatResultatsTournoi(VueEtatResultatsTournoi vue, Tournoi tournoi) {
        this.vue = vue;
        this.tournoi = tournoi;
        this.modeleTournoi = new ModeleTournoi();
        this.modeleImpression = new ModeleImpression();
    }

    /**
     * Quand on clique sur le bouton "Fermer"
     * On ferme la fenêtre
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();
        
        switch(bouton.getText()) {
            case "Fermer":
                this.vue.fermerFenetre();
                break;
            case "Imprimer":
                this.modeleImpression.imprimerEtatResultatsTournoi(this.vue.getTableImpression(), this.tournoi);
                break;
        }
    }

    /**
     * Récupère les résultats d'un tournoi
     * @param tournoi : tournoi
     * @return les résultats d'un tournoi sous forme de StatistiquesEquipe
     * @see StatistiquesEquipe
     */
    public List<StatistiquesEquipe> getResultatsTournoi(Tournoi tournoi) {
        return this.modeleTournoi.getResultatsTournoi(tournoi);
    }

}
