package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.metier.Equipe;
import modele.metier.Joueur;
import vue.VueAjoutEquipe;

public class ControleurAjoutEquipe implements ActionListener {

	private VueAjoutEquipe vue;
	private ModeleEquipe modeleEquipe;
	private ModeleJoueur modeleJoueur;
	
	public ControleurAjoutEquipe(VueAjoutEquipe vue) {
		this.vue = vue;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleJoueur = new ModeleJoueur();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		if(bouton.getText() == "Valider") {
			String nom = vue.getNomEquipe();
			String pays = vue.getPaysEquipe();
			
			List<String> nomsJoueurs = vue.getNomJoueurs();
			
			if(nom.isEmpty() || pays.isEmpty() || nomsJoueurs.size() != 5) {
				vue.afficherPopupErreur("Veuillez remplir tous les champs.");
				throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
			}
			
			int worldRanking = 1000;
			try {
				if(vue.getWorldRanking() != null) {
					worldRanking = vue.getWorldRanking();
				}
			} catch(NumberFormatException err) {
				this.vue.afficherPopupErreur("Le World Ranking doit être un entier.");
				throw err;
			}
			
			int idEquipe = this.modeleEquipe.getNextValId();
			
			Equipe equipe = new Equipe(idEquipe, nom, pays, 1000, worldRanking, String.valueOf(LocalDate.now().getYear()));
			modeleEquipe.ajouter(equipe);
			
			for(String nomJoueur: nomsJoueurs) {
				int idJoueur = this.modeleJoueur.getNextValId();
				Joueur joueur = new Joueur(idJoueur, nomJoueur, idEquipe);
				this.modeleJoueur.ajouter(joueur);
			}
			
			this.vue.afficherPopupMessage("L'équipe a bien été ajoutée.");
			this.vue.viderChamps();
		} else if(bouton.getText() == "Annuler") {
			vue.fermerFenetre();
		}
	}

}
