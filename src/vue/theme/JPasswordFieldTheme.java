package vue.theme;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Extension de JPasswordField de Swing
 */
public class JPasswordFieldTheme extends JPasswordField {

	/**
	 * Extension de JPasswordField aux couleurs du thème
     * @param limite : limite de caractères du champ
	 */
    public JPasswordFieldTheme(int limite) {
    	// Personnalisation du champ
        this.setForeground(CharteGraphique.TEXTE);
        this.setFont(CharteGraphique.getPolice(16, false));
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        this.setColumns(10);
        this.setBorder(new EmptyBorder(7, 5, 7, 5));
        
        // Masquage des caractères
        this.setEchoChar('*');

        // Insère un caractère dans le champ seulement si la limite n'est pas dépassée
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;

                if ((getLength() + str.length()) <= limite) {
                    super.insertString(offs, str, a);
                }
            }
        });
    }
}
