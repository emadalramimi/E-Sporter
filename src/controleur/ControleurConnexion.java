package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import modele.ModeleAdministrateur;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import vue.VueConnexion;
import vue.VueTournois;

public class ControleurConnexion implements ActionListener {

	private VueConnexion vue;
	private ModeleAdministrateur modele;
	private Etat etat;
	
	private enum Etat {
		MDP_AFFICHE,
		MDP_CACHE
	};
	
	public ControleurConnexion(VueConnexion vue) {
		this.vue = vue;
		this.modele = new ModeleAdministrateur();
		this.etat = Etat.MDP_CACHE;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(vue.isCheckboxAfficherMdp(e)) {
			if(this.etat == Etat.MDP_CACHE) {
				vue.affichageMotDePasse(true);
				this.etat = Etat.MDP_AFFICHE;
			} else {
				vue.affichageMotDePasse(false);
				this.etat = Etat.MDP_CACHE;
			}
		} else {
			// Si c un bouton
			JButton b = (JButton) e.getSource();
			switch(b.getText()) {
			case "Connexion":
				if(vue.getIdentifiant().length() == 0 || vue.getMotDePasse().length() == 0) {
					vue.afficherPopupErreur("Veuillez remplir tous les champs.");
				} else {
					// on connecte l'administrateur
					try {
						modele.connecter(vue.getIdentifiant(), vue.getMotDePasse());
						
						vue.fermerFenetre();
						try {
							VueTournois frame = new VueTournois();
							frame.setVisible(true);
						} catch (Exception err) {
							err.printStackTrace();
						}
					} catch (IdentifiantOuMdpIncorrectsException err) {
						vue.afficherPopupErreur("Identifiant et/ou mot de passe incorrects.");
					} catch (RuntimeException err) {
						vue.afficherPopupErreur("Une erreur inattendue est survenue.");
					}
				}
				break;
			case "Quitter":
				vue.fermerFenetre();
				break;
			}
		}
	}

}
