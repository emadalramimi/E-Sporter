package vue.theme;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class StatutCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
    	super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Centrer les textes dans toutes les cellules
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		
		// Définir la hauteur de la rangée de titre
		this.setPreferredSize(new Dimension(this.getWidth(), 50));
		
		// Couleur du texte
		if ("Ouvert".equals(value)) {
            setForeground(new Color(111, 227, 96));
        } else {
            setForeground(Color.RED);
        }
		
		// Couleur de fond des cellules alternantes
		if(row % 2 == 0) {
			this.setBackground(CharteGraphique.FOND_SECONDAIRE);
		} else {
			this.setBackground(CharteGraphique.FOND);
		}
		
		// Changement de la couleur et de l'aspect des bordures
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, CharteGraphique.BORDURE));
		
		// Changement de la police (en gras pour les titres)
		if(row == -1) {
			this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, CharteGraphique.BORDURE));
			this.setFont(CharteGraphique.getPolice(16, true));
		} else {
			this.setFont(CharteGraphique.getPolice(16, false));
		}
 
        return this;
    }
}
