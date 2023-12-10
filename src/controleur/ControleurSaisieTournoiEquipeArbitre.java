package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.VueSaisieTournoi;
import vue.VueSaisieTournoiEquipeArbitre;

public class ControleurSaisieTournoiEquipeArbitre implements ActionListener {

	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipe;
	private VueSaisieTournoi vueSaisieTournoi;
	
	public ControleurSaisieTournoiEquipeArbitre(VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipe, VueSaisieTournoi vueSaisieTournoi) {
		this.vueSaisieTournoiEquipe = vueSaisieTournoiEquipe;
		this.vueSaisieTournoi = vueSaisieTournoi;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter l'équipe":
			this.vueSaisieTournoi.ajouterEquipe(this.vueSaisieTournoiEquipe.getEquipe());
			this.vueSaisieTournoiEquipe.fermerFenetre();
			break;
		case "Ajouter l'arbitre":
			this.vueSaisieTournoi.ajouterArbitre(this.vueSaisieTournoiEquipe.getArbitre());
			this.vueSaisieTournoiEquipe.fermerFenetre();
			break;
		case "Annuler":
			this.vueSaisieTournoiEquipe.fermerFenetre();
			break;
		}
	}
	
}
