package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleEquipe;
import modele.ModeleTournoi;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.VueInscriptionEquipesTournoi;
import vue.VueTournois;

public class ControleurInscriptionEquipesTournoi implements ActionListener, ListSelectionListener {

	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
	private VueTournois vueTournois;
	private Tournoi tournoi;
	private ModeleEquipe modeleEquipe;
	private ModeleTournoi modeleTournoi;
	
	public ControleurInscriptionEquipesTournoi(VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, VueTournois vueTournois, Tournoi tournoi) {
		this.vueInscriptionEquipesTournoi = vueInscriptionEquipesTournoi;
		this.vueTournois = vueTournois;
		this.tournoi = tournoi;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleTournoi = new ModeleTournoi();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch (bouton.getText()) {
			case "Inscrire une équipe":
				try {
					this.vueInscriptionEquipesTournoi.afficherVueSaisieTournoiEquipe(modeleEquipe.getTableauEquipes());
				} catch (Exception err) {
					this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
					err.printStackTrace();
				}
				break;
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting() && e.getSource() instanceof JList) { // Pour éviter les événements redondants
			JList<?> liste = (JList<?>) e.getSource();

			if (this.vueInscriptionEquipesTournoi.estListeEquipes(liste)) {
				if (liste.getSelectedValue() != null && this.vueInscriptionEquipesTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désinscrire cette équipe ?")) {
					try {
						this.modeleEquipe.desinscrireEquipe((Equipe) liste.getSelectedValue(), this.tournoi);
						this.vueInscriptionEquipesTournoi.supprimerEquipe((Equipe) liste.getSelectedValue());
						liste.clearSelection();
					} catch (Exception err) {
						this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de désinscrire l'équipe");
						err.printStackTrace();
					}
				}
			}
		}
	}

}
