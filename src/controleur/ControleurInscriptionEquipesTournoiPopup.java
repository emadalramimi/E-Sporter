package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
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
	private DAOEquipe daoEquipe;
	
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
        this.daoEquipe = new DAOEquipeImpl();
	}
	
	/**
	 * Effectue un traitement au clic d'un élément de la fenêtre
	 * Quand on clique sur le bouton "Ajouter l'équipe" : on ajoute l'équipe au tournoi
	 * Quand on clique sur le bouton "Annuler" : on ferme la fenêtre
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		// Traitement différent en fonction du bouton
		switch(bouton.getActionCommand()) {
			case "AJOUTER_EQUIPE":
				// On récupère l'équipe saisie			
				Equipe equipe = this.vueSaisieTournoiEquipeArbitre.getEquipe();

				// On vérifie que le nombre maximal d'équipes n'est pas atteint
				if (this.vueInscriptionEquipesTournoi.getEquipes().size() >= 8) {
					this.vueSaisieTournoiEquipeArbitre.afficherPopupErreur("Le nombre maximal d'équipes inscrites a été atteint");
					throw new IllegalArgumentException("Le nombre maximal d'équipes inscrites a été atteint");
				}
				
				try {
					// On inscrit l'équipe au tournoi
					this.daoEquipe.inscrireEquipe(equipe, this.tournoi);
					this.tournoi.addEquipe(equipe);
					this.vueInscriptionEquipesTournoi.ajouterEquipe(this.vueSaisieTournoiEquipeArbitre.getEquipe());
					this.vueSaisieTournoiEquipeArbitre.fermerFenetre();

					// On désactive le bouton d'inscription si le nombre maximal d'équipes est atteint ou si aucune équipe n'est éligible
					if (this.vueInscriptionEquipesTournoi.getEquipes().size() >= 8 || this.daoEquipe.getTableauEquipes(this.vueInscriptionEquipesTournoi.getEquipes()).length == 0) {
						this.vueInscriptionEquipesTournoi.setBtnInscrireEquipeActif(false);
					} else {
						this.vueInscriptionEquipesTournoi.setBtnInscrireEquipeActif(true);
					}
				} catch (Exception err) {
					this.vueSaisieTournoiEquipeArbitre.afficherPopupErreur("Une erreur est survenue : impossible d'inscrire cette équipe");
					err.printStackTrace();
				}
				break;
		case "ANNULER":
			this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
			break;
		}
	}
	
}
