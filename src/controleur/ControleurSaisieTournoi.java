package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;

import modele.ModeleEquipe;
import vue.VueSaisieTournoi;

public class ControleurSaisieTournoi extends FocusAdapter implements ActionListener {

	private VueSaisieTournoi vueSaisieTournoi;
	private ModeleEquipe modeleEquipe;
//	private VueTournois vueTournois;
//	private ControleurTournois controleurTournois;
//	private Optional<Tournoi> tournoiOptionnel;
	
	public ControleurSaisieTournoi(VueSaisieTournoi vueSaisieTournoi) {
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.modeleEquipe = new ModeleEquipe();
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
			break;
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO FAIRE SUPPRESSION
		System.out.println("ok");
	}

}
