package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JTextField;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.metier.Equipe;
import vue.VueEquipes;
import vue.theme.JButtonTable;

/**
 * Controleur de VueEquipes
 */
public class ControleurEquipes extends KeyAdapter implements ActionListener {

	private VueEquipes vue;
	private ModeleEquipe modeleEquipe;
	private ModeleJoueur modeleJoueur;
	
	/**
	 * Constructeur du controleur de VueEquipes
	 * @param vue : vueEquipes
	 */
	public ControleurEquipes(VueEquipes vue) {
		this.vue = vue;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleJoueur = new ModeleJoueur();
	}
	
	/**
	 * Quand on effectue une action sur un élément de VueEquipes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Clic sur un bouton du tableau (voir, modifier, supprimer)
		if(e.getSource() instanceof JButtonTable) {
			JButtonTable bouton = (JButtonTable) e.getSource();
			
			// Récupération de l'ID de l'équipe sélectionné
			int idEquipe = bouton.getIdElement();
			Optional<Equipe> equipeOptionnel;
			try {
				equipeOptionnel = this.modeleEquipe.getParId(idEquipe);
			} catch(Exception err) {
				equipeOptionnel = Optional.empty();
			}
			
			// Traitement différent en fonction du bouton
			switch(bouton.getType()) {
			case VOIR:
				// Afficher la liste des joueurs
				this.vue.afficherVueJoueurs(this.modeleJoueur.getListeJoueursParId(idEquipe));
				break;
			case MODIFIER:
				// Afficher une page de saisie d'équipe vierge
				this.vue.afficherVueSaisieEquipe(equipeOptionnel);
				break;
			case SUPPRIMER:
				// Suppression d'une équipe
				Equipe equipe = equipeOptionnel.orElse(null);
				// Si équipe n'est pas trouvée
				if(equipe == null) {
					this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
					throw new RuntimeException("Equipe est inexistant");
				}
				// Affiche une demande de confirmation de suppression
				if(this.vue.afficherConfirmationSuppression()) {
					// Supprime l'équipe, affiche un message d'équipe supprimée et met à jour le tableau sur VueEquipes
					if(this.modeleEquipe.supprimer(equipe)) {
						this.vue.afficherPopupMessage("L'équipe a bien été supprimée");
						try {
							this.vue.remplirTableau(this.modeleEquipe.getTout());
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Impossible de récupérer les équipes");
							throw new RuntimeException("Impossible de récupérer les équipes");
						}
					} else {
						// En cas d'erreur
						this.vue.afficherPopupErreur("Une erreur est survenue");
					}
				}
				break;
			}
		} else if(e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			// Si il s'agit du bouton ajouter
			if(bouton.getText() == "Ajouter") {
				// Ouverture de la fenêtre d'ajout d'équipe
				this.vue.afficherVueSaisieEquipe(Optional.empty());
			}
			// Si il s'agit du bouton de recherche
			else if(this.vue.estBoutonRecherche(bouton)) {
				String requeteRecherche = this.vue.getRequeteRecherche();
				if(requeteRecherche != null) {
					this.rechercher(this.vue.getRequeteRecherche());
				}
			}
		}
		
	}
	
	/**
	 * Quand on appuie sur une touche du clavier dans le champ de recherche
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		JTextField txtRecherche = (JTextField) e.getSource();
		// Si il s'agit du champ de recherche
		if (this.vue.estChampRecherche(txtRecherche)) {
			// Récupère la requête de recherche
			String requeteRecherche = this.vue.getRequeteRecherche();
			// Effectuer la recherche à l'appui de la touche entrée
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.rechercher(requeteRecherche);
			}
			// Lorsqu'on supprime tous les caractères dans le champ de recherche, sortir de la recherche
			else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (requeteRecherche != null
						&& requeteRecherche.length() == 1
						|| txtRecherche.getSelectedText() != null
								&& txtRecherche.getSelectedText().equals(requeteRecherche)) {
					this.rechercher("");
				}
			}
		}
	}
	
	/**
	 * Effectue une recherche de requête requeteRecherche
	 * @param requeteRecherche
	 */
	private void rechercher(String requeteRecherche) {
		try {
			// Mise à jour du tableau avec les résultats de recherche
			this.vue.remplirTableau(this.modeleEquipe.getParNom(requeteRecherche));
		} catch (Exception e1) {
			this.vue.afficherPopupErreur("Une erreur est survenue");
			throw new RuntimeException("Erreur dans la recherche");
		}
	}

	/**
	 * Retourne la liste de toutes les équipes de la saison pour VueEquipes
	 * @return La liste de toutes les équipes de la siaosn
	 */
	public List<Equipe> getEquipes() {
		try {
			return this.modeleEquipe.getTout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
