package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.VueInscriptionEquipesTournoi;
import vue.VueSaisieTournoi;
import vue.VueSaisieTournoiEquipeArbitre;
import vue.theme.JFrameTheme;

public class ControleurSaisieTournoiEquipeArbitre implements ActionListener {

	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre;
	private JFrameTheme vue;
	
	public ControleurSaisieTournoiEquipeArbitre(VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre, JFrameTheme vue) {
		this.vueSaisieTournoiEquipeArbitre = vueSaisieTournoiEquipeArbitre;
		this.vue = vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter l'équipe":
			VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi = (VueInscriptionEquipesTournoi) this.vue;
			vueInscriptionEquipesTournoi.ajouterEquipe(this.vueSaisieTournoiEquipeArbitre.getEquipe());
			this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
			break;
		case "Ajouter l'arbitre":
			VueSaisieTournoi vueSaisieTournoi = (VueSaisieTournoi) this.vue;
			vueSaisieTournoi.ajouterArbitre(this.vueSaisieTournoiEquipeArbitre.getArbitre());
			this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
			break;
		case "Annuler":
			this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
			break;
		}
	}
	
}
