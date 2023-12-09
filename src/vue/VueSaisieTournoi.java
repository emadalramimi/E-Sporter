package vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Tournoi;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class VueSaisieTournoi extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldDateDebut;
	private JTextField textFieldIdentifiant;
	private JPasswordField passwordField;
	private JTextField textFieldDateFin;
	private JComboBox comboBoxNotoriete;
	private DefaultListModel<String> listeModel;
	private DefaultListModel<Equipe> listeEquipes;
	private DefaultListModel<Arbitre> listeArbitres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueSaisieTournoi frame = new VueSaisieTournoi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VueSaisieTournoi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 517, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
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
		gbl_panelInfo.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelInfo.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelInfo.setLayout(gbl_panelInfo);
		
		JLabel lblNom = new JLabel("Nom de le tournoi");
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfo.add(lblNom, gbc_lblNom);
		
		textFieldNom = new JTextField();
		GridBagConstraints gbc_textFieldNom = new GridBagConstraints();
		gbc_textFieldNom.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNom.gridx = 0;
		gbc_textFieldNom.gridy = 1;
		panelInfo.add(textFieldNom, gbc_textFieldNom);
		textFieldNom.setColumns(10);
		
		JLabel lblDateDebut = new JLabel("Date début");
		GridBagConstraints gbc_lblDateDebut = new GridBagConstraints();
		gbc_lblDateDebut.insets = new Insets(0, 0, 5, 0);
		gbc_lblDateDebut.gridx = 0;
		gbc_lblDateDebut.gridy = 2;
		panelInfo.add(lblDateDebut, gbc_lblDateDebut);
		
		textFieldDateDebut = new JTextField();
		GridBagConstraints gbc_textFieldDateDebut = new GridBagConstraints();
		gbc_textFieldDateDebut.anchor = GridBagConstraints.NORTH;
		gbc_textFieldDateDebut.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDateDebut.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDateDebut.gridx = 0;
		gbc_textFieldDateDebut.gridy = 3;
		panelInfo.add(textFieldDateDebut, gbc_textFieldDateDebut);
		textFieldDateDebut.setColumns(10);
		
		JLabel lblDateFin = new JLabel("Date Fin");
		GridBagConstraints gbc_lblDateFin = new GridBagConstraints();
		gbc_lblDateFin.insets = new Insets(0, 0, 5, 0);
		gbc_lblDateFin.gridx = 0;
		gbc_lblDateFin.gridy = 4;
		panelInfo.add(lblDateFin, gbc_lblDateFin);
		
		textFieldDateFin = new JTextField();
		GridBagConstraints gbc_textFieldDateFin = new GridBagConstraints();
		gbc_textFieldDateFin.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldDateFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldDateFin.gridx = 0;
		gbc_textFieldDateFin.gridy = 5;
		panelInfo.add(textFieldDateFin, gbc_textFieldDateFin);
		textFieldDateFin.setColumns(10);
		
		JPanel panel_4_1 = new JPanel();
		panelSaisie.add(panel_4_1);
		GridBagLayout gbl_panel_4_1 = new GridBagLayout();
		gbl_panel_4_1.columnWidths = new int[]{0, 0};
		gbl_panel_4_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_4_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_4_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_4_1.setLayout(gbl_panel_4_1);
		
		JLabel lblIdentifiant = new JLabel("Identifiant ");
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 0;
		panel_4_1.add(lblIdentifiant, gbc_lblIdentifiant);
		
		textFieldIdentifiant = new JTextField();
		textFieldIdentifiant.setColumns(10);
		GridBagConstraints gbc_textFieldIdentifiant = new GridBagConstraints();
		gbc_textFieldIdentifiant.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldIdentifiant.gridx = 0;
		gbc_textFieldIdentifiant.gridy = 1;
		panel_4_1.add(textFieldIdentifiant, gbc_textFieldIdentifiant);
		
		JLabel lblMDP = new JLabel("Mot de passe");
		GridBagConstraints gbc_lblMDP = new GridBagConstraints();
		gbc_lblMDP.insets = new Insets(0, 0, 5, 0);
		gbc_lblMDP.gridx = 0;
		gbc_lblMDP.gridy = 2;
		panel_4_1.add(lblMDP, gbc_lblMDP);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 3;
		panel_4_1.add(passwordField, gbc_passwordField);
		
		JLabel lblNotoriete = new JLabel("Notoriété");
		GridBagConstraints gbc_lblNotoriete = new GridBagConstraints();
		gbc_lblNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_lblNotoriete.gridx = 0;
		gbc_lblNotoriete.gridy = 4;
		panel_4_1.add(lblNotoriete, gbc_lblNotoriete);
		
		comboBoxNotoriete = new JComboBox();
		comboBoxNotoriete.setModel(new DefaultComboBoxModel<>());
	    for (Tournoi.Notoriete notoriete : Tournoi.Notoriete.values()) {
	        comboBoxNotoriete.addItem(notoriete.getLibelle());
	    }
		GridBagConstraints gbc_comboBoxNotoriete = new GridBagConstraints();
		gbc_comboBoxNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxNotoriete.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxNotoriete.gridx = 0;
		gbc_comboBoxNotoriete.gridy = 5;
		panel_4_1.add(comboBoxNotoriete, gbc_comboBoxNotoriete);
		
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
		btnAjouterEquipe.addActionListener(e -> {
			try {
				ouvrirVueSaisieTournoiEquipe();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		panelBtn.add(btnAjouterEquipe);
		
		JScrollPane scrollPaneListeEquipes = new JScrollPane();
		panelEquipes.add(scrollPaneListeEquipes, BorderLayout.CENTER);
		
		listeModel = new DefaultListModel<>();
		
		JList listEquipes = new JList(listeModel);
		scrollPaneListeEquipes.setViewportView(listEquipes);
		
		JPanel panelArbitres = new JPanel();
		panelAjouter.add(panelArbitres);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitreArbitre = new JLabel("Arbitres désignés");
		panelArbitres.add(lblTitreArbitre, BorderLayout.NORTH);
		
		JPanel panelBtnAjouterArbitre = new JPanel();
		panelArbitres.add(panelBtnAjouterArbitre, BorderLayout.SOUTH);
		
		JButton btnAjouterArbitre = new JButton("Ajouter un arbitre");
//		btnAjouterArbitre.addActionListener(e -> {
//			try {
//				ouvrirVueSaisieTournoiArbitre();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//		});
		panelBtnAjouterArbitre.add(btnAjouterArbitre);
		
		JScrollPane scrollPaneListeArbitre = new JScrollPane();
		panelArbitres.add(scrollPaneListeArbitre, BorderLayout.CENTER);
		
		JList listArbitres = new JList();
		scrollPaneListeArbitre.setViewportView(listArbitres);
		
		JPanel panelBtnCreer = new JPanel();
		contentPane.add(panelBtnCreer, BorderLayout.SOUTH);
		
		JButton btnAnnuler = new JButton("Annuler");
		panelBtnCreer.add(btnAnnuler);
		
		JButton btnCreerTournoi = new JButton("Creer tournoi");
		panelBtnCreer.add(btnCreerTournoi);
		
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblTitre = new JLabel("Ajouter un tournoi");
		panelTitre.add(lblTitre);
	}
	

	public String getNomTournoi() {
        return textFieldNom.getText();
    }

    public String getDateDebut() {
        return textFieldDateDebut.getText();
    }

    public String getDateFin() {
        return textFieldDateFin.getText();
    }

    public String getIdentifiant() {
        return textFieldIdentifiant.getText();
    }

    public String getMotDePasse() {
        return new String(passwordField.getPassword());
    }
	public String getNotoriete() {
		return (String) this.comboBoxNotoriete.getSelectedItem();
	}
    private void ouvrirVueSaisieTournoiEquipe() throws Exception {
    	VueSaisieTournoiEquipe VueSaisieTournoiEquipe = new VueSaisieTournoiEquipe(this, listeModel, listeEquipes);
    	VueSaisieTournoiEquipe.setVisible(true);
    }
    public List<Equipe> getEquipes() {
		return (List<Equipe>) this.listeEquipes;
	}
    public List<Arbitre> getArbitres() {
		return (List<Arbitre>) this.listeArbitres;
	}
    
//    private void ouvrirVueSaisieTournoiArbitre() throws Exception {
//    	VueSaisieTournoiArbitre VueSaisieTournoiArbitre = new VueSaisieTournoiArbitre(this, listeModel, listeArbitres);
//    	VueSaisieTournoiArbitre.setVisible(true);
//    }

}
