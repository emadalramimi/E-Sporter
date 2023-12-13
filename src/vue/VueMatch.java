package vue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.Vector;

import vue.theme.JFrameTheme;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import modele.metier.Equipe;
import modele.metier.Rencontre;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JButton;

public class VueMatch extends JFrameTheme {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelLblMatchs;
	private JLabel lblMatchs;
	private DefaultTableModel model;
	private JTableTheme tableMatchs;
	private JPanel panelTableau;
	private JScrollPane scrollPane;
	private JPanel panelBtn;
	private JButton btnQuitter;
	private JButton btnValider;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 812, 526);
		
		contentPane = super.getContentPane();
		
		contentPane.setBorder(new EmptyBorder(15,15,15,15));
		
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
		gbl_panelTableau.columnWidths = new int[] {};
		gbl_panelTableau.rowHeights = new int[] {15, 0, 15};
		gbl_panelTableau.columnWeights = new double[]{1.0};
		gbl_panelTableau.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelTableau.setLayout(gbl_panelTableau);
		
		scrollPane = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelTableau.add(scrollPane, gbc_scrollPane);
		
		tableMatchs = new JTableTheme();
		tableMatchs.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Equipe 1", "Equipe 2", "Début","Fin","Actions"
			}
		));
		
		/* Ajouter buttons dans la derniere colonne
		TableColumn buttonColumn = tableMatchs.getColumnModel().getColumn(tableMatchs.getColumnCount() - 1);
		
			!!! A FINIR !!!
		
		*/
				
		scrollPane.setViewportView(tableMatchs);
		
		panelBtn = new JPanel();
		panelBtn.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelBtnQuitter = new GridBagConstraints();
		gbc_panelBtnQuitter.anchor = GridBagConstraints.NORTH;
		gbc_panelBtnQuitter.insets = new Insets(0, 0, 0, 5);
		gbc_panelBtnQuitter.gridx = 0;
		gbc_panelBtnQuitter.gridy = 1;
		panelTableau.add(panelBtn, gbc_panelBtnQuitter);
		
		btnQuitter = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Quitter");
		panelBtn.add(btnQuitter);
		
		btnValider = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Valider");
		panelBtn.add(btnValider);
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
