package vue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.JFrameTheme;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTextFieldTheme;
import vue.theme.ThemeTableCellRenderer;
import vue.theme.JButtonTheme.Types;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurPalmares;
import modele.metier.Equipe;
import modele.metier.Palmares;
import modele.metier.Pays;

/**
 * IHM équipes
 */
public class VuePalmares extends JFrameTheme implements RecherchableVue<Palmares> {
	
	private JTable table;
	private JTextFieldTheme txtRecherche;
	private JButton btnRecherche;
	private JPanel panel;
	private DefaultTableModel model;
	
	public void afficherVuePalmares(JPanel contentPane, VueBase vueBase) {
		ControleurPalmares controleur = new ControleurPalmares(this);
		
		List<Palmares> podium = controleur.getClassement();

		// panel contient tous les éléments de la page
		panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1020, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		panel.setLayout(gbl_panel);
		
		// panelLabelHistoriquePoints, le panel contenant le label lblHistoriquePoints
		JPanel panelLabelPalmares = new JPanel();
		GridBagConstraints gbc_panelLabelPalmares = new GridBagConstraints();
		gbc_panelLabelPalmares.anchor = GridBagConstraints.NORTH;
		gbc_panelLabelPalmares.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelLabelPalmares.insets = new Insets(0, 0, 20, 0);
		gbc_panelLabelPalmares.gridx = 0;
		gbc_panelLabelPalmares.gridy = 0;
		panel.add(panelLabelPalmares, gbc_panelLabelPalmares);
		panelLabelPalmares.setBackground(CharteGraphique.FOND);
		panelLabelPalmares.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblPalmares = new JLabel("Palmarès");
		lblPalmares.setHorizontalAlignment(SwingConstants.LEFT);
		lblPalmares.setFont(CharteGraphique.getPolice(30, true));
		lblPalmares.setForeground(CharteGraphique.TEXTE);
		panelLabelPalmares.add(lblPalmares);
		
		JPanel panelPodium = new JPanel();
		panelPodium.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelPodium = new GridBagConstraints();
		gbc_panelPodium.insets = new Insets(0, 0, 20, 0);
		gbc_panelPodium.fill = GridBagConstraints.VERTICAL;
		gbc_panelPodium.gridx = 0;
		gbc_panelPodium.gridy = 1;
		panel.add(panelPodium, gbc_panelPodium);
		GridBagLayout gbl_panelPodium = new GridBagLayout();
		gbl_panelPodium.columnWidths = new int[] {200, 200, 200};
		gbl_panelPodium.rowHeights = new int[]{141, 0, 0};
		gbl_panelPodium.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPodium.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelPodium.setLayout(gbl_panelPodium);

		JLabel icon2 = new JLabel(new ImageIcon(VueTournois.class.getResource("/images/medailles/top2.png")));
		GridBagConstraints gbc_lblIcon2 = new GridBagConstraints();
		gbc_lblIcon2.gridx = 0;
		gbc_lblIcon2.gridy = 0;
		panelPodium.add(icon2, gbc_lblIcon2);

		JLabel icon1 = new JLabel(new ImageIcon(VueTournois.class.getResource("/images/medailles/top1.png")));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.NORTH;
		gbc_lblIcon.gridx = 1;
		gbc_lblIcon.gridy = 0;
		panelPodium.add(icon1, gbc_lblIcon);

		JLabel icon3 = new JLabel(new ImageIcon(VueTournois.class.getResource("/images/medailles/top3.png")));
		GridBagConstraints gbc_lblIcon_1 = new GridBagConstraints();
		gbc_lblIcon_1.anchor = GridBagConstraints.SOUTH;
		gbc_lblIcon_1.gridx = 2;
		gbc_lblIcon_1.gridy = 0;
		panelPodium.add(icon3, gbc_lblIcon_1);
		
		JTextArea lblTop2 = this.textePodium(podium, 2);
		GridBagConstraints gbc_lblTop2 = new GridBagConstraints();
		gbc_lblTop2.insets = new Insets(0, 0, 0, 5);
		gbc_lblTop2.gridx = 0;
		gbc_lblTop2.gridy = 1;
		panelPodium.add(lblTop2, gbc_lblTop2);

		JTextArea lblTop1 = this.textePodium(podium, 1);
		GridBagConstraints gbc_lblTop1 = new GridBagConstraints();
		gbc_lblTop1.insets = new Insets(0, 0, 0, 5);
		gbc_lblTop1.gridx = 1;
		gbc_lblTop1.gridy = 1;
		panelPodium.add(lblTop1, gbc_lblTop1);

		JTextArea lblTop3 = this.textePodium(podium, 3);
		GridBagConstraints gbc_lblTop3 = new GridBagConstraints();
		gbc_lblTop3.gridx = 2;
		gbc_lblTop3.gridy = 1;
		panelPodium.add(lblTop3, gbc_lblTop3);
		
		// JPanel de recherche
		JPanel panelTableauFiltres = new JPanel();
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelRecherche = new GridBagConstraints();
		gbc_panelRecherche.insets = new Insets(0, 0, 20, 0);
		gbc_panelRecherche.fill = GridBagConstraints.BOTH;
		gbc_panelRecherche.gridx = 0;
		gbc_panelRecherche.gridy = 2;
		panel.add(panelTableauFiltres, gbc_panelRecherche);
		panelTableauFiltres.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// Champ de recherche
		txtRecherche = new JTextFieldTheme(20);
		txtRecherche.addKeyListener(controleur);
		txtRecherche.setColumns(20);
		panelTableauFiltres.add(txtRecherche);
		
		// Bouton de recherche
		btnRecherche = new JButtonTheme(Types.PRIMAIRE, new ImageIcon(VueTournois.class.getResource("/images/actions/rechercher.png")));
		btnRecherche.addActionListener(controleur);
		panelTableauFiltres.add(btnRecherche);
		
		// Panel contenant les filtres
		JPanel panelChoixFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelChoixFiltres.setBackground(CharteGraphique.FOND);
		
		// ScrollPane englobant le tableau
		JScrollPaneTheme scrollPaneEquipes = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPaneEquipes = new GridBagConstraints();
		gbc_scrollPaneEquipes.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneEquipes.gridx = 0;
		gbc_scrollPaneEquipes.gridy = 3;
		panel.add(scrollPaneEquipes, gbc_scrollPaneEquipes);
				
		// Création du modèle du tableau avec désactivation de l'édition
		this.model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"Classement", "Pays", "Equipe", "Points"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
			
		// Tableau de palmarès
		this.table = new JTableTheme();
		this.table.setModel(model);

		// Règles d'affichage du drapeau du pays
		TableColumn paysColumn = table.getColumnModel().getColumn(1);
	    paysColumn.setCellRenderer(new ImageTableCellRenderer());

		// Règles d'affichage du classement
		TableColumn classementColumn = table.getColumnModel().getColumn(0);
		classementColumn.setCellRenderer(new TableCellRendererPalmares());
				
		scrollPaneEquipes.setViewportView(this.table);

		this.remplirTableau(podium);
	}

