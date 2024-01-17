package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
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
     * @param vue : vueEtatResultatsTournoi VueEtatResultatsTournoi
     */
    public ControleurEtatResultatsTournoi(VueEtatResultatsTournoi vue, Tournoi tournoi) {
        this.vue = vue;
        this.tournoi = tournoi;
        this.modeleTournoi = new ModeleTournoi();
        this.modeleImpression = new ModeleImpression();
    }

    /**
     * Quand on clique sur le bouton "Fermer" : on ferme la fenêtre
     * Quand on clique sur le bouton "Imprimer" : on imprime les résultats du tournoi
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();
        
        switch(bouton.getText()) {
            case "Fermer":
                this.vue.fermerFenetre();
                break;
            case "Imprimer":
                try {
                    this.modeleImpression.imprimerEtatResultatsTournoi(this.vue.getTableImpression(), this.tournoi);
                } catch(PrinterException err) {
                    this.vue.afficherPopupErreur("Une erreur est survenue lors du lancement de la fenêtre d'impression");
                }
                break;
        }
    }

    /**
     * Retourne les résultats d'un tournoi sous forme de StatistiquesEquipe
     * @param tournoi : tournoi
     * @return les résultats d'un tournoi sous forme de StatistiquesEquipe
     * @see StatistiquesEquipe
     */
    public List<StatistiquesEquipe> getResultatsTournoi(Tournoi tournoi) {
        return this.modeleTournoi.getResultatsTournoi(tournoi);
    }

}
