package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleArbitre;
import modele.ModeleEquipe;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import vue.VueSaisieTournoi;

public class ControleurSaisieTournoi implements ActionListener, ListSelectionListener {

	private VueSaisieTournoi vueSaisieTournoi;
	private ModeleEquipe modeleEquipe;
	private ModeleArbitre modeleArbitre;
//	private VueTournois vueTournois;
//	private ControleurTournois controleurTournois;
//	private Optional<Tournoi> tournoiOptionnel;
	
	public ControleurSaisieTournoi(VueSaisieTournoi vueSaisieTournoi) {
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleArbitre = new ModeleArbitre();
//		this.vueTournois = vueTournois;
//		this.controleurTournois = controleurTournois;
//		this.tournoiOptionnel = tournoiOptionnel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter une équipe":
			try {
				this.vueSaisieTournoi.afficherVueSaisieTournoiEquipe(modeleEquipe.getTableauEquipes());
			} catch (Exception err) {
				this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
				err.printStackTrace();
			}
			break;
		case "Ajouter un arbitre":
			try {
				this.vueSaisieTournoi.afficherVueSaisieTournoiArbitre(modeleArbitre.getTableauArbitres());
			} catch (Exception err) {
				this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les arbitres");
				err.printStackTrace();
			}
			break;
		case "Annuler":
			this.vueSaisieTournoi.fermerFenetre();
			break;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
	    if (!e.getValueIsAdjusting()) { // Pour éviter les événements redondants
	        JList<?> liste = (JList<?>) e.getSource();

	        if (this.vueSaisieTournoi.estListeEquipes(liste)) {
	            if (this.vueSaisieTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désinscrire cette équipe ?")) {
	                this.vueSaisieTournoi.supprimerEquipe((Equipe) liste.getSelectedValue());
	            }
	        } else if (this.vueSaisieTournoi.estListeArbitres(liste)) {
	            if (this.vueSaisieTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désassigner cet arbitre ?")) {
	                this.vueSaisieTournoi.supprimerArbitre((Arbitre) liste.getSelectedValue());
	            }
	        }
	    }
	}

}
