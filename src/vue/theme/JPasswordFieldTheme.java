package vue.theme;

import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JPasswordFieldTheme extends JPasswordField {

	/**
	 * Extension de JPasswordField aux couleurs du thème
     * @param limite : limite de caractères du champ (-1 pour illimité)
	 */
    public JPasswordFieldTheme(int limite) {
        this.setForeground(CharteGraphique.TEXTE);
        this.setFont(CharteGraphique.getPolice(16, false));
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        this.setColumns(10);
        this.setBorder(new EmptyBorder(7, 5, 7, 5));
        this.setEchoChar('*');

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
