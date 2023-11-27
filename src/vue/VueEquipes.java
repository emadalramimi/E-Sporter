package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.ButtonRenderer;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurEquipes;
import modele.metier.Equipe;

public class VueEquipes extends JFrame {
	
	private JTable table;
	private DefaultTableModel model;
	
	private ControleurEquipes controleur;
	private JButtonTheme btnAjouter;
	private JPanel panel;
	private JPanel panelLabelEquipe;
	private JLabel lblEquipes;
	private JPanel panelAjouter;
	private JScrollPaneTheme scrollPaneEquipes;
	
	/**
	 * Create the frame.
	 */
	public void afficherVueEquipe(JPanel contentPane) {
		this.controleur = new ControleurEquipes(this);
		
		// panel contient tous les éléments de la page
		panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 20));
		
		// panelLabelEquipe, le panel contenant le label lblEquipes
		panelLabelEquipe = new JPanel();
		panel.add(panelLabelEquipe, BorderLayout.NORTH);
		panelLabelEquipe.setBackground(CharteGraphique.FOND);
		panelLabelEquipe.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblEquipes = new JLabel("Equipes");
		lblEquipes.setHorizontalAlignment(SwingConstants.LEFT);
		lblEquipes.setFont(CharteGraphique.getPolice(30, true));
		lblEquipes.setForeground(CharteGraphique.TEXTE);
		panelLabelEquipe.add(lblEquipes);
		
		// panelAjouter, le panel contenant le bouton btnAjouter
		panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		FlowLayout flowLayout = (FlowLayout) panelAjouter.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelLabelEquipe.add(panelAjouter);
		
		// btnAjouter, un bouton pour permettre l'ajout d'une équipe
		btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnAjouter.addActionListener(controleur);
		btnAjouter.setHorizontalAlignment(SwingConstants.RIGHT);
		panelAjouter.add(btnAjouter);
		
		scrollPaneEquipes = new JScrollPaneTheme();
		panel.add(scrollPaneEquipes);
		
		// Création du modèle du tableau avec désactivation de l'édition
		this.model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"Nom", "Pays", "Classement", "World Ranking", "Actions"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTableTheme();
		table.setModel(model);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		int[] teamIds = new int[this.controleur.getEquipes().size()]; 
		// Ajouter buttons dans la derniere colonne
		TableColumn buttonColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
		buttonColumn.setCellRenderer(new ButtonRenderer(table, teamIds, controleur));
		
		this.model.setRowCount(0); // vider le tableau
		
		int i = 0;
		//Entrée des données des équipe ainsi que l'option de modification et de suppression des équipes
		for(Equipe equipe : this.controleur.getEquipes()) {
			Vector<Object> rowData = new Vector<>();
			rowData.add(equipe.getNom());
			rowData.add(equipe.getPays());
			rowData.add(equipe.getClassement());
			rowData.add(equipe.getWorldRanking());
			this.model.addRow(rowData);
			teamIds[i++] = equipe.getIdEquipe();
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
	
	// TODO afficher fenetre joueurs ici
	
}
