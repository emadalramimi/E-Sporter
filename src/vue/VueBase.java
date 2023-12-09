package vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurBase;
import modele.BDD;
import modele.ModeleUtilisateur;
import modele.metier.Administrateur;
import modele.metier.Tournoi;
import modele.metier.Utilisateur;
import vue.theme.CharteGraphique;
import vue.theme.JButtonMenu;
import vue.theme.JFrameTheme;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

/**
 * Vue qui sert de base pour les fenêtres principales (contenant le menu)
 */
public class VueBase extends JFrameTheme {
	
	private JPanel contenu;
	private JLabel lblUtilisateur;
	private JButtonMenu btnUtilisateur;
	
	private ControleurBase controleurBase;
	
	/**
	 * Crée la VueBase
	 */
	public VueBase() {
		this.controleurBase = new ControleurBase(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 700);
		
		// contentPane, un panel contenant tous les éléments de la fenêtre
		JPanel contentPane = super.getContentPane();
		
		// Panel de contenu de l'onglet
		this.contenu = new JPanel();
		this.contenu.setBackground(CharteGraphique.FOND);
		contentPane.add(this.contenu, BorderLayout.CENTER);
		this.contenu.setLayout(new BorderLayout(0, 20));
		
		// Affichage du menu
		this.afficherMenu(contentPane, ControleurBase.Menus.TOURNOIS);
		this.changerOnglet(ControleurBase.Menus.TOURNOIS);
		
		// Affichage de l'utilisateur courant
		Utilisateur compteCourant = ModeleUtilisateur.getCompteCourant();
		if(compteCourant.getRole() == Utilisateur.Role.ADMINISTRATEUR) {
			Administrateur administrateurCourant = (Administrateur) compteCourant;
			this.lblUtilisateur.setText(administrateurCourant.getPrenom());
			String description = administrateurCourant.getPrenom() + " " + administrateurCourant.getNom();
			this.lblUtilisateur.setToolTipText(description);
			this.btnUtilisateur.setToolTipText(description);
		} else if(compteCourant.getRole() == Utilisateur.Role.ARBITRE) {
			this.lblUtilisateur.setText("Arbitre");
			Tournoi tournoiCourant = (Tournoi) compteCourant;
			this.lblUtilisateur.setToolTipText(tournoiCourant.getNomTournoi());
		}
	}
	
	/**
	 * Affiche le menu de l'application
	 * @param panel de type BorderLayout
	 */
	public void afficherMenu(JPanel panel, ControleurBase.Menus actif) {
		// Panel du menu
		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelMenu.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panel.add(panelMenu, BorderLayout.NORTH);
		panelMenu.setLayout(new GridLayout(1, 0, 20, 0));
		
		// Logo
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(VueBase.class.getResource("/images/logo_menu.png")));
		panelMenu.add(lblLogo);
		
		// Panel centre
		JPanel panelCentre = new JPanel();
		FlowLayout fl_panelCentre = (FlowLayout) panelCentre.getLayout();
		fl_panelCentre.setVgap(0);
		fl_panelCentre.setHgap(0);
		panelCentre.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panelMenu.add(panelCentre);
		
		// Panel du menu au centre
		JPanel panelMenuCentre = new JPanel();
		panelMenuCentre.setBorder(null);
		panelMenuCentre.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panelCentre.add(panelMenuCentre);
		GridBagLayout gbl_panelMenuCentre = new GridBagLayout();
		gbl_panelMenuCentre.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panelMenuCentre.rowHeights = new int[] {35, 0, 0};
		gbl_panelMenuCentre.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelMenuCentre.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelMenuCentre.setLayout(gbl_panelMenuCentre);
		
		// Bouton tournois
		JButtonMenu btnTournois = new JButtonMenu("", ControleurBase.Menus.TOURNOIS);
		btnTournois.setToolTipText("Tournois");
		btnTournois.addMouseListener(this.controleurBase);
		// Si le menu actuel est tournoi
		if(actif == ControleurBase.Menus.TOURNOIS) {
			this.controleurBase.setBoutonActif(btnTournois);
		}
		this.controleurBase.ajouterBoutonMenu(btnTournois);
		GridBagConstraints gbc_btnTournois = new GridBagConstraints();
		gbc_btnTournois.insets = new Insets(0, 0, 5, 10);
		gbc_btnTournois.gridx = 1;
		gbc_btnTournois.gridy = 0;
		panelMenuCentre.add(btnTournois, gbc_btnTournois);
		
