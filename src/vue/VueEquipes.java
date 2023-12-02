package vue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.TableButtonsPanel;
import vue.theme.JButtonTheme.Types;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.CharteGraphique;
import vue.theme.TableButtonsCellEditor;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurEquipes;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;

/**
 * IHM équipes
 */
public class VueEquipes extends JFrameTheme {
	
	private JTable table;
	private DefaultTableModel model;
	private JButtonTheme btnAjouter;
	private JPanel panel;
	private JPanel panelLabelEquipe;
	private JLabel lblEquipes;
	private JPanel panelAjouter;
	private JPanel panelTableauFiltres;
    private JTextFieldTheme txtRecherche;
    private JButtonTheme btnRecherche;
	private JScrollPaneTheme scrollPaneEquipes;

	private ControleurEquipes controleur;
	private VueSaisieEquipe vueSaisieEquipe;
	private VueJoueurs vueJoueurs;
	private VueBase vueBase;
	
	public void afficherVueEquipe(JPanel contentPane, VueBase vueBase) {
		this.controleur = new ControleurEquipes(this);
		this.vueBase = vueBase;
		
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
		
		// panelLabelEquipe, le panel contenant le label lblEquipes
		panelLabelEquipe = new JPanel();
		GridBagConstraints gbc_panelLabelEquipe = new GridBagConstraints();
		gbc_panelLabelEquipe.anchor = GridBagConstraints.NORTH;
		gbc_panelLabelEquipe.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelLabelEquipe.insets = new Insets(0, 0, 20, 0);
		gbc_panelLabelEquipe.gridx = 0;
		gbc_panelLabelEquipe.gridy = 0;
		panel.add(panelLabelEquipe, gbc_panelLabelEquipe);
		panelLabelEquipe.setBackground(CharteGraphique.FOND);
		panelLabelEquipe.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblEquipes = new JLabel("Equipes");
		lblEquipes.setHorizontalAlignment(SwingConstants.LEFT);
		lblEquipes.setFont(CharteGraphique.getPolice(30, true));
		lblEquipes.setForeground(CharteGraphique.TEXTE);
		panelLabelEquipe.add(lblEquipes);
		
		// panelAjouter, le panel contenant le bouton btnAjouter
		panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		FlowLayout flowLayout = (FlowLayout) panelAjouter.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panelLabelEquipe.add(panelAjouter);
		
		// btnAjouter, un bouton pour permettre l'ajout d'une équipe
		btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnAjouter.addActionListener(controleur);
		btnAjouter.setHorizontalAlignment(SwingConstants.RIGHT);
		panelAjouter.add(btnAjouter);
		
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
		txtRecherche.addKeyListener(controleur);
		txtRecherche.setColumns(20);
		panelTableauFiltres.add(txtRecherche);
		
		// Bouton de recherche
		btnRecherche = new JButtonTheme(Types.PRIMAIRE, new ImageIcon(VueTournois.class.getResource("/images/actions/rechercher.png")));
		btnRecherche.addActionListener(controleur);
		panelTableauFiltres.add(btnRecherche);
		
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
			new String[] {"ID", "Nom", "Pays", "World Ranking", "Actions"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column != table.getColumnCount() - 1) {
					return false;
				}
				return true;
			}
		};
		
		// Tableau d'équipes
		table = new JTableTheme();
		table.setModel(model);
		
		// Ajouter buttons dans la derniere colonne
		TableColumn buttonColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
		buttonColumn.setCellRenderer(new TableButtonsPanel(table, controleur, 0));
		buttonColumn.setCellEditor(new TableButtonsCellEditor(controleur));
		
		// Règles d'affichage du drapeau du pays
		TableColumn paysColumn = table.getColumnModel().getColumn(2);
	    paysColumn.setCellRenderer(new ImageTableCellRenderer());
		
		// Masquage de la colonne ID (sert pour obtenir l'Equipe d'une ligne dont un bouton est cliqué)
		TableColumn idColumn = table.getColumnModel().getColumn(0);
		idColumn.setMinWidth(1); // 1px pour garder la bordure
		idColumn.setMaxWidth(1);
		idColumn.setWidth(1);
		idColumn.setPreferredWidth(1);
		
		this.remplirTableau(this.controleur.getEquipes());
		
		scrollPaneEquipes.setViewportView(table);
	}
	
	/**
	 * Ouvre la fenêtre de saisie équipe et modification équipe si equipe est renseignée
	 * @param equipe : équipe à modifier (optionnel)
	 */
	public void afficherVueSaisieEquipe(Optional<Equipe> equipe) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
        if (this.vueSaisieEquipe == null || !this.vueSaisieEquipe.isVisible()) {
        	this.vueSaisieEquipe = new VueSaisieEquipe(this, this.controleur, equipe);
        	this.vueBase.ajouterFenetreEnfant(this.vueSaisieEquipe);
        	this.vueSaisieEquipe.setLocationRelativeTo(this);
        	this.vueSaisieEquipe.setVisible(true);
        } else {
        	this.vueSaisieEquipe.toFront();
        }
    }
	
	/**
	 * Ouvre la fenêtre avec le détail des joueurs de l'équipe
	 * @param joueurs : liste des joueurs de l'équipe
	 */
	public void afficherVueJoueurs(List<Joueur> joueurs) {
		// Une fenêtre à la fois, si une est déjà ouverte, alors la fermer avant
		if(this.vueJoueurs != null) {
			this.vueJoueurs.fermerFenetre();
		}
		this.vueJoueurs = new VueJoueurs(joueurs, this);
		this.vueBase.ajouterFenetreEnfant(this.vueJoueurs);
		this.vueJoueurs.setLocationRelativeTo(this);
		this.vueJoueurs.setVisible(true);
	}
	
	/**
	 * Retire une fenêtre enfant de la liste des fenêtres enfant dans VueBase
	 * @param fenetre
	 */
	public void retirerFenetreEnfant(JFrameTheme fenetre) {
		this.vueBase.retirerFenetreEnfant(fenetre);
	}
	
	/**
	 * Affiche un message de confirmation de suppression d'équipe
	 * @return true si "Oui" a été sélectionné, false si "Annuler" a été sélectionné ou si la popup a été fermée
	 */
	public boolean afficherConfirmationSuppression() {
		Object[] options = {"Oui", "Annuler"};
        int choix = JOptionPaneTheme.showOptionDialog(
	    	null,
	    	"Êtes-vous sûr de vouloir supprimer cette équipe ?",
	    	"Confirmation",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        options,
	        options[0]
        );
        
        return choix == 0;
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
	 * @param champ
	 * @return true si le champ est le champ de recherche, false sinon
	 */
	public boolean estChampRecherche(JTextField champ) {
		return this.txtRecherche.equals(champ);
	}
	
	/**
	 * @return la requête de recherche tapée par l'utilisateur
	 */
	public String getRequeteRecherche() {
		return this.txtRecherche.getText().trim();
	}
	
	/**
	 * Remplit/met à jour le tableau d'équipes
	 * @param equipes : liste des équipes à mettre dans le tableau
	 */
	public void remplirTableau(List<Equipe> equipes) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Equipe equipe : equipes) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(equipe.getIdEquipe());
		    rowData.add(equipe.getNom());
		    
		    Pays pays = Pays.valueOfNom(equipe.getPays());
	        ImageIcon drapeau = pays.getDrapeauPays();
	        rowData.add(new LabelIcon(drapeau, equipe.getPays()));
	        
		    rowData.add(equipe.getWorldRanking());
		    this.model.addRow(rowData);
		}
		
		// Mise à jour du tableau
		this.table.setModel(this.model);
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
}
