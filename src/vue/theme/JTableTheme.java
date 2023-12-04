package vue.theme;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Extension de JTable de Swing
 */
public class JTableTheme extends JTable {

	/**
	 * JTable personnalisé au thème E-sporter
	 */
	public JTableTheme() {
		// Mettre en place le Renderer personnalisé pour toutes les lignes (dont titre)
		JTableHeader header = this.getTableHeader();
		header.setDefaultRenderer(new ThemeTableCellRenderer());
		this.setDefaultRenderer(Object.class, new ThemeTableCellRenderer());
		
		// Suppression des bordures supplémentaires et définition d'une hauteur pour les cellules (hors titre)
		this.setIntercellSpacing(new Dimension(0,0));
		this.setRowHeight(50);
		
		// Pour qu'une seule ligne à la fois soit sélectionnée
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Empêcher le redimensionnement des colonnes
		TableColumnModel columnModel = this.getColumnModel();
	    for (int i = 0; i < columnModel.getColumnCount(); i++) {
	        TableColumn column = columnModel.getColumn(i);
	        column.setResizable(false);
	    }
	}
	
	/**
	 * Rendu personnalisé pour centrer les images et les textes dans le tableau
	 */
	public class ThemeTableCellRenderer extends DefaultTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Centrer les textes dans toutes les cellules
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
			
			// Définir la hauteur de la rangée de titre
			this.setPreferredSize(new Dimension(this.getWidth(), 50));
			
			// Couleur du texte
			this.setForeground(CharteGraphique.TEXTE);
			
			// Couleur de fond des cellules alternantes
			if(row % 2 == 0) {
				this.setBackground(CharteGraphique.FOND_SECONDAIRE);
			} else {
				this.setBackground(CharteGraphique.FOND);
			}
			
			// Changement de la couleur et de l'aspect des bordures
			if(column == 0) {
				this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, CharteGraphique.BORDURE));
			} else {
				this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, CharteGraphique.BORDURE));
			}
			
			// Changement de la police (en gras pour les titres)
			if(row == -1) {
				this.setFont(CharteGraphique.getPolice(16, true));
			} else {
				this.setFont(CharteGraphique.getPolice(16, false));
			}

			return component;
		}
	}
	
}