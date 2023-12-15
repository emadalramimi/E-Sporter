package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.VueSaisieTournoi;
import vue.VueSaisieTournoiEquipeArbitre;

public class ControleurSaisieTournoiEquipeArbitre implements ActionListener {

	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre;
	private VueSaisieTournoi vueSaisieTournoi;
	
	public ControleurSaisieTournoiEquipeArbitre(VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre, VueSaisieTournoi vueSaisieTournoi) {
		this.vueSaisieTournoiEquipeArbitre = vueSaisieTournoiEquipeArbitre;
		this.vueSaisieTournoi = vueSaisieTournoi;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter l'arbitre":
			this.vueSaisieTournoi.ajouterArbitre(this.vueSaisieTournoiEquipeArbitre.getArbitre());
		case "Annuler":
			this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
			break;
		}
	}
	
}
