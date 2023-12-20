package vue.theme;

import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

/**
 * Extension of JSpinner for Swing
 */
public class JSpinnerTheme extends JSpinner {

    /**
     * Extension of JSpinner with theme colors
     */
    public JSpinnerTheme() {
        this.setForeground(CharteGraphique.TEXTE);
        // Customization of the spinner
        this.setFont(CharteGraphique.getPolice(19, false));
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        this.setBorder(new EmptyBorder(7, 5, 7, 5));
    }
}