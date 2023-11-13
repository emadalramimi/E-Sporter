package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurMenu;
import vue.theme.CharteGraphique;
import vue.theme.JButtonMenu;

public class VueMenu {

	/**
	 * Affiche le menu de l'application
	 * @param contentPane de type BorderLayout
	 */
	public static void afficherMenu(JPanel contentPane, ControleurMenu.Menus actif) {
		ControleurMenu controleur = new ControleurMenu();
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(CharteGraphique.FOND_SECONDAIRE);
		contentPane.add(panelMenu, BorderLayout.NORTH);
		panelMenu.setLayout(new GridLayout(1, 0, 20, 0));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\logo_menu.png"));
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
		
		JButtonMenu btnTournois = new JButtonMenu("", ControleurMenu.Menus.TOURNOIS);
		btnTournois.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\menu\\tournois.png"));
		btnTournois.setToolTipText("Tournois");
		btnTournois.addMouseListener(controleur);
		if(actif == ControleurMenu.Menus.TOURNOIS) {
			controleur.setBoutonActif(btnTournois);
		}
		controleur.ajouterBoutonMenu(btnTournois);
		GridBagConstraints gbc_btnTournois = new GridBagConstraints();
		gbc_btnTournois.insets = new Insets(0, 0, 5, 10);
		gbc_btnTournois.gridx = 1;
		gbc_btnTournois.gridy = 0;
		panelMenuCentre.add(btnTournois, gbc_btnTournois);
		
		JButtonMenu btnEquipes = new JButtonMenu("", ControleurMenu.Menus.EQUIPES);
		btnEquipes.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\menu\\equipes.png"));
		btnEquipes.setToolTipText("Équipes");
		btnEquipes.addMouseListener(controleur);
		if(actif == ControleurMenu.Menus.EQUIPES) {
			controleur.setBoutonActif(btnEquipes);
		}
		controleur.ajouterBoutonMenu(btnEquipes);
		GridBagConstraints gbc_btnEquipes = new GridBagConstraints();
		gbc_btnEquipes.anchor = GridBagConstraints.NORTH;
		gbc_btnEquipes.insets = new Insets(0, 0, 5, 10);
		gbc_btnEquipes.gridx = 2;
		gbc_btnEquipes.gridy = 0;
		panelMenuCentre.add(btnEquipes, gbc_btnEquipes);
		
		JButtonMenu btnHistorique = new JButtonMenu("", ControleurMenu.Menus.HISTORIQUE);
		btnHistorique.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\menu\\historique.png"));
		btnHistorique.setToolTipText("Historique des points");
		btnHistorique.addMouseListener(controleur);
		if(actif == ControleurMenu.Menus.HISTORIQUE) {
			controleur.setBoutonActif(btnHistorique);
		}
		controleur.ajouterBoutonMenu(btnHistorique);
		GridBagConstraints gbc_btnHistorique = new GridBagConstraints();
		gbc_btnHistorique.insets = new Insets(0, 0, 5, 10);
		gbc_btnHistorique.gridx = 3;
		gbc_btnHistorique.gridy = 0;
		panelMenuCentre.add(btnHistorique, gbc_btnHistorique);

		JButtonMenu btnPalmares = new JButtonMenu("", ControleurMenu.Menus.PALMARES);
		btnPalmares.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\menu\\palmares.png"));
		btnPalmares.setToolTipText("Palmarès");
		btnPalmares.addMouseListener(controleur);
		if(actif == ControleurMenu.Menus.PALMARES) {
			controleur.setBoutonActif(btnPalmares);
		}
		controleur.ajouterBoutonMenu(btnPalmares);
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
		
		JButtonMenu btnUtilisateur = new JButtonMenu("", ControleurMenu.Menus.UTILISATEUR);
		btnUtilisateur.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\menu\\profil.png"));
		btnUtilisateur.setToolTipText("Admin");
		btnUtilisateur.addMouseListener(controleur);
		controleur.ajouterBoutonMenu(btnUtilisateur);
		GridBagConstraints gbc_btnUtilisateur = new GridBagConstraints();
		gbc_btnUtilisateur.anchor = GridBagConstraints.NORTH;
		gbc_btnUtilisateur.insets = new Insets(0, 0, 5, 10);
		gbc_btnUtilisateur.gridx = 0;
		gbc_btnUtilisateur.gridy = 0;
		panelMenuDroite.add(btnUtilisateur, gbc_btnUtilisateur);
		
		JButtonMenu btnDeconnexion = new JButtonMenu("", ControleurMenu.Menus.DECONNEXION);
		btnDeconnexion.setIcon(new ImageIcon("C:\\Users\\Nassim\\Desktop\\IUT\\SAE\\S3\\workspace-s3-divers\\S3A01_E-Sporter\\assets\\images\\menu\\deconnexion.png"));
		btnDeconnexion.setToolTipText("Déconnexion");
		btnDeconnexion.addMouseListener(controleur);
		controleur.ajouterBoutonMenu(btnDeconnexion);
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
	
	/**
	 * Modifie l'aspect du bouton : actif en couleur et non actif en blanc
	 * @param actif : état de l'activité du bouton
	 * @param bouton : bouton à modifier l'activité
	 */
	public static void setBoutonActif(boolean actif, JButtonMenu bouton) {
		String cheminIcone = bouton.getIcon().toString();
		if(actif) {
	        cheminIcone = cheminIcone.replace(".png", "_actif.png");
	        bouton.setIcon(new ImageIcon(cheminIcone));
		} else {
	        cheminIcone = cheminIcone.replace("_actif.png", ".png");
	        bouton.setIcon(new ImageIcon(cheminIcone));
		}
	}
	
}
