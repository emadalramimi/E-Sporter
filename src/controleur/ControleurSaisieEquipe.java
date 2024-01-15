package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import vue.VueEquipes;
import vue.VueSaisieEquipe;

/**
 * Contrôleur de la vue de saisie d'une équipe
 * @see VueSaisieEquipe
 */
public class ControleurSaisieEquipe implements ActionListener {

	private VueSaisieEquipe vueSaisieEquipe;
	private VueEquipes vueEquipes;
	private DAOEquipe daoEquipe;
	private Optional<Equipe> equipeOptionnel;
	
	/**
	 * Construit un controleur pour VueSaisieEquipe
	 * @param vueSaisieEquipes : VueSaisieEquipe
	 * @param vueEquipes : VueEquipes (pour mettre à jour le tableau à l'insertion d'une nouvelle donnée)
	 * @param equipeOptionnel : À saisir si modification d'équipe
	 */
	public ControleurSaisieEquipe(VueSaisieEquipe vueSaisieEquipes, VueEquipes vueEquipes, Optional<Equipe> equipeOptionnel) {
		this.vueSaisieEquipe = vueSaisieEquipes;
		this.vueEquipes = vueEquipes;
		this.equipeOptionnel = equipeOptionnel;
		this.daoEquipe = new DAOEquipeImpl();
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
				this.vueSaisieEquipe.afficherPopupErreur("Veuillez remplir tous les champs.");
				throw new IllegalArgumentException("Tous les champs ne sont pas remplis.");
			}
			
			// Création de l'équipe au clic de valider
			if(bouton.getText() == "Valider") {
				
				// Créer des instances de Joueur pour chaque pseudo de joueur renseigné
				List<Joueur> joueurs = new ArrayList<>();
				for (String nomJoueur : nomsJoueurs) {
					joueurs.add(new Joueur(nomJoueur));
				}
				Equipe equipe = new Equipe(nom, Pays.valueOfNom(pays), joueurs);
				
				// Ajout de l'équipe dans la base de données
				try {
					daoEquipe.ajouter(equipe);
				} catch (Exception ex) {
					this.vueSaisieEquipe.afficherPopupErreur("Une erreur est survenue lors de l'ajout de l'équipe.");
					throw new RuntimeException("Erreur dans l'ajout de l'équipe", ex);
				}
				
				// Message de confirmation et mise à jour du tableau
				this.vueSaisieEquipe.afficherPopupMessage("L'équipe a bien été ajoutée.");
				try {
					this.vueEquipes.remplirTableau(this.daoEquipe.getEquipesSaison());
				} catch (Exception err) {
					this.vueSaisieEquipe.afficherPopupErreur("Impossible de récupérer les équipes");
					throw new RuntimeException("Impossible de récupérer les équipes", err);
				}
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
				equipe.setPays(Pays.valueOfNom(pays));
				
				// Récupération des joueurs saisis et mise à jour des joueurs de l'équipe
				List<Joueur> joueursEquipe = equipe.getJoueurs();
				for(int i = 0; i < joueursEquipe.size(); i++) {
					joueursEquipe.get(i).setPseudo(nomsJoueurs.get(i));
				}
				
				// Modification de l'équipe et affichage d'un message de succès/erreur
				try {
					this.daoEquipe.modifier(equipe);
				} catch (Exception err) {
					this.vueSaisieEquipe.afficherPopupErreur("Une erreur est survenue lors de la modification de l'équipe.");
					throw new RuntimeException("Erreur dans la modification de l'équipe", err);
				}
				
				this.vueSaisieEquipe.afficherPopupMessage("L'équipe a bien été modifiée.");
				
				// Mise à jour du tableau
				try {
					this.vueEquipes.remplirTableau(this.daoEquipe.getEquipesSaison());
				} catch (Exception err) {
					this.vueSaisieEquipe.afficherPopupErreur("Impossible de récupérer les équipes");
					throw new RuntimeException("Impossible de récupérer les équipes", err);
				}
			}
			
			// TODO SI FILTRE/RECHERCHE réappliquer ici
			this.vueEquipes.resetChampRecherche();

			this.vueSaisieEquipe.fermerFenetre();
		} else if(bouton.getText() == "Annuler") {
			this.vueSaisieEquipe.fermerFenetre();
		}
	}

}
