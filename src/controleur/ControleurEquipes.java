package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.metier.Equipe;
import vue.VueEquipes;
import vue.theme.JButtonTable;

public class ControleurEquipes implements ActionListener {

	private VueEquipes vue;
	private ModeleEquipe modeleEquipe;
	private ModeleJoueur modeleJoueur;
	
	public ControleurEquipes(VueEquipes vue) {
		this.vue = vue;
		this.modeleEquipe = new ModeleEquipe();
		this.modeleJoueur = new ModeleJoueur();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButtonTable) {
			JButtonTable bouton = (JButtonTable) e.getSource();
			
			int idEquipe = bouton.getIdElement();
			Optional<Equipe> equipeOptionnel;
			try {
				equipeOptionnel = this.modeleEquipe.getParId(idEquipe);
			} catch(Exception err) {
				equipeOptionnel = Optional.empty();
			}
			
			switch(bouton.getType()) {
			case VOIR:
				this.vue.afficherVueJoueurs(this.modeleJoueur.getListeJoueursParId(idEquipe));
				break;
			case MODIFIER:
				this.vue.afficherFenetreSaisieEquipe(equipeOptionnel);
				break;
			case SUPPRIMER:
				Equipe equipe = equipeOptionnel.orElse(null);
				if(equipe == null) {
					this.vue.afficherPopupErreur("Une erreur est survenue : équipe inexistante.");
					throw new RuntimeException("Equipe est inexistant");
				}
				if(this.vue.afficherConfirmationSuppression()) {
					if(this.modeleEquipe.supprimer(equipe)) {
						this.vue.afficherPopupMessage("L'équipe a bien été supprimée");
					} else {
						this.vue.afficherPopupErreur("Une erreur est survenue");
					}
				}
				break;
			}
		} else {
			JButton bouton = (JButton) e.getSource();
			
			if(bouton.getText() == "Ajouter") {
				this.vue.afficherFenetreSaisieEquipe(Optional.empty());
			}
		}
		
	}
	
	public List<Equipe> getEquipes(){
		try {
			return this.modeleEquipe.getTout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
