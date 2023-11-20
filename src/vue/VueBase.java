package vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurBase;
import vue.theme.CharteGraphique;
import vue.theme.JButtonMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class VueBase extends JFrame {
	
	private JPanel contenu;
	
	private ControleurBase controleurMenu;
	
	/**
	 * Create the frame.
	 */
	public VueBase() {
		this.controleurMenu = new ControleurBase(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 700);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(CharteGraphique.FOND);
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		this.contenu = new JPanel();
		this.contenu.setBackground(CharteGraphique.FOND);
		contentPane.add(this.contenu, BorderLayout.CENTER);
		this.contenu.setLayout(new BorderLayout(0, 20));
		
		// Affichage du menu
		this.afficherMenu(contentPane, ControleurBase.Menus.TOURNOIS);
		this.changerOnglet(ControleurBase.Menus.TOURNOIS);
	}
	
	/**
	 * Affiche le menu de l'application
	 * @param panel de type BorderLayout
	 */
	public void afficherMenu(JPanel panel, ControleurBase.Menus actif) {
		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panel.add(panelMenu, BorderLayout.NORTH);
		panelMenu.setLayout(new GridLayout(1, 0, 20, 0));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(VueBase.class.getResource("/images/logo_menu.png")));
		panelMenu.add(lblLogo);
		
		JPanel panelCentre = new JPanel();
		FlowLayout fl_panelCentre = (FlowLayout) panelCentre.getLayout();
		fl_panelCentre.setVgap(0);
		fl_panelCentre.setHgap(0);
		panelCentre.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panelMenu.add(panelCentre);
		
		JPanel panelMenuCentre = new JPanel();
		panelMenuCentre.setBorder(null);
		panelMenuCentre.setBackground(new Color(18, 26, 55));
		panelCentre.add(panelMenuCentre);
		GridBagLayout gbl_panelMenuCentre = new GridBagLayout();
		gbl_panelMenuCentre.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelMenuCentre.rowHeights = new int[] {35, 0, 0};
		gbl_panelMenuCentre.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelMenuCentre.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelMenuCentre.setLayout(gbl_panelMenuCentre);
		
		JButtonMenu btnTournois = new JButtonMenu("", ControleurBase.Menus.TOURNOIS);
		btnTournois.setToolTipText("Tournois");
		btnTournois.addMouseListener(this.controleurMenu);
		if(actif == ControleurBase.Menus.TOURNOIS) {
			this.controleurMenu.setBoutonActif(btnTournois);
		}
		this.controleurMenu.ajouterBoutonMenu(btnTournois);
		GridBagConstraints gbc_btnTournois = new GridBagConstraints();
		gbc_btnTournois.insets = new Insets(0, 0, 5, 10);
		gbc_btnTournois.gridx = 1;
		gbc_btnTournois.gridy = 0;
		panelMenuCentre.add(btnTournois, gbc_btnTournois);
		
		JButtonMenu btnEquipes = new JButtonMenu("", ControleurBase.Menus.EQUIPES);
		btnEquipes.setToolTipText("Équipes");
		btnEquipes.addMouseListener(this.controleurMenu);
		if(actif == ControleurBase.Menus.EQUIPES) {
			this.controleurMenu.setBoutonActif(btnEquipes);
		}
		this.controleurMenu.ajouterBoutonMenu(btnEquipes);
		GridBagConstraints gbc_btnEquipes = new GridBagConstraints();
		gbc_btnEquipes.anchor = GridBagConstraints.NORTH;
		gbc_btnEquipes.insets = new Insets(0, 0, 5, 10);
		gbc_btnEquipes.gridx = 2;
		gbc_btnEquipes.gridy = 0;
		panelMenuCentre.add(btnEquipes, gbc_btnEquipes);
		
		JButtonMenu btnHistorique = new JButtonMenu("", ControleurBase.Menus.HISTORIQUE);
		btnHistorique.setToolTipText("Historique des points");
		btnHistorique.addMouseListener(this.controleurMenu);
		if(actif == ControleurBase.Menus.HISTORIQUE) {
			this.controleurMenu.setBoutonActif(btnHistorique);
		}
		this.controleurMenu.ajouterBoutonMenu(btnHistorique);
		GridBagConstraints gbc_btnHistorique = new GridBagConstraints();
		gbc_btnHistorique.insets = new Insets(0, 0, 5, 10);
		gbc_btnHistorique.gridx = 3;
		gbc_btnHistorique.gridy = 0;
		panelMenuCentre.add(btnHistorique, gbc_btnHistorique);

		JButtonMenu btnPalmares = new JButtonMenu("", ControleurBase.Menus.PALMARES);
		btnPalmares.setToolTipText("Palmarès");
		btnPalmares.addMouseListener(this.controleurMenu);
		if(actif == ControleurBase.Menus.PALMARES) {
			this.controleurMenu.setBoutonActif(btnPalmares);
		}
		this.controleurMenu.ajouterBoutonMenu(btnPalmares);
		GridBagConstraints gbc_btnPalmares = new GridBagConstraints();
		gbc_btnPalmares.anchor = GridBagConstraints.NORTH;
		gbc_btnPalmares.insets = new Insets(0, 0, 5, 0);
		gbc_btnPalmares.gridx = 4;
		gbc_btnPalmares.gridy = 0;
		panelMenuCentre.add(btnPalmares, gbc_btnPalmares);
		
		JLabel lblTournois = new JLabel("Tournois");
		lblTournois.setForeground(Color.WHITE);
		lblTournois.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblTournois = new GridBagConstraints();
		gbc_lblTournois.anchor = GridBagConstraints.NORTH;
		gbc_lblTournois.insets = new Insets(0, 0, 0, 10);
		gbc_lblTournois.gridx = 1;
		gbc_lblTournois.gridy = 1;
		panelMenuCentre.add(lblTournois, gbc_lblTournois);
		
		JLabel lblEquipes = new JLabel("Équipes");
		lblEquipes.setForeground(Color.WHITE);
		lblEquipes.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblEquipes = new GridBagConstraints();
		gbc_lblEquipes.anchor = GridBagConstraints.NORTH;
		gbc_lblEquipes.insets = new Insets(0, 0, 0, 10);
		gbc_lblEquipes.gridx = 2;
		gbc_lblEquipes.gridy = 1;
		panelMenuCentre.add(lblEquipes, gbc_lblEquipes);
		
		JLabel lblHistorique = new JLabel("Historique");
		lblHistorique.setForeground(Color.WHITE);
		lblHistorique.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblHistorique = new GridBagConstraints();
		gbc_lblHistorique.anchor = GridBagConstraints.NORTH;
		gbc_lblHistorique.insets = new Insets(0, 0, 0, 10);
		gbc_lblHistorique.gridx = 3;
		gbc_lblHistorique.gridy = 1;
		panelMenuCentre.add(lblHistorique, gbc_lblHistorique);
		
		JLabel lblPalmares = new JLabel("Palmarès");
		lblPalmares.setForeground(Color.WHITE);
		lblPalmares.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblPalmares = new GridBagConstraints();
		gbc_lblPalmares.anchor = GridBagConstraints.NORTH;
		gbc_lblPalmares.gridx = 4;
		gbc_lblPalmares.gridy = 1;
		panelMenuCentre.add(lblPalmares, gbc_lblPalmares);
		
		JPanel panelDroite = new JPanel();
		FlowLayout fl_panelDroite = (FlowLayout) panelDroite.getLayout();
		fl_panelDroite.setVgap(0);
		fl_panelDroite.setHgap(0);
		fl_panelDroite.setAlignment(FlowLayout.RIGHT);
		panelDroite.setBackground(new Color(18, 26, 55));
		panelMenu.add(panelDroite);
		
		JPanel panelMenuDroite = new JPanel();
		panelMenuDroite.setBackground(new Color(18, 26, 55));
		panelDroite.add(panelMenuDroite);
		GridBagLayout gbl_panelMenuDroite = new GridBagLayout();
		gbl_panelMenuDroite.columnWidths = new int[]{0, 0, 0};
		gbl_panelMenuDroite.rowHeights = new int[] {35, 0, 0};
		gbl_panelMenuDroite.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelMenuDroite.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelMenuDroite.setLayout(gbl_panelMenuDroite);
		
		JButtonMenu btnUtilisateur = new JButtonMenu("", ControleurBase.Menus.UTILISATEUR);
		btnUtilisateur.setToolTipText("Admin");
		btnUtilisateur.addMouseListener(this.controleurMenu);
		this.controleurMenu.ajouterBoutonMenu(btnUtilisateur);
		GridBagConstraints gbc_btnUtilisateur = new GridBagConstraints();
		gbc_btnUtilisateur.anchor = GridBagConstraints.NORTH;
		gbc_btnUtilisateur.insets = new Insets(0, 0, 5, 10);
		gbc_btnUtilisateur.gridx = 0;
		gbc_btnUtilisateur.gridy = 0;
		panelMenuDroite.add(btnUtilisateur, gbc_btnUtilisateur);
		
		JButtonMenu btnDeconnexion = new JButtonMenu("", ControleurBase.Menus.DECONNEXION);
		btnDeconnexion.setToolTipText("Déconnexion");
		btnDeconnexion.addMouseListener(this.controleurMenu);
		this.controleurMenu.ajouterBoutonMenu(btnDeconnexion);
		GridBagConstraints gbc_btnDeconnexion = new GridBagConstraints();
		gbc_btnDeconnexion.anchor = GridBagConstraints.NORTH;
		gbc_btnDeconnexion.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeconnexion.gridx = 1;
		gbc_btnDeconnexion.gridy = 0;
		panelMenuDroite.add(btnDeconnexion, gbc_btnDeconnexion);
		
		JLabel lblUtilisateur = new JLabel("Admin");
		lblUtilisateur.setForeground(Color.WHITE);
		lblUtilisateur.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblUtilisateur = new GridBagConstraints();
		gbc_lblUtilisateur.anchor = GridBagConstraints.NORTH;
		gbc_lblUtilisateur.insets = new Insets(0, 0, 0, 10);
		gbc_lblUtilisateur.gridx = 0;
		gbc_lblUtilisateur.gridy = 1;
		panelMenuDroite.add(lblUtilisateur, gbc_lblUtilisateur);
		
		JLabel lblDeconnexion = new JLabel("Déconnexion");
		lblDeconnexion.setForeground(Color.WHITE);
		lblDeconnexion.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblDeconnexion = new GridBagConstraints();
		gbc_lblDeconnexion.anchor = GridBagConstraints.NORTH;
		gbc_lblDeconnexion.gridx = 1;
		gbc_lblDeconnexion.gridy = 1;
		panelMenuDroite.add(lblDeconnexion, gbc_lblDeconnexion);
	}
	
	public void changerOnglet(ControleurBase.Menus menu) {
		if(menu.getEstActivable()) {
			this.contenu.removeAll();
			this.contenu.updateUI();
		}
		
		switch(menu) {
		case TOURNOIS:
			VueTournois vueTournois = new VueTournois();
			vueTournois.afficherVueTournois(this.contenu);
			break;
		case EQUIPES:
			VueEquipes vueEquipe = new VueEquipes();
			vueEquipe.afficherVueEquipe(this.contenu);
			break;
		case HISTORIQUE:
			break;
		case PALMARES:
			break;
		default:
			break;
		}
		
		this.contenu.revalidate();
	    this.contenu.repaint();
	}

}
