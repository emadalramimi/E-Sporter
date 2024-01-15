package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.ControleurConnexion;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;
import vue.theme.JPasswordFieldTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import vue.theme.JButtonTheme.Types;

/**
 * IHM de connexion
 */
public class VueConnexion extends JFrameTheme {
	
	private JTextFieldTheme textIdentifiant;
	private JPasswordFieldTheme motDePasse;
	private JCheckBox chckbxAfficherMotDePasse;
	private JButtonTheme btnQuitter;
	private JButtonTheme btnConnexion;
	
	public VueConnexion() {
		// Impossible de redimensionner la fenêtre
		super.setAgrandissable(false);

		ControleurConnexion controleur = new ControleurConnexion(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1140, 625);
		
		// contentPane, un panel contenant tous les éléments de la fenêtre
		JPanel contentPane = super.getContentPane();
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {300, 700};
		gbl_contentPane.rowHeights = new int[]{600, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// superPanelOuest, un panel contenant tous les éléments de gauche
		JPanel superPanelOuest = new JPanel();
		GridBagConstraints gbc_superPanelOuest = new GridBagConstraints();
		gbc_superPanelOuest.anchor = GridBagConstraints.WEST;
		gbc_superPanelOuest.insets = new Insets(0, 70, 0, 70);
		gbc_superPanelOuest.gridx = 0;
		gbc_superPanelOuest.gridy = 0;
		contentPane.add(superPanelOuest, gbc_superPanelOuest);
		superPanelOuest.setLayout(new BorderLayout(0, 0));
		
		// panelNord, un panel contenant le logo
		JPanel panelNord = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelNord.getLayout();
		flowLayout_1.setHgap(0);
		flowLayout_1.setVgap(0);
		panelNord.setBackground(CharteGraphique.FOND);
		superPanelOuest.add(panelNord, BorderLayout.NORTH);
		
		// lblLogo, le logo de l'application
		JLabel lblLogo = new JLabel(new ImageIcon(VueConnexion.class.getResource("/images/logo.png")));
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		panelNord.add(lblLogo);
		
		// panelCentre, un panel contenant les champs de saisie et les boutons
		JPanel panelCentre = new JPanel();
		panelCentre.setBackground(CharteGraphique.FOND);
		superPanelOuest.add(panelCentre, BorderLayout.SOUTH);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[]{300, 0};
		gbl_panelCentre.rowHeights = new int[]{190, 33, 0};
		gbl_panelCentre.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		// panelChamps, un panel contenant les champs de saisie
		JPanel panelChamps = new JPanel();
		panelChamps.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelChamps = new GridBagConstraints();
		gbc_panelChamps.fill = GridBagConstraints.BOTH;
		gbc_panelChamps.insets = new Insets(30, 0, 30, 0);
		gbc_panelChamps.gridx = 0;
		gbc_panelChamps.gridy = 0;
		panelCentre.add(panelChamps, gbc_panelChamps);
		GridBagLayout gbl_panelChamps = new GridBagLayout();
		gbl_panelChamps.columnWidths = new int[]{250, 0};
		gbl_panelChamps.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelChamps.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelChamps.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		panelChamps.setLayout(gbl_panelChamps);
		
		// lblIdentifiant, le label de l'identifiant
		JLabelTheme lblIdentifiant = new JLabelTheme("Identifiant", 17, false);
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.anchor = GridBagConstraints.WEST;
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 0;
		panelChamps.add(lblIdentifiant, gbc_lblIdentifiant);
		
		// textIdentifiant, le champ de saisie de l'identifiant
		this.textIdentifiant = new JTextFieldTheme(20);
		this.textIdentifiant.addKeyListener(controleur);
		GridBagConstraints gbc_textIdentifiant = new GridBagConstraints();
		gbc_textIdentifiant.fill = GridBagConstraints.BOTH;
		gbc_textIdentifiant.insets = new Insets(0, 0, 20, 0);
		gbc_textIdentifiant.gridx = 0;
		gbc_textIdentifiant.gridy = 1;
		panelChamps.add(this.textIdentifiant, gbc_textIdentifiant);
		
		// lblMotDePasse, le label du mot de passe
		JLabelTheme lblMotDePasse = new JLabelTheme("Mot de passe", 17, false);
		GridBagConstraints gbc_lblMotDePasse = new GridBagConstraints();
		gbc_lblMotDePasse.anchor = GridBagConstraints.WEST;
		gbc_lblMotDePasse.insets = new Insets(0, 0, 5, 0);
		gbc_lblMotDePasse.gridx = 0;
		gbc_lblMotDePasse.gridy = 2;
		panelChamps.add(lblMotDePasse, gbc_lblMotDePasse);
		
		// motDePasse, le champ de saisie du mot de passe
		this.motDePasse = new JPasswordFieldTheme(20);
		this.motDePasse.addKeyListener(controleur);
		GridBagConstraints gbc_motDePasse = new GridBagConstraints();
		gbc_motDePasse.fill = GridBagConstraints.HORIZONTAL;
		gbc_motDePasse.insets = new Insets(0, 0, 10, 0);
		gbc_motDePasse.gridx = 0;
		gbc_motDePasse.gridy = 3;
		panelChamps.add(this.motDePasse, gbc_motDePasse);
		
		// chckbxAfficherMotDePasse, la checkbox pour afficher le mot de passe
		this.chckbxAfficherMotDePasse = new JCheckBox("Afficher le mot de passe");
		this.chckbxAfficherMotDePasse.setFocusPainted(false);
		this.chckbxAfficherMotDePasse.addActionListener(controleur);
		this.chckbxAfficherMotDePasse.setForeground(CharteGraphique.TEXTE);
		this.chckbxAfficherMotDePasse.setFont(CharteGraphique.getPolice(16, false));
		this.chckbxAfficherMotDePasse.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_chckbxAfficherMotDePasse = new GridBagConstraints();
		gbc_chckbxAfficherMotDePasse.anchor = GridBagConstraints.WEST;
		gbc_chckbxAfficherMotDePasse.gridx = 0;
		gbc_chckbxAfficherMotDePasse.gridy = 4;
		panelChamps.add(this.chckbxAfficherMotDePasse, gbc_chckbxAfficherMotDePasse);
		
		// panelBoutons, un panel contenant les boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelBoutons = new GridBagConstraints();
		gbc_panelBoutons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBoutons.anchor = GridBagConstraints.NORTH;
		gbc_panelBoutons.gridx = 0;
		gbc_panelBoutons.gridy = 1;
		panelCentre.add(panelBoutons, gbc_panelBoutons);
		panelBoutons.setLayout(new GridLayout(0, 2, 15, 0));
		
		// btnQuitter, le bouton quitter
		this.btnQuitter = new JButtonTheme(Types.SECONDAIRE, "Quitter");
		this.btnQuitter.addActionListener(controleur);
		panelBoutons.add(this.btnQuitter);
		
		// btnConnexion, le bouton connexion
		this.btnConnexion = new JButtonTheme(Types.PRIMAIRE, "Connexion");
		this.btnConnexion.addActionListener(controleur);
		panelBoutons.add(this.btnConnexion);
		
		// superPanelEst, un panel contenant l'image de droite
		JPanel superPanelEst = new JPanel();
		FlowLayout flowLayout = (FlowLayout) superPanelEst.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		GridBagConstraints gbc_superPanelEst = new GridBagConstraints();
		gbc_superPanelEst.anchor = GridBagConstraints.NORTHWEST;
		gbc_superPanelEst.gridx = 1;
		gbc_superPanelEst.gridy = 0;
		contentPane.add(superPanelEst, gbc_superPanelEst);

		// lblImage, the image on the right side of the window
		JLabel lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(VueConnexion.class.getResource("/images/connexion/splash.png")));
		superPanelEst.add(lblImage);
	}
	
