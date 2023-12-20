package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleAdministrateur;
import modele.ModeleArbitre;
import modele.ModeleTournoi;
import modele.ModeleUtilisateur;
import modele.metier.Arbitre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import vue.VueSaisieTournoi;
import vue.VueTournois;

/**
 * Contrôleur de la vue de saisie d'un tournoi
 * @see VueSaisieTournoi
 */
public class ControleurSaisieTournoi implements ActionListener, ListSelectionListener {

	private VueSaisieTournoi vueSaisieTournoi;
	private VueTournois vueTournois;
	private Optional<Tournoi> tournoiOptionnel;
	private ModeleArbitre modeleArbitre;
	private ModeleTournoi modeleTournoi;
	private ModeleAdministrateur modeleAdministrateur;
	
	/**
	 * Constructeur du contrôleur de la vue de saisie d'un tournoi
	 * @param vueSaisieTournoi Vue de saisie d'un tournoi
	 * @param vueTournois Vue des tournois
	 * @param tournoiOptionnel Tournoi à modifier (optionnel)
	 */
	public ControleurSaisieTournoi(VueSaisieTournoi vueSaisieTournoi, VueTournois vueTournois, Optional<Tournoi> tournoiOptionnel) {
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.vueTournois = vueTournois;
		this.tournoiOptionnel = tournoiOptionnel;
		this.modeleArbitre = new ModeleArbitre();
		this.modeleTournoi = new ModeleTournoi();
		this.modeleAdministrateur = new ModeleAdministrateur();
	}
	
