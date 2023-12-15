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
import javax.swing.JTable;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VueMatch extends JFrameTheme {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panelLblPoule;
	private JLabel lblPoule;
	private DefaultTableModel model;
	private JTable tableMatchs;
	private JTable tableClassement;
	private JPanel panelTableau;
	private JScrollPane scrollPaneMatchs;
	private JScrollPane scrollPaneClassement;
	private JPanel panelBtn;
	private JButton btnEquipe1;
	private JButton btnEquipe2;
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
		
		panelLblPoule = new JPanel();
		panelLblPoule.setBackground(CharteGraphique.FOND);
		contentPane.add(panelLblPoule, BorderLayout.NORTH);
		
		lblPoule = new JLabel("Poule");
		lblPoule.setFont(CharteGraphique.getPolice(30, true));
		lblPoule.setForeground(CharteGraphique.TEXTE);
		panelLblPoule.add(lblPoule);	
		
		panelTableau = new JPanel();
		panelTableau.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTableau, BorderLayout.CENTER);
		GridBagLayout gbl_panelTableau = new GridBagLayout();
		gbl_panelTableau.columnWidths = new int[] {};
		gbl_panelTableau.rowHeights = new int[] {15, 0, 15};
		gbl_panelTableau.columnWeights = new double[]{1.0};
		gbl_panelTableau.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelTableau.setLayout(gbl_panelTableau);
		
		scrollPaneMatchs = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelTableau.add(scrollPaneMatchs, gbc_scrollPane);
		
		tableMatchs = new JTableTheme();
		tableMatchs.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Date de début" ,"Date de fin", "Equipe 1", "Equipe 2"
			}
		));
				
		scrollPaneMatchs.setViewportView(tableMatchs);
		
		panelBtn = new JPanel();
		contentPane.add(panelBtn, BorderLayout.SOUTH);
		panelBtn.setBackground(CharteGraphique.FOND);
		
		btnQuitter = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Quitter");
		panelBtn.add(btnQuitter);
		
		btnValider = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Cloturer");
		panelBtn.add(btnValider);
	}
	
	public  void remplirTableauMatchs(List<Rencontre> rencontres) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Rencontre rencontre : rencontres) {
		    Vector<Object> rowData = new Vector<>();
		    Equipe[] equipes = rencontre.getEquipes();
		    btnEquipe1.setText(equipes[0].getNom());
		    btnEquipe1.setIcon(new ImageIcon(VueMatch.class.getResource("/images/match/trophe.png")));
		    btnEquipe2.setText(equipes[1].getNom());
		    btnEquipe2.setIcon(new ImageIcon(VueMatch.class.getResource("/images/match/trophe.png")));
		    
		    rowData.add(rencontre.getDateHeureDebut());
		    rowData.add(rencontre.getDateHeureFin());
		    rowData.add(btnEquipe1);
		    rowData.add(btnEquipe2);
		    
		    this.model.addRow(rowData);
		}
	}
	
	public  void remplirTableauClassement(List<Equipe> equipes) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Equipe equipe : equipes) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(equipe.getNom());
		    rowData.add(equipe.getClassement());
		    this.model.addRow(rowData);
		}
	}
}
