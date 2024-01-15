package vue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Vector;

import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurPoule;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.ThemeTableCellRenderer;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Insets;

import javax.swing.ImageIcon;

/**
 * Vue de la saisie d'une poule
 */
public class VuePoule extends JFrameTheme {

	private JTableTheme table;
	private boolean[][] isActif;
	
	private VueTournois vueTournois;
	
	/**
     * Constructeur de l'IHM pour la saisie d'un poule
     * @param vueTournois La vue principale des tournois
     * @param tournoi Le tournoi en cours
	 * @param poule La poule en cours
     */
	public VuePoule(VueTournois vueTournois, Tournoi tournoi, Poule poule) {
		this.vueTournois = vueTournois;
		
		ControleurPoule controleur = new ControleurPoule(this, tournoi);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 550);

		JPanel contentPane = super.getContentPane();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(0, 20));

		// Panel du header
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(CharteGraphique.FOND);
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));

		// Panel du titre
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		panelHeader.add(panelTitre, BorderLayout.WEST);
		GridBagLayout gbl_panelTitre = new GridBagLayout();
		gbl_panelTitre.columnWeights = new double[] { 0.0 };
		gbl_panelTitre.rowWeights = new double[] { 0.0, 0.0 };
		panelTitre.setLayout(gbl_panelTitre);

		// Label du titre 
		JLabelTheme lblTitre;
		// Si la poule n'est pas une finale on affiche "Poule de qualifications" sinon on affiche "Poule finale"
		if (!poule.getEstFinale()) {
			lblTitre = new JLabelTheme("Poule de qualifications", 19, true);
		} else {
			lblTitre = new JLabelTheme("Poule finale", 19, true);
		}
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panelTitre.add(lblTitre, gbc_lblTitre);

		// Label nom tournoi
		JLabelTheme lblTournoi = new JLabelTheme(tournoi.getNomTournoi(), 16, false);
		GridBagConstraints gbc_lblTournoi = new GridBagConstraints();
		gbc_lblTournoi.insets = new Insets(0, 0, 0, 5);
		gbc_lblTournoi.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTournoi.gridx = 0;
		gbc_lblTournoi.gridy = 1;
		panelTitre.add(lblTournoi, gbc_lblTournoi);

		// Bouton de clôture de la poule
		JButtonTheme btnCloturerPoule = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Clôturer la poule");
		btnCloturerPoule.addActionListener(controleur);
		btnCloturerPoule.setFont(CharteGraphique.getPolice(17, false));

		// Panel du bouton de clôture de la poule
		JPanel panelBtnCloturerPoule = new JPanel();
		panelBtnCloturerPoule.setBackground(CharteGraphique.FOND);
		panelBtnCloturerPoule.setLayout(new BorderLayout());
		panelBtnCloturerPoule.add(btnCloturerPoule, BorderLayout.NORTH);

		panelHeader.add(panelBtnCloturerPoule, BorderLayout.EAST);

		// Panel du tableau
		JPanel panelTableau = new JPanel();
		panelTableau.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTableau, BorderLayout.CENTER);
		panelTableau.setLayout(new BorderLayout(0, 10));

		if (poule.getEstFinale()) {
			JPanel panelVersus = new JPanel();
			panelVersus.setBackground(CharteGraphique.FOND);
			panelTableau.add(panelVersus, BorderLayout.NORTH);
			GridBagLayout gbl_panelVersus = new GridBagLayout();
			gbl_panelVersus.rowHeights = new int[]{49, 0};
			gbl_panelVersus.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panelVersus.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			panelVersus.setLayout(gbl_panelVersus);
			
			Equipe[] equipes = poule.getRencontres().get(0).getEquipes();
			
			JPanel panelLblEquipe1 = new JPanel();
			panelLblEquipe1.setBackground(CharteGraphique.FOND);
			GridBagConstraints gbc_panelEquipe1 = new GridBagConstraints();
			gbc_panelEquipe1.anchor = GridBagConstraints.WEST;
			gbc_panelEquipe1.insets = new Insets(0, 0, 0, 5);
			gbc_panelEquipe1.gridx = 0;
			gbc_panelEquipe1.gridy = 0;
			panelVersus.add(panelLblEquipe1, gbc_panelEquipe1);
			
			JLabelTheme lblEquipe1 = new JLabelTheme(equipes[0].getNom(), 25, true);
			lblEquipe1.setIcon(equipes[0].getPays().getDrapeauPays());
			panelLblEquipe1.add(lblEquipe1);
			
			JPanel panelVS = new JPanel();
			panelVS.setBackground(CharteGraphique.FOND);
			GridBagConstraints gbc_panelVS = new GridBagConstraints();
			gbc_panelVS.insets = new Insets(0, 0, 0, 5);
			gbc_panelVS.gridx = 1;
			gbc_panelVS.gridy = 0;
			panelVersus.add(panelVS, gbc_panelVS);
			
			JLabelTheme lblVS = new JLabelTheme("VS", 15, false);
			panelVS.add(lblVS);
			
			JPanel panelEquipe2 = new JPanel();
			panelEquipe2.setBackground(CharteGraphique.FOND);
			GridBagConstraints gbc_panelEquipe2 = new GridBagConstraints();
			gbc_panelEquipe2.anchor = GridBagConstraints.EAST;
			gbc_panelEquipe2.gridx = 2;
			gbc_panelEquipe2.gridy = 0;
			panelVersus.add(panelEquipe2, gbc_panelEquipe2);
			
			JLabelTheme lblEquipe2 = new JLabelTheme(equipes[1].getNom(), 25, true);
			lblEquipe2.setHorizontalTextPosition(SwingConstants.LEFT);
			lblEquipe2.setIcon(equipes[1].getPays().getDrapeauPays());
			panelEquipe2.add(lblEquipe2);
		}
		
		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panelTableau.add(scrollPane, BorderLayout.CENTER);

		// Création de la table
		this.table = new JTableTheme();
		this.table.addMouseListener(controleur);
		this.table.setBackground(CharteGraphique.FOND);
		this.table.setFont(CharteGraphique.getPolice(16, false));

		// Création du model de la table
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] { "ID", "Match", "Équipe 1", "Équipe 2" }
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		this.isActif = new boolean[poule.getRencontres().size()][4];

		// Ajout des lignes
		for (int i = 0; i < poule.getRencontres().size(); i++) {
			Rencontre rencontre = poule.getRencontres().get(i);
			Vector<Object> row = new Vector<>();
			row.add(rencontre.getIdRencontre());
			row.add(i + 1);
			row.add(rencontre.getEquipes()[0].getNom());
			row.add(rencontre.getEquipes()[1].getNom());
			model.addRow(row);

			// Vérification si une équipe gagnante est définie
			if (rencontre.getIdEquipeGagnante() != 0) {
				// Si l'équipe gagnante est l'équipe 1, on active l'icone de la colonne 2
				if (rencontre.getEquipes()[0].getIdEquipe() == rencontre.getIdEquipeGagnante()) {
					this.isActif[i][2] = true;
				}
				// Sinon l'équipe gagnante est l'équipe 2, on active l'icone de la colonne 3
				else if (rencontre.getEquipes()[1].getIdEquipe() == rencontre.getIdEquipeGagnante()) {
					this.isActif[i][3] = true;
				}
			}
		}
		this.table.setModel(model);

		// Masquage de la colonne ID (sert pour obtenir la Poule d'une ligne dont un bouton est cliqué)
		TableColumn idColumn = this.table.getColumnModel().getColumn(0);
		idColumn.setMinWidth(1); // 1px pour garder la bordure
		idColumn.setMaxWidth(1);
		idColumn.setWidth(1);
		idColumn.setPreferredWidth(1);
		
		// Création du renderer pour les colonnes "Equipe 1" et "Equipe 2"
		ThemeTableCellRenderer renderer = new ThemeTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (isActif[row][column]) {
					label.setIcon(new ImageIcon(VuePoule.class.getResource("/images/match/trophe_actif.png")));
				} else {
					label.setIcon(new ImageIcon(VuePoule.class.getResource("/images/match/trophe.png")));
				}
				
				// Aligne le contenu à gauche
				label.setHorizontalAlignment(JLabel.LEFT);
				
				// Ajout d'une marge de 20px à gauche tout en gardant la bordure initiale
				Border border = label.getBorder();
				Border margin = new EmptyBorder(0, 20, 0, 0);
				label.setBorder(new CompoundBorder(border, margin));
				
				// Espace de 10px entre l'icone et le texte
				label.setIconTextGap(10);
				
				return label;
			}
		};

		// Application du renderer aux colonnes "Equipe 1" et "Equipe 2"
		this.table.getColumnModel().getColumn(2).setCellRenderer(renderer);
		this.table.getColumnModel().getColumn(3).setCellRenderer(renderer);

		// Largeur de la colonne "Match"
		TableColumn matchColumn = this.table.getColumnModel().getColumn(1);
		matchColumn.setPreferredWidth(80);
		matchColumn.setMaxWidth(80);

		scrollPane.setViewportView(this.table);

		// Création des boutons
		JButtonTheme btnFermer = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Fermer");
		btnFermer.addActionListener(controleur);
		btnFermer.setFont(CharteGraphique.getPolice(17, false));

		JButtonTheme btnEtatsResultats = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "État des résultats");
		btnEtatsResultats.addActionListener(controleur);
		btnEtatsResultats.setFont(CharteGraphique.getPolice(17, false));

		// Création du panel pour les boutons
		JPanel panelBoutons = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(10);
		panelBoutons.setLayout(layout);
		panelBoutons.setBackground(CharteGraphique.FOND);

		// Ajout des boutons au panel
		panelBoutons.add(btnFermer);
		panelBoutons.add(btnEtatsResultats);

		// Ajout du panel à contentPane
		contentPane.add(panelBoutons, BorderLayout.SOUTH);
	}

	/*
	 * Méthode permettant de vérifier si la source de l'événement est le tableau des rencontres
	 * @param source La source de l'événement
	 * @return true si la source est le tableau des rencontres, false sinon
	 */
	public boolean estTableauRencontres(Object source) {
		return this.table.equals(source);
	}

	/*
	 * Méthode permettant de récupérer la ligne de la rencontre cliquée
	 * @return La ligne de la rencontre cliquée
	 */
	public void toggleGagnant(int ligne, int col) {
		// Active la cellule cliquée
		isActif[ligne][col] = true;
	
		// Désactive les autres cellules de la rangée
		for (int autreCol = 0; autreCol < table.getColumnCount(); autreCol++) {
			if (autreCol != col) {
				isActif[ligne][autreCol] = false;
			}
		}
	
		table.repaint();
	}

	/**
	 * Désactive l'icone de la colonne col de la ligne ligne
	 * @param ligne ligne de la rencontre
	 * @param col colonne de l'équipe
	 */
	public void resetGagnant(int ligne, int col) {
		// Désactive la cellule cliquée
		isActif[ligne][col] = false;

		table.repaint();
	}

	/**
	 * Ouvre la fenêtre d'état des résultats du tournoi dans la vue tournois
	 * @param tournoi le tournoi dont on veut afficher les résultats
	 */
	public void afficherVueEtatResultatsTournoi(Tournoi tournoi) {
		this.vueTournois.afficherVueEtatResultatsTournoi(tournoi);
    }

	/**
	 * Retourne la vue tournois fenêtre parente
	 * @return Vue tournois
	 */
	public VueTournois getVueTournois() {
		return this.vueTournois;
	}

}