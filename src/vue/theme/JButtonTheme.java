package vue.theme;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 * Extension de JButton de Swing
 */
public class JButtonTheme extends JButton {

	/**
	 * Énumération du type du bouton (primaire ou secondaire)
	 */
	public enum Types {
		PRIMAIRE, SECONDAIRE
	}
	
	private Types type;
	
	/**
	 * Crée un JButtonTheme avec un texte
	 * @param type : primaire ou secondaire
	 * @param label : label du bouton
	 */
	public JButtonTheme(Types type, String label) {
		super(label);
		this.type = type;
		this.initialiser();
	}
	
	/**
	 * Crée un JButtonTheme avec une image
	 * @param type : primlaire ou secondaire
	 * @param icone : label du bouton
	 */
	public JButtonTheme(Types type, ImageIcon icone) {
		super(icone);
		this.type = type;
		this.initialiser();
	}
	
	/**
	 * Personnalise le JButton aux couleurs du thème
	 */
	private void initialiser() {
		// Personnalisation du bouton
		this.setFont(CharteGraphique.getPolice(19, false));
		this.setBorder(new EmptyBorder(7, 10, 7, 10));
		this.setForeground(Color.WHITE);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFocusPainted(false);
		
		// Modification de la couleur de fond selon le type
		switch(type) {
		case PRIMAIRE:
			this.setBackground(CharteGraphique.PRIMAIRE);
			break;
		case SECONDAIRE:
			this.setBackground(CharteGraphique.FOND_SECONDAIRE);
			break;
		}
	}
	
}
