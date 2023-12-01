package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.metier.Equipe;
import modele.metier.Joueur;
import vue.VueEquipes;
import vue.VueSaisieEquipe;

public class ControleurSaisieEquipe implements ActionListener {

	private VueSaisieEquipe vueSaisieEquipe;
	private VueEquipes vueEquipes;
	private ControleurEquipes controleurEquipes;
	private ModeleEquipe modeleEquipe;
	private ModeleJoueur modeleJoueur;
	private Optional<Equipe> equipeOptionnel;
	
	public ControleurSaisieEquipe(VueSaisieEquipe vue, VueEquipes vueEquipes, ControleurEquipes controleurEquipes, Optional<Equipe> equipeOptionnel) {
		this.vueSaisieEquipe = vue;
		this.vueEquipes = vueEquipes;
		this.controleurEquipes = controleurEquipes;
		this.equipeOptionnel = equipeOptionnel;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleJoueur = new ModeleJoueur();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		if(bouton.getText() == "Valider" || bouton.getText() == "Modifier") {
			String nom = vueSaisieEquipe.getNomEquipe();
			String pays = vueSaisieEquipe.getPaysEquipe();
			
			List<String> nomsJoueurs = vueSaisieEquipe.getNomJoueurs();
			
			if(nom.isEmpty() || pays.isEmpty() || nomsJoueurs.size() != 5) {
				vueSaisieEquipe.afficherPopupErreur("Veuillez remplir tous les champs.");
				throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
			}
			
			int worldRanking = 1000;
			try {
				if(vueSaisieEquipe.getWorldRanking() != null) {
					worldRanking = vueSaisieEquipe.getWorldRanking();
				}
			} catch(NumberFormatException err) {
				this.vueSaisieEquipe.afficherPopupErreur("Le World Ranking doit être un entier.");
				throw err;
			}
			
			if(bouton.getText() == "Valider") {
				int idEquipe = this.modeleEquipe.getNextValId();
				
				Equipe equipe = new Equipe(idEquipe, nom, pays, 1000, worldRanking, String.valueOf(LocalDate.now().getYear()));
				modeleEquipe.ajouter(equipe);
				
				for(String nomJoueur: nomsJoueurs) {
					int idJoueur = this.modeleJoueur.getNextValId();
					Joueur joueur = new Joueur(idJoueur, nomJoueur, idEquipe);
					this.modeleJoueur.ajouter(joueur);
				}
				
				this.vueSaisieEquipe.afficherPopupMessage("L'équipe a bien été ajoutée.");
				this.vueEquipes.remplirTableau(this.controleurEquipes.getEquipes());
			} else if(bouton.getText() == "Modifier") {
				Equipe equipe = this.equipeOptionnel.orElse(null);
				
				if(equipe == null) {
					this.vueSaisieEquipe.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
					throw new RuntimeException("Equipe inexistante");
				}
				
				equipe.setNom(nom);
				equipe.setPays(pays);
				equipe.setWorldRanking(worldRanking);
				
				List<Joueur> joueursEquipe = equipe.getJoueurs();
				List<Joueur> joueursSaisis = new ArrayList<>();
				for(String nomJoueur: nomsJoueurs) {
					joueursSaisis.add(new Joueur(nomJoueur));
				}
				for(int i = 0; i < joueursEquipe.size(); i++) {
					if(joueursEquipe.get(i) != joueursSaisis.get(i)) {
						joueursEquipe.get(i).setPseudo(joueursSaisis.get(i).getPseudo());
						try {
							this.modeleJoueur.modifier(joueursEquipe.get(i));
						} catch (Exception err) {
							throw new RuntimeException("Erreur dans la modification des joueurs");
						}
					}
				}
				
				// TODO faire un commit ici
				
				if(this.modeleEquipe.modifier(equipe)) {
					this.vueSaisieEquipe.afficherPopupMessage("L'équipe a bien été modifiée.");
					this.vueEquipes.remplirTableau(this.controleurEquipes.getEquipes());
				} else {
					this.vueSaisieEquipe.afficherPopupErreur("Une erreur est survenue.");
				}
				
				// TODO mettre à jour le tableau
			}
			
			this.vueSaisieEquipe.fermerFenetre();
		} else if(bouton.getText() == "Annuler") {
			this.vueSaisieEquipe.fermerFenetre();
		}
	}

}
