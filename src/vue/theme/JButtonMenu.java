package vue.theme;

import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import controleur.ControleurBase;

/**
 * Extension du JButton de Swing pour le menu de l'application
 */
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
		// Enlève la couleur de fond du menu au clic
		super.setContentAreaFilled(false);
		this.menu = menu;
		
		// Création des chemins des icones (actif et inactif)
		this.cheminIcone = "/images/menu/" + menu.toString().toLowerCase() + ".png";
        this.cheminIconeActif = cheminIcone.replace(".png", "_actif.png");
        
        
        // Personnalisation de l'icone
        this.setIcon(new ImageIcon(JButtonMenu.class.getResource(cheminIcone)));
		this.setBorder(null);
		this.setBackground(CharteGraphique.FOND_SECONDAIRE);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Retourne le menu relatif au bouton
	 * @return Menu relatif au bouton
	 */
	public ControleurBase.Menus getMenu() {
		return this.menu;
	}
	
	/**
	 * Change l'icone du menu
	 * @param actif : état d'activité
	 */
	public void activerIconeBouton(boolean actif) {
		if(actif) {
			this.setIcon(new ImageIcon(JButtonMenu.class.getResource(this.cheminIconeActif)));
		} else {
			this.setIcon(new ImageIcon(JButtonMenu.class.getResource(this.cheminIcone)));
		}
	}
	
	/**
	 * Enlève la couleur de fond de base de Swing au clic
	 * @param g : Graphics de Swing
	 */
	@Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

	/**
	 * Enlève la couleur de fond de base de Swing au clic
	 */
    @Override
    public void setContentAreaFilled(boolean b) {}
    
}
