package vue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import vue.theme.JLabelTheme;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTableThemeImpression;
import vue.theme.LabelIcon;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurHistoriquePoints;
import modele.metier.Equipe;
import modele.metier.HistoriquePoints;
import modele.metier.Pays;

/**
 * IHM équipes
 */
public class VueHistoriquePoints extends RecherchableVue<Equipe> {
	
	private ControleurHistoriquePoints controleur;
	private JTable tableEquipes;
	private JTable tableHistoriquePoints;
	private DefaultTableModel modelTableEquipes;
	private DefaultTableModel modelTableHistoriquePoints;
	private JPanel panel;
	private JLabelTheme lblHistoriquePoints;

	public VueHistoriquePoints() {
		super();
		super.setControleur(new ControleurHistoriquePoints(this));
		this.controleur = (ControleurHistoriquePoints) super.getControleur();
	}
	
	public void afficherVueHistoriquePoints(JPanel contentPane, VueBase vueBase) {
		// panel contient tous les éléments de la page
		panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1020, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0};
		panel.setLayout(gbl_panel);
		
		// panelLabelHistoriquePoints, le panel contenant le label lblHistoriquePoints
		JPanel panelLabelHistoriquePoints = new JPanel();
		GridBagConstraints gbc_panelLabelHistoriquePoints = new GridBagConstraints();
		gbc_panelLabelHistoriquePoints.anchor = GridBagConstraints.NORTH;
		gbc_panelLabelHistoriquePoints.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelLabelHistoriquePoints.insets = new Insets(0, 0, 20, 0);
		gbc_panelLabelHistoriquePoints.gridx = 0;
		gbc_panelLabelHistoriquePoints.gridy = 0;
		panel.add(panelLabelHistoriquePoints, gbc_panelLabelHistoriquePoints);
		panelLabelHistoriquePoints.setBackground(CharteGraphique.FOND);
		panelLabelHistoriquePoints.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblHistoriquePoints = new JLabelTheme("Historique des points", 30, true);
		lblHistoriquePoints.setHorizontalAlignment(SwingConstants.LEFT);
		panelLabelHistoriquePoints.add(lblHistoriquePoints);

		// panelImprimer, le panel contenant le bouton btnImprimer
		JPanel panelImprimer = new JPanel();
		panelImprimer.setBackground(CharteGraphique.FOND);
		FlowLayout flowLayout = (FlowLayout) panelImprimer.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelLabelHistoriquePoints.add(panelImprimer);
		
		// btnImprimer, un bouton pour permettre l'ajout d'une équipe
		JButtonTheme btnImprimer = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Imprimer l'historique sélectionné");
		btnImprimer.addActionListener(this.controleur);
		btnImprimer.setHorizontalAlignment(SwingConstants.RIGHT);
		panelImprimer.add(btnImprimer);
		
		JPanel panelTableauFiltres = new JPanel();
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelRecherche = new GridBagConstraints();
		gbc_panelRecherche.insets = new Insets(0, 0, 20, 0);
		gbc_panelRecherche.fill = GridBagConstraints.BOTH;
		gbc_panelRecherche.gridx = 0;
		gbc_panelRecherche.gridy = 1;
		panel.add(panelTableauFiltres, gbc_panelRecherche);
		panelTableauFiltres.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// Panel de recherche
		panelTableauFiltres.add(super.getPanelRecherche());
		
		JPanel panelTableaux = new JPanel();
		panelTableaux.setBackground(CharteGraphique.FOND);
		panelTableaux.setLayout(new GridLayout(1, 2, 20, 0));

		// Tableau des équipes
		this.modelTableEquipes = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"ID", "Pays", "Nom"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPaneTableEquipes = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPaneTableEquipes = new GridBagConstraints();
		gbc_scrollPaneTableEquipes.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTableEquipes.gridx = 0;
		gbc_scrollPaneTableEquipes.gridy = 2;
		panelTableaux.add(scrollPaneTableEquipes, gbc_scrollPaneTableEquipes);

		// Création de la table des équipes
		this.tableEquipes = new JTableTheme();
		this.tableEquipes.getSelectionModel().addListSelectionListener(this.controleur);
		this.tableEquipes.setModel(this.modelTableEquipes);
		scrollPaneTableEquipes.setViewportView(this.tableEquipes);
		
		// Règles d'affichage du drapeau du pays
		TableColumn paysColumn = tableEquipes.getColumnModel().getColumn(1);
	    paysColumn.setCellRenderer(new ImageTableCellRenderer());
		
