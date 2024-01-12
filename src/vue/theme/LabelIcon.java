package vue.theme;

import javax.swing.ImageIcon;

/**
 * Classe interne de label avec une icone (pour les drapeaux)
 */
public class LabelIcon {

    ImageIcon icon;
    String label;

    public LabelIcon(ImageIcon icon, String label) {
        this.icon = icon;
        this.label = label;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public String getText() {
        return this.label;
    }
    
}