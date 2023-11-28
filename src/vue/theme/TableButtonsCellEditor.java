package vue.theme;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class TableButtonsCellEditor extends DefaultCellEditor {

	private ActionListener controleur;
	
	public TableButtonsCellEditor(ActionListener controleur) {
		super(new JCheckBox());
		this.controleur = controleur;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object o, boolean bln, int row, int i1) {
		TableButtonsPanel br = new TableButtonsPanel(table, this.controleur, (int) table.getValueAt(row, 0));
		return br;
	}
	
}
