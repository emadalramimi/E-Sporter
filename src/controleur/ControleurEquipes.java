package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JTextField;

import modele.ModeleEquipe;
import modele.ModeleJoueur;
import modele.metier.Equipe;
import vue.VueEquipes;
import vue.theme.JButtonTable;

public class ControleurEquipes extends KeyAdapter implements ActionListener {

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
				this.vue.afficherVueSaisieEquipe(equipeOptionnel);
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
						this.vue.remplirTableau(this.getEquipes());
					} else {
						this.vue.afficherPopupErreur("Une erreur est survenue");
					}
				}
				break;
			}
		} else if(e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();
			
			if(bouton.getText() == "Ajouter") {
				this.vue.afficherVueSaisieEquipe(Optional.empty());
			} else if(this.vue.estBoutonRecherche(bouton)) {
				String requeteRecherche = this.vue.getRequeteRecherche();
				if(requeteRecherche != null) {
					this.rechercher(this.vue.getRequeteRecherche());
				}
			}
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		JTextField txtRecherche = (JTextField) e.getSource();
		String requeteRecherche = this.vue.getRequeteRecherche();
		if (this.vue.estChampRecherche(txtRecherche)) {
	        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	            this.rechercher(requeteRecherche);
	        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
	            if (
	            	requeteRecherche != null
	            	&& requeteRecherche.length() == 1
	            	|| txtRecherche.getSelectedText() != null
	            	&& txtRecherche.getSelectedText().equals(requeteRecherche)
	            ) {
	                this.rechercher("");
	            }
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
	
	private void rechercher(String requeteRecherche) {
		try {
			this.vue.remplirTableau(this.modeleEquipe.getParNom(requeteRecherche));
		} catch (Exception e1) {
			this.vue.afficherPopupErreur("Une erreur est survenue");
			throw new RuntimeException("Erreur dans la recherche");
		}
	}
	
}
