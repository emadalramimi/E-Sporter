package vue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.CharteGraphique;
import vue.theme.ImageTableCellRenderer;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.LabelIcon;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JOptionPane;

import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.awt.FlowLayout;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import controleur.ControleurEquipes;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;

/**
 * IHM équipes
 */
public class VueEquipes extends SuperVueListe<Equipe> {
	
	private JTable table;
	private DefaultTableModel model;
	private JButtonTheme btnAjouter;
	private JComboBoxTheme<String> cboxPays;

	private ControleurEquipes controleur;
	private VueSaisieEquipe vueSaisieEquipe;
	private VueJoueursArbitres vueJoueurs;
	private VueBase vueBase;

	public VueEquipes(VueBase vueBase) {
		super();
		super.setControleur(new ControleurEquipes(this));
		this.controleur = (ControleurEquipes) super.getControleur();
		this.vueBase = vueBase;
	}
	
	public void afficherVueEquipe(JPanel contentPane) {
		// btnAjouter, un bouton pour permettre l'ajout d'une équipe
		btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
		btnAjouter.setIcon(new ImageIcon(VueEquipes.class.getResource("/images/buttons/ajouter.png")));
		btnAjouter.addActionListener(controleur);

		JPanel panelCorps = super.getPanelCorps();

		// panelTableauFiltres, le panel contenant le panelRecherche et le panelChoixFiltres
		JPanel panelTableauFiltres = new JPanel();
		panelTableauFiltres.setBackground(CharteGraphique.FOND);
		panelTableauFiltres.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		panelCorps.add(panelTableauFiltres, BorderLayout.NORTH);
		
		// Panel de recherche
		panelTableauFiltres.add(super.getPanelRecherche());
		
		// Panel contenant les filtres
		JPanel panelChoixFiltres = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		panelChoixFiltres.setBackground(CharteGraphique.FOND);

	    // Ajouter comboBox filtre pour filtrer par pays
	 	cboxPays = new JComboBoxTheme<String>(Pays.getLibellesFiltres());
		cboxPays.addItemListener(controleur);
	 	cboxPays.setPreferredSize(new Dimension(200, 45));
	 	panelChoixFiltres.add(cboxPays);

		// Ajouter les filtres
		panelTableauFiltres.add(panelChoixFiltres);
		
		// ScrollPane englobant le tableau
		JScrollPaneTheme scrollPaneEquipes = new JScrollPaneTheme();
		
		// Tableau d'équipes
		table = new JTableTheme(
			new String[] {"ID", "Nom", "Pays", "World Ranking", "Actions"},
			controleur
		);
		this.model = (DefaultTableModel) table.getModel();
		
		// Règles d'affichage du drapeau du pays
		TableColumn paysColumn = table.getColumnModel().getColumn(2);
	    paysColumn.setCellRenderer(new ImageTableCellRenderer());
		
		scrollPaneEquipes.setViewportView(table);

		panelCorps.add(scrollPaneEquipes, BorderLayout.CENTER);

		this.remplirTableau(this.controleur.getEquipes());

		super.afficherVue(contentPane, "Équipes", btnAjouter, panelCorps);
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
		this.vueJoueurs = new VueJoueursArbitres(joueurs, this);
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
	 * Remet les filtres à leur état initial
	 */
	@Override
	public void resetFiltres() {
		this.cboxPays.setSelectedIndex(0);
	}

	/**
	 * Remplit/met à jour le tableau d'équipes
	 * @param equipes : liste des équipes à mettre dans le tableau
	 */
	@Override
	public void remplirTableau(List<Equipe> equipes) {
		// Vider le tableau
		this.model.setRowCount(0);
		
		// Remplir avec les données d'équipes
		for(Equipe equipe : equipes) {
		    Vector<Object> rowData = new Vector<>();
		    rowData.add(equipe.getIdEquipe());
		    rowData.add(equipe.getNom());
		    
		    Pays pays = equipe.getPays();
	        ImageIcon drapeau = pays.getDrapeauPays();
	        rowData.add(new LabelIcon(drapeau, equipe.getPays().getNomPays()));
	        
		    rowData.add(equipe.getWorldRanking());
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
	public boolean estCboxPays(JComboBoxTheme<?> comboBox) {
		return comboBox.equals(this.cboxPays);
	}

	/**
	 * Retourne le pays sélectionné dans la comboBox
	 * @return le pays sélectionné dans la comboBox
	 */
	public Pays getPaysSelectionne() {
		if(this.cboxPays.getSelectedIndex() == 0) {
			return null;
		}
		return Pays.valueOfNom(this.cboxPays.getSelectedItem().toString());
	}

	/**
	 * Retourne vueBase
	 * @return vueBase
	 */
	public VueBase getVueBase() {
		return this.vueBase;
	}

}