		// Bouton équipes
		JButtonMenu btnEquipes = new JButtonMenu("", ControleurBase.Menus.EQUIPES);
		btnEquipes.setToolTipText("Équipes");
		btnEquipes.addMouseListener(this.controleurBase);
		// Si le menu actuel est équipes
		if(actif == ControleurBase.Menus.EQUIPES) {
			this.controleurBase.setBoutonActif(btnEquipes);
		}
		this.controleurBase.ajouterBoutonMenu(btnEquipes);
		GridBagConstraints gbc_btnEquipes = new GridBagConstraints();
		gbc_btnEquipes.anchor = GridBagConstraints.NORTH;
		gbc_btnEquipes.insets = new Insets(0, 0, 5, 10);
		gbc_btnEquipes.gridx = 2;
		gbc_btnEquipes.gridy = 0;
		panelMenuCentre.add(btnEquipes, gbc_btnEquipes);
		
		// Bouton historique
		JButtonMenu btnHistorique = new JButtonMenu("", ControleurBase.Menus.HISTORIQUE);
		btnHistorique.setToolTipText("Historique des points");
		btnHistorique.addMouseListener(this.controleurBase);
		// Si le menu actuel est historique
		if(actif == ControleurBase.Menus.HISTORIQUE) {
			this.controleurBase.setBoutonActif(btnHistorique);
		}
		this.controleurBase.ajouterBoutonMenu(btnHistorique);
		GridBagConstraints gbc_btnHistorique = new GridBagConstraints();
		gbc_btnHistorique.insets = new Insets(0, 0, 5, 10);
		gbc_btnHistorique.gridx = 3;
		gbc_btnHistorique.gridy = 0;
		panelMenuCentre.add(btnHistorique, gbc_btnHistorique);

		// Bouton palmarès
		JButtonMenu btnPalmares = new JButtonMenu("", ControleurBase.Menus.PALMARES);
		btnPalmares.setToolTipText("Palmarès");
		btnPalmares.addMouseListener(this.controleurBase);
		// Si le menu actuel est palmarès
		if(actif == ControleurBase.Menus.PALMARES) {
			this.controleurBase.setBoutonActif(btnPalmares);
		}
		this.controleurBase.ajouterBoutonMenu(btnPalmares);
		GridBagConstraints gbc_btnPalmares = new GridBagConstraints();
		gbc_btnPalmares.anchor = GridBagConstraints.NORTH;
		gbc_btnPalmares.insets = new Insets(0, 0, 5, 0);
		gbc_btnPalmares.gridx = 4;
		gbc_btnPalmares.gridy = 0;
		panelMenuCentre.add(btnPalmares, gbc_btnPalmares);
		
		// Label du bouton tournois
		JLabel lblTournois = new JLabel("Tournois");
		lblTournois.setForeground(CharteGraphique.TEXTE);
		lblTournois.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblTournois = new GridBagConstraints();
		gbc_lblTournois.anchor = GridBagConstraints.NORTH;
		gbc_lblTournois.insets = new Insets(0, 0, 0, 10);
		gbc_lblTournois.gridx = 1;
		gbc_lblTournois.gridy = 1;
		panelMenuCentre.add(lblTournois, gbc_lblTournois);
		
		// Label du bouton équipes
		JLabel lblEquipes = new JLabel("Équipes");
		lblEquipes.setForeground(CharteGraphique.TEXTE);
		lblEquipes.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblEquipes = new GridBagConstraints();
		gbc_lblEquipes.anchor = GridBagConstraints.NORTH;
		gbc_lblEquipes.insets = new Insets(0, 0, 0, 10);
		gbc_lblEquipes.gridx = 2;
		gbc_lblEquipes.gridy = 1;
		panelMenuCentre.add(lblEquipes, gbc_lblEquipes);
		
		// Label du bouton historique
		JLabel lblHistorique = new JLabel("Historique");
		lblHistorique.setForeground(CharteGraphique.TEXTE);
		lblHistorique.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblHistorique = new GridBagConstraints();
		gbc_lblHistorique.anchor = GridBagConstraints.NORTH;
		gbc_lblHistorique.insets = new Insets(0, 0, 0, 10);
		gbc_lblHistorique.gridx = 3;
		gbc_lblHistorique.gridy = 1;
		panelMenuCentre.add(lblHistorique, gbc_lblHistorique);
		
		// Label du bouton palmarès
		JLabel lblPalmares = new JLabel("Palmarès");
		lblPalmares.setForeground(CharteGraphique.TEXTE);
		lblPalmares.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblPalmares = new GridBagConstraints();
		gbc_lblPalmares.anchor = GridBagConstraints.NORTH;
		gbc_lblPalmares.gridx = 4;
		gbc_lblPalmares.gridy = 1;
		panelMenuCentre.add(lblPalmares, gbc_lblPalmares);
		
