package vue.theme;

import java.awt.Cursor;

import javax.swing.JButton;

import controleur.ControleurBase;

public class JButtonMenu extends JButton {

	private ControleurBase.Menus menu;
	
	/**
	 * JButton avec stockage du menu relatif
	 * @param label : label du menu
	 * @param menu : menu relatif
	 */
	public JButtonMenu(String label, ControleurBase.Menus menu) {
		super(label);
		this.menu = menu;

		this.setBorder(null);
		this.setBackground(CharteGraphique.FOND);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Retourne le menu relatif au bouton
	 */
	public ControleurBase.Menus getMenu() {
		return this.menu;
	}
	
}
