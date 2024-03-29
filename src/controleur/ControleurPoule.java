package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import modele.ModeleRencontre;
import modele.ModeleTournoiCloture;
import modele.ModeleUtilisateur;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAORencontre;
import modele.DAO.DAORencontreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.DroitsInsuffisantsException;
import modele.exception.TournoiClotureException;
import modele.exception.TournoiInexistantException;
import modele.metier.Equipe;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import vue.VueConnexion;
import vue.VuePoule;
import vue.theme.JTableTheme;

/**
 * Contrôleur de la vue des poules
 * @see VuePoule
 */
public class ControleurPoule extends MouseAdapter implements ActionListener {
    
    private VuePoule vue;
    private Tournoi tournoi;
    private DAOTournoi daoTournoi;
    private DAORencontre daoRencontre;
    private DAOEquipe daoEquipe;
    private ModeleTournoiCloture modeleTournoiCloture;
    private ModeleRencontre modeleRencontre;
    private ModeleUtilisateur modeleUtilisateur;

    /**
     * Constructeur du controleur de VuePoule
     * @param vue Vue poule
     * @param tournoi Tournoi
     */
    public ControleurPoule(VuePoule vue, Tournoi tournoi) {
        this.vue = vue;
        this.tournoi = tournoi;
        this.daoTournoi = new DAOTournoiImpl();
        this.daoRencontre = new DAORencontreImpl();
        this.daoEquipe = new DAOEquipeImpl();
        this.modeleTournoiCloture = new ModeleTournoiCloture();
        this.modeleRencontre = new ModeleRencontre();
        this.modeleUtilisateur = new ModeleUtilisateur();
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
                    rencontre = this.modeleRencontre.getRencontreInMemory(this.tournoi, idRencontre);
                } catch (Exception ex) {
                    this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération de la rencontre.");
                    throw new RuntimeException("Une erreur est survenue lors de la récupération de la rencontre.");
                }
                if (rencontre == null) {
                    this.vue.afficherPopupErreur("La rencontre n'existe pas.");
                    throw new IllegalArgumentException("La rencontre n'existe pas");
                }

                try {
                    // Si la rencontre cliquée est la même que la rencontre actuellement active
                    Equipe equipeGagnante = this.daoEquipe.getParId(rencontre.getIdEquipeGagnante()).orElse(null);

                    // Si l'équipe sélectionnée est celle qui est déjà gagnante, on la retire, sinon, on l'ajoute
                    if(equipeGagnante != null && equipeGagnante.getNom().equals(nomEquipe)) {
                        this.daoRencontre.resetEquipeGagnante(rencontre);
                        this.vue.resetGagnant(ligne, col);
                    } else {
                        this.daoRencontre.setEquipeGagnante(rencontre, nomEquipe);
                        this.vue.toggleGagnant(ligne, col);
                    }
                } catch (TournoiInexistantException | TournoiClotureException | DroitsInsuffisantsException ex) {
                    // Affichage des messages d'erreur
                    this.vue.afficherPopupErreur(ex.getMessage());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    this.vue.afficherPopupErreur("Une erreur est survenue lors de la mise à jour de la rencontre.");
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Effectue un traitement lors de la sélection d'un élément de la fenêtre
     * Quand on clique sur le bouton "Clôturer la poule" : on clôture la poule
     * Quand on clique sur le bouton "État des résultats" : on affiche la vue d'état des résultats du tournoi
     * Quand on clique sur le bouton "Fermer" : on ferme la fenêtre
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();

        switch(bouton.getActionCommand()) {
            case "CLOTURER_POULE":
                try {
                    this.modeleTournoiCloture.cloturerPoule(this.tournoi);
                    
                    if(!this.tournoi.getPouleActuelle().getEstFinale()) {
                        this.vue.afficherPopupMessage("La poule a été clôturée.");
                    } else {
                        this.vue.afficherPopupMessage("Le tournoi a été clôturé. Vous allez être déconnecté.");
                        this.vue.getVueTournois().remplirTableau(this.daoTournoi.getTout());

                        this.modeleUtilisateur.deconnecter();
                        VueConnexion vueConnexion = new VueConnexion();
                        vueConnexion.afficher();
                        this.vue.getVueTournois().getVueBase().fermerFenetre();
                    }

                    this.vue.fermerFenetre();
                } catch(IllegalArgumentException ex) {
                    this.vue.afficherPopupErreur(ex.getMessage());
                } catch (Exception ex) {
                    this.vue.afficherPopupErreur("Une erreur est survenue lors de la clôture de la poule.");
                    ex.printStackTrace();
                }
                break;
            case "LISTER_ARBITRES":
                // Affiche la liste des arbitres
                this.vue.afficherVueArbitres(this.tournoi.getArbitres());
                break;
            case "ETATS_RESULTATS":
                // Affiche la vue d'état des résultats du tournoi
                this.vue.afficherVueEtatResultatsTournoi(this.tournoi);
                break;
            case "FERMER":
                this.vue.fermerFenetre();
                break;
        }
    }

}
