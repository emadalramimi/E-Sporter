package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleEquipe;
//import modele.metier.Arbitre;
import modele.metier.Equipe;
import vue.VueInscriptionEquipesTournoi;
import vue.VueTournois;

public class ControleurInscriptionEquipesTournoi implements ActionListener, ListSelectionListener {

	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
//	private VueTournois vueTournois;
	private ModeleEquipe modeleEquipe;
	
	public ControleurInscriptionEquipesTournoi(VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, VueTournois vueTournois) {
		this.vueInscriptionEquipesTournoi = vueInscriptionEquipesTournoi;
//		this.vueTournois = vueTournois;
		this.modeleEquipe = new ModeleEquipe();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		if(bouton.getText() == "Inscrire une équipe") {
			try {
				this.vueInscriptionEquipesTournoi.afficherVueSaisieTournoiEquipe(modeleEquipe.getTableauEquipes());
			} catch (Exception err) {
				this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
				err.printStackTrace();
			}
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
	    if (!e.getValueIsAdjusting()) { // Pour éviter les événements redondants
	        JList<?> liste = (JList<?>) e.getSource();

	        if (this.vueInscriptionEquipesTournoi.estListeEquipes(liste)) {
	            if (this.vueInscriptionEquipesTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désinscrire cette équipe ?")) {
	                this.vueInscriptionEquipesTournoi.supprimerEquipe((Equipe) liste.getSelectedValue());
	            }
	        }
	    }
	}

}
