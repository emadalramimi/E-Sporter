package vue.theme;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class JPasswordFieldTheme extends JPasswordField {

	public JPasswordFieldTheme() {
		this.setForeground(CharteGraphique.TEXTE);
		this.setFont(CharteGraphique.getPolice(16, false));
		this.setBackground(CharteGraphique.FOND_SECONDAIRE);
		this.setColumns(10);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setEchoChar('*');
	}
	
}