	/**
	 * Classe interne pour changer la couleur des 3 premiers du classement
	 */
	private class TableCellRendererPalmares extends ThemeTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			String text = this.getText();
			
			if("#1".equals(text)) {
				this.setForeground(CharteGraphique.OR);
				this.setFont(CharteGraphique.getPolice(16, true));
			} else if("#2".equals(text)) {
				this.setForeground(CharteGraphique.ARGENT);
				this.setFont(CharteGraphique.getPolice(16, true));
			} else if("#3".equals(text)) {
				this.setForeground(CharteGraphique.BRONZE);
				this.setFont(CharteGraphique.getPolice(16, true));
			}
			
			return this;
		}

	}

	/**
	 * Classe interne de label avec une icone (pour les drapeaux)
	 */
	private static class LabelIcon {

        ImageIcon icon;
        String label;

        public LabelIcon(ImageIcon icon, String label) {
            this.icon = icon;
            this.label = label;
        }
        
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
	        setIcon(labelIcon.icon);
	        setText(labelIcon.label);
	        
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

	private JTextArea textePodium(List<Palmares> podium, int classement) {
		List<String> equipes = podium.stream()
			.filter(p -> p.getEquipe().getClassement() == classement)
			.map(p -> p.getEquipe().getNom())
			.collect(Collectors.toList());

		if (equipes.size() > 3) {
			equipes = equipes.subList(0, 3);
			equipes.add("...");
		}

		JTextArea textArea = new JTextArea(String.join("\n", equipes));
		textArea.setFont(CharteGraphique.getPolice(20, true));
		textArea.setForeground(CharteGraphique.TEXTE);
		textArea.setEditable(false);
		textArea.setOpaque(false);

		return textArea;
	}
	
	/**
	 * @param bouton
	 * @return true si bouton est le bouton de recherche, false sinon
	 */
	@Override
	public boolean estBoutonRecherche(JButton bouton) {
		if(bouton instanceof JButtonTheme && bouton.getIcon() != null) {
			String iconeRecherche = VueTournois.class.getResource("/images/actions/rechercher.png").toString();
		    return bouton.getIcon().toString().equals(iconeRecherche);
		}
		return false;
	}
	
	/**
	 * @param champ
	 * @return true si le champ est le champ de recherche, false sinon
	 */
	@Override
	public boolean estChampRecherche(JTextField champ) {
		return this.txtRecherche.equals(champ);
	}

	/**
	 * Remet à zéro le champ de recherche
	 */
	@Override
	public void resetChampRecherche() {
		this.txtRecherche.setText("");
	}
	
	/**
	 * @return la requête de recherche tapée par l'utilisateur
	 */
	@Override
	public String getRequeteRecherche() {
		return this.txtRecherche.getText().trim();
	}

	/**
	 * Remplit/met à jour le tableau d'équipes
	 * @param equipes : liste des équipes à mettre dans le tableau
	 */
	@Override
	public void remplirTableau(List<Palmares> palmares) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Palmares podium : palmares) {
		    Vector<Object> rowData = new Vector<>();

			Equipe equipe = podium.getEquipe();

			rowData.add("#" + equipe.getClassement());
		    
		    Pays pays = equipe.getPays();
	        ImageIcon drapeau = pays.getDrapeauPays();
	        rowData.add(new LabelIcon(drapeau, equipe.getPays().getNomPays()));

		    rowData.add(equipe.getNom());

			rowData.add(podium.getPoints());

		    this.model.addRow(rowData);
		}
		
		// Mise à jour du tableau
		this.table.setModel(this.model);
	}

}