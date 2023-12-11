package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modele.metier.Equipe;
import modele.metier.Rencontre;
import vue.theme.CharteGraphique;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

public class VueMatch extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelLblMatchs;
	private JLabel lblMatchs;
	private JScrollPaneTheme scrollPaneMatchs;
	private DefaultTableModel model;
	private JTableTheme tableMatchs;
	private JPanel panelTableau;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueMatch frame = new VueMatch();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VueMatch() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 400);
		contentPane = new JPanel();
		contentPane.setBackground(CharteGraphique.FOND);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelLblMatchs = new JPanel();
		panelLblMatchs.setBackground(CharteGraphique.FOND);
		contentPane.add(panelLblMatchs, BorderLayout.NORTH);
		
		lblMatchs = new JLabel("Matchs");
		lblMatchs.setFont(CharteGraphique.getPolice(30, true));
		lblMatchs.setForeground(CharteGraphique.TEXTE);
		panelLblMatchs.add(lblMatchs);	
		
		panelTableau = new JPanel();
		panelTableau.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTableau, BorderLayout.CENTER);
		GridBagLayout gbl_panelTableau = new GridBagLayout();
		gbl_panelTableau.columnWidths = new int[] {30};
		gbl_panelTableau.rowHeights = new int[] {15, 15};
		gbl_panelTableau.columnWeights = new double[]{0.0, 1.0};
		gbl_panelTableau.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelTableau.setLayout(gbl_panelTableau);
		
		scrollPane = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		panelTableau.add(scrollPane, gbc_scrollPane);
		
		tableMatchs = new JTableTheme();
		tableMatchs.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Equipe 1", "Equipe 2", "Début","Fin"
			}
		));
		scrollPane.setViewportView(tableMatchs);
	}
	
	public  void remplirTableau(List<Rencontre> rencontres) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Rencontre rencontre : rencontres) {
		    Vector<Object> rowData = new Vector<>();
		    Equipe[] equipes = rencontre.getEquipes();
		    rowData.add(equipes[0]);
		    rowData.add(equipes[1]);
	        
		    rowData.add(rencontre.getDateHeureDebut());
		    rowData.add(rencontre.getDateHeureFin());
		    this.model.addRow(rowData);
		}
	}
}
