package vue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
//
//import vue.theme.CharteGraphique;
//import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.TableButtonsCellEditor;
import vue.theme.TableButtonsPanel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.util.List;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurEquipes;
import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Tournoi;

public class VueTournois extends JFrameTheme {
	private JTable table;
	private DefaultTableModel model;
	private JButtonTheme btnAjouter;
	private JPanel panelAjouter;

	/**
	 * WIP : PAGE D'EXEMPLE
	 */
	public void afficherVueTournois(JPanel contentPane) {
		JPanel panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 20));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setBackground(CharteGraphique.FOND);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Tournois");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(CharteGraphique.getPolice(30, true));
		lblNewLabel.setForeground(CharteGraphique.TEXTE);
		panel_1.add(lblNewLabel);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(CharteGraphique.FOND);
		FlowLayout flowLayout1 = (FlowLayout) panel_2.getLayout();
		flowLayout1.setVgap(0);
		flowLayout1.setHgap(0);
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		panel_1.add(panel_2);
		
		JButtonTheme btnNewButton = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnNewButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(btnNewButton);
		
		btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lors du clic sur le bouton Ajouter
                ouvrirVueSaisieTournois();
            }
        });
		
		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panel.add(scrollPane);
		
		// Création du modèle du tableau avec désactivation de l'édition
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] { "Statut", "Nom", "Niveau", "Date debut", "Date fin", "Poule", "Actions" }
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column != table.getColumnCount() - 1) {
					return false;
				}
				return true;
			}
		};
		
		table = new JTableTheme();
		table.setModel(model);
		
		//TODO Apres le controleur
		// Ajouter buttons dans la derniere colonne
//		TableColumn buttonColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
//		buttonColumn.setCellRenderer(new TableButtonsPanel(table, controleur, 0));
//		buttonColumn.setCellEditor(new TableButtonsCellEditor(controleur));
		
		model.setRowCount(0); // vider le tableau
		for(int i = 0; i < 20; i++) {
			Vector<Object> rowData = new Vector<>();
			rowData.add("Ouvert");
			rowData.add("KCX 2023");
			rowData.add("International classé");
			rowData.add("30/10/2023");
			rowData.add("30/11/2023");
			rowData.add("Poules");

			model.addRow(rowData);
		}
		scrollPane.setViewportView(table);
	}
	
	public void remplirTableau(List<Tournoi> tournois) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Tournoi tournoi : tournois) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(tournoi.getIdTournoi());
		    rowData.add(tournoi.getNomTournoi());
		    rowData.add(tournoi.getNotoriete());
		    rowData.add(tournoi.getDateDebut());
		    rowData.add(tournoi.getDateFin());
		    rowData.add(tournoi.getPoules());
	        
		    this.model.addRow(rowData);
		}
		
		// Mise à jour du tableau
		this.table.setModel(this.model);
	}
	
	private void ouvrirVueSaisieTournois() {
        VueSaisieTournois vueSaisieTournois = new VueSaisieTournois(this);
        vueSaisieTournois.setVisible(true);
    }
	
	

}