		// Masquage de la colonne ID (sert pour obtenir l'HistoriquePoints d'une ligne dont un bouton est cliqué)
		TableColumn idColumn = tableEquipes.getColumnModel().getColumn(0);
		idColumn.setMinWidth(1); // 1px pour garder la bordure
		idColumn.setMaxWidth(1);
		idColumn.setWidth(1);
		idColumn.setPreferredWidth(1);

		// Tableau des historiques de points
		this.modelTableHistoriquePoints = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"Tournoi", "Points"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		// Création de la table des historiques de points
		JScrollPane scrollPaneHistoriquePoints2 = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPaneHistoriquePoints2 = new GridBagConstraints();
		gbc_scrollPaneHistoriquePoints2.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneHistoriquePoints2.gridx = 1;
		gbc_scrollPaneHistoriquePoints2.gridy = 2;
		panelTableaux.add(scrollPaneHistoriquePoints2, gbc_scrollPaneHistoriquePoints2);

		this.tableHistoriquePoints = new JTableTheme();
		this.tableHistoriquePoints.setModel(this.modelTableHistoriquePoints);
		scrollPaneHistoriquePoints2.setViewportView(this.tableHistoriquePoints);

		//panelTableauFiltres.add(panelTableaux);
		GridBagConstraints gbc_panelTableaux = new GridBagConstraints();
		gbc_panelTableaux.fill = GridBagConstraints.BOTH;
		gbc_panelTableaux.gridx = 0;
		gbc_panelTableaux.gridy = 2;
		panel.add(panelTableaux, gbc_panelTableaux);
		
		this.remplirTableau(this.controleur.getEquipes());
	}
	
	/**
	 * Classe interne pour afficher les drapeaux
	 */
	private static class ImageTableCellRenderer extends DefaultTableCellRenderer {
		
		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
			// Affichage du label et de l'icone à gauche
	        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	        LabelIcon labelIcon = (LabelIcon) value;
	        setIcon(labelIcon.getIcon());
	        setText(labelIcon.getText());
	        
	        // Couleur de fond des cellules alternantes
 			if(row % 2 == 0) {
 				this.setBackground(CharteGraphique.FOND_SECONDAIRE);
 			} else {
 				this.setBackground(CharteGraphique.FOND);
 			}
 			
 			// Bordure de la cellule du tableau
 			setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, CharteGraphique.BORDURE));
 			
 			// Police
 			this.setFont(CharteGraphique.getPolice(16, false));
 			this.setForeground(CharteGraphique.TEXTE);
 			
 			// Centrer les textes dans toutes les cellules
			this.setHorizontalAlignment(CENTER);
			this.setVerticalAlignment(CENTER);
	        
	        return label;
	    }
		
	}

	/**
	 * Remplit/met à jour le tableau d'équipes
	 * @param equipes : liste des équipes à mettre dans le tableau
	 */
	@Override
	public void remplirTableau(List<Equipe> equipes) {
		// Vider le tableau
		this.modelTableEquipes.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Equipe equipe : equipes) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(equipe.getIdEquipe());
		    
		    Pays pays = equipe.getPays();
	        ImageIcon drapeau = pays.getDrapeauPays();
	        rowData.add(new LabelIcon(drapeau, equipe.getPays().getNomPays()));

		    rowData.add(equipe.getNom());
			
		    this.modelTableEquipes.addRow(rowData);
		}
		
		// Mise à jour du tableau
		this.tableEquipes.setModel(this.modelTableEquipes);
	}

	/**
	 * Remplit/met à jour le tableau d'historique points
	 * @param equipes : liste des historiques points à mettre dans le tableau
	 */
	public void remplirTableauHistoriquePoints(List<HistoriquePoints> historiquePoints) {
		// Vider le tableau
		this.modelTableHistoriquePoints.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(HistoriquePoints element : historiquePoints) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(element.getTournoi().getNomTournoi());
			rowData.add(element.getPoints());
			
		    this.modelTableHistoriquePoints.addRow(rowData);
		}
		
		// Mise à jour du tableau
		this.tableHistoriquePoints.setModel(this.modelTableHistoriquePoints);
	}

	/**
	 * Retourne le tableau d'équipes
	 * @return le tableau d'équipes
	 */
	public JTable getTableEquipes() {
		return this.tableEquipes;
	}

	public JTableThemeImpression getTableImpression() throws IllegalArgumentException {
		if(this.modelTableHistoriquePoints.getRowCount() == 0) {
			throw new IllegalArgumentException("Aucune donnée à imprimer");
		}

		JTableThemeImpression table = new JTableThemeImpression(this.modelTableHistoriquePoints);

		// Création d'un JFrame et pack pour rendre le tableau
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(table));
		frame.pack();

		return table;
	}

}