	/**
	 * Affiche la fenêtre de connexion
	 */
	public void afficher() {
		try {
			VueConnexion frame = new VueConnexion();
			// Centrer la fenêtre
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Récupère le contenu du champ identifiant
	 * @return contenu du champ identifiant
	 */
	public String getIdentifiant() {
		if(this.textIdentifiant.getText().isEmpty()) {
			return null;
		}
		return this.textIdentifiant.getText().trim();
	}
	
	/**
	 * Récupère le contenu du champ mot de passe
	 * @return contenu du champ mot de passe
	 */
	public String getMotDePasse() {
		if(this.motDePasse.getPassword().length == 0) {
			return null;
		}
		return String.valueOf(this.motDePasse.getPassword());
	}
	
	/**
	 * Affiche un mot de passe si affiche true, le cache si affiche false
	 * @param affiche
	 */
	public void affichageMotDePasse(boolean affiche) {
	    if (affiche) {
	        motDePasse.setEchoChar((char) 0);
	    } else {
	        motDePasse.setEchoChar('*');
	    }
	}
	
	/**
	 * Retourne true si le clic réfère le bouton connexion
	 * @param e ActionEvent du clic sur le bouton connexion
	 * @return true si le clic réfère la checbox afficher mot de passe
	 */
	public boolean isCheckboxAfficherMdp(ActionEvent e) {
		return e.getSource() instanceof JCheckBox && ((JCheckBox) e.getSource()).getText() == "Afficher le mot de passe";
	}
	
	/**
	 * Ferme la fenêtre
	 */
	public void fermerFenetre() {
		dispose();
	}

}
