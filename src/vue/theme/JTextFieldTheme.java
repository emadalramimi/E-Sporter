package vue.theme;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Extension de JTextField de Swing
 */
public class JTextFieldTheme extends JTextField {

    /**
     * Extension de JTextField aux couleurs du thème
     * @param limite : limite de caractères du champ
     */
    public JTextFieldTheme(int limite) {
    	this.setForeground(CharteGraphique.TEXTE);
    	// Personnalisation du champ
        this.setFont(CharteGraphique.getPolice(19, false));
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        this.setColumns(10);
        this.setBorder(new EmptyBorder(7, 5, 7, 5));

        // Insère un caractère dans le champ seulement si la limite n'est pas dépassée
        setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;

                if (limite > -1 && (getLength() + str.length()) <= limite) {
                    super.insertString(offs, str, a);
                }
            }
        });
    }
}
