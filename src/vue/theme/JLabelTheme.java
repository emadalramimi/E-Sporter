package vue.theme;

import javax.swing.JLabel;

/**
 * Extension de JLabel de Swing
 */
public class JLabelTheme extends JLabel {
    
    public JLabelTheme(String text, int taille, boolean estGras) {
        // Personnalisation
        super(text);
        this.setForeground(CharteGraphique.TEXTE);
        this.setFont(CharteGraphique.getPolice(taille, estGras));
    }

}
