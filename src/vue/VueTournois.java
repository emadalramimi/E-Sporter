package vue;

import javax.swing.JPanel;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.ThemeTableCellRenderer;
import vue.theme.JComboBoxTheme;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.Vector;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.FlowLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controleur.ControleurTournois;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

/**
 * IHM de la liste des tournois
 */
public class VueTournois extends SuperVueListe<Tournoi> {

	private JTable table;
	private DefaultTableModel model;
	private JComboBoxTheme<String> cboxNotoriete;
	private JComboBoxTheme<String> cboxStatuts;

	private ControleurTournois controleur;
	private VueSaisieTournoi vueSaisieTournoi;
	private VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi;
	private VueEtatResultatsTournoi vueEtatResultatsTournoi;
	private VuePoule vuePoule;
	private VueBase vueBase;

	public VueTournois() {
		super();
		super.setControleur(new ControleurTournois(this));
		this.controleur = (ControleurTournois) super.getControleur();
	}
	
	public void afficherVueTournois(JPanel contentPane, VueBase vueBase) {
		this.vueBase = vueBase;
		
		// btnAjouter, un bouton pour permettre l'ajout d'une équipe
		JButtonTheme btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnAjouter.setIcon(new ImageIcon(VueTournois.class.getResource("/images/buttons/ajouter.png")));
		btnAjouter.addActionListener(controleur);
		
		JPanel panelCorps = super.getPanelCorps();

		// panelTableauFiltres, le panel contenant le champ de recherche et les filtres
		JPanel panelTableauFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		panelCorps.add(panelTableauFiltres, BorderLayout.NORTH);
		
		// Panel de recherche
		panelTableauFiltres.add(super.getPanelRecherche());

		// Création du panel contenant les filtres
		JPanel panelChoixFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelChoixFiltres.setBackground(CharteGraphique.FOND);

		// Ajouter comboBox filtre pour choisir un statut
		cboxStatuts = new JComboBoxTheme<>(ControleurTournois.Statut.getLibellesFiltres());
		cboxStatuts.addItemListener(controleur);
		cboxStatuts.setPreferredSize(new Dimension(200, 45));
		panelChoixFiltres.add(cboxStatuts);

		// Ajouter comboBox filtre pour choisir une notoriete
		cboxNotoriete = new JComboBoxTheme<>(Notoriete.getLibellesFiltres());
		cboxNotoriete.addItemListener(controleur);
		cboxNotoriete.setPreferredSize(new Dimension(200, 45));
		panelChoixFiltres.add(cboxNotoriete);

		// Ajouter les filtres
		panelTableauFiltres.add(panelChoixFiltres);
		
		// ScrollPane englobant le tableau
		JScrollPaneTheme scrollPaneTournois = new JScrollPaneTheme();
		panelCorps.add(scrollPaneTournois, BorderLayout.CENTER);
		
		// Tableau de tournois
		this.table = new JTableTheme(
			new String[] {"ID", "Statut", "Nom", "Niveau", "Date de début", "Date de fin", "Actions"},
			controleur
		);
		this.model = (DefaultTableModel) table.getModel();
		this.table.getColumnModel().getColumn(1).setCellRenderer(new StatutCellRenderer());
		
		scrollPaneTournois.setViewportView(table);

		this.remplirTableau(this.controleur.getTournois());

		super.afficherVue(contentPane, "Tournois", btnAjouter, panelCorps);
	}
	
	private class StatutCellRenderer extends ThemeTableCellRenderer {

		/**
		 * Méthode pour personnaliser l'affichage du statut dans le tableau
		 * Phase d'inscriptions : bleu, Ouvert : vert, Clôturé : rouge
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Couleur du texte en fonction du statut
			if (column == 1) {
				switch ((String) value) {
					case "Phase d'inscriptions":
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
	
	/**
	 * Ouvre la fenêtre de saisie d'un tournoi
	 * @param tournoi le tournoi à modifier, null si création d'un nouveau tournoi
	 */
	public void afficherVueSaisieTournoi(Optional<Tournoi> tournoi) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
		if (this.vueSaisieTournoi == null || !this.vueSaisieTournoi.isVisible()) {
			this.vueSaisieTournoi = new VueSaisieTournoi(this, tournoi);
			this.vueBase.ajouterFenetreEnfant(this.vueSaisieTournoi);
			this.vueSaisieTournoi.setLocationRelativeTo(this);
			this.vueSaisieTournoi.setVisible(true);
		} else {
			this.vueSaisieTournoi.toFront();
		}
	}
	
