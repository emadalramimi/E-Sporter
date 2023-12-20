package vue.theme;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 * Extension de DefaultCellEditor de Swing
 * Pour pouvoir cliquer sur un JButtonTable (boutons voir, modifier supprimer des tableaux)
 */
public class TableButtonsCellEditor extends DefaultCellEditor {

	private ActionListener controleur;
	
	/**
	 * Crée un TableButtonsCellEditor
	 * @param controleur : controleur des boutons du tableau
	 */
	public TableButtonsCellEditor(ActionListener controleur) {
		super(new JCheckBox());
		this.controleur = controleur;
	}

	/**
	 * Retourne le tableButtonsPanel avec possibilité de cliquer sur les boutons
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		TableButtonsPanel tableButtonsPanel = new TableButtonsPanel(table, this.controleur);
		tableButtonsPanel.getTableCellRendererComponent(table, value, isSelected, isSelected, row, column);
		return tableButtonsPanel;
	}
	
}
