package vue.theme;

import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import controleur.ControleurBase;

public class JButtonMenu extends JButton {

	private ControleurBase.Menus menu;
	private String cheminIcone;
    private String cheminIconeActif;
	
	/**
	 * JButton avec stockage du menu relatif
	 * @param label : label du menu
	 * @param menu : menu relatif
	 */
	public JButtonMenu(String label, ControleurBase.Menus menu) {
		super(label);
		super.setContentAreaFilled(false);
		this.menu = menu;
		this.cheminIcone = "/images/menu/" + menu.toString().toLowerCase() + ".png";
        this.cheminIconeActif = cheminIcone.replace(".png", "_actif.png");
        
        this.setIcon(new ImageIcon(JButtonMenu.class.getResource(cheminIcone)));
		this.setBorder(null);
		this.setBackground(CharteGraphique.FOND_SECONDAIRE);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Retourne le menu relatif au bouton
	 */
	public ControleurBase.Menus getMenu() {
		return this.menu;
	}
	
	public void activerIconeBouton(boolean actif) {
		if(actif) {
			this.setIcon(new ImageIcon(JButtonMenu.class.getResource(this.cheminIconeActif)));
		} else {
			this.setIcon(new ImageIcon(JButtonMenu.class.getResource(this.cheminIcone)));
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {}
    
}