	/**
	 * Ouvre la fenêtre d'inscription des équipes à un tournoi
	 * @param tournoi le tournoi auquel on veut inscrire des équipes
	 */
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
	 * Ouvre la fenêtre de poule d'un tournoi
	 * @param tournoi le tournoi dont on veut afficher la poule
	 */
	public void afficherVuePoule(Tournoi tournoi) {
		// Une seule fenêtre de poule à la fois, si déjà ouverte elle est mise au premier plan
		if (this.vuePoule == null || !this.vuePoule.isVisible()) {
			this.vuePoule = new VuePoule(this, tournoi, tournoi.getPouleActuelle());
			this.vueBase.ajouterFenetreEnfant(this.vuePoule);
			this.vuePoule.setLocationRelativeTo(this);
			this.vuePoule.setVisible(true);
		} else {
			this.vuePoule.toFront();
		}
	}

	/**
	 * Ouvre la fenêtre d'état des résultats du tournoi
	 * @param tournoi le tournoi dont on veut afficher les résultats
	 */
	public void afficherVueEtatResultatsTournoi(Tournoi tournoi) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
        if (this.vueEtatResultatsTournoi == null || !this.vueEtatResultatsTournoi.isVisible()) {
        	this.vueEtatResultatsTournoi = new VueEtatResultatsTournoi(tournoi);
        	this.ajouterFenetreEnfant(this.vueEtatResultatsTournoi);
        	this.vueEtatResultatsTournoi.setLocationRelativeTo(this);
        	this.vueEtatResultatsTournoi.setVisible(true);
        } else {
        	this.vueEtatResultatsTournoi.toFront();
        }
    }
	
	/**
	 * Retire une fenêtre enfant de la liste des fenêtres enfant dans VueBase
	 * @param fenetre : la fenêtre à retirer
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
	    	"Êtes-vous sûr de vouloir supprimer ce tournoi ?",
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
	 * Remet à zéro les filtres
	 */
	@Override
	public void resetFiltres() {
		this.cboxNotoriete.setSelectedIndex(0);
		this.cboxStatuts.setSelectedIndex(0);
	}
	
	/**
	 * Remplit/met à jour le tableau d'équipes
	 * @param tournois : liste des tournois à mettre dans le tableau
	 */
	@Override
	public void remplirTableau(List<Tournoi> tournois) {
		// Vider le tableau
		this.model.setRowCount(0);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
		sdf.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

		// Remplir avec les données de tournois
		for (Tournoi tournoi : tournois) {
			Vector<Object> rowData = new Vector<>();
			rowData.add(tournoi.getIdTournoi());

			if (System.currentTimeMillis() / 1000 < tournoi.getDateTimeFin() && tournoi.getEstCloture() == true) {
				rowData.add("Phase d'inscriptions");
			} else if (tournoi.getEstCloture() == true) {
				rowData.add("Clôturé");
			} else {
				rowData.add("Ouvert");
			}

			rowData.add(tournoi.getNomTournoi());
			rowData.add(tournoi.getNotoriete().getLibelle());

			String dateTimeDebut = sdf.format(new Date(tournoi.getDateTimeDebut() * 1000L));
			String dateTimeFin = sdf.format(new Date(tournoi.getDateTimeFin() * 1000L));

			rowData.add(dateTimeDebut);
			rowData.add(dateTimeFin);
			
			this.model.addRow(rowData);
		}
		
		// Mise à jour du tableau
		this.table.setModel(this.model);
	}

	/**
	 * Méthode pour vérifier si la comboBox est la comboBox de notoriétés ou de statuts
	 * @param comboBox : la comboBox à vérifier
	 * @return true si la comboBox est la comboBox de notoriétés ou de statuts, false sinon
	 */
	public boolean estCboxFiltre(JComboBoxTheme<?> comboBox) {
		return comboBox.equals(this.cboxNotoriete) || comboBox.equals(this.cboxStatuts);
	}

	/**
	 * Retourne la notoriété sélectionnée dans la comboBox
	 * @return la notoriété sélectionnée dans la comboBox
	 */
	public Notoriete getNotorieteSelectionnee() {
		if(this.cboxNotoriete.getSelectedIndex() == 0) {
			return null;
		}
		return Notoriete.valueOfLibelle((String) this.cboxNotoriete.getSelectedItem());
	}

	/**
	 * Retourne le statut sélectionné dans la comboBox
	 * @return le statut sélectionné dans la comboBox
	 */
	public ControleurTournois.Statut getStatutSelectionne() {
		if(this.cboxStatuts.getSelectedIndex() == 0) {
			return null;
		}
		return ControleurTournois.Statut.valueOfLibelle((String) this.cboxStatuts.getSelectedItem());
	}

	/**
	 * Retourne vueBase
	 * @return vueBase
	 */
	public VueBase getVueBase() {
		return this.vueBase;
	}
	
}
