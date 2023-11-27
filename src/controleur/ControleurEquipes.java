package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import modele.ModeleEquipe;
import modele.metier.Equipe;
import vue.VueEquipes;
import vue.theme.JButtonTableau;

public class ControleurEquipes implements ActionListener {

	private VueEquipes vue;
	private ModeleEquipe modele;
	
	public ControleurEquipes(VueEquipes vue) {
		this.vue = vue;
		this.modele = new ModeleEquipe();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("lol2");
		if(e.getSource() instanceof JButtonTableau) {
			System.out.println("lol");
			JButtonTableau bouton = (JButtonTableau) e.getSource();
			
			switch(bouton.getType()) {
			case VOIR:
				System.out.println("voir");
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
			return this.modele.getTout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
