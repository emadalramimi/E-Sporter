package vue.theme;

import javax.swing.JLabel;

public class JLabelTheme extends JLabel {
    
    public JLabelTheme(String text, int taille, boolean estGras) {
        super(text);
        this.setForeground(CharteGraphique.TEXTE);
        this.setFont(CharteGraphique.getPolice(taille, estGras));
    }

}
