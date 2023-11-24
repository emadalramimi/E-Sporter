package vue.theme;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JTextFieldTheme extends JTextField {

	public JTextFieldTheme() {
		this.setForeground(CharteGraphique.TEXTE);
		this.setFont(CharteGraphique.getPolice(19, false));
		this.setBackground(CharteGraphique.FOND_SECONDAIRE);
		this.setColumns(10);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
}
