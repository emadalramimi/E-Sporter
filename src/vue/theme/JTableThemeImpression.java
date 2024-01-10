package vue.theme;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 * Extension de JTable de Swing pour l'impression
 */
public class JTableThemeImpression extends JTable {

	/**
	 * JTable personnalisé au thème E-sporter pour l'impression
	 */
	public JTableThemeImpression(TableModel model) {
        super(model);
        
		// Mettre en place le Renderer personnalisé pour toutes les lignes (dont titre)
		JTableHeader header = this.getTableHeader();
		header.setDefaultRenderer(new ThemeTableCellRendererImpression());
		this.setDefaultRenderer(Object.class, new ThemeTableCellRendererImpression());

		// Suppression des bordures supplémentaires et définition d'une hauteur pour les cellules (hors titre)
		this.setIntercellSpacing(new Dimension(0, 0));
		this.setRowHeight(25);
	}
	
}