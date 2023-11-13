package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import vue.VueMenu;
import vue.theme.JButtonMenu;

public class ControleurMenu extends MouseAdapter {

	public enum Menus {
		TOURNOIS(true), EQUIPES(true), HISTORIQUE(true), PALMARES(true), UTILISATEUR(false), DECONNEXION(false);
		
		private boolean estActivable;
		
		/**
		 * Un menu peut être activable (entraînera une nouvelle fenêtre au clic et passera en activé) ou non (exemple : ouverture d'une popup)
		 * @param estActivable : état d'activabilité d'un menu
		 */
		Menus(boolean estActivable) {
			this.estActivable = estActivable;
		}
		
		/**
		 * @return Si un menu est activable
		 */
		public boolean getEstActivable() {
			return this.estActivable;
		}
	}
	
	private List<JButtonMenu> boutonsMenu;
	private JButtonMenu boutonActif;
	
	public ControleurMenu() {
		this.boutonsMenu = new LinkedList<>();
		this.boutonActif = null;
	}

	/**
	 * Ajoute un bouton à la liste des boutons du menu
	 * @param bouton
	 */
	public void ajouterBoutonMenu(JButtonMenu bouton) {
		this.boutonsMenu.add(bouton);
	}
	
	/**
	 * @return L'unique bouton activé (hors survol) du menu
	 */
	public JButtonMenu getBoutonActif() {
		return this.boutonActif;
	}
	
	/**
	 * Modifie l'unique bouton activé (hors survol) du menu
	 * @param boutonActif
	 */
	public void setBoutonActif(JButtonMenu boutonActif) {
		for(JButtonMenu bouton : boutonsMenu) {
	    	VueMenu.setBoutonActif(false, bouton);
		}
		this.boutonActif = boutonActif;
    	VueMenu.setBoutonActif(true, boutonActif);
	}

	/**
	 * Si un menu non activé est cliqué, il passe en actif (les autres sont désactivés) puis son traitement est exécuté
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		JButtonMenu boutonClique = (JButtonMenu) e.getSource();
		if(boutonClique != this.boutonActif) {
			if(boutonClique.getMenu().getEstActivable()) {
				this.setBoutonActif(boutonClique);
			}
			switch(boutonClique.getMenu()) {
			case TOURNOIS:
				System.out.println("tournois");
				break;
			case EQUIPES:
				System.out.println("equipes");
				break;
			case HISTORIQUE:
				System.out.println("historique");
				break;
			case PALMARES:
				System.out.println("palmares");
				break;
			case UTILISATEUR:
				System.out.println("utilisateur");
				break;
			case DECONNEXION:
				System.out.println("deconnexion");
			default:
			}
		}
	}

	/**
	 * Mise en activité (survol) d'un menu
	 */
	@Override
    public void mouseEntered(MouseEvent e) {
		JButtonMenu bouton = (JButtonMenu) e.getSource();
        if (bouton.getIcon() != null && bouton != this.getBoutonActif()) {
        	VueMenu.setBoutonActif(true, bouton);
        }
    }
	
	/**
	 * Mise hors activité (survol) d'un menu
	 */
    @Override
    public void mouseExited(MouseEvent e) {
    	JButtonMenu bouton = (JButtonMenu) e.getSource();
        if (bouton.getIcon() != null && bouton != this.getBoutonActif()) {
        	VueMenu.setBoutonActif(false, bouton);
        }
    }
	
}
