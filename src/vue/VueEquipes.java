package vue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.TableButtonsPanel;
import vue.theme.JButtonTheme.Types;
import vue.theme.CharteGraphique;
import vue.theme.TableButtonsCellEditor;
import vue.theme.JButtonTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurEquipes;
import modele.metier.Equipe;
import modele.metier.Joueur;

public class VueEquipes extends JFrame {
	
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
	
	/**
	 * Create the frame.
	 */
	public void afficherVueEquipe(JPanel contentPane) {
		this.controleur = new ControleurEquipes(this);
		
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
		
		txtRecherche = new JTextFieldTheme(20);
		txtRecherche.addKeyListener(controleur);
		txtRecherche.setColumns(20);
		panelTableauFiltres.add(txtRecherche);
		
		btnRecherche = new JButtonTheme(Types.PRIMAIRE, new ImageIcon(VueTournois.class.getResource("/images/eye.png")));
		btnRecherche.addActionListener(controleur);
		panelTableauFiltres.add(btnRecherche);
		
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
		
		table = new JTableTheme();
		table.setModel(model);
		
		// Ajouter buttons dans la derniere colonne
		TableColumn buttonColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
		buttonColumn.setCellRenderer(new TableButtonsPanel(table, controleur, 0));
		buttonColumn.setCellEditor(new TableButtonsCellEditor(controleur));
		
		// Masquage de la colonne ID
		TableColumn idColumn = table.getColumnModel().getColumn(0);
		idColumn.setMinWidth(1);
		idColumn.setMaxWidth(1);
		idColumn.setWidth(1);
		idColumn.setPreferredWidth(1);
		
		this.remplirTableau(this.controleur.getEquipes());
		
		scrollPaneEquipes.setViewportView(table);
	}
	
	public void afficherFenetreSaisieEquipe(Optional<Equipe> equipe) {
        if (this.vueSaisieEquipe == null || !this.vueSaisieEquipe.isVisible()) {
        	this.vueSaisieEquipe = new VueSaisieEquipe(this, this.controleur, equipe);
        	this.vueSaisieEquipe.setLocationRelativeTo(this);
        	this.vueSaisieEquipe.setVisible(true);
        } else {
        	this.vueSaisieEquipe.toFront();
        }
    }
	
	public void afficherVueJoueurs(List<Joueur> joueurs) {
		try {
			VueJoueurs frame = new VueJoueurs(joueurs);
			frame.setLocationRelativeTo(this);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void afficherPopupErreur(String message) {
		JOptionPaneTheme.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	public void afficherPopupMessage(String message) {
		JOptionPaneTheme.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public boolean afficherConfirmationSuppression() {
		Object[] options = {"Oui", "Annuler"};
        int choix = JOptionPane.showOptionDialog(null, "Êtes-vous sûr de vouloir supprimer cette équipe ?", "Confirmation",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
        
        return choix == 0; // Renvoie true si "Oui" est sélectionné
    }
	
	public boolean estBoutonRecherche(JButton bouton) {
		if(bouton instanceof JButtonTheme) {
			String iconeRecherche = VueTournois.class.getResource("/images/eye.png").toString();
		    return bouton.getIcon().toString().equals(iconeRecherche);
		}
		return false;
	}
	
	public boolean estChampRecherche(JTextField champ) {
		return this.txtRecherche.equals(champ);
	}
	
	public String getRequeteRecherche() {
		return this.txtRecherche.getText().trim();
	}
	
	public void remplirTableau(List<Equipe> equipes) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Equipe equipe : equipes) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(equipe.getIdEquipe());
		    rowData.add(equipe.getNom());
		    rowData.add(equipe.getPays());
		    rowData.add(equipe.getWorldRanking());
		    this.model.addRow(rowData);
		}
		this.table.setModel(this.model);
	}
	
}
