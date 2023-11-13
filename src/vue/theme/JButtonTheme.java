package vue.theme;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class JButtonTheme extends JButton {

	public enum Types {
		PRIMAIRE, SECONDAIRE
	}
	
	/**
	 * JButton personnalisé au thème E-sporter
	 * @param type : primaire ou secondaire
	 * @param label : label du bouton
	 */
	public JButtonTheme(Types type, String label) {
		super(label);

		// Modifications de base
		this.setFont(CharteGraphique.getPolice(16, false));
		this.setBorder(new EmptyBorder(7, 10, 7, 10));
		this.setForeground(Color.WHITE);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
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
