package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleTournoiOuverture;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.DatesTournoiException;
import modele.exception.OuvertureTournoiException;
import modele.exception.TournoiDejaOuvertException;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.VueInscriptionEquipesTournoi;
import vue.VueTournois;

/**
 * Contrôleur de la vue d'inscription des équipes à un tournoi
 * @see VueInscriptionEquipesTournoi
 */
public class ControleurInscriptionEquipesTournoi implements ActionListener, ListSelectionListener {

	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
	private VueTournois vueTournois;
	private Tournoi tournoi;
	private DAOEquipe daoEquipe;
	private DAOTournoi daoTournoi;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	
	/**
	 * Constructeur du contrôleur de la vue d'inscription des équipes à un tournoi
	 * @param vueInscriptionEquipesTournoi Vue d'inscription des équipes à un tournoi
	 * @param vueTournois Vue des tournois
	 * @param tournoi Tournoi
	 */
	public ControleurInscriptionEquipesTournoi(VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, VueTournois vueTournois, Tournoi tournoi) {
		this.vueInscriptionEquipesTournoi = vueInscriptionEquipesTournoi;
		this.vueTournois = vueTournois;
		this.tournoi = tournoi;
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();
	}
	
	/**
	 * Effectue un traitement au clic d'un élément de la fenêtre
	 * Quand on clique sur le bouton "Inscrire une équipe" : on affiche la vue de saisie d'une équipe
	 * Quand on clique sur le bouton "Ouvrir le tournoi" : on ouvre le tournoi
	 * Quand on clique sur le bouton "Fermer" : on ferme la fenêtre
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();

		// Récupération du nombre d'équipes inscrites
		int nbEquipes = this.vueInscriptionEquipesTournoi.getEquipes().size();

		// Traitement différent en fonction du bouton
		switch (bouton.getText()) {
			case "Inscrire une équipe":
				// On récupère les équipes éligibles (non inscrites au tournoi)
				Equipe[] equipes;
				try {
					equipes = this.getEquipesEligibles();
				} catch (Exception ex) {
					this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
					throw new RuntimeException(ex);
				}

				// On vérifie qu'il reste des équipes éligibles et qu'on n'a pas atteint le nombre maximal d'équipes inscrites
				if (equipes.length == 0) {
					this.vueInscriptionEquipesTournoi.afficherPopupErreur("Plus aucune équipe disponible");
					throw new RuntimeException("Plus aucune équipe disponible");
				}
				if (nbEquipes >= 8) {
					this.vueInscriptionEquipesTournoi.afficherPopupErreur("Le nombre maximal d'équipes inscrites a été atteint");
					throw new RuntimeException("Le nombre maximal d'équipes inscrites a été atteint");
				}

				// On affiche la vue de saisie d'une équipe
				try {
					this.vueInscriptionEquipesTournoi.afficherVueSaisieTournoiEquipe(equipes);
				} catch (Exception err) {
					this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
					err.printStackTrace();
				}
				break;
			case "Ouvrir le tournoi":
				// On ouvre le tournoi
				try {
					this.modeleTournoiOuverture.ouvrirTournoi(this.tournoi);
					this.vueTournois.afficherPopupMessage("Le tournoi a été ouvert avec succès");
					// On met à jour le tableau des tournois
					this.vueTournois.remplirTableau(this.daoTournoi.getTout());
					this.vueInscriptionEquipesTournoi.fermerFenetre();
				} catch (OuvertureTournoiException | DatesTournoiException | TournoiDejaOuvertException | IllegalArgumentException ex) {
					// Récupération des erreurs IllegalArgumentException et affichage de leur message
					this.vueInscriptionEquipesTournoi.afficherPopupErreur(ex.getMessage());
					ex.printStackTrace();
				} catch (Exception ex) {
					// Pour le reste, on affiche un message générique
					this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible d'ouvrir le tournoi");
					ex.printStackTrace();
				}
				break;
			case "Fermer":
				this.vueInscriptionEquipesTournoi.fermerFenetre();
				break;

		}
	}
	
	/**
	 * Effectue un traitement lors de la sélection d'un élément de la fenêtre
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// Pour éviter les événements redondants
		if (!e.getValueIsAdjusting() && e.getSource() instanceof JList) {
			JList<?> liste = (JList<?>) e.getSource();

			// Si la liste est celle des équipes inscrites
			if (this.vueInscriptionEquipesTournoi.estListeEquipes(liste)) {
				// On vérifie que l'équipe sélectionnée n'est pas null et on affiche une demande de confirmation de désinscription
				if (liste.getSelectedValue() != null && this.vueInscriptionEquipesTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désinscrire cette équipe ?")) {
					try {
						// On désinscrit l'équipe, on la supprime de la liste des équipes inscrites et on la supprime du tournoi
						Equipe equipe = (Equipe) liste.getSelectedValue();
						this.daoEquipe.desinscrireEquipe(equipe, this.tournoi);
						this.tournoi.removeEquipe(equipe);
						this.vueInscriptionEquipesTournoi.supprimerEquipe(equipe);
						liste.clearSelection();

						// On réactive le bouton d'inscription d'équipe car le nombre maximal d'équipes inscrites n'est pas atteint
						this.vueInscriptionEquipesTournoi.setBtnInscrireEquipeActif(true);
					} catch (Exception err) {
						this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de désinscrire l'équipe");
						err.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * Retourne un tableau des équipes éligibles (non inscrites au tournoi)
	 * @return Tableau des équipes éligibles
	 */
	public Equipe[] getEquipesEligibles() {
		try {
			return this.daoEquipe.getTableauEquipes(this.vueInscriptionEquipesTournoi.getEquipes());
		} catch (Exception e) {
			this.vueInscriptionEquipesTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
			throw new RuntimeException(e);
		}
	}

}
