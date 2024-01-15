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

    /**
     * Retourne l'icone
     * @return Icone
     */
    public ImageIcon getIcon() {
        return this.icon;
    }

    /**
     * Retourne le texte
     * @return Texte
     */
    public String getText() {
        return this.label;
    }
    
}