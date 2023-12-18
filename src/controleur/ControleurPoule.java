package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import modele.ModeleRencontre;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import vue.VuePoule;
import vue.theme.JTableTheme;

/**
 * Contrôleur de la vue des poules
 * @see VuePoule
 */
public class ControleurPoule extends MouseAdapter implements ActionListener {
    
    private VuePoule vue;
    private Tournoi tournoi;
    private ModeleRencontre modele;

    /**
     * Constructeur du controleur de VuePoule
     * @param vue : vuePoule
     */
    public ControleurPoule(VuePoule vue, Tournoi tournoi) {
        this.vue = vue;
        this.tournoi = tournoi;
        this.modele = new ModeleRencontre();
    }

    /**
     * Quand on clique sur une équipe d'une rencontre
     * On met à jour la rencontre avec l'équipe gagnante
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Si on clique sur une cellule du tableau des rencontres
        if (this.vue.estTableauRencontres(e.getSource())) {
            JTableTheme table = (JTableTheme) e.getSource();

            // Récupération de la ligne et de la colonne
            int ligne = table.rowAtPoint(e.getPoint());
            int col = table.columnAtPoint(e.getPoint());
            
            // Si on clique sur une cellule contenant une équipe
            String nomColonne = table.getColumnName(col);
            if (nomColonne.equals("Équipe 1") || nomColonne.equals("Équipe 2")) {
                // Récupération de l'ID de la rencontre et du nom de l'équipe
                int idRencontre = (Integer) table.getValueAt(ligne, 0);
                String nomEquipe = (String) table.getValueAt(ligne, col);

                // Récupération de la rencontre
                Rencontre rencontre;
                try {
                    rencontre = this.tournoi.getPoules().stream()
                        .flatMap(poule -> poule.getRencontres().stream())
                        .filter(r -> r.getIdRencontre() == idRencontre)
                        .findFirst()
                        .orElse(null);
                } catch (Exception ex) {
                    this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération de la rencontre.");
                    throw new RuntimeException("Une erreur est survenue lors de la récupération de la rencontre.");
                }
                if (rencontre == null) {
                    this.vue.afficherPopupErreur("La rencontre n'existe pas.");
                    throw new IllegalArgumentException("La rencontre n'existe pas");
                }

                try {
                    // On met à jour la rencontre avec l'équipe gagnante
                    this.modele.setEquipeGagnante(rencontre, nomEquipe);
                    this.vue.toggleGagnant(ligne, col);
                } catch (IllegalArgumentException ex) {
                    // Affichage des messages d'erreur IllegalArgumentException
                    this.vue.afficherPopupErreur(ex.getMessage());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    this.vue.afficherPopupErreur("Une erreur est survenue lors de la mise à jour de la rencontre.");
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();

        switch(bouton.getText()) {
            case "Clôturer la poule":
                // TODO S3
                break;
            case "État des résultats":
                this.vue.afficherVueEtatResultatsTournoi(this.tournoi);
                break;
            case "Fermer":
                this.vue.fermerFenetre();
                break;
        }
    }

}
