package vue.theme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Rendu personnalisé pour centrer les images et les textes dans le tableau pour l'impression
 */
public class ThemeTableCellRendererImpression extends DefaultTableCellRenderer {
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// Centrer les textes dans toutes les cellules
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		
		// Définir la hauteur de la rangée de titre
		this.setPreferredSize(new Dimension(this.getWidth(), 25));
		
		// Couleur du texte
		this.setForeground(Color.BLACK);

        // Couleur de fond
		if(row % 2 == 0) {
			this.setBackground(Color.WHITE);
		} else {
			this.setBackground(Color.LIGHT_GRAY);
		}
		
		// Changement de la couleur et de l'aspect des bordures
		if(column != 0) {
			this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK));
		} else {
			this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		}
		
		// Changement de la police (en gras pour les titres)
		if(row == -1) {
			if (column != 0) {
				this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));
			} else {
				this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			}
			this.setFont(CharteGraphique.getPolice(10, true));
		} else {
			this.setFont(CharteGraphique.getPolice(10, false));
		}

		if (value instanceof LabelIcon) {
			LabelIcon labelIcon = (LabelIcon) value;
			this.setText(labelIcon.getText());
		}

		return component;
	}
	
}