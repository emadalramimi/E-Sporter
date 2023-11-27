package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
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
			
			if(nom == null || pays == null || nomsJoueurs.size() != 5) {
				vue.afficherPopupErreur("Veuillez remplir tous les champs.");
			} else {
				int idEquipe = modeleEquipe.getNextValId();
				
				List<Joueur> joueurs = new ArrayList<>();
				
				for(String nomJoueur: nomsJoueurs) {
					joueurs.add(new Joueur(this.modeleJoueur.getNextValId(), nomJoueur, idEquipe));
				}
				
				Equipe equipe = new Equipe(idEquipe, nom, pays, 1000, 1000, String.valueOf(LocalDate.now().getYear()), joueurs);
				
				this.modeleEquipe.ajouter(equipe);
				for(Joueur joueur : joueurs) {
					this.modeleJoueur.ajouter(joueur);
				}
				
				System.out.println("ok");
			}
		} else if(bouton.getText() == "Annuler") {
			vue.fermerFenetre();
		}
	}

}
