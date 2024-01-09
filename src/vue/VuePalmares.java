package vue;

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
import vue.theme.JButtonTheme.Types;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Vector;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import modele.metier.Equipe;
import modele.metier.Pays;

/**
 * IHM équipes
 */
public class VuePalmares extends JFrameTheme {
	public VuePalmares() {
	}
	
	private JTable tableEquipes;
	private JPanel panelTableauFiltres;
	private JTextFieldTheme txtRecherche;
	private JButton btnRecherche;
	private JScrollPaneTheme scrollPaneEquipes;
	private JPanel panel;
	private DefaultTableModel model;
	private JLabel lblPalmares;
	
	public void afficherVuePalmares(JPanel contentPane, VueBase vueBase) {
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
		
		lblPalmares = new JLabel("Palmares");
		lblPalmares.setHorizontalAlignment(SwingConstants.LEFT);
		lblPalmares.setFont(CharteGraphique.getPolice(30, true));
		lblPalmares.setForeground(CharteGraphique.TEXTE);
		panelLabelPalmares.add(lblPalmares);
		
		// JPanel affichant le Podium
		JPanel panelPodium = new JPanel();
		
		//JPanel de recherche
		panelTableauFiltres = new JPanel();
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelRecherche = new GridBagConstraints();
		gbc_panelRecherche.insets = new Insets(0, 0, 20, 0);
		gbc_panelRecherche.fill = GridBagConstraints.BOTH;
		gbc_panelRecherche.gridx = 0;
		gbc_panelRecherche.gridy = 1;
		panel.add(panelTableauFiltres, gbc_panelRecherche);
		panelTableauFiltres.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		// Champ de recherche
		txtRecherche = new JTextFieldTheme(20);
//		txtRecherche.addKeyListener(controleur);
		txtRecherche.setColumns(20);
		panelTableauFiltres.add(txtRecherche);
		
		// Bouton de recherche
		btnRecherche = new JButtonTheme(Types.PRIMAIRE, new ImageIcon(VueTournois.class.getResource("/images/actions/rechercher.png")));
//		btnRecherche.addActionListener(controleur);
		panelTableauFiltres.add(btnRecherche);
		
		// Panel contenant les filtres
		JPanel panelChoixFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelChoixFiltres.setBackground(CharteGraphique.FOND);
		
		// ScrollPane englobant le tableau
		scrollPaneEquipes = new JScrollPaneTheme();
		GridBagConstraints gbc_scrollPaneEquipes = new GridBagConstraints();
		gbc_scrollPaneEquipes.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneEquipes.gridx = 0;
		gbc_scrollPaneEquipes.gridy = 2;
		panel.add(scrollPaneEquipes, gbc_scrollPaneEquipes);
				
		// Création du modèle du tableau avec désactivation de l'édition
		this.model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] {"ID", "Classement", "Pays", "Equipe"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column != tableEquipes.getColumnCount() - 1) {
					return false;
				}
				return true;
			}
		};
			
		// Tableau d'équipes
		tableEquipes = new JTableTheme();
		tableEquipes.setModel(model);
		
		// Masquage de la colonne ID (sert pour obtenir l'Equipe d'une ligne dont un bouton est cliqué)
				TableColumn idColumn = tableEquipes.getColumnModel().getColumn(0);
				idColumn.setMinWidth(1); // 1px pour garder la bordure
				idColumn.setMaxWidth(1);
				idColumn.setWidth(1);
				idColumn.setPreferredWidth(1);
				
				//this.remplirTableau(this.controleur.getEquipes());
		scrollPaneEquipes.setViewportView(tableEquipes);
		
	}
	
	/**
	 * @param bouton
	 * @return true si bouton est le bouton de recherche, false sinon
	 */
	public boolean estBoutonRecherche(JButton bouton) {
		if(bouton instanceof JButtonTheme) {
			String iconeRecherche = VueTournois.class.getResource("/images/actions/rechercher.png").toString();
		    return bouton.getIcon().toString().equals(iconeRecherche);
		}
		return false;
	}
	
	/**
	 * Remplit/met à jour le tableau d'équipes
	 * @param equipes : liste des équipes à mettre dans le tableau
	 */
	public void remplirTableau(List<Equipe> equipes) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		int i = 1;
		// Remplir avec les données d'équipes
		for(Equipe equipe : equipes) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(i);
		    
		    Pays pays = equipe.getPays();
	        ImageIcon drapeau = pays.getDrapeauPays();
	        rowData.add(new LabelIcon(drapeau, equipe.getPays().getNomPays()));
	        
	        rowData.add(equipe.getNom());
		    this.model.addRow(rowData);
		    i++;
		}
		
		// Mise à jour du tableau
		this.tableEquipes.setModel(this.model);
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
}
