package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.ModeleArbitre;
import vue.VueSaisieTournoi;
import vue.VueSaisieTournoiEquipeArbitre;

/**
 * Contrôleur de la vue de saisie d'un tournoi
 * @see VueSaisieTournoi
 */
public class ControleurSaisieTournoiArbitre implements ActionListener {

	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre;
	private VueSaisieTournoi vueSaisieTournoi;
	
	/**
	 * Constructeur du contrôleur de la vue de saisie d'un tournoi
	 * @param vueSaisieTournoiEquipeArbitre Vue de saisie d'une équipe et d'un arbitre
	 * @param vueSaisieTournoi Vue de saisie d'un tournoi
	 */
	public ControleurSaisieTournoiArbitre(VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre, VueSaisieTournoi vueSaisieTournoi) {
		this.vueSaisieTournoiEquipeArbitre = vueSaisieTournoiEquipeArbitre;
		this.vueSaisieTournoi = vueSaisieTournoi;
	}

	/**
	 * Effectue un traitement au clic d'un élément de la fenêtre
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		// Traitement différent en fonction du bouton
		switch(bouton.getText()) {
			case "Ajouter l'arbitre":
				// On récupère l'arbitre saisi et on l'ajoute au tournoi
				this.vueSaisieTournoi.ajouterArbitre(this.vueSaisieTournoiEquipeArbitre.getArbitre());
				ModeleArbitre modeleArbitre = new ModeleArbitre();
				try {
					// On vérifie que le nombre maximal d'arbitres n'est pas atteint
					if (modeleArbitre.getTableauArbitres(this.vueSaisieTournoi.getArbitres()).length == 0) {
						this.vueSaisieTournoi.setBtnAjouterArbitreActif(false);
					} else {
						this.vueSaisieTournoi.setBtnAjouterArbitreActif(true);
					}
				} catch (Exception ex) {
					this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les arbitres");
					ex.printStackTrace();
				}
			case "Annuler":
				this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
				break;
		}
	}
	
}
