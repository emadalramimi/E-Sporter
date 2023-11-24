package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vue.VueEquipes;

public class ControleurEquipe implements ActionListener {

	private VueEquipes vue;
	
	public ControleurEquipe(VueEquipes vue) {
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		if(bouton.getText() == "Ajouter") {
			this.vue.afficherFenetreAjoutEquipe();
		}
	}
	
}