	/**
	 * Effectue un traitement au clic d'un élément de la fenêtre
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		// Traitement différent en fonction du bouton
		switch(bouton.getText()) {
			case "Ajouter un arbitre":
				// On récupère les arbitres éligibles (non assignés au tournoi)
				Arbitre[] arbitres = this.getArbitresEligibles();
				if (arbitres.length == 0) {
					this.vueSaisieTournoi.afficherPopupErreur("Plus aucun arbitre disponible");
					throw new RuntimeException("Plus aucun arbitre disponible");
				}

				// On affiche la vue de saisie d'un arbitre
				this.vueSaisieTournoi.afficherVueSaisieTournoiArbitre(arbitres);
				break;
			case "Annuler":
				this.vueSaisieTournoi.fermerFenetre();
				break;
		}
		
		if(bouton.getText() == "Valider" || bouton.getText() == "Modifier") {
			// Vérification des champs
			if (!this.vueSaisieTournoi.tousChampsRemplis()) {
				this.vueSaisieTournoi.afficherPopupErreur("Veuillez remplir tous les champs.");
				throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
			}

			// Récupération des champs
			String nomTournoi = this.vueSaisieTournoi.getNomTournoi();
			String identifiant = this.vueSaisieTournoi.getIdentifiant();
			String motDePasse = this.vueSaisieTournoi.getMotDePasse();
			Notoriete notoriete = this.vueSaisieTournoi.getNotoriete();
			long dateTimeDebut = this.vueSaisieTournoi.getDateTimeDebut();
			long dateTimeFin = this.vueSaisieTournoi.getDateTimeFin();
			List<Arbitre> arbitres = this.vueSaisieTournoi.getArbitres();

			// Vérification des champs
			// Vérification du nombre d'arbitres
			if (arbitres.size() == 0) {
				this.vueSaisieTournoi.afficherPopupErreur("Au moins un arbitre doit être assigné.");
				throw new IllegalArgumentException("Au moins un arbitre doit être assigné.");
			}
			// Vérification de la date et de l'heure de début qui doit être supérieure à la date et l'heure actuelle
			if (dateTimeDebut < System.currentTimeMillis() / 1000) {
				this.vueSaisieTournoi.afficherPopupErreur("La date et l'heure de début doit être supérieure à la date et l'heure actuelle.");
				throw new IllegalArgumentException("La date et l'heure de début doit être supérieure à la date et l'heure actuelle.");
			}
			// Vérification de la date et de l'heure de fin qui doit être supérieure à la date et l'heure de début
			if (dateTimeDebut >= dateTimeFin) {
				this.vueSaisieTournoi.afficherPopupErreur("La date et l'heure de début doit être antérieure à la date et l'heure de fin.");
				throw new IllegalArgumentException("La date et l'heure de début doit être antérieure à la date et l'heure de fin.");
			}
			// Un identifiant ne peut contenir que des caractères, chiffres et - et _ et doit être unique
			if (!identifiant.matches("^[a-zA-Z0-9_-]+$")) {
				this.vueSaisieTournoi.afficherPopupErreur("L'identifiant ne peut contenir que des caractères, chiffres, tirets et underscores.");
				throw new IllegalArgumentException("L'identifiant ne peut contenir que des caractères, chiffres, tirets et underscores.");
			}
			// Vérification de la longueur du mot de passe
			if (motDePasse.length() < 3) {
				this.vueSaisieTournoi.afficherPopupErreur("Le mot de passe doit contenir au moins 3 caractères.");
				throw new IllegalArgumentException("Le mot de passe doit contenir au moins 3 caractères.");
			}
			
			// Création du tournoi au clic de valider
			if(bouton.getText() == "Valider") {
				// Vérification de l'unicité de l'identifiant
				this.verifierUniciteIdentifiant(identifiant);

				// Création du tournoi
				Tournoi tournoi = new Tournoi(nomTournoi, notoriete, dateTimeDebut, dateTimeFin, identifiant, motDePasse, arbitres);
				
				// Ajout du tournoi et affichage d'un message de succès/erreur
				try {
					modeleTournoi.ajouter(tournoi);
				} catch (Exception err) {
					this.vueSaisieTournoi.afficherPopupErreur("Impossible d'ajouter le tournoi");
					throw new RuntimeException("Impossible d'ajouter le tournoi", err);
				}
				this.vueSaisieTournoi.afficherPopupMessage("Le tournoi a bien été ajouté.");

				// Mise à jour du tableau des tournois
				try {
					this.vueTournois.remplirTableau(this.modeleTournoi.getTout());
				} catch (Exception err) {
					this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les tournois");
					throw new RuntimeException("Impossible de récupérer les tournois", err);
				}
			} 
			// Modification du tournoi au clic de modifier
			else if (bouton.getText() == "Modifier") {
				// Récupérer le tournoi à modifier
				Tournoi tournoi = this.tournoiOptionnel.orElse(null);

				// Si il y a un problème de récupération de tournoi
				if (tournoi == null) {
					this.vueSaisieTournoi.afficherPopupErreur("Une erreur est survenue : tournoi inexistant.");
					throw new RuntimeException("Tournoi inexistant");
				}

				// Vérification de l'unicité de l'identifiant en cas de modification
				if (!identifiant.equals(tournoi.getIdentifiant())) {
					this.verifierUniciteIdentifiant(identifiant);
				}

				// Modification des champs
				tournoi.setNomTournoi(nomTournoi);
				tournoi.setIdentifiant(identifiant);
				tournoi.setMotDePasse(ModeleUtilisateur.chiffrerMotDePasse(motDePasse));
				tournoi.setNotoriete(notoriete);
				tournoi.setDateTimeDebut(dateTimeDebut);
				tournoi.setDateTimeFin(dateTimeFin);
				tournoi.setArbitres(arbitres);

				// Modification du tournoi et affichage d'un message de succès/erreur
				try {
					this.modeleTournoi.modifier(tournoi);
				} catch (Exception err) {
					this.vueSaisieTournoi.afficherPopupErreur("Une erreur est survenue lors de la modification du tournoi.");
					throw new RuntimeException("Erreur dans la modification du tournoi", err);
				}
				this.vueSaisieTournoi.afficherPopupMessage("Le tournoi a bien été modifié.");

				// Mise à jour du tableau des tournois
				try {
					this.vueTournois.remplirTableau(this.modeleTournoi.getTout());
				} catch (Exception err) {
					this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les tournois");
					throw new RuntimeException("Impossible de récupérer les tournois", err);
				}
			}

			// TODO SI FILTRE/RECHERCHE réappliquer ici
			this.vueTournois.resetChampRecherche();
			this.vueTournois.resetCboxNotoriete();
			this.vueTournois.resetCboxStatuts();

			this.vueSaisieTournoi.fermerFenetre();
		}
	}

	/**
	 * Effectue un traitement lors de la sélection d'un élément de la liste
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Pour éviter les événements redondants
		if (!e.getValueIsAdjusting() && e.getSource() instanceof JList) {
			JList<?> liste = (JList<?>) e.getSource();

			// Si la liste est celle des arbitres assignés
			if (this.vueSaisieTournoi.estListeArbitres(liste)) {
				// Si un arbitre est sélectionné, on affiche une demande de confirmation de désassignation
				if (liste.getSelectedValue() != null && this.vueSaisieTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désassigner cet arbitre ?")) {
					// On désassigne l'arbitre et on réactive le bouton d'ajout d'arbitre
					this.vueSaisieTournoi.supprimerArbitre((Arbitre) liste.getSelectedValue());
					this.vueSaisieTournoi.setBtnAjouterArbitreActif(true);
				}
			}
		}
	}
	
	/**
	 * Récupère les arbitres éligibles (non assignés au tournoi)
	 * @return Arbitres éligibles
	 */
	public Arbitre[] getArbitresEligibles() {
		try {
			return modeleArbitre.getTableauArbitres(this.vueSaisieTournoi.getArbitres());
		} catch (Exception e) {
			this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les arbitres");
			throw new RuntimeException(e);
		}
	}

	/**
	 * Vérifie l'unicité d'un identifiant
	 * @param identifiant Identifiant à vérifier
	 * @throws IllegalArgumentException Si l'identifiant n'est pas unique
	 */
	private void verifierUniciteIdentifiant(String identifiant) throws IllegalArgumentException {
		// Vérification si l'identifiant est déjà utilisé par un arbitre ou un administrateur
		try {
			if (
				this.modeleTournoi.getParIdentifiant(identifiant).isPresent()
				|| this.modeleAdministrateur.getParIdentifiant(identifiant).isPresent()
			) {
				this.vueSaisieTournoi.afficherPopupErreur("Cet identifiant est déjà utilisé par un autre utilisateur.");
				throw new IllegalArgumentException("Cet identifiant est déjà utilisé par un autre utilisateur.");
			}
		} catch (SQLException ex) {
			this.vueSaisieTournoi.afficherPopupErreur("Une erreur est survenue lors de la vérification de l'identifiant.");
			throw new RuntimeException("Une erreur est survenue lors de la vérification de l'identifiant.", ex);
		}
	}

}
