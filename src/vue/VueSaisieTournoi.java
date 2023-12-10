package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurSaisieTournoi;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class VueSaisieTournoi extends JFrameTheme {

	private JPanel contentPane;
	
	private JTextField txtNom;
	private JTextField txtDateDebut;
	private JTextField txtIdentifiantArbitres;
	private JPasswordField motDePasseArbitres;
	private JTextField txtDateFin;
	private JComboBox<String> cboxNotoriete;
	
	private JList<Equipe> listeEquipes;
	private JList<Arbitre> listeArbitres;
	
	private DefaultListModel<Equipe> listModelEquipes;
	private DefaultListModel<Arbitre> listModelArbitres;
	
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipe;
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiArbitre;

	public VueSaisieTournoi() {
		ControleurSaisieTournoi controleur = new ControleurSaisieTournoi(this);
		
		this.listModelEquipes = new DefaultListModel<>();
		this.listModelArbitres = new DefaultListModel<>();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 517, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane = super.getContentPane();
		
		JPanel panelCentre = new JPanel();
		contentPane.add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[]{0, 0};
		gbl_panelCentre.rowHeights = new int[]{0, 0, 0};
		gbl_panelCentre.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentre.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		JPanel panelSaisie = new JPanel();
		GridBagConstraints gbc_panelSaisie = new GridBagConstraints();
		gbc_panelSaisie.insets = new Insets(0, 0, 5, 0);
		gbc_panelSaisie.fill = GridBagConstraints.BOTH;
		gbc_panelSaisie.gridx = 0;
		gbc_panelSaisie.gridy = 0;
		panelCentre.add(panelSaisie, gbc_panelSaisie);
		panelSaisie.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelInfo = new JPanel();
		panelSaisie.add(panelInfo);
		GridBagLayout gbl_panelInfo = new GridBagLayout();
		gbl_panelInfo.columnWidths = new int[]{0, 0};
		gbl_panelInfo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelInfo.setLayout(gbl_panelInfo);
		
		JLabel lblNom = new JLabel("Nom du tournoi");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfo.add(lblNom, gbc_lblNom);
		
		txtNom = new JTextField();
		GridBagConstraints gbc_textFieldNom = new GridBagConstraints();
		gbc_textFieldNom.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNom.gridx = 0;
		gbc_textFieldNom.gridy = 1;
		panelInfo.add(txtNom, gbc_textFieldNom);
		txtNom.setColumns(10);
		
		JLabel lblDateDebut = new JLabel("Date début");
		GridBagConstraints gbc_lblDateDebut = new GridBagConstraints();
		gbc_lblDateDebut.insets = new Insets(0, 0, 5, 0);
		gbc_lblDateDebut.gridx = 0;
		gbc_lblDateDebut.gridy = 2;
		panelInfo.add(lblDateDebut, gbc_lblDateDebut);
		
		txtDateDebut = new JTextField();
		GridBagConstraints gbc_textFieldDateDebut = new GridBagConstraints();
		gbc_textFieldDateDebut.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDateDebut.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDateDebut.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDateDebut.gridx = 0;
		gbc_textFieldDateDebut.gridy = 3;
		panelInfo.add(txtDateDebut, gbc_textFieldDateDebut);
		txtDateDebut.setColumns(10);
		
		JLabel lblDateFin = new JLabel("Date Fin");
		GridBagConstraints gbc_lblDateFin = new GridBagConstraints();
		gbc_lblDateFin.insets = new Insets(0, 0, 5, 0);
		gbc_lblDateFin.gridx = 0;
		gbc_lblDateFin.gridy = 4;
		panelInfo.add(lblDateFin, gbc_lblDateFin);
		
		txtDateFin = new JTextField();
		GridBagConstraints gbc_textFieldDateFin = new GridBagConstraints();
		gbc_textFieldDateFin.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDateFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDateFin.gridx = 0;
		gbc_textFieldDateFin.gridy = 5;
		panelInfo.add(txtDateFin, gbc_textFieldDateFin);
		txtDateFin.setColumns(10);
		
		JPanel panel_4_1 = new JPanel();
		panelSaisie.add(panel_4_1);
		GridBagLayout gbl_panel_4_1 = new GridBagLayout();
		gbl_panel_4_1.columnWidths = new int[]{0, 0};
		gbl_panel_4_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_4_1.setLayout(gbl_panel_4_1);
		
		JLabel lblIdentifiant = new JLabel("Identifiant ");
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 0;
		panel_4_1.add(lblIdentifiant, gbc_lblIdentifiant);
		
		txtIdentifiantArbitres = new JTextField();
		txtIdentifiantArbitres.setColumns(10);
		GridBagConstraints gbc_textFieldIdentifiant = new GridBagConstraints();
		gbc_textFieldIdentifiant.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldIdentifiant.gridx = 0;
		gbc_textFieldIdentifiant.gridy = 1;
		panel_4_1.add(txtIdentifiantArbitres, gbc_textFieldIdentifiant);
		
		JLabel lblMDP = new JLabel("Mot de passe");
		GridBagConstraints gbc_lblMDP = new GridBagConstraints();
		gbc_lblMDP.insets = new Insets(0, 0, 5, 0);
		gbc_lblMDP.gridx = 0;
		gbc_lblMDP.gridy = 2;
		panel_4_1.add(lblMDP, gbc_lblMDP);
		
		motDePasseArbitres = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 3;
		panel_4_1.add(motDePasseArbitres, gbc_passwordField);
		
		JLabel lblNotoriete = new JLabel("Notoriété");
		GridBagConstraints gbc_lblNotoriete = new GridBagConstraints();
		gbc_lblNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_lblNotoriete.gridx = 0;
		gbc_lblNotoriete.gridy = 4;
		panel_4_1.add(lblNotoriete, gbc_lblNotoriete);
		
		cboxNotoriete = new JComboBox<String>();
		cboxNotoriete.setModel(new DefaultComboBoxModel<>());
	    for (Tournoi.Notoriete notoriete : Tournoi.Notoriete.values()) {
	        cboxNotoriete.addItem(notoriete.getLibelle());
	    }
		GridBagConstraints gbc_comboBoxNotoriete = new GridBagConstraints();
		gbc_comboBoxNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxNotoriete.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxNotoriete.gridx = 0;
		gbc_comboBoxNotoriete.gridy = 5;
		panel_4_1.add(cboxNotoriete, gbc_comboBoxNotoriete);
		
		JPanel panelAjouter = new JPanel();
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.fill = GridBagConstraints.BOTH;
		gbc_panelAjouter.gridx = 0;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelAjouter, gbc_panelAjouter);
		panelAjouter.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelEquipes = new JPanel();
		panelAjouter.add(panelEquipes);
		panelEquipes.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitreEquipes = new JLabel("Equipes inscrites");
		panelEquipes.add(lblTitreEquipes, BorderLayout.NORTH);
		
		JPanel panelBtn = new JPanel();
		panelEquipes.add(panelBtn, BorderLayout.SOUTH);
		
		JButton btnAjouterEquipe = new JButton("Ajouter une équipe");
		btnAjouterEquipe.addActionListener(controleur);
		panelBtn.add(btnAjouterEquipe);
		
		JScrollPane scrollPaneListeEquipes = new JScrollPane();
		panelEquipes.add(scrollPaneListeEquipes, BorderLayout.CENTER);
		
		listeEquipes = new JList<>(listModelEquipes);
		listeEquipes.setCellRenderer(new ListCellRenderer());
		listeEquipes.addListSelectionListener(controleur);
		scrollPaneListeEquipes.setViewportView(listeEquipes);
		
		JPanel panelArbitres = new JPanel();
		panelAjouter.add(panelArbitres);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitreArbitre = new JLabel("Arbitres désignés");
		panelArbitres.add(lblTitreArbitre, BorderLayout.NORTH);
		
		JPanel panelBtnAjouterArbitre = new JPanel();
		panelArbitres.add(panelBtnAjouterArbitre, BorderLayout.SOUTH);
		
		JButton btnAjouterArbitre = new JButton("Ajouter un arbitre");
		btnAjouterArbitre.addActionListener(controleur);
		panelBtnAjouterArbitre.add(btnAjouterArbitre);
		
		JScrollPane scrollPaneListeArbitre = new JScrollPane();
		panelArbitres.add(scrollPaneListeArbitre, BorderLayout.CENTER);
		
		listeArbitres = new JList<>(listModelArbitres);
		listeArbitres.setCellRenderer(new ListCellRenderer());
		listeArbitres.addListSelectionListener(controleur);
		scrollPaneListeArbitre.setViewportView(listeArbitres);
		
		JPanel panelBtnCreer = new JPanel();
		contentPane.add(panelBtnCreer, BorderLayout.SOUTH);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(controleur);
		panelBtnCreer.add(btnAnnuler);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(controleur);
		panelBtnCreer.add(btnValider);
		
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblTitre = new JLabel("Ajouter un tournoi");
		panelTitre.add(lblTitre);
	}
	
	private class ListCellRenderer extends DefaultListCellRenderer {
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        if(value instanceof Equipe) {
	        	this.setText(((Equipe) value).getNom());
	        } else if(value instanceof Arbitre) {
	        	this.setText(((Arbitre) value).getNom());
	        }

	        return this;
	    }
	}
	
	public String getNomTournoi() {
        return txtNom.getText().trim();
    }

    public String getDateDebut() {
        return txtDateDebut.getText().trim();
    }

    public String getDateFin() {
        return txtDateFin.getText().trim();
    }

    public String getIdentifiant() {
        return txtIdentifiantArbitres.getText().trim();
    }

    public String getMotDePasse() {
        return new String(motDePasseArbitres.getPassword());
    }
    
	public String getNotoriete() {
		return (String) this.cboxNotoriete.getSelectedItem();
	}
	
	public void afficherVueSaisieTournoiEquipe(Equipe[] equipes) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
        if (this.vueSaisieTournoiEquipe == null || !this.vueSaisieTournoiEquipe.isVisible()) {
        	this.vueSaisieTournoiEquipe = new VueSaisieTournoiEquipeArbitre(VueSaisieTournoiEquipeArbitre.Type.EQUIPE, this, equipes);
        	this.ajouterFenetreEnfant(this.vueSaisieTournoiEquipe);
        	this.vueSaisieTournoiEquipe.setLocationRelativeTo(this);
        	this.vueSaisieTournoiEquipe.setVisible(true);
        } else {
        	this.vueSaisieTournoiEquipe.toFront();
        }
	}
	
	public void afficherVueSaisieTournoiArbitre(Arbitre[] arbitres) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
        if (this.vueSaisieTournoiArbitre == null || !this.vueSaisieTournoiArbitre.isVisible()) {
        	this.vueSaisieTournoiArbitre = new VueSaisieTournoiEquipeArbitre(VueSaisieTournoiEquipeArbitre.Type.ARBITRE, this, arbitres);
        	this.ajouterFenetreEnfant(this.vueSaisieTournoiArbitre);
        	this.vueSaisieTournoiArbitre.setLocationRelativeTo(this);
        	this.vueSaisieTournoiArbitre.setVisible(true);
        } else {
        	this.vueSaisieTournoiArbitre.toFront();
        }
	}
	
	public void ajouterEquipe(Equipe equipe) {
		this.listModelEquipes.addElement(equipe);
	}
	
	public void supprimerEquipe(Equipe equipe) {
		this.listModelEquipes.removeElement(equipe);
	}
	
	public void ajouterArbitre(Arbitre arbitre) {
		this.listModelArbitres.addElement(arbitre);
	}
	
	public void supprimerArbitre(Arbitre arbitre) {
		this.listModelArbitres.removeElement(arbitre);
	}
	
	public boolean estListeEquipes(JList<?> liste) {
		return liste.equals(this.listeEquipes);
	}
	
	public boolean estListeArbitres(JList<?> liste) {
		return liste.equals(this.listeArbitres);
	}

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

}
