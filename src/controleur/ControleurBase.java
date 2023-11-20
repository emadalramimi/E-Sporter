package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import vue.VueBase;
import vue.theme.JButtonMenu;

public class ControleurBase extends MouseAdapter {

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
	private VueBase vue;
	
	public ControleurBase(VueBase vue) {
		this.boutonsMenu = new LinkedList<>();
		this.boutonActif = null;
		this.vue = vue;
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
	    	this.vue.setBoutonActif(false, bouton);
		}
		this.boutonActif = boutonActif;
    	this.vue.setBoutonActif(true, boutonActif);
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
				this.vue.changerOnglet(boutonClique.getMenu());
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
        	this.vue.setBoutonActif(true, bouton);
        }
    }
	
	/**
	 * Mise hors activité (survol) d'un menu
	 */
    @Override
    public void mouseExited(MouseEvent e) {
    	JButtonMenu bouton = (JButtonMenu) e.getSource();
        if (bouton.getIcon() != null && bouton != this.getBoutonActif()) {
        	this.vue.setBoutonActif(false, bouton);
        }
    }
	
}
