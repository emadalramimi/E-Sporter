package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import controleur.ControleurMenu;
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
import java.awt.EventQueue;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Font;

public class VueAjoutEquipe extends JFrame {

	private JPanel contentPane;
	private JPanel panelNord;
	private JLabel lblLogo;
	private JPanel panelEquipe;
	private JPanel panelJoueurs;
	private JLabel lblEquipe;
	private JLabel lblNom;
	private JTextField textNom;
	private JLabel lblPays;
	private JTextField txtEquipe;
	private JLabel lblJoueurs;
	private JTextField txtJoueur1;
	private JTextField txtJoueur2;
	private JTextField textJoueur3;
	private JTextField textJoueur4;
	private JTextField txtJoueur5;
	private JPanel panelAnnuler;
	private JButton btnAnnuler;
	private JPanel panelAjouter;
	private JButton btnAjouter;
	
	public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VueAjoutEquipe frame = new VueAjoutEquipe();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	public VueAjoutEquipe() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		contentPane = new JPanel();
		contentPane.setBackground(CharteGraphique.FOND);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		VueMenu.afficherMenu(contentPane, ControleurMenu.Menus.TOURNOIS);
		
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
		
		lblEquipe = new JLabel("Equipe");
		GridBagConstraints gbc_lblEquipe = new GridBagConstraints();
		gbc_lblEquipe.insets = new Insets(0, 0, 5, 0);
		gbc_lblEquipe.gridx = 0;
		gbc_lblEquipe.gridy = 0;
		panelEquipe.add(lblEquipe, gbc_lblEquipe);
		
		lblNom = new JLabel("Nom");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		panelEquipe.add(lblNom, gbc_lblNom);
		
		textNom = new JTextFieldTheme();
		GridBagConstraints gbc_textNom = new GridBagConstraints();
		gbc_textNom.insets = new Insets(0, 0, 5, 0);
		gbc_textNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNom.gridx = 0;
		gbc_textNom.gridy = 2;
		panelEquipe.add(textNom, gbc_textNom);
		textNom.setColumns(10);
		
		lblPays = new JLabel("Pays");
		GridBagConstraints gbc_lblPays = new GridBagConstraints();
		gbc_lblPays.insets = new Insets(0, 0, 5, 0);
		gbc_lblPays.gridx = 0;
		gbc_lblPays.gridy = 3;
		panelEquipe.add(lblPays, gbc_lblPays);
		
		txtEquipe = new JTextFieldTheme();
		GridBagConstraints gbc_txtEquipe = new GridBagConstraints();
		gbc_txtEquipe.insets = new Insets(0, 0, 5, 0);
		gbc_txtEquipe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEquipe.gridx = 0;
		gbc_txtEquipe.gridy = 4;
		panelEquipe.add(txtEquipe, gbc_txtEquipe);
		txtEquipe.setColumns(10);
		
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
		lblJoueurs.setFont(new Font("Dialog", Font.BOLD, 12));
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
		
		textJoueur3 = new JTextFieldTheme();
		GridBagConstraints gbc_textJoueur3 = new GridBagConstraints();
		gbc_textJoueur3.insets = new Insets(0, 0, 5, 0);
		gbc_textJoueur3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textJoueur3.gridx = 0;
		gbc_textJoueur3.gridy = 3;
		panelJoueurs.add(textJoueur3, gbc_textJoueur3);
		textJoueur3.setColumns(10);
		
		textJoueur4 = new JTextFieldTheme();
		GridBagConstraints gbc_textJoueur4 = new GridBagConstraints();
		gbc_textJoueur4.insets = new Insets(0, 0, 5, 0);
		gbc_textJoueur4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textJoueur4.gridx = 0;
		gbc_textJoueur4.gridy = 4;
		panelJoueurs.add(textJoueur4, gbc_textJoueur4);
		textJoueur4.setColumns(10);
		
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
		
		btnAnnuler = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Annuler");
		panelAnnuler.add(btnAnnuler);
		
		panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelAjouter.gridx = 1;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelAjouter, gbc_panelAjouter);
		
		btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		panelAjouter.add(btnAjouter);
	}

}
