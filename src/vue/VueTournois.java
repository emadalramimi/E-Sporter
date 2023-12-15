package vue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTextFieldTheme;
import vue.theme.TableButtonsCellEditor;
import vue.theme.TableButtonsPanel;
import vue.theme.ThemeTableCellRenderer;
import vue.theme.JButtonTheme.Types;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.Vector;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurTournois;
import modele.metier.Tournoi;

public class VueTournois extends JFrameTheme {
	
	private JTable table;
	private DefaultTableModel model;
	private JButtonTheme btnAjouter;
	private JPanel panel;
	private JPanel panelLabelEquipe;
	private JLabel lblTournois;
	private JPanel panelAjouter;
	private JPanel panelTableauFiltres;
    private JTextFieldTheme txtRecherche;
    private JButtonTheme btnRecherche;
	private JScrollPaneTheme scrollPaneEquipes;

	private ControleurTournois controleur;
	private VueSaisieTournoi vueSaisieTournoi;
	// Temp : gérer fen enfant
	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
	private VueBase vueBase;
	
	public void afficherVueTournois(JPanel contentPane, VueBase vueBase) {
		this.controleur = new ControleurTournois(this);
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
		
		lblTournois = new JLabel("Tournois");
		lblTournois.setHorizontalAlignment(SwingConstants.LEFT);
		lblTournois.setFont(CharteGraphique.getPolice(30, true));
		lblTournois.setForeground(CharteGraphique.TEXTE);
		panelLabelEquipe.add(lblTournois);
		
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
			new String[] {"ID","Statut", "Nom", "Niveau", "Date de début", "Date de fin", "Actions"}
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column != table.getColumnCount() - 1) {
					return false;
				}
				return true;
			}
		};
		
		// Tableau de tournois
		table = new JTableTheme();
		table.setModel(model);
		table.getColumnModel().getColumn(1).setCellRenderer(new StatutCellRenderer());
		
		// Ajouter buttons dans la derniere colonne
		TableColumn buttonColumn = table.getColumnModel().getColumn(table.getColumnCount() - 1);
		buttonColumn.setCellRenderer(new TableButtonsPanel(table, controleur, 0));
		buttonColumn.setCellEditor(new TableButtonsCellEditor(controleur));
		
		// Masquage de la colonne ID (sert pour obtenir l'Equipe d'une ligne dont un bouton est cliqué)
		TableColumn idColumn = table.getColumnModel().getColumn(0);
		idColumn.setMinWidth(1); // 1px pour garder la bordure
		idColumn.setMaxWidth(1);
		idColumn.setWidth(1);
		idColumn.setPreferredWidth(1);
		
		this.remplirTableau(this.controleur.getTournois());
		
		scrollPaneEquipes.setViewportView(table);
	}
	
	private class StatutCellRenderer extends ThemeTableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Couleur du texte
			if (column == 1) {
				switch ((String) value) {
					case "En création":
						this.setForeground(CharteGraphique.PRIMAIRE);
						break;
					case "Ouvert":
						this.setForeground(CharteGraphique.TOURNOI_OUVERT);
						break;
					case "Clôturé":
						this.setForeground(CharteGraphique.TOURNOI_CLOTURE);
						break;
				}
			}

			return this;
		}
	}
	
	public void afficherVueSaisieTournoi(Optional<Tournoi> tournoi) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
		if (this.vueSaisieTournoi == null || !this.vueSaisieTournoi.isVisible()) {
			this.vueSaisieTournoi = new VueSaisieTournoi(this);
			this.vueBase.ajouterFenetreEnfant(this.vueSaisieTournoi);
			this.vueSaisieTournoi.setLocationRelativeTo(this);
			this.vueSaisieTournoi.setVisible(true);
		} else {
			this.vueSaisieTournoi.toFront();
		}
	}

	public void afficherVueInscriptionEquipes(Tournoi tournoi) {
		// Une seule fenêtre d'inscription à la fois, si déjà ouverte elle est mise au premier plan
		if (this.vueInscriptionEquipesTournoi == null || !this.vueInscriptionEquipesTournoi.isVisible()) {
			this.vueInscriptionEquipesTournoi = new VueInscriptionEquipesTournoi(this, tournoi);
			this.vueBase.ajouterFenetreEnfant(this.vueInscriptionEquipesTournoi);
			this.vueInscriptionEquipesTournoi.setLocationRelativeTo(this);
			this.vueInscriptionEquipesTournoi.setVisible(true);
		} else {
			this.vueInscriptionEquipesTournoi.toFront();
		}
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
	public void remplirTableau(List<Tournoi> tournois) {
	    // Vider le tableau
	    this.model.setRowCount(0);

	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
	    sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

	    // Remplir avec les données de tournois
	    for (Tournoi tournoi : tournois) {
	        Vector<Object> rowData = new Vector<>();
			rowData.add(tournoi.getIdTournoi());
			
			if (System.currentTimeMillis() / 1000 < tournoi.getDateFin() && tournoi.getEstCloture() == true) {
				rowData.add("En création");
			} else if (tournoi.getEstCloture() == true) {
				rowData.add("Clôturé");
			} else {
				rowData.add("Ouvert");
			}

	        rowData.add(tournoi.getNomTournoi());
	        rowData.add(tournoi.getNotoriete().getLibelle());

	        String dateTimeDebut = sdf.format(new Date(tournoi.getDateDebut() * 1000L));
	        String dateTimeFin = sdf.format(new Date(tournoi.getDateFin() * 1000L));

	        rowData.add(dateTimeDebut);
	        rowData.add(dateTimeFin);

	        this.model.addRow(rowData);
	    }

	    // Mise à jour du tableau
	    this.table.setModel(this.model);
	}
	
}
