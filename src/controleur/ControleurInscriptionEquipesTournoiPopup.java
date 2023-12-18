package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.VueInscriptionEquipesTournoi;
import vue.VueSaisieTournoiEquipeArbitre;

/**
 * Contrôleur de la vue d'inscription des équipes à un tournoi
 * @see VueInscriptionEquipesTournoi
 */
public class ControleurInscriptionEquipesTournoiPopup implements ActionListener {

    private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre;
	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
    private Tournoi tournoi;
	private ModeleEquipe modeleEquipe;
	
	/**
	 * Constructeur du contrôleur de la vue d'inscription des équipes à un tournoi
	 * @param vueSaisieTournoiEquipeArbitre Vue de saisie d'une équipe et d'un arbitre
	 * @param vueInscriptionEquipesTournoi Vue d'inscription des équipes à un tournoi
	 * @param tournoi Tournoi
	 */
    public ControleurInscriptionEquipesTournoiPopup(VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre, VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, Tournoi tournoi) {
        this.vueSaisieTournoiEquipeArbitre = vueSaisieTournoiEquipeArbitre;
        this.vueInscriptionEquipesTournoi = vueInscriptionEquipesTournoi;
        this.tournoi = tournoi;
        this.modeleEquipe = new ModeleEquipe();
	}
	
	/**
	 * Effectue un traitement au clic d'un élément de la fenêtre
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		// Traitement différent en fonction du bouton
		switch(bouton.getText()) {
			case "Ajouter l'équipe":
				// On récupère l'équipe saisie			
				Equipe equipe = this.vueSaisieTournoiEquipeArbitre.getEquipe();

				// On vérifie que le nombre maximal d'équipes n'est pas atteint
				if (this.vueInscriptionEquipesTournoi.getEquipes().size() >= 8) {
					this.vueSaisieTournoiEquipeArbitre.afficherPopupErreur("Le nombre maximal d'équipes inscrites a été atteint");
					throw new IllegalArgumentException("Le nombre maximal d'équipes inscrites a été atteint");
				}
				
				try {
					// On inscrit l'équipe au tournoi
					this.modeleEquipe.inscrireEquipe(equipe, this.tournoi);
					this.tournoi.addEquipe(equipe);
					this.vueInscriptionEquipesTournoi.ajouterEquipe(this.vueSaisieTournoiEquipeArbitre.getEquipe());
					this.vueSaisieTournoiEquipeArbitre.fermerFenetre();

					// On désactive le bouton d'inscription si le nombre maximal d'équipes est atteint ou si aucune équipe n'est éligible
					if (this.vueInscriptionEquipesTournoi.getEquipes().size() >= 8 || this.modeleEquipe.getTableauEquipes(this.vueInscriptionEquipesTournoi.getEquipes()).length == 0) {
						this.vueInscriptionEquipesTournoi.setBtnInscrireEquipeActif(false);
					} else {
						this.vueInscriptionEquipesTournoi.setBtnInscrireEquipeActif(true);
					}
				} catch (Exception err) {
					this.vueSaisieTournoiEquipeArbitre.afficherPopupErreur("Une erreur est survenue : impossible d'inscrire cette équipe");
					err.printStackTrace();
				}
				break;
		case "Annuler":
			this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
			break;
		}
	}
	
}
