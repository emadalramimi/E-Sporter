package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import modele.ModeleEquipes;
import modele.metier.Equipe;
import vue.VueEquipes;

public class ControleurEquipes implements ActionListener {

	private VueEquipes vue;
	private ModeleEquipes modele;
	
	public ControleurEquipes(VueEquipes vue) {
		this.vue = vue;
		this.modele = new ModeleEquipes();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton bouton = (JButton) e.getSource();
		
		if(bouton.getText() == "Ajouter") {
			this.vue.afficherFenetreAjoutEquipe();
		}
	}
	
	public List<Equipe> getEquipes(){
		try {
			return this.modele.getTout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
