package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
			switch(bouton.getType()) {
			case VOIR:
				this.vue.afficherVueJoueurs(this.modeleJoueur.getListeJoueursParId(idEquipe));
				break;
			case MODIFIER:
				System.out.println("modifier");
				break;
			case SUPPRIMER:
				System.out.println("supprimer");
				break;
			}
		} else {
			JButton bouton = (JButton) e.getSource();
			
			if(bouton.getText() == "Ajouter") {
				this.vue.afficherFenetreAjoutEquipe();
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
