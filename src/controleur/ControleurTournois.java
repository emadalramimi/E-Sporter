package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JTextField;

import modele.ModeleTournoi;
import modele.ModeleUtilisateur;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.metier.Utilisateur;
import vue.VueTournois;
import vue.theme.JButtonTable;
import vue.theme.JComboBoxTheme;

/**
 * Contrôleur de la vue des tournois
 * @see VueTournois
 */
public class ControleurTournois extends KeyAdapter implements ActionListener, ItemListener {

	private VueTournois vue;
	private ModeleTournoi modeleTournoi;
	
	/**
	 * Constructeur du controleur de VueTournois
	 * @param vue : vueTournois
	 */
	public ControleurTournois(VueTournois vue) {
		this.vue = vue;
		this.modeleTournoi = new ModeleTournoi();
	}
	
	/**
	 * Quand on effectue une action sur un élément de VueTournois
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Clic sur un bouton du tableau (voir, modifier, supprimer)
		if(e.getSource() instanceof JButtonTable) {
			JButtonTable bouton = (JButtonTable) e.getSource();
			
			// Récupération de l'ID du tournoi sélectionné
			int idTournoi = bouton.getIdElement();
			Optional<Tournoi> tournoiOptionnel;
			try {
				tournoiOptionnel = this.modeleTournoi.getParId(idTournoi);
				System.out.println(idTournoi);
				System.out.println(tournoiOptionnel);
			} catch(Exception err) {
				this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération du tournoi");
				throw new RuntimeException("Une erreur est survenue lors de la récupération du tournoi");
			}
			
			// Si tournoi n'est pas trouvé
			if (tournoiOptionnel.orElse(null) == null) {
				this.vue.afficherPopupErreur("Une erreur est survenue : tournoi inexistant.");
				throw new RuntimeException("Tournoi est inexistant");
			}

			// Récupération du tournoi
			Tournoi tournoi = tournoiOptionnel.get();

			// Traitement différent en fonction du bouton
			switch (bouton.getType()) {
				case VOIR:
					if (this.estTournoiCloture(tournoi)) {
						// Seul un administrateur peut inscrire une équipe à un tournoi
						if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
							this.vue.afficherPopupErreur("Seul un administrateur peut inscrire une équipe à un tournoi.");
							throw new RuntimeException("Droits insuffisants");
						}

						// Le tournoi n'a pas encore commencé : affichage de la vue d'inscription des équipes
						this.vue.afficherVueInscriptionEquipes(tournoi);
					} else if (tournoi.getEstCloture() == true) {
						// Le tournoi est cloturé : affichage d'un message d'erreur
						this.vue.afficherPopupErreur("Le tournoi est cloturé.");
						throw new RuntimeException("Tournoi cloturé");
					} else {
						// Le tournoi est en cours : affichage de la vue de poule en cours
						this.vue.afficherVuePoule(tournoi);
					}
					break;
				case MODIFIER:
					// Seul un administrateur peut modifier un tournoi
					if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
						this.vue.afficherPopupErreur("Seul un administrateur peut modifier un tournoi.");
						throw new RuntimeException("Droits insuffisants");
					}

					// Si le tournoi est en cours ou cloturé, impossible de le modifier
					if (!this.estTournoiCloture(tournoi)) {
						this.vue.afficherPopupErreur("Le tournoi est en cours ou cloturé, impossible de le modifier.");
						throw new RuntimeException("Tournoi en cours ou cloturé, impossible de le modifier");
					}

					// Affiche une page de saisie de tournoi pré-remplie
					this.vue.afficherVueSaisieTournoi(tournoiOptionnel);

					break;
				case SUPPRIMER:
					// Seul un administrateur peut supprimer un tournoi
					if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
						this.vue.afficherPopupErreur("Seul un administrateur peut supprimer un tournoi.");
						throw new RuntimeException("Droits insuffisants");
					}

					// Si le tournoi est cloturé, impossible de le supprimer
					// Important : on peut supprimer un tournoi ouvert ou en phase d'inscription
					if (System.currentTimeMillis() / 1000 >= tournoi.getDateTimeDebut() && tournoi.getEstCloture()) {
						this.vue.afficherPopupErreur("Le tournoi est cloturé, impossible de le supprimer.");
						throw new RuntimeException("Tournoi est cloturé, impossible de le supprimer");
					}

					// Affiche une demande de confirmation de suppression
					if(this.vue.afficherConfirmationSuppression()) {
						// Supprime l'équipe, affiche un message d'équipe supprimée et met à jour le tableau sur VueEquipes
						try {
							this.modeleTournoi.supprimer(tournoi);
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue lors de la suppression du tournoi.");
							throw new RuntimeException("Erreur dans la suppression du tournoi", err);
						}

						this.vue.afficherPopupMessage("Le tournoi a bien été supprimé");

						// Mise à jour du tableau des tournois
						try {
							this.vue.remplirTableau(this.modeleTournoi.getTout());
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Impossible de récupérer les tournois");
							throw new RuntimeException("Impossible de récupérer les tournois");
						}
					}
					break;
			}
		} else if(e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			// Si il s'agit du bouton ajouter
			if(bouton.getText() == "Ajouter") {
				// Seul un administrateur peut ajouter un tournoi
				if(ModeleUtilisateur.getCompteCourant().getRole() != Utilisateur.Role.ADMINISTRATEUR) {
					this.vue.afficherPopupErreur("Seul un administrateur peut ajouter un tournoi.");
					throw new RuntimeException("Droits insuffisants");
				}

				// Ouverture de la fenêtre d'ajout d'équipe
				this.vue.afficherVueSaisieTournoi(Optional.empty());
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
					&& txtRecherche.getSelectedText().equals(requeteRecherche)
				) {
					this.rechercher("");
				}
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

			// Si il s'agit de la combobox notoriété
			if (this.vue.estCboxNotoriete(comboBox)) {
				String selection = (String) comboBox.getSelectedItem();
				if (selection.equals("Toutes notoriétés")) {
					// Chargement des tournois avec toutes les notoriétés
					try {
						this.vue.remplirTableau(this.modeleTournoi.getTout());
					} catch (Exception err) {
						this.vue.afficherPopupErreur("Une erreur est survenue");
						throw new RuntimeException("Erreur dans la récupération des tournois");
					}
				} else {
					// Chargement des tournois avec la notoriété sélectionnée
					try {
						this.vue.remplirTableau(this.modeleTournoi.getParNotoriete(Notoriete.valueOfLibelle(selection)));
					} catch (Exception err) {
						this.vue.afficherPopupErreur("Une erreur est survenue");
						throw new RuntimeException("Erreur dans la récupération des tournois");
					}
				}
			} 
			// Si il s'agit de la combobox statuts
			else if (this.vue.estCboxStatuts(comboBox)) {
				switch((String) comboBox.getSelectedItem()) {
					case "Tous statuts":
						// Chargement des tournois avec tous les statuts
						try {
							this.vue.remplirTableau(this.modeleTournoi.getTout());
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue");
							throw new RuntimeException("Erreur dans la récupération des tournois");
						}
						break;
					case "Phase d'inscriptions":
						// Chargement des tournois avec le statut "Phase d'inscriptions"
						try {
							this.vue.remplirTableau(this.modeleTournoi.getParPhaseInscription());
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue");
							throw new RuntimeException("Erreur dans la récupération des tournois");
						}
						break;
					case "Ouvert":
						// Chargement des tournois avec le statut "Ouvert"
						try {
							this.vue.remplirTableau(this.modeleTournoi.getParOuvert());
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue");
							throw new RuntimeException("Erreur dans la récupération des tournois");
						}
						break;
					case "Clôturé":
						// Chargement des tournois avec le statut "Clôturé"
						try {
							this.vue.remplirTableau(this.modeleTournoi.getParCloture());
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue");
							throw new RuntimeException("Erreur dans la récupération des tournois");
						}
						break;
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
			this.vue.remplirTableau(this.modeleTournoi.getParNom(requeteRecherche));
		} catch (Exception e1) {
			this.vue.afficherPopupErreur("Une erreur est survenue");
			throw new RuntimeException("Erreur dans la recherche");
		}
	}

	/**
	 * Récupère tous les tournois
	 * @return tous les tournois
	 */
	public List<Tournoi> getTournois() {
		try {
			return this.modeleTournoi.getTout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Vérifie si un tournoi est cloturé
	 * @param tournoi : tournoi
	 * @return true si le tournoi est cloturé, false sinon
	 */
	private boolean estTournoiCloture(Tournoi tournoi) {
		return System.currentTimeMillis() / 1000 < tournoi.getDateTimeFin() && tournoi.getEstCloture() == true;
	}
	
}
