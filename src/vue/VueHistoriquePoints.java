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

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurHistoriquePoints;
import modele.metier.Equipe;
import modele.metier.HistoriquePoints;
import modele.metier.Pays;

/**
 * IHM équipes
 */
public class VueHistoriquePoints extends SuperVueListe<Equipe> {
	
	private ControleurHistoriquePoints controleur;
	private JTable tableEquipes;
	private JTable tableHistoriquePoints;
	private DefaultTableModel modelTableEquipes;
	private DefaultTableModel modelTableHistoriquePoints;

	public VueHistoriquePoints() {
		super();
		super.setControleur(new ControleurHistoriquePoints(this));
		this.controleur = (ControleurHistoriquePoints) super.getControleur();
	}
	
	public void afficherVueHistoriquePoints(JPanel contentPane, VueBase vueBase) {
		// btnImprimer, un bouton pour permettre l'ajout d'une équipe
		JButtonTheme btnImprimer = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Imprimer l'historique sélectionné");
		btnImprimer.addActionListener(this.controleur);

		JPanel panelCorps = super.getPanelCorps();
		
		JPanel panelTableauFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		
		// Panel de recherche
		panelTableauFiltres.add(super.getPanelRecherche());

		panelCorps.add(panelTableauFiltres, BorderLayout.NORTH);
		
		JPanel panelTableaux = new JPanel();
		panelTableaux.setBackground(CharteGraphique.FOND);
		panelTableaux.setLayout(new GridLayout(1, 2, 20, 0));

		JScrollPane scrollPaneTableEquipes = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPaneTableEquipes = new GridBagConstraints();
		gbc_scrollPaneTableEquipes.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTableEquipes.gridx = 0;
		gbc_scrollPaneTableEquipes.gridy = 2;
		panelTableaux.add(scrollPaneTableEquipes, gbc_scrollPaneTableEquipes);

		// Tableau d'équipes
		this.tableEquipes = new JTableTheme(
			new String[] {"ID", "Pays", "Nom"},
			null
		);
		this.tableEquipes.getSelectionModel().addListSelectionListener(this.controleur);
		this.tableEquipes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.modelTableEquipes = (DefaultTableModel) this.tableEquipes.getModel();

		scrollPaneTableEquipes.setViewportView(this.tableEquipes);
		
		// Règles d'affichage du drapeau du pays
		TableColumn paysColumn = tableEquipes.getColumnModel().getColumn(1);
	    paysColumn.setCellRenderer(new ImageTableCellRenderer());

		// Tableau des historiques de points
		JScrollPane scrollPaneHistoriquePoints2 = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPaneHistoriquePoints2 = new GridBagConstraints();
		gbc_scrollPaneHistoriquePoints2.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneHistoriquePoints2.gridx = 1;
		gbc_scrollPaneHistoriquePoints2.gridy = 2;
		panelTableaux.add(scrollPaneHistoriquePoints2, gbc_scrollPaneHistoriquePoints2);

		this.tableHistoriquePoints = new JTableTheme(
			new String[] {"Tournoi", "Points"},
			null
		);
		this.modelTableHistoriquePoints = (DefaultTableModel) this.tableHistoriquePoints.getModel();
		scrollPaneHistoriquePoints2.setViewportView(this.tableHistoriquePoints);

		panelCorps.add(panelTableaux, BorderLayout.CENTER);
		
		this.remplirTableau(this.controleur.getEquipes());

		super.afficherVue(contentPane, "Historique des points", btnImprimer, panelCorps);
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
