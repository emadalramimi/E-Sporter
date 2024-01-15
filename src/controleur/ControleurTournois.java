package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

import modele.ModeleUtilisateur;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Tournoi;
import modele.metier.Utilisateur;
import vue.VueTournois;
import vue.theme.JButtonTable;
import vue.theme.JComboBoxTheme;

/**
 * Contrôleur de la vue des tournois
 * @see VueTournois
 */
public class ControleurTournois extends ControleurRecherche<Tournoi> implements ActionListener, ItemListener {

	/**
	 * Enumération des statuts d'un tournoi
	 */
	public enum Statut {
		PHASE_INSCRIPTIONS("Phase d'inscriptions"),
		OUVERT("Ouvert"),
		CLOTURE("Clôturé");

		private String libelle;

		/**
		 * Constructeur d'un statut
		 * @param libelle : libellé du statut
		 */
		Statut(String libelle) {
			this.libelle = libelle;
		}

		/**
		 * Récupère le libellé du statut
		 * @return libellé du statut
		 */
		public String getLibelle() {
			return this.libelle;
		}

		/**
		 * Récupère le statut en fonction de son libellé
		 * @param libelle : libellé du statut
		 * @return statut
		 */
		public static Statut valueOfLibelle(String libelle) {
			for (Statut statut : Statut.values()) {
				if (statut.getLibelle().equals(libelle)) {
					return statut;
				}
			}
			return null;
		}

		/**
		 * Récupère tous les libellés des statuts pour les filtres
		 * @return libellés des statuts pour les filtres
		 */
		public static String[] getLibellesFiltres() {
			String[] libelles = new String[Statut.values().length + 1];
			libelles[0] = "Tous les statuts";
			for (int i = 0; i < Statut.values().length; i++) {
				libelles[i + 1] = Statut.values()[i].getLibelle();
			}
			return libelles;
		}
	}

	private VueTournois vue;
	private DAOTournoi daoTournoi;
	
	/**
	 * Constructeur du controleur de VueTournois
	 * @param vue : vueTournois
	 */
	public ControleurTournois(VueTournois vue) {
		super(new DAOTournoiImpl(), vue);
		this.vue = vue;
		this.daoTournoi = (DAOTournoiImpl) super.getModele();
	}
	
	/**
	 * Quand on effectue une action sur un élément de VueTournois
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		super.traitementClicBoutonRecherche(e);
		
		// Clic sur un bouton du tableau (voir, modifier, supprimer)
		if(e.getSource() instanceof JButtonTable) {
			JButtonTable bouton = (JButtonTable) e.getSource();
			
			// Récupération de l'ID du tournoi sélectionné
			int idTournoi = bouton.getIdElement();
			Optional<Tournoi> tournoiOptionnel;
			try {
				tournoiOptionnel = this.daoTournoi.getParId(idTournoi);
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
						// Le tournoi est cloturé : affichage de l'état des résultats du tournoi
						this.vue.afficherVueEtatResultatsTournoi(tournoi);
						// this.vue.afficherPopupErreur("Le tournoi est cloturé.");
						// throw new RuntimeException("Tournoi cloturé");
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
							this.daoTournoi.supprimer(tournoi);
						} catch (Exception err) {
							this.vue.afficherPopupErreur("Une erreur est survenue lors de la suppression du tournoi.");
							throw new RuntimeException("Erreur dans la suppression du tournoi", err);
						}

						this.vue.afficherPopupMessage("Le tournoi a bien été supprimé");

						// Mise à jour du tableau des tournois
						this.vue.getVueBase().changerOnglet(ControleurBase.Menus.TOURNOIS);

						this.vue.resetChampRecherche();
						this.vue.resetFiltres();
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

			if (this.vue.estCboxFiltre(comboBox)) {
			 	try {
					this.vue.remplirTableau(this.daoTournoi.getParFiltrage(this.vue.getNotorieteSelectionnee(), this.vue.getStatutSelectionne()));
				} catch (Exception err) {
					this.vue.afficherPopupErreur("Une erreur est survenue");
					throw new RuntimeException("Erreur dans la récupération des tournois");
				}
			}
		}
	}

	/**
	 * Récupère tous les tournois
	 * @return tous les tournois
	 */
	public List<Tournoi> getTournois() {
		try {
			return this.daoTournoi.getTout();
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
