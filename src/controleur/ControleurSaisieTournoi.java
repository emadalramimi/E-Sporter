package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleArbitre;
import modele.ModeleTournoi;
import modele.metier.Arbitre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import vue.VueSaisieTournoi;
import vue.VueTournois;

public class ControleurSaisieTournoi implements ActionListener, ListSelectionListener {

	private VueSaisieTournoi vueSaisieTournoi;
	private ModeleArbitre modeleArbitre;
	private ModeleTournoi modeleTournoi;
	private VueTournois vueTournois;
	
	public ControleurSaisieTournoi(VueSaisieTournoi vueSaisieTournoi, VueTournois vueTournois) {
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.modeleArbitre = new ModeleArbitre();
		this.modeleTournoi = new ModeleTournoi();
		this.vueTournois = vueTournois;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		switch(bouton.getText()) {
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
			if (this.vueSaisieTournoi.getArbitres().size() == 0) {
				this.vueSaisieTournoi.afficherPopupErreur("Le nombre d'arbitres doit être supérieur à 0.");
				throw new IllegalArgumentException("Le nombre d'arbitres doit être supérieur à 0.");
			}
			if(this.vueSaisieTournoi.getDateTimeDebut() < System.currentTimeMillis() / 1000) {
				this.vueSaisieTournoi.afficherPopupErreur("La date et l'heure de début doit être supérieure à la date et l'heure actuelle.");
				throw new IllegalArgumentException("La date et l'heure de début doit être supérieure à la date et l'heure actuelle.");
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
			List<Arbitre> arbitres = this.vueSaisieTournoi.getArbitres();
			
			if(bouton.getText() == "Valider") {
				Tournoi tournoi = new Tournoi(nomTournoi, notoriete, dateTimeDebut, dateTimeFin, identifiant, motDePasse, arbitres);
				
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

	        if (this.vueSaisieTournoi.estListeArbitres(liste)) {
	            if (this.vueSaisieTournoi.afficherConfirmationSuppression("Êtes-vous sûr de vouloir désassigner cet arbitre ?")) {
	                this.vueSaisieTournoi.supprimerArbitre((Arbitre) liste.getSelectedValue());
	            }
	        }
	    }
	}

}
