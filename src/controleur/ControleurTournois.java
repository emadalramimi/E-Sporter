package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

import modele.ModeleTournoi;
import modele.metier.Tournoi;
import vue.VueTournois;
import vue.theme.JButtonTable;

public class ControleurTournois extends KeyAdapter implements ActionListener {

	private VueTournois vue;
	private ModeleTournoi modeleTournoi;
	
	public ControleurTournois(VueTournois vue) {
		this.vue = vue;
		this.modeleTournoi = new ModeleTournoi();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Clic sur un bouton du tableau (voir, modifier, supprimer)
		if(e.getSource() instanceof JButtonTable) {
			JButtonTable bouton = (JButtonTable) e.getSource();
			
			// Récupération de l'ID de l'équipe sélectionné
			int idTournoi = bouton.getIdElement();
			Optional<Tournoi> tournoiOptionnel;
			try {
				tournoiOptionnel = this.modeleTournoi.getParId(idTournoi);
			} catch(Exception err) {
				this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
				throw new RuntimeException("Equipe inexistante");
			}
			
			// Si équipe n'est pas trouvée
			if (tournoiOptionnel.orElse(null) == null) {
				this.vue.afficherPopupErreur("Une erreur est survenue : tournoi inexistant.");
				throw new RuntimeException("Tournoi est inexistant");
			}

			Tournoi tournoi = tournoiOptionnel.get();

			// Traitement différent en fonction du bouton
			switch (bouton.getType()) {
				case VOIR:
					if(System.currentTimeMillis() / 1000 < tournoi.getDateFin() && tournoi.getEstCloture() == true) {
						// Le tournoi n'a pas encore commencé : affichage de la vue d'inscription des équipes
						this.vue.afficherVueInscriptionEquipes(tournoi);
					} else if(tournoi.getEstCloture() == true) {
						// Afficher msg tournoi cloturé
						System.out.println("Afficher msg tournoi cloture");
					} else {
						// Afficher la vue matchs
						System.out.println("afficher vue matchs");
					}
					break;
				case MODIFIER:
					break;
				case SUPPRIMER:
					break;
			}
		} else if(e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			// Si il s'agit du bouton ajouter
			if(bouton.getText() == "Ajouter") {
				// Ouverture de la fenêtre d'ajout d'équipe
				this.vue.afficherVueSaisieTournoi(Optional.empty());
			}
			// Si il s'agit du bouton de recherche
	//			else if(this.vue.estBoutonRecherche(bouton)) {
	//				String requeteRecherche = this.vue.getRequeteRecherche();
	//				if(requeteRecherche != null) {
	//					this.rechercher(this.vue.getRequeteRecherche());
	//				}
	//			}
		}
	}

	public List<Tournoi> getTournois(){
		try {
			return this.modeleTournoi.getTout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
