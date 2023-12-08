package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import modele.ModeleUtilisateur;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import vue.VueBase;
import vue.VueConnexion;

/**
 * Controleur de VueConnexion
 */
public class ControleurConnexion extends KeyAdapter implements ActionListener {

	private VueConnexion vue;
	private ModeleUtilisateur modele;
	private Etat etat;
	
	/**
	 * Etats d'affichage du mot de passe
	 */
	private enum Etat {
		MDP_AFFICHE,
		MDP_CACHE
	};
	
	/**
	 * Construit un controleur connexion
	 * @param vue
	 */
	public ControleurConnexion(VueConnexion vue) {
		this.vue = vue;
		this.modele = new ModeleUtilisateur();
		// Le mot de passe est caché par défaut
		this.etat = Etat.MDP_CACHE;
	}
	
	/**
	 * Effectue un traitement au clic d'un élément de la fenêtre
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Si l'élément cliqué est la checkbox afficher mot de passe
		if(vue.isCheckboxAfficherMdp(e)) {
			// Affichage/masquage du mot de passe
			if(this.etat == Etat.MDP_CACHE) {
				vue.affichageMotDePasse(true);
				this.etat = Etat.MDP_AFFICHE;
			} else {
				vue.affichageMotDePasse(false);
				this.etat = Etat.MDP_CACHE;
			}
		} else {
			// Si c'est un bouton
			JButton b = (JButton) e.getSource();
			switch(b.getText()) {
			case "Connexion":
				// Connexion de l'utilisateur
				this.connecter();
				break;
			case "Quitter":
				// Fermeture de la fenêtre
				vue.fermerFenetre();
				break;
			}
		}
	}
	
	/**
	 * Connecte un utilisateur en appuyant sur entrée sur un champ
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.connecter();
		}
	}
	
	/**
	 * Connecte l'utilisateur
	 */
	private void connecter() {
		// Si tous les champs ne sont pas remplis
		if(vue.getIdentifiant() == null || vue.getMotDePasse() == null) {
			vue.afficherPopupErreur("Veuillez remplir tous les champs.");
		} else {
			// Sinon, on connecte l'administrateur
			try {
				modele.connecter(vue.getIdentifiant(), vue.getMotDePasse());
				
				// Fermeture de la page de connexion et ouverture de l'espace administrateur/arbitre
				vue.fermerFenetre();
				try {
					VueBase frame = new VueBase();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception err) {
					err.printStackTrace();
				}
			} catch (IdentifiantOuMdpIncorrectsException err) {
				// Si le couple identifiant/mot de passe est incorrect
				vue.afficherPopupErreur("Identifiant et/ou mot de passe incorrects.");
			} catch (RuntimeException err) {
				// Si une autre erreur SQL est survenue (deux instances ouvertes par exemple)
				vue.afficherPopupErreur("Une erreur inattendue est survenue.");
				err.printStackTrace();
			}
		}
	}

}