		// Panel de droite
		JPanel panelDroite = new JPanel();
		FlowLayout fl_panelDroite = (FlowLayout) panelDroite.getLayout();
		fl_panelDroite.setVgap(0);
		fl_panelDroite.setHgap(0);
		fl_panelDroite.setAlignment(FlowLayout.RIGHT);
		panelDroite.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panelMenu.add(panelDroite);
		
		// Panel du menu à droite
		JPanel panelMenuDroite = new JPanel();
		panelMenuDroite.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panelDroite.add(panelMenuDroite);
		GridBagLayout gbl_panelMenuDroite = new GridBagLayout();
		gbl_panelMenuDroite.columnWidths = new int[]{0, 0, 0};
		gbl_panelMenuDroite.rowHeights = new int[] {35, 0, 0};
		gbl_panelMenuDroite.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelMenuDroite.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelMenuDroite.setLayout(gbl_panelMenuDroite);
		
		// Bouton utilisateur (bouton qui ne mène pas à un onglet)
		this.btnUtilisateur = new JButtonMenu("", ControleurBase.Menus.UTILISATEUR);
		this.btnUtilisateur.addMouseListener(this.controleurBase);
		this.controleurBase.ajouterBoutonMenu(this.btnUtilisateur);
		GridBagConstraints gbc_btnUtilisateur = new GridBagConstraints();
		gbc_btnUtilisateur.anchor = GridBagConstraints.NORTH;
		gbc_btnUtilisateur.insets = new Insets(0, 0, 5, 10);
		gbc_btnUtilisateur.gridx = 0;
		gbc_btnUtilisateur.gridy = 0;
		panelMenuDroite.add(this.btnUtilisateur, gbc_btnUtilisateur);
		
		// Bouton déconnexion (bouton qui ne mène pas à un onglet)
		JButtonMenu btnDeconnexion = new JButtonMenu("", ControleurBase.Menus.DECONNEXION);
		btnDeconnexion.setToolTipText("Déconnexion");
		btnDeconnexion.addMouseListener(this.controleurBase);
		this.controleurBase.ajouterBoutonMenu(btnDeconnexion);
		GridBagConstraints gbc_btnDeconnexion = new GridBagConstraints();
		gbc_btnDeconnexion.anchor = GridBagConstraints.NORTH;
		gbc_btnDeconnexion.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeconnexion.gridx = 1;
		gbc_btnDeconnexion.gridy = 0;
		panelMenuDroite.add(btnDeconnexion, gbc_btnDeconnexion);
		
		// Label du bouton utilisateur
		this.lblUtilisateur = new JLabel("");
		this.lblUtilisateur.setForeground(CharteGraphique.TEXTE);
		this.lblUtilisateur.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblUtilisateur = new GridBagConstraints();
		gbc_lblUtilisateur.anchor = GridBagConstraints.NORTH;
		gbc_lblUtilisateur.insets = new Insets(0, 0, 0, 10);
		gbc_lblUtilisateur.gridx = 0;
		gbc_lblUtilisateur.gridy = 1;
		panelMenuDroite.add(this.lblUtilisateur, gbc_lblUtilisateur);
		
		// Label du bouton déconnexion
		JLabel lblDeconnexion = new JLabel("Déconnexion");
		lblDeconnexion.setForeground(CharteGraphique.TEXTE);
		lblDeconnexion.setFont(CharteGraphique.getPolice(11, false));
		GridBagConstraints gbc_lblDeconnexion = new GridBagConstraints();
		gbc_lblDeconnexion.anchor = GridBagConstraints.NORTH;
		gbc_lblDeconnexion.gridx = 1;
		gbc_lblDeconnexion.gridy = 1;
		panelMenuDroite.add(lblDeconnexion, gbc_lblDeconnexion);
	}
	
	/**
	 * Change l'onglet actuel
	 * @param menu : onglet à charger
	 */
	public void changerOnglet(ControleurBase.Menus menu) {
		// Si le menu dispose d'un onglet attribué
		if(menu.getEstActivable()) {
			this.contenu.removeAll();
			this.contenu.updateUI();
		}
		
		// Ouvrir l'onglet en fonction du menu
		switch(menu) {
		case TOURNOIS:
			VueTournois vueTournois = new VueTournois();
			vueTournois.afficherVueTournois(this.contenu, this);
			break;
		case EQUIPES:
			VueEquipes vueEquipe = new VueEquipes();
			vueEquipe.afficherVueEquipe(this.contenu, this);
			break;
		case HISTORIQUE:
			break;
		case PALMARES:
			break;
		default:
			break;
		}
		
		// Mise à jour de l'affichage
		this.contenu.revalidate();
	    this.contenu.repaint();
	}
	
	@Override
	public void fermerFenetre() {
		BDD.fermerConnexion();
		super.fermerFenetre();
	}
	
}
