package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.ModeleUtilisateur;
import modele.metier.Equipe;
import modele.metier.Utilisateur;
import vue.VueEquipes;
import vue.theme.JButtonTable;
import vue.theme.JComboBoxTheme;

/**
 * Contrôleur de la vue des équipes
 * @see VueEquipes
 */
public class ControleurEquipes extends ControleurRecherche<Equipe> implements ItemListener, ActionListener {

	private VueEquipes vue;
	private ModeleEquipe modeleEquipe;
	private ModeleJoueur modeleJoueur;
	
	/**
	 * Constructeur du controleur de VueEquipes
	 * @param vue : vueEquipes
	 */
	public ControleurEquipes(VueEquipes vue) {
		super(new ModeleEquipe(), vue);
		this.vue = vue;
		this.modeleEquipe = (ModeleEquipe) super.getModele();
		this.modeleJoueur = new ModeleJoueur();
	}
	
	/**
	 * Quand on effectue une action sur un élément de VueEquipes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		super.traitementClicBoutonRecherche(e);

		// Clic sur un bouton du tableau (voir, modifier, supprimer)
		if(e.getSource() instanceof JButtonTable) {
			JButtonTable bouton = (JButtonTable) e.getSource();
			
			// Récupération de l'ID de l'équipe sélectionné
			int idEquipe = bouton.getIdElement();
			Optional<Equipe> equipeOptionnel;
			try {
				equipeOptionnel = this.modeleEquipe.getParId(idEquipe);
			} catch(Exception err) {
				this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
				throw new RuntimeException("Equipe inexistante");
			}
			
			// Traitement différent en fonction du bouton
			switch(bouton.getType()) {
				case VOIR:
					// Afficher la liste des joueurs
					this.vue.afficherVueJoueurs(this.modeleJoueur.getListeJoueursParId(idEquipe));

					break;
				case MODIFIER:
					// Seul un administrateur peut modifier une équipe
					if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
						this.vue.afficherPopupErreur("Seul un administrateur peut modifier une équipe.");
						throw new RuntimeException("Droits insuffisants");
					}

					// Si équipe n'est pas trouvée
					if (equipeOptionnel.orElse(null) == null) {
						this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
						throw new RuntimeException("Equipe est inexistant");
					}

					// Si l'équipe est déjà inscrite dans un tournoi ouvert, impossible de la supprimer
					try {
						if (this.modeleEquipe.estEquipeInscriteUnTournoiOuvert(equipeOptionnel.get())) {
							this.vue.afficherPopupErreur("Impossible de modifier l'équipe : elle est inscrite à un tournoi actuellement ouvert.");
							throw new RuntimeException("Equipe inscrite dans un tournoi actuellement ouvert");
						}
					} catch (Exception ex) {
						throw new RuntimeException("Erreur dans la vérification de l'inscription de l'équipe dans un tournoi");
					}

					// Afficher une page de saisie d'équipe vierge
					this.vue.afficherVueSaisieEquipe(equipeOptionnel);

					break;
				case SUPPRIMER:
					// Seul un administrateur peut supprimer une équipe
					if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
						this.vue.afficherPopupErreur("Seul un administrateur peut supprimer une équipe.");
						throw new RuntimeException("Droits insuffisants");
					}

					// Suppression d'une équipe
					Equipe equipe = equipeOptionnel.orElse(null);

					// Si équipe n'est pas trouvée
					if (equipe == null) {
						this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
						throw new RuntimeException("Equipe est inexistant");
					}

					// Si l'équipe est déjà inscrite dans un tournoi, impossible de la supprimer
					try {
						if (this.modeleEquipe.estEquipeInscriteUnTournoi(equipe)) {
							this.vue.afficherPopupErreur("Impossible de supprimer l'équipe : elle est ou a été inscrite à un tournoi en cours ou clôturé.");
							throw new RuntimeException("Equipe inscrite dans un tournoi");
						}
					} catch (Exception ex) {
						throw new RuntimeException("Erreur dans la vérification de l'inscription de l'équipe dans un tournoi");
					}

					// Affiche une demande de confirmation de suppression
					if(this.vue.afficherConfirmationSuppression()) {
						// Supprime l'équipe, affiche un message d'équipe supprimée et met à jour le tableau sur VueEquipes
						try {
							this.modeleEquipe.supprimer(equipe);
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue dans la suppression de l'équipe.");
							throw new RuntimeException("Erreur dans la suppression de l'équipe");
						}

						this.vue.afficherPopupMessage("L'équipe a bien été supprimée");
						
						// Mise à jour du tableau des équipes
						// PS : À la suppression, pour éviter un bug, nous sommes contraints de recharger la page entière
						this.vue.getVueBase().changerOnglet(ControleurBase.Menus.EQUIPES);

						this.vue.resetFiltres();
					}

					break;
			}
		} else if(e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			// Si il s'agit du bouton ajouter
			if(bouton.getText() == "Ajouter") {
				// Seul un administrateur peut ajouter une équipe
				if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
					this.vue.afficherPopupErreur("Seul un administrateur peut ajouter une équipe.");
					throw new RuntimeException("Droits insuffisants");
				}

				// Ouverture de la fenêtre d'ajout d'équipe
				this.vue.afficherVueSaisieEquipe(Optional.empty());
			}
		}
	}

	/**
	 * Quand on change la sélection d'un élément de la fenêtre
	 * Filtre les tournois en fonction de la sélection
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() instanceof JComboBoxTheme<?>) {
			JComboBoxTheme<?> comboBox = (JComboBoxTheme<?>) e.getSource();

			if (this.vue.estCboxPays(comboBox) && e.getStateChange() == ItemEvent.SELECTED) {
			 	try {
					this.vue.remplirTableau(this.modeleEquipe.getParFiltrage(this.vue.getPaysSelectionne()));
				} catch (Exception err) {
					this.vue.afficherPopupErreur("Une erreur est survenue");
					throw new RuntimeException("Erreur dans la récupération des tournois");
				}
			}
		}
	}

	/**
	 * Retourne la liste de toutes les équipes de la saison pour VueEquipes
	 * @return La liste de toutes les équipes de la siaosn
	 */
	public List<Equipe> getEquipes() {
		try {
			return this.modeleEquipe.getEquipesSaison();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
