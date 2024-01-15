package vue.theme;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;

/**
 * Classe pour afficher les drapeaux dans un JLabel
 */
public class ImageTableCellRenderer extends ThemeTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        // Affichage du label et de l'icone à gauche
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        LabelIcon labelIcon = (LabelIcon) value;
        setIcon(labelIcon.getIcon());
        setText(labelIcon.getText());
        
        return label;
    }
    
}