package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleArbitre;
import modele.ModeleEquipe;
import modele.ModeleTournoi;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import vue.VueSaisieTournoi;
import vue.VueTournois;

public class ControleurSaisieTournoi implements ActionListener, ListSelectionListener {

	private VueSaisieTournoi vueSaisieTournoi;
	private ModeleEquipe modeleEquipe;
	private ModeleArbitre modeleArbitre;
	private ModeleTournoi modeleTournoi;
	private VueTournois vueTournois;
//	private ControleurTournois controleurTournois;
//	private Optional<Tournoi> tournoiOptionnel;
	
	public ControleurSaisieTournoi(VueSaisieTournoi vueSaisieTournoi, VueTournois vueTournois) {
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleArbitre = new ModeleArbitre();
		this.modeleTournoi = new ModeleTournoi();
		this.vueTournois = vueTournois;
//		this.controleurTournois = controleurTournois;
//		this.tournoiOptionnel = tournoiOptionnel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
		case "Ajouter une équipe":
			try {
				this.vueSaisieTournoi.afficherVueSaisieTournoiEquipe(modeleEquipe.getTableauEquipes());
			} catch (Exception err) {
				this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les équipes");
				err.printStackTrace();
			}
			break;
		case "Ajouter un arbitre":
			try {
				this.vueSaisieTournoi.afficherVueSaisieTournoiArbitre(modeleArbitre.getTableauArbitres());
			} catch (Exception err) {
				this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les arbitres");
				err.printStackTrace();
			}
			break;
		case "Annuler":
			this.vueSaisieTournoi.fermerFenetre();
			break;
		}
		
		if(bouton.getText() == "Valider" || bouton.getText() == "Modifier") {
			if (!this.vueSaisieTournoi.tousChampsRemplis()) {
				this.vueSaisieTournoi.afficherPopupErreur("Veuillez remplir tous les champs.");
				throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
			}
			if (this.vueSaisieTournoi.getEquipes().size() < 4 || this.vueSaisieTournoi.getEquipes().size() > 8) {
				this.vueSaisieTournoi.afficherPopupErreur("Le nombre d'équipes inscrites doit être de 4 à 8 équipes.");
				throw new IllegalArgumentException("Le nombre d'équipes inscrites doit être de 4 à 8 équipes..");
			}
			if (this.vueSaisieTournoi.getArbitres().size() == 0) {
				this.vueSaisieTournoi.afficherPopupErreur("Le nombre d'arbitres doit être supérieur à 0.");
				throw new IllegalArgumentException("Le nombre d'arbitres doit être supérieur à 0.");
			}
			if(this.vueSaisieTournoi.getDateTimeDebut() >= this.vueSaisieTournoi.getDateTimeFin()) {
				this.vueSaisieTournoi.afficherPopupErreur("La date et l'heure de début doit être antérieure à la date et l'heure de fin.");
				throw new IllegalArgumentException("La date et l'heure de début doit être antérieure à la date et l'heure de fin.");
			}
			
			String nomTournoi = this.vueSaisieTournoi.getNomTournoi();
			String identifiant = this.vueSaisieTournoi.getIdentifiant();
			String motDePasse = this.vueSaisieTournoi.getMotDePasse();
			Notoriete notoriete = this.vueSaisieTournoi.getNotoriete();
			long dateTimeDebut = this.vueSaisieTournoi.getDateTimeDebut();
			long dateTimeFin = this.vueSaisieTournoi.getDateTimeFin();
			List<Equipe> equipes = this.vueSaisieTournoi.getEquipes();
			List<Arbitre> arbitres = this.vueSaisieTournoi.getArbitres();
			
			if(bouton.getText() == "Valider") {
				Tournoi tournoi = new Tournoi(nomTournoi, notoriete, dateTimeDebut, dateTimeFin, false, identifiant, motDePasse, equipes, arbitres);
				
				try {
					modeleTournoi.ajouter(tournoi);
				} catch (Exception err) {
					this.vueSaisieTournoi.afficherPopupErreur("Impossible d'ajouter le tournoi");
					throw new RuntimeException("Impossible d'ajouter le tournoi", err);
				}

				this.vueSaisieTournoi.afficherPopupMessage("Le tournoi a bien été ajouté.");
				try {
					this.vueTournois.remplirTableau(this.modeleTournoi.getTout());
				} catch (Exception err) {
					this.vueSaisieTournoi.afficherPopupErreur("Impossible de récupérer les tournois");
					throw new RuntimeException("Impossible de récupérer les tournois", err);
				}
				this.vueSaisieTournoi.fermerFenetre();
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
	    if (!e.getValueIsAdjusting()) { // Pour éviter les événements redondants
	        JList<?> liste = (JList<?>) e.getSource();

	        if (this.vueSaisieTournoi.estListeEquipes(liste)) {
	            if (this.vueSaisieTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désinscrire cette équipe ?")) {
	                this.vueSaisieTournoi.supprimerEquipe((Equipe) liste.getSelectedValue());
	            }
	        } else if (this.vueSaisieTournoi.estListeArbitres(liste)) {
	            if (this.vueSaisieTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désassigner cet arbitre ?")) {
	                this.vueSaisieTournoi.supprimerArbitre((Arbitre) liste.getSelectedValue());
	            }
	        }
	    }
	}

}
