package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurInscriptionEquipesTournoi;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JScrollPaneTheme;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class VueInscriptionEquipesTournoi extends JFrameTheme {

	private JPanel contentPane;
	private JButtonTheme btnInscrireEquipe;
	private JLabel lblTitre;
	
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipe;
	private Tournoi tournoi;
	
	private DefaultListModel<Equipe> listModelEquipes;
	private JList<Equipe> listeEquipes;

	/**
     * Constructeur de l'IHM pour inscrire des équipes à un tournoi
	 * @param vueTournois : la fenêtre parente
	 * @param tournoi : le tournoi en cours d'inscription
     */
	public VueInscriptionEquipesTournoi(VueTournois vueTournois, Tournoi tournoi) {
		ControleurInscriptionEquipesTournoi controleur = new ControleurInscriptionEquipesTournoi(this, vueTournois, tournoi);
		this.tournoi = tournoi;
		this.listModelEquipes = new DefaultListModel<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 384);
		
		contentPane = super.getContentPane();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		contentPane.setLayout(new BorderLayout(0, 20));
		
		// Panel header
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(CharteGraphique.FOND);
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		// Panel titre
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		panelHeader.add(panelTitre, BorderLayout.WEST);
		GridBagLayout gbl_panelTitre = new GridBagLayout();
		gbl_panelTitre.columnWeights = new double[]{0.0};
		gbl_panelTitre.rowWeights = new double[]{0.0, 0.0};
		panelTitre.setLayout(gbl_panelTitre);
		
		// Label titre
		this.lblTitre = new JLabel();
		this.lblTitre.setFont(CharteGraphique.getPolice(19, true));
		this.lblTitre.setForeground(CharteGraphique.TEXTE);
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panelTitre.add(this.lblTitre, gbc_lblTitre);
		
		// Label nom du tournoi
		JLabel lblTournoi = new JLabel(tournoi.getNomTournoi());
		lblTournoi.setFont(CharteGraphique.getPolice(16, false));
		lblTournoi.setForeground(CharteGraphique.TEXTE);
		GridBagConstraints gbc_lblTournoi = new GridBagConstraints();
		gbc_lblTournoi.insets = new Insets(0, 0, 0, 5);
		gbc_lblTournoi.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTournoi.gridx = 0;
		gbc_lblTournoi.gridy = 1;
		panelTitre.add(lblTournoi, gbc_lblTournoi);
		
		// Bouton inscrire une équipe
		this.btnInscrireEquipe = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Inscrire une équipe");
		this.btnInscrireEquipe.addActionListener(controleur);
		this.btnInscrireEquipe.setFont(CharteGraphique.getPolice(17, false));

		// Panel bouton inscrire une équipe
		JPanel panelBtnInscrireEquipe = new JPanel();
		panelBtnInscrireEquipe.setBackground(CharteGraphique.FOND);
		panelBtnInscrireEquipe.setLayout(new BorderLayout());
		panelBtnInscrireEquipe.add(this.btnInscrireEquipe, BorderLayout.NORTH);

		panelHeader.add(panelBtnInscrireEquipe, BorderLayout.EAST);

		// Panel liste des équipes
		JPanel panelListe = new JPanel();
		panelListe.setBackground(CharteGraphique.FOND);
		contentPane.add(panelListe, BorderLayout.CENTER);
		panelListe.setLayout(new BorderLayout(0, 0));
		
		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		// Liste des équipes inscrites
		listeEquipes = new JList<>();
		listeEquipes.addListSelectionListener(controleur);
		listeEquipes.setBackground(CharteGraphique.FOND);
		listeEquipes.setFont(CharteGraphique.getPolice(16, false));
		listeEquipes.setCellRenderer(new EquipeListCellRenderer());
		listeEquipes.addListSelectionListener(controleur);
		listeEquipes.setBackground(CharteGraphique.FOND_SECONDAIRE);
		listeEquipes.setForeground(CharteGraphique.TEXTE);
		listeEquipes.setFont(CharteGraphique.getPolice(16, false));
		listeEquipes.setModel(listModelEquipes);
		scrollPane.setViewportView(listeEquipes);

		for (Equipe equipe : tournoi.getEquipes()) {
			this.listModelEquipes.addElement(equipe);
		}

		this.lblTitre.setText("Equipes inscrites (" + this.listModelEquipes.size() + ")");
		
		// Si le nombre d'équipes inscrites est supérieur ou égal à 8, on désactive le bouton inscrire une équipe
		if(this.listModelEquipes.size() >= 8 || controleur.getEquipesEligibles().length == 0) {
			this.btnInscrireEquipe.setEnabled(false);
		}

		// Création des boutons
		JButtonTheme btnFermer = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Fermer");
		btnFermer.setFont(CharteGraphique.getPolice(17, false));
		btnFermer.addActionListener(controleur);

		// Bouton ouvrir le tournoi
		JButtonTheme btnOuvrirTournoi = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ouvrir le tournoi");
		btnOuvrirTournoi.setFont(CharteGraphique.getPolice(17, false));
		btnOuvrirTournoi.addActionListener(controleur);

		// Création du panel pour les boutons
		JPanel panelBoutons = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(10);
		panelBoutons.setLayout(layout);
		panelBoutons.setBackground(CharteGraphique.FOND);

		// Ajout des boutons au panel
		panelBoutons.add(btnFermer);
		panelBoutons.add(btnOuvrirTournoi);

		// Ajout du panel à contentPane
		contentPane.add(panelBoutons, BorderLayout.SOUTH);
	}

	/*
	 * Classe interne pour afficher le nom de l'équipe dans la liste
	 */
	private class EquipeListCellRenderer extends DefaultListCellRenderer {
		/**
		 * Méthode pour afficher le nom de l'équipe dans la liste
		 * @param list : la liste des équipes
		 * @param value : l'équipe à afficher
		 * @param index : l'index de l'équipe dans la liste
		 * @param isSelected : si l'équipe est sélectionnée
		 * @param cellHasFocus : si l'équipe a le focus
		 * @return this : le JLabel
		 */
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        if(value instanceof Equipe) {
	        	this.setText(((Equipe) value).getNom());
	        }

	        return this;
	    }
	}
	
	/**
	 * Méthode pour savoir si la liste passée en paramètre est la liste des équipes
	 * @param liste : la liste à tester
	 * @return true si la liste est la liste des équipes, false sinon
	 */
	public boolean estListeEquipes(JList<?> liste) {
		return liste.equals(this.listeEquipes);
	}
	
	/**
	 * Méthode pour ajouter une équipe à la liste des équipes
	 * @param equipe : l'équipe à ajouter
	 */
	public void ajouterEquipe(Equipe equipe) {
		if(!this.listModelEquipes.contains(equipe)) {
			this.listModelEquipes.addElement(equipe);
			this.majTitre();
		}
	}
	
	/**
	 * Méthode pour supprimer une équipe de la liste des équipes
	 * @param equipe : l'équipe à supprimer
	 */
	public void supprimerEquipe(Equipe equipe) {
		this.listModelEquipes.removeElement(equipe);
		this.majTitre();
	}

	/**
	 * Méthode pour mettre à jour le titre de la fenêtre
	 */
	private void majTitre() {
		this.lblTitre.setText("Equipes inscrites (" + this.listModelEquipes.size() + ")");
	}
	
	/**
	 * Méthode pour récupérer la liste des équipes
	 * @return la liste des équipes inscrites
	 */
	public List<Equipe> getEquipes() {
	    List<Equipe> equipes = new ArrayList<>();
	    for (int i = 0; i < listModelEquipes.size(); i++) {
	        equipes.add(listModelEquipes.getElementAt(i));
	    }
	    return equipes;
	}
	
	/**
	 * Méthode pour afficher une boîte de dialogue de confirmation
	 * @param message : le message à afficher
	 * @return true si l'utilisateur a cliqué sur Oui, false sinon
	 */
	public boolean afficherConfirmationSuppression(String message) {
		Object[] options = {"Oui", "Annuler"};
        int choix = JOptionPaneTheme.showOptionDialog(
	    	null,
	    	message,
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
	 * Méthode pour afficher la fenêtre 
	 * @param equipes : la liste des équipes inscrites
	 */
	public void afficherVueSaisieTournoiEquipe(Equipe[] equipes) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
		if (this.vueSaisieTournoiEquipe == null || !this.vueSaisieTournoiEquipe.isVisible()) {
			this.vueSaisieTournoiEquipe = new VueSaisieTournoiEquipeArbitre(this, equipes, this.tournoi);
			this.ajouterFenetreEnfant(this.vueSaisieTournoiEquipe);
			this.vueSaisieTournoiEquipe.setLocationRelativeTo(this);
			this.vueSaisieTournoiEquipe.setVisible(true);
		} else {
			this.vueSaisieTournoiEquipe.toFront();
		}
	}
	
	/**
	 * Méthode pour désactiver le bouton inscrire une équipe
	 * @param actif : true pour activer le bouton, false pour le désactiver
	 */
	public void setBtnInscrireEquipeActif(boolean actif) {
		this.btnInscrireEquipe.setEnabled(actif);
	}

}
