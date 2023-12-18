package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

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
    private ModeleTournoi modeleTournoi;

    /**
     * Constructeur du controleur de VueEtatResultatsTournoi
     * @param vue : vueEtatResultatsTournoi
     */
    public ControleurEtatResultatsTournoi(VueEtatResultatsTournoi vue) {
        this.vue = vue;
        this.modeleTournoi = new ModeleTournoi();
    }

    /**
     * Quand on clique sur le bouton "Fermer"
     * On ferme la fenêtre
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();
        
        if (bouton.getText() == "Fermer") {
        	this.vue.fermerFenetre();
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
