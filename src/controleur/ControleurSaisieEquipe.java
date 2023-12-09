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

/**
 * Controleur de VueSaisieEquipe
 */
public class ControleurSaisieEquipe implements ActionListener {

	private VueSaisieEquipe vueSaisieEquipe;
	private VueEquipes vueEquipes;
	private ControleurEquipes controleurEquipes;
	private ModeleEquipe modeleEquipe;
	private ModeleJoueur modeleJoueur;
	private Optional<Equipe> equipeOptionnel;
	
	/**
	 * Construit un controleur pour VueSaisieEquipe
	 * @param vueSaisieEquipes : VueSaisieEquipe
	 * @param vueEquipes : VueEquipes (pour mettre à jour le tableau à l'insertion d'une nouvelle donnée)
	 * @param controleurEquipes : Pour mettre à jour le tableau à l'insertion d'une nouvelle donnée
	 * @param equipeOptionnel : À saisir si modification d'équipe
	 */
	public ControleurSaisieEquipe(VueSaisieEquipe vueSaisieEquipes, VueEquipes vueEquipes, ControleurEquipes controleurEquipes, Optional<Equipe> equipeOptionnel) {
		this.vueSaisieEquipe = vueSaisieEquipes;
		this.vueEquipes = vueEquipes;
		this.controleurEquipes = controleurEquipes;
		this.equipeOptionnel = equipeOptionnel;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleJoueur = new ModeleJoueur();
	}

	/**
	 * Au clic du bouton valider ou modifier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		// Si le bouton est bien le bouton valider ou modifier
		if(bouton.getText() == "Valider" || bouton.getText() == "Modifier") {
			// Récupération du texte des champs
			String nom = vueSaisieEquipe.getNomEquipe();
			String pays = vueSaisieEquipe.getPaysEquipe();
			
			List<String> nomsJoueurs = vueSaisieEquipe.getNomJoueurs();
			
			// Si un des champs est vide
			if(nom.isEmpty() || pays.isEmpty() || nomsJoueurs.size() != 5) {
				vueSaisieEquipe.afficherPopupErreur("Veuillez remplir tous les champs.");
				throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
			}
			
			// Récupération du worldRanking (1000 si non renseigné)
			int worldRanking = 1000;
			try {
				if(vueSaisieEquipe.getWorldRanking() != null) {
					worldRanking = vueSaisieEquipe.getWorldRanking();
				}
			} catch(NumberFormatException err) {
				this.vueSaisieEquipe.afficherPopupErreur("Le World Ranking doit être un entier.");
				throw err;
			}
			
			// Création de l'équipe au clic de valider
			if(bouton.getText() == "Valider") {
				// Génération d'un identifiant unique pour la BDD
				int idEquipe = this.modeleEquipe.getNextValId();
				
				Equipe equipe = new Equipe(idEquipe, nom, pays, 1000, worldRanking, String.valueOf(LocalDate.now().getYear()));
				
				// Ajout de l'équipe dans la base de données
				modeleEquipe.ajouter(equipe);
				
				// Créer des instances de Joueur pour chaque pseudo de joueur renseigné
				for(String nomJoueur: nomsJoueurs) {
					// Génération de l'identifiant unique de joueur
					int idJoueur = this.modeleJoueur.getNextValId();
					
					Joueur joueur = new Joueur(idJoueur, nomJoueur, idEquipe);
					
					// Ajout du joueur dans la BDD
					this.modeleJoueur.ajouter(joueur);
				}
				
				// Message de confirmation et mise à jour du tableau
				this.vueSaisieEquipe.afficherPopupMessage("L'équipe a bien été ajoutée.");
				this.vueEquipes.remplirTableau(this.controleurEquipes.getEquipes());
			} 
			// Modification de l'équipe au clic de modifier
			else if(bouton.getText() == "Modifier") {
				// Récupérer l'équipe à modifier
				Equipe equipe = this.equipeOptionnel.orElse(null);
				
				// Si il y a un problème de récupération d'équipe
				if(equipe == null) {
					this.vueSaisieEquipe.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
					throw new RuntimeException("Equipe inexistante");
				}
				
				// Modification des champs
				equipe.setNom(nom);
				equipe.setPays(pays);
				equipe.setWorldRanking(worldRanking);
				
				// Récupération des joueurs saisis et détection d'un changement pour mettre à jour le Joueur concerné
				List<Joueur> joueursEquipe = equipe.getJoueurs();
				List<Joueur> joueursSaisis = new ArrayList<>();
				for(String nomJoueur: nomsJoueurs) {
					joueursSaisis.add(new Joueur(nomJoueur));
				}
				for(int i = 0; i < joueursEquipe.size(); i++) {
					if(joueursEquipe.get(i) != joueursSaisis.get(i)) {
						// Modification du pseudo du joueur
						joueursEquipe.get(i).setPseudo(joueursSaisis.get(i).getPseudo());
						try {
							this.modeleJoueur.modifier(joueursEquipe.get(i));
						} catch (Exception err) {
							throw new RuntimeException("Erreur dans la modification des joueurs");
						}
					}
				}
				
				// Modification de l'équipe et affichage d'un message de succès/erreur
				if(this.modeleEquipe.modifier(equipe)) {
					this.vueSaisieEquipe.afficherPopupMessage("L'équipe a bien été modifiée.");
					this.vueEquipes.remplirTableau(this.controleurEquipes.getEquipes());
				} else {
					this.vueSaisieEquipe.afficherPopupErreur("Une erreur est survenue.");
				}
			}
			
			this.vueSaisieEquipe.fermerFenetre();
		} else if(bouton.getText() == "Annuler") {
			this.vueSaisieEquipe.fermerFenetre();
		}
	}

}
