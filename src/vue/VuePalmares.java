package vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vue.theme.CharteGraphique;
import vue.theme.ImageTableCellRenderer;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTableThemeImpression;
import vue.theme.LabelIcon;
import vue.theme.ThemeTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurPalmares;
import modele.metier.Equipe;
import modele.metier.Palmares;
import modele.metier.Pays;

/**
 * IHM Palmarès
 */
public class VuePalmares extends SuperVueListe<Palmares> {
	
	private ControleurPalmares controleur;
	private JTable table;
	private DefaultTableModel model;

	public VuePalmares() {
		super();
		super.setControleur(new ControleurPalmares(this));
		this.controleur = (ControleurPalmares) super.getControleur();
	}
	
	/**
	 * Affiche la vue du palmarès
	 * @param contentPane : le panel où afficher la vue
	 * @param vueBase : la vue de base
	 */
	public void afficherVuePalmares(JPanel contentPane, VueBase vueBase) {
		List<Palmares> podium = controleur.getClassement();

		// btnImprimer, un bouton pour permettre l'ajout d'une équipe
		JButtonTheme btnImprimer = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Imprimer");
		btnImprimer.setActionCommand("IMPRIMER_PALMARES");
		btnImprimer.addActionListener(this.controleur);
		
		JPanel panelCorps = super.getPanelCorps();

		// panelPodium, le panel contenant les 3 premiers du classement
		JPanel panelPodium = new JPanel();
		panelPodium.setBackground(CharteGraphique.FOND);
		GridBagLayout gbl_panelPodium = new GridBagLayout();
		gbl_panelPodium.columnWidths = new int[] {200, 200, 200};
		gbl_panelPodium.rowHeights = new int[]{141, 0, 0};
		gbl_panelPodium.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelPodium.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelPodium.setLayout(gbl_panelPodium);
		panelCorps.add(panelPodium, BorderLayout.NORTH);

		// Label podium et icônes des 3 premiers
		JLabel icon1 = new JLabel(new ImageIcon(VueTournois.class.getResource("/images/medailles/top1.png")));
		GridBagConstraints gbc_lblIcon = new GridBagConstraints();
		gbc_lblIcon.anchor = GridBagConstraints.NORTH;
		gbc_lblIcon.gridx = 1;
		gbc_lblIcon.gridy = 0;
		panelPodium.add(icon1, gbc_lblIcon);

		JLabel icon2 = new JLabel(new ImageIcon(VueTournois.class.getResource("/images/medailles/top2.png")));
		GridBagConstraints gbc_lblIcon2 = new GridBagConstraints();
		gbc_lblIcon2.gridx = 0;
		gbc_lblIcon2.gridy = 0;
		panelPodium.add(icon2, gbc_lblIcon2);

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
		
		JPanel panelTableau = super.getPanelCorps();
		panelCorps.add(panelTableau, BorderLayout.CENTER);

		// JPanel de recherche
		JPanel panelTableauFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		panelTableau.add(panelTableauFiltres, BorderLayout.NORTH);
		
		// Panel de recherche
		panelTableauFiltres.add(super.getPanelRecherche());
		
		// Panel contenant les filtres
		JPanel panelChoixFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelChoixFiltres.setBackground(CharteGraphique.FOND);
		
		// ScrollPane englobant le tableau
		JScrollPaneTheme scrollPaneEquipes = new JScrollPaneTheme();
		panelTableau.add(scrollPaneEquipes, BorderLayout.CENTER);
		
		// Tableau de palmarès
		this.table = new JTableTheme(
			new String[] {"Classement", "Pays", "Equipe", "Points"},
			null
		);
		this.model = (DefaultTableModel) this.table.getModel();

		// Règles d'affichage du drapeau du pays
		TableColumn paysColumn = table.getColumnModel().getColumn(1);
	    paysColumn.setCellRenderer(new ImageTableCellRenderer());

		// Règles d'affichage du classement
		TableColumn classementColumn = table.getColumnModel().getColumn(0);
		classementColumn.setCellRenderer(new TableCellRendererPalmares());
				
		scrollPaneEquipes.setViewportView(this.table);

		this.remplirTableau(podium);

		super.afficherVue(contentPane, "Palmarès saison " + LocalDate.now().getYear(), btnImprimer, panelCorps);
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
	 * Création du texte du podium
	 * @param podium : le podium
	 * @param classement : le classement du podium
	 * @return le texte du podium
	 */
	private JTextArea textePodium(List<Palmares> podium, int classement) {
		List<String> equipes = podium.stream()
			.filter(p -> p.getEquipe().getClassement() == classement)
			.map(p -> p.getEquipe().getNom())
			.collect(Collectors.toList());
	
		if (equipes.isEmpty()) {
			equipes.add("N/A");
		} else if (equipes.size() > 3) {
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
	 * Remplit/met à jour le tableau du palmares
	 * @param palmares : liste des palamres à mettre dans le tableau
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

	/**
	 * Retourne le tableau d'équipes pour l'impression
	 * @return le tableau d'équipes
	 */
	public JTableThemeImpression getTableImpression() {
		JTableThemeImpression table = new JTableThemeImpression(this.table.getModel());

		// Création d'un JFrame et pack pour rendre le tableau
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(table));
		frame.pack();

		return table;
	}

}
