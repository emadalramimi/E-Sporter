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

public class ControleurTournois extends KeyAdapter implements ActionListener {

	private VueTournois vue;
	private ModeleTournoi modeleTournoi;
	
	public ControleurTournois(VueTournois vue) {
		this.vue = vue;
		this.modeleTournoi = new ModeleTournoi();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// faire traitement btns tableau ici
		if (e.getSource() instanceof JButton) {
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
