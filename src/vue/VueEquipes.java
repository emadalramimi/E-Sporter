package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.util.Vector;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class VueEquipes extends JFrame {
	private JTable table;

	/**
	 * Create the frame.
	 */
	public void afficherVueEquipe(JPanel contentPane) {
		JPanel panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 20));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setBackground(CharteGraphique.FOND);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Equipes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(CharteGraphique.getPolice(30, true));
		lblNewLabel.setForeground(CharteGraphique.TEXTE);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(CharteGraphique.FOND);
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_1.add(panel_2);
		
		JButtonTheme btnNewButton = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(btnNewButton);
		
		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panel.add(scrollPane);
		
		// Création du modèle du tableau avec désactivation de l'édition
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"Nom", "Pays", "Classement", "World Ranking", "Modifier", "Supprimer"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTableTheme();
		table.setModel(model);
		
		/**
		 * Remplir avec données d'exemple
		 */
		model.setRowCount(0); // vider le tableau
		for(int i = 0; i < 20; i++) {
			Vector<Object> rowData = new Vector<>();
			rowData.add("CFO Academy");
			rowData.add("Taiwan");
			rowData.add("3e");
			rowData.add("414");
			rowData.add("BoutonModif");
			rowData.add("BoutonDelete (me rend fou)");
			model.addRow(rowData);
		}
		
		scrollPane.setViewportView(table);
	}

}
