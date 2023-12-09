package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.VueSaisieTournoi;
import vue.VueSaisieTournoiEquipe;

public class ControleurSaisieTournoiEquipe implements ActionListener {

	private VueSaisieTournoiEquipe vueSaisieTournoiEquipe;
	private VueSaisieTournoi vueSaisieTournoi;
	
	public ControleurSaisieTournoiEquipe(VueSaisieTournoiEquipe vueSaisieTournoiEquipe, VueSaisieTournoi vueSaisieTournoi) {
		this.vueSaisieTournoiEquipe = vueSaisieTournoiEquipe;
		this.vueSaisieTournoi = vueSaisieTournoi;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter l'équipe":
			this.vueSaisieTournoi.ajouterEquipe(this.vueSaisieTournoiEquipe.getEquipe());
		case "Annuler":
			this.vueSaisieTournoiEquipe.fermerFenetre();
			break;
		}
	}
	
}
