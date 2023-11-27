package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurAjoutEquipe;
import modele.metier.Pays;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class VueAjoutEquipe extends JFrame {

	private JPanel contentPane;
	private JPanel panelEquipe;
	private JPanel panelJoueurs;
	private JLabel lblNom;
	private JTextField txtNom;
	private JLabel lblPays;
	private JComboBox<String> cboxPays;
	private JLabel lblWorldRancking;
	private JTextFieldTheme txtWorldRanking;
	private JLabel lblJoueurs;
	private JTextField txtJoueur1;
	private JTextField txtJoueur2;
	private JTextField txtJoueur3;
	private JTextField txtJoueur4;
	private JTextField txtJoueur5;
	private JPanel panelAnnuler;
	private JButton btnAnnuler;
	private JPanel panelValider;
	private JButton btnValider;
	
	public VueAjoutEquipe() {
		ControleurAjoutEquipe controleur = new ControleurAjoutEquipe(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPane = new JPanel();
		contentPane.setBackground(CharteGraphique.FOND);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentre = new JPanel();
		panelCentre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[]{0, 0, 0};
		gbl_panelCentre.rowHeights = new int[]{0, 0, 0};
		gbl_panelCentre.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelCentre.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		panelEquipe = new JPanel();
		panelEquipe.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelEquipe = new GridBagConstraints();
		gbc_panelEquipe.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelEquipe.insets = new Insets(0, 25, 25, 15);
		gbc_panelEquipe.gridx = 0;
		gbc_panelEquipe.gridy = 0;
		panelCentre.add(panelEquipe, gbc_panelEquipe);
		GridBagLayout gbl_panelEquipe = new GridBagLayout();
		gbl_panelEquipe.columnWidths = new int[] {0, 0};
		gbl_panelEquipe.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelEquipe.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelEquipe.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelEquipe.setLayout(gbl_panelEquipe);
		
		lblNom = new JLabel("Nom de l'équipe");
		lblNom.setForeground(Color.WHITE);
		lblNom.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		panelEquipe.add(lblNom, gbc_lblNom);
		
		txtNom = new JTextFieldTheme();
		GridBagConstraints gbc_textNom = new GridBagConstraints();
		gbc_textNom.insets = new Insets(0, 0, 20, 0);
		gbc_textNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNom.gridx = 0;
		gbc_textNom.gridy = 2;
		panelEquipe.add(txtNom, gbc_textNom);
		txtNom.setColumns(10);
		
		lblPays = new JLabel("Pays de l'équipe");
		lblPays.setForeground(Color.WHITE);
		lblPays.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblPays = new GridBagConstraints();
		gbc_lblPays.insets = new Insets(0, 0, 5, 0);
		gbc_lblPays.gridx = 0;
		gbc_lblPays.gridy = 3;
		panelEquipe.add(lblPays, gbc_lblPays);
		
		cboxPays = new JComboBox<String>(Pays.getTout());
		GridBagConstraints gbc_txtEquipe = new GridBagConstraints();
		gbc_txtEquipe.insets = new Insets(0, 0, 20, 0);
		gbc_txtEquipe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEquipe.gridx = 0;
		gbc_txtEquipe.gridy = 4;
		panelEquipe.add(cboxPays, gbc_txtEquipe);
		
		lblWorldRancking = new JLabel("World Ranking");
		lblWorldRancking.setForeground(Color.WHITE);
		lblWorldRancking.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblWorldRancking = new GridBagConstraints();
		gbc_lblWorldRancking.insets = new Insets(0, 0, 5, 0);
		gbc_lblWorldRancking.gridx = 0;
		gbc_lblWorldRancking.gridy = 5;
		panelEquipe.add(lblWorldRancking, gbc_lblWorldRancking);
		
		txtWorldRanking = new JTextFieldTheme();
		GridBagConstraints gbc_textWorldRanking = new GridBagConstraints();
		gbc_textWorldRanking.insets = new Insets(0, 0, 5, 0);
		gbc_textWorldRanking.fill = GridBagConstraints.HORIZONTAL;
		gbc_textWorldRanking.gridx = 0;
		gbc_textWorldRanking.gridy = 6;
		panelEquipe.add(txtWorldRanking, gbc_textWorldRanking);
		txtWorldRanking.setColumns(10);
		
		panelJoueurs = new JPanel();
		panelJoueurs.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelJoueurs = new GridBagConstraints();
		gbc_panelJoueurs.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelJoueurs.insets = new Insets(0, 25, 25, 15);
		gbc_panelJoueurs.gridx = 1;
		gbc_panelJoueurs.gridy = 0;
		panelCentre.add(panelJoueurs, gbc_panelJoueurs);
		GridBagLayout gbl_panelJoueurs = new GridBagLayout();
		gbl_panelJoueurs.columnWidths = new int[] {0, 0};
		gbl_panelJoueurs.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
		gbl_panelJoueurs.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelJoueurs.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelJoueurs.setLayout(gbl_panelJoueurs);
		
		lblJoueurs = new JLabel("Joueurs");
		lblJoueurs.setForeground(Color.WHITE);
		lblJoueurs.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblJoueurs = new GridBagConstraints();
		gbc_lblJoueurs.insets = new Insets(0, 0, 5, 0);
		gbc_lblJoueurs.gridx = 0;
		gbc_lblJoueurs.gridy = 0;
		panelJoueurs.add(lblJoueurs, gbc_lblJoueurs);
		
		txtJoueur1 = new JTextFieldTheme();
		GridBagConstraints gbc_txtJoueur1 = new GridBagConstraints();
		gbc_txtJoueur1.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur1.gridx = 0;
		gbc_txtJoueur1.gridy = 1;
		panelJoueurs.add(txtJoueur1, gbc_txtJoueur1);
		txtJoueur1.setColumns(10);
		
		txtJoueur2 = new JTextFieldTheme();
		GridBagConstraints gbc_txtJoueur2 = new GridBagConstraints();
		gbc_txtJoueur2.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur2.gridx = 0;
		gbc_txtJoueur2.gridy = 2;
		panelJoueurs.add(txtJoueur2, gbc_txtJoueur2);
		txtJoueur2.setColumns(10);
		
		txtJoueur3 = new JTextFieldTheme();
		GridBagConstraints gbc_txtJoueur3 = new GridBagConstraints();
		gbc_txtJoueur3.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur3.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur3.gridx = 0;
		gbc_txtJoueur3.gridy = 3;
		panelJoueurs.add(txtJoueur3, gbc_txtJoueur3);
		txtJoueur3.setColumns(10);
		
		txtJoueur4 = new JTextFieldTheme();
		GridBagConstraints gbc_txtJoueur4 = new GridBagConstraints();
		gbc_txtJoueur4.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur4.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur4.gridx = 0;
		gbc_txtJoueur4.gridy = 4;
		panelJoueurs.add(txtJoueur4, gbc_txtJoueur4);
		txtJoueur4.setColumns(10);
		
		txtJoueur5 = new JTextFieldTheme();
		GridBagConstraints gbc_txtJoueur5 = new GridBagConstraints();
		gbc_txtJoueur5.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur5.gridx = 0;
		gbc_txtJoueur5.gridy = 5;
		panelJoueurs.add(txtJoueur5, gbc_txtJoueur5);
		txtJoueur5.setColumns(10);
		
		panelAnnuler = new JPanel();
		panelAnnuler.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAnnuler = new GridBagConstraints();
		gbc_panelAnnuler.anchor = GridBagConstraints.NORTHEAST;
		gbc_panelAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_panelAnnuler.gridx = 0;
		gbc_panelAnnuler.gridy = 1;
		panelCentre.add(panelAnnuler, gbc_panelAnnuler);
		
		btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Annuler");
		btnAnnuler.addActionListener(controleur);
		panelAnnuler.add(btnAnnuler);
		
		panelValider = new JPanel();
		panelValider.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelAjouter.gridx = 1;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelValider, gbc_panelAjouter);
		
		btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Valider");
		btnValider.addActionListener(controleur);
		panelValider.add(btnValider);
	}
	
	public void fermerFenetre() {
		this.dispose();
	}
	
	public String getNomEquipe() {
		return this.txtNom.getText();
	}
	
	public String getPaysEquipe() {
		return (String) this.cboxPays.getSelectedItem();
	}
	
	public Integer getWorldRanking() throws NumberFormatException {
		String text = this.txtWorldRanking.getText();
		if(text.length() > 0) {
			return Integer.parseInt(text);
		}
		return null;
	}
	
	public void viderChamps() {
		this.txtNom.setText("");
		this.cboxPays.setSelectedIndex(0);
		this.txtWorldRanking.setText("");
		this.txtJoueur1.setText("");
		this.txtJoueur2.setText("");
		this.txtJoueur3.setText("");
		this.txtJoueur4.setText("");
		this.txtJoueur5.setText("");
	}
	
	public List<String> getNomJoueurs() {
	    List<String> noms = new ArrayList<>();
	    
	    String joueur1 = this.txtJoueur1.getText().trim();
	    String joueur2 = this.txtJoueur2.getText().trim();
	    String joueur3 = this.txtJoueur3.getText().trim();
	    String joueur4 = this.txtJoueur4.getText().trim();
	    String joueur5 = this.txtJoueur5.getText().trim();
	    
	    if (!joueur1.isEmpty()) {
	        noms.add(joueur1);
	    }
	    if (!joueur2.isEmpty()) {
	        noms.add(joueur2);
	    }
	    if (!joueur3.isEmpty()) {
	        noms.add(joueur3);
	    }
	    if (!joueur4.isEmpty()) {
	        noms.add(joueur4);
	    }
	    if (!joueur5.isEmpty()) {
	        noms.add(joueur5);
	    }
	    
	    return noms;
	}

	public void afficherPopupErreur(String message) {
		JOptionPaneTheme.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	public void afficherPopupMessage(String message) {
		JOptionPaneTheme.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
