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

import controleur.ControleurEquipes;
import modele.metier.Equipe;

public class VueEquipes extends JFrame {
	
	private JTable table;
	private DefaultTableModel model;
	
	private ControleurEquipes controleur;
	
	/**
	 * Create the frame.
	 */
	public void afficherVueEquipe(JPanel contentPane) {
		this.controleur = new ControleurEquipes(this);
		
		JPanel panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 20));
		
		JPanel panelLabelEquipe = new JPanel();
		panel.add(panelLabelEquipe, BorderLayout.NORTH);
		panelLabelEquipe.setBackground(CharteGraphique.FOND);
		panelLabelEquipe.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblEquipes = new JLabel("Equipes");
		lblEquipes.setHorizontalAlignment(SwingConstants.LEFT);
		lblEquipes.setFont(CharteGraphique.getPolice(30, true));
		lblEquipes.setForeground(CharteGraphique.TEXTE);
		panelLabelEquipe.add(lblEquipes);
		
		JPanel panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		FlowLayout flowLayout = (FlowLayout) panelAjouter.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelLabelEquipe.add(panelAjouter);
		
		JButtonTheme btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnAjouter.addActionListener(controleur);
		btnAjouter.setHorizontalAlignment(SwingConstants.RIGHT);
		panelAjouter.add(btnAjouter);
		
		JScrollPaneTheme scrollPaneEquipes = new JScrollPaneTheme();
		panel.add(scrollPaneEquipes);
		
		// Création du modèle du tableau avec désactivation de l'édition
		this.model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"Nom", "Pays", "Classement", "World Ranking", "Modifier", "Supprimer"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTableTheme();
		
		this.model.setRowCount(0); // vider le tableau
		for(Equipe equipe : this.controleur.getEquipes()) {
			Vector<Object> rowData = new Vector<>();
			rowData.add(equipe.getNom());
			rowData.add(equipe.getPays());
			rowData.add(equipe.getClassement());
			rowData.add(equipe.getWorldRanking());
			rowData.add("BoutonModif");
			rowData.add("BoutonDelete (me rend fou)");
			this.model.addRow(rowData);
		}
		
		this.table.setModel(model);
		
		scrollPaneEquipes.setViewportView(table);
	}
	
	public void afficherFenetreAjoutEquipe() {
		try {
            VueAjoutEquipe frame = new VueAjoutEquipe();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
