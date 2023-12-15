package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.VueInscriptionEquipesTournoi;
import vue.VueSaisieTournoiEquipeArbitre;

public class ControleurInscriptionEquipesTournoiPopup implements ActionListener {

    private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre;
	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
    private Tournoi tournoi;
    private ModeleEquipe modeleEquipe;
	
    public ControleurInscriptionEquipesTournoiPopup(VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipeArbitre, VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, Tournoi tournoi) {
        this.vueSaisieTournoiEquipeArbitre = vueSaisieTournoiEquipeArbitre;
        this.vueInscriptionEquipesTournoi = vueInscriptionEquipesTournoi;
        this.tournoi = tournoi;
        this.modeleEquipe = new ModeleEquipe();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter l'équipe":
			Equipe equipe = this.vueSaisieTournoiEquipeArbitre.getEquipe();
			try {
				this.modeleEquipe.inscrireEquipe(equipe, this.tournoi);
				this.vueInscriptionEquipesTournoi.ajouterEquipe(this.vueSaisieTournoiEquipeArbitre.getEquipe());
				this.vueSaisieTournoiEquipeArbitre.fermerFenetre();
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
