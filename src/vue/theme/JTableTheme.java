package vue.theme;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Extension de JTable de Swing
 */
public class JTableTheme extends JTable {

	/**
	 * JTable personnalisé au thème E-sporter
	 */
	public JTableTheme(String[] colonnes, ActionListener controleurBtnColumn) {
		// Mettre en place le Renderer personnalisé pour toutes les lignes (dont titre)
		JTableHeader header = this.getTableHeader();
		header.setDefaultRenderer(new ThemeTableCellRenderer());
		this.setDefaultRenderer(Object.class, new ThemeTableCellRenderer());

		// Suppression des bordures supplémentaires et définition d'une hauteur pour les cellules (hors titre)
		this.setIntercellSpacing(new Dimension(0, 0));
		this.setRowHeight(50);

		// Pour qu'une seule ligne à la fois soit sélectionnée
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Définition du modèle et de la non-éditabilité des cellules (si controleurBtnColumn != null)
		this.setModel(new DefaultTableModel(
			new Object[][] {}, 
			colonnes
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
                return controleurBtnColumn != null && column == this.getColumnCount() - 1;
			}
		});

		// Si les boutons d'action sont à ajouter dans la dernière colonne
        if(controleurBtnColumn != null) {
            TableColumn buttonColumn = this.getColumnModel().getColumn(this.getColumnCount() - 1);
            buttonColumn.setCellRenderer(new TableButtonsPanel(this, controleurBtnColumn));
            buttonColumn.setCellEditor(new TableButtonsCellEditor(controleurBtnColumn));
        }

		// Si la première colonne est ID, la cacher
        if(colonnes[0].equals("ID")) {
            TableColumn idColumn = this.getColumnModel().getColumn(0);
            idColumn.setMinWidth(1); // 1px pour garder la bordure
            idColumn.setMaxWidth(1);
            idColumn.setWidth(1);
            idColumn.setPreferredWidth(1);
        }
	}
	
	@Override
	public void setModel(final TableModel model) {
		super.setModel(model);

		// Empêcher le redimensionnement des colonnes et le déplacement des colonnes
		TableColumnModel columnModel = this.getColumnModel();
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setResizable(false);
		}
		
		JTableHeader header = this.getTableHeader();
		if (header != null) {
			header.setReorderingAllowed(false);
		}
	}
	
}