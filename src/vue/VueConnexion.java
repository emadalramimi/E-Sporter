package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JPasswordFieldTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.JButton;

public class VueConnexion extends JFrame {

	private JPanel contentPane;
	private JPanel panelNord;
	private JLabel lblLogo;
	private JPanel panelBoutons;
	private JButton btnQuitter;
	private JButton btnConnexion;
	private JPanel panelChamps;
	private JLabel lblIdentifiant;
	private JTextField textIdentifiant;
	private JLabel lblMotDePasse;
	private JPasswordField motDePasse;
	private JCheckBox chckbxAfficherMotDePasse;
	
	public VueConnexion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 517);
		contentPane = new JPanel();
		contentPane.setBackground(CharteGraphique.FOND);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelNord = new JPanel();
		FlowLayout fl_panelNord = (FlowLayout) panelNord.getLayout();
		fl_panelNord.setAlignOnBaseline(true);
		fl_panelNord.setVgap(30);
		fl_panelNord.setHgap(0);
		panelNord.setBackground(CharteGraphique.FOND);
		contentPane.add(panelNord, BorderLayout.NORTH);
		
		lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("assets/images/logo.png"));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		panelNord.add(lblLogo);
		
		JPanel panelCentre = new JPanel();
		panelCentre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[] {250};
		gbl_panelCentre.rowHeights = new int[]{190, 33, 0};
		gbl_panelCentre.columnWeights = new double[]{0.0};
		gbl_panelCentre.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		panelChamps = new JPanel();
		panelChamps.setBackground(new Color(7, 10, 20));
		GridBagConstraints gbc_panelChamps = new GridBagConstraints();
		gbc_panelChamps.fill = GridBagConstraints.BOTH;
		gbc_panelChamps.insets = new Insets(0, 0, 5, 0);
		gbc_panelChamps.gridx = 0;
		gbc_panelChamps.gridy = 0;
		panelCentre.add(panelChamps, gbc_panelChamps);
		GridBagLayout gbl_panelChamps = new GridBagLayout();
		gbl_panelChamps.columnWidths = new int[]{250, 0};
		gbl_panelChamps.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelChamps.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelChamps.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelChamps.setLayout(gbl_panelChamps);
		
		lblIdentifiant = new JLabel("Identifiant");
		lblIdentifiant.setForeground(Color.WHITE);
		lblIdentifiant.setFont(CharteGraphique.getPolice(16, false));
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.anchor = GridBagConstraints.WEST;
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 0;
		panelChamps.add(lblIdentifiant, gbc_lblIdentifiant);
		
		textIdentifiant = new JTextFieldTheme();
		GridBagConstraints gbc_textIdentifiant = new GridBagConstraints();
		gbc_textIdentifiant.fill = GridBagConstraints.BOTH;
		gbc_textIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_textIdentifiant.gridx = 0;
		gbc_textIdentifiant.gridy = 1;
		panelChamps.add(textIdentifiant, gbc_textIdentifiant);
		
		lblMotDePasse = new JLabel("Mot de passe");
		lblMotDePasse.setForeground(Color.WHITE);
		lblMotDePasse.setFont(CharteGraphique.getPolice(16, false));
		GridBagConstraints gbc_lblMotDePasse = new GridBagConstraints();
		gbc_lblMotDePasse.anchor = GridBagConstraints.WEST;
		gbc_lblMotDePasse.insets = new Insets(0, 0, 5, 0);
		gbc_lblMotDePasse.gridx = 0;
		gbc_lblMotDePasse.gridy = 2;
		panelChamps.add(lblMotDePasse, gbc_lblMotDePasse);
		
		motDePasse = new JPasswordFieldTheme();
		GridBagConstraints gbc_motDePasse = new GridBagConstraints();
		gbc_motDePasse.fill = GridBagConstraints.HORIZONTAL;
		gbc_motDePasse.insets = new Insets(0, 0, 5, 0);
		gbc_motDePasse.gridx = 0;
		gbc_motDePasse.gridy = 3;
		panelChamps.add(motDePasse, gbc_motDePasse);
		
		chckbxAfficherMotDePasse = new JCheckBox("Afficher le mot de passe");
		chckbxAfficherMotDePasse.setForeground(Color.WHITE);
		chckbxAfficherMotDePasse.setFont(CharteGraphique.getPolice(13, false));
		chckbxAfficherMotDePasse.setBackground(new Color(7, 10, 20));
		GridBagConstraints gbc_chckbxAfficherMotDePasse = new GridBagConstraints();
		gbc_chckbxAfficherMotDePasse.anchor = GridBagConstraints.WEST;
		gbc_chckbxAfficherMotDePasse.gridx = 0;
		gbc_chckbxAfficherMotDePasse.gridy = 4;
		panelChamps.add(chckbxAfficherMotDePasse, gbc_chckbxAfficherMotDePasse);
		
		panelBoutons = new JPanel();
		panelBoutons.setBackground(new Color(7, 10, 20));
		GridBagConstraints gbc_panelBoutons = new GridBagConstraints();
		gbc_panelBoutons.anchor = GridBagConstraints.NORTH;
		gbc_panelBoutons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBoutons.gridx = 0;
		gbc_panelBoutons.gridy = 1;
		panelCentre.add(panelBoutons, gbc_panelBoutons);
		panelBoutons.setLayout(new GridLayout(0, 2, 10, 0));
		
		btnQuitter = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Quitter");
		panelBoutons.add(btnQuitter);
		
		btnConnexion = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Connexion");
		panelBoutons.add(btnConnexion);
	}

}
