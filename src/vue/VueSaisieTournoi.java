package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controleur.ControleurSaisieTournoi;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Tournoi.Notoriete;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JPasswordFieldTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import java.awt.FlowLayout;
import javax.swing.SpinnerNumberModel;

public class VueSaisieTournoi extends JFrameTheme {

	private JPanel contentPane;
	
	private JList<Equipe> listeEquipes;
	private JList<Arbitre> listeArbitres;
	
	private DefaultListModel<Equipe> listModelEquipes;
	private DefaultListModel<Arbitre> listModelArbitres;
	
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipe;
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiArbitre;
	private JTextFieldTheme txtNom;
	private JTextFieldTheme txtIdentifiantArbitres;
	private JPasswordFieldTheme motDePasseArbitres;
	private JComboBoxTheme<String> cboxNotoriete;
	private JSpinner spinner;
	private JSpinner spinner_1;
	private JSpinner spinner1;
	private JSpinner spinner_11;
	
	private UtilDateModel modelDateDebut;
	private UtilDateModel modelDateFin;

	public VueSaisieTournoi() {
		ControleurSaisieTournoi controleur = new ControleurSaisieTournoi(this);
		
		this.listModelEquipes = new DefaultListModel<>();
		this.listModelArbitres = new DefaultListModel<>();

        Properties properties = new Properties();
        properties.put("text.today", "Aujourd'hui");
        properties.put("text.month", "Mois");
        properties.put("text.year", "Année");
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 812, 626);
		contentPane = super.getContentPane();
		contentPane.setBorder(new EmptyBorder(25, 25, 25, 25));
		
		JPanel panelCentre = new JPanel(); 
		panelCentre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[]{0, 0};
		gbl_panelCentre.rowHeights = new int[]{0, 0, 0};
		gbl_panelCentre.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentre.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		JPanel panelSaisie = new JPanel();
		panelSaisie.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelSaisie = new GridBagConstraints();
		gbc_panelSaisie.insets = new Insets(0, 0, 5, 0);
		gbc_panelSaisie.fill = GridBagConstraints.BOTH;
		gbc_panelSaisie.gridx = 0;
		gbc_panelSaisie.gridy = 0;
		panelCentre.add(panelSaisie, gbc_panelSaisie);
		panelSaisie.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBackground(CharteGraphique.FOND);
		panelSaisie.add(panel_4_1);
		GridBagLayout gbl_panel_4_1 = new GridBagLayout();
		gbl_panel_4_1.columnWeights = new double[]{1.0};
		gbl_panel_4_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_4_1.setLayout(gbl_panel_4_1);
		
		JLabel lblIdentifiant = new JLabel("Identifiant ");
		lblIdentifiant.setForeground(CharteGraphique.TEXTE);
		lblIdentifiant.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 2;
		panel_4_1.add(lblIdentifiant, gbc_lblIdentifiant);
		
		txtIdentifiantArbitres = new JTextFieldTheme(20);
		txtIdentifiantArbitres.setColumns(10);
		GridBagConstraints gbc_txtIdentifiantArbitres = new GridBagConstraints();
		gbc_txtIdentifiantArbitres.insets = new Insets(0, 0, 5, 0);
		gbc_txtIdentifiantArbitres.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIdentifiantArbitres.gridx = 0;
		gbc_txtIdentifiantArbitres.gridy = 3;
		panel_4_1.add(txtIdentifiantArbitres, gbc_txtIdentifiantArbitres);
		
		JLabel lblMDP = new JLabel("Mot de passe");
		lblMDP.setForeground(CharteGraphique.TEXTE);
		lblMDP.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblMDP = new GridBagConstraints();
		gbc_lblMDP.insets = new Insets(0, 0, 5, 0);
		gbc_lblMDP.gridx = 0;
		gbc_lblMDP.gridy = 4;
		panel_4_1.add(lblMDP, gbc_lblMDP);
		
		motDePasseArbitres = new JPasswordFieldTheme(20);
		GridBagConstraints gbc_motDePasseArbitres = new GridBagConstraints();
		gbc_motDePasseArbitres.insets = new Insets(0, 0, 5, 0);
		gbc_motDePasseArbitres.fill = GridBagConstraints.HORIZONTAL;
		gbc_motDePasseArbitres.gridx = 0;
		gbc_motDePasseArbitres.gridy = 5;
		panel_4_1.add(motDePasseArbitres, gbc_motDePasseArbitres);
		
		JLabel lblNotoriete = new JLabel("Notoriété");
		lblNotoriete.setForeground(CharteGraphique.TEXTE);
		lblNotoriete.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblNotoriete = new GridBagConstraints();
		gbc_lblNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_lblNotoriete.gridx = 0;
		gbc_lblNotoriete.gridy = 6;
		panel_4_1.add(lblNotoriete, gbc_lblNotoriete);
		
		List<String> notorietes = new ArrayList<>();
		for (Notoriete notoriete : Notoriete.values()) {
			notorietes.add(notoriete.getLibelle());
		}
		cboxNotoriete = new JComboBoxTheme<String>(notorietes.toArray(new String[0]));
		
		GridBagConstraints gbc_cboxNotoriete = new GridBagConstraints();
		gbc_cboxNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_cboxNotoriete.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboxNotoriete.gridx = 0;
		gbc_cboxNotoriete.gridy = 7;
		panel_4_1.add(cboxNotoriete, gbc_cboxNotoriete);
		
		JLabel lblNom = new JLabel("Nom du tournoi");
		lblNom.setForeground(CharteGraphique.TEXTE);
		lblNom.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panel_4_1.add(lblNom, gbc_lblNom);
		
		txtNom = new JTextFieldTheme(20);
		txtNom.setColumns(10);
		GridBagConstraints gbc_txtNom = new GridBagConstraints();
		gbc_txtNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNom.gridx = 0;
		gbc_txtNom.gridy = 1;
		panel_4_1.add(txtNom, gbc_txtNom);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(CharteGraphique.FOND);
        panelSaisie.add(panelInfo);
        GridBagLayout gbl_panelInfo = new GridBagLayout();
        panelInfo.setLayout(gbl_panelInfo);

        JLabel lblDateDebut = new JLabel("Date début");
        lblDateDebut.setForeground(CharteGraphique.TEXTE);
        lblDateDebut.setFont(CharteGraphique.getPolice(19, true));
        GridBagConstraints gbc_lblDateDebut = new GridBagConstraints();
        gbc_lblDateDebut.insets = new Insets(0, 0, 5, 0);
        gbc_lblDateDebut.gridx = 0;
        gbc_lblDateDebut.gridy = 0;
        panelInfo.add(lblDateDebut, gbc_lblDateDebut);

        modelDateDebut = new UtilDateModel();
        JDatePanelImpl dateDebutPanel = new JDatePanelImpl(modelDateDebut, properties);
        JDatePickerImpl dateDebutPicker = new JDatePickerImpl(dateDebutPanel, new DateLabelFormatter());
        GridBagConstraints gbc_dateDebutPicker = new GridBagConstraints();
        gbc_dateDebutPicker.anchor = GridBagConstraints.NORTH;
        gbc_dateDebutPicker.insets = new Insets(0, 0, 5, 0);
        gbc_dateDebutPicker.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateDebutPicker.gridx = 0;
        gbc_dateDebutPicker.gridy = 1;
        panelInfo.add(dateDebutPicker, gbc_dateDebutPicker);

        JPanel panel = new JPanel();
        panel.setBackground(CharteGraphique.FOND);
        FlowLayout flowLayout_1 = new FlowLayout();
        flowLayout_1.setVgap(0);
        panel.setLayout(flowLayout_1);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 2;
        panelInfo.add(panel, gbc_panel);

        JLabel lblHeure = new JLabel("Heure : ");
        lblHeure.setForeground(CharteGraphique.TEXTE);
        lblHeure.setFont(CharteGraphique.getPolice(14, true));
        panel.add(lblHeure);

        spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        panel.add(spinner);

        JLabel lblHeurDebut = new JLabel("h");
        lblHeurDebut.setForeground(CharteGraphique.TEXTE);
        lblHeurDebut.setFont(CharteGraphique.getPolice(14, true));
        panel.add(lblHeurDebut);

        spinner_1 = new JSpinner();
        spinner_1.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        panel.add(spinner_1);

        JLabel lblDateFin = new JLabel("Date fin");
        lblDateFin.setForeground(CharteGraphique.TEXTE);
        lblDateFin.setFont(CharteGraphique.getPolice(19, true));
        GridBagConstraints gbc_lblDateFin = new GridBagConstraints();
        gbc_lblDateFin.insets = new Insets(0, 0, 5, 0);
        gbc_lblDateFin.gridx = 0;
        gbc_lblDateFin.gridy = 3;
        panelInfo.add(lblDateFin, gbc_lblDateFin);

        modelDateFin = new UtilDateModel();
        JDatePanelImpl dateFinPanel = new JDatePanelImpl(modelDateFin, properties);
        JDatePickerImpl dateFinPicker = new JDatePickerImpl(dateFinPanel, new DateLabelFormatter());
        GridBagConstraints gbc_dateFinPicker = new GridBagConstraints();
        gbc_dateFinPicker.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateFinPicker.gridx = 0;
        gbc_dateFinPicker.gridy = 4;
        panelInfo.add(dateFinPicker, gbc_dateFinPicker);

        JPanel panel1 = new JPanel();
        panel1.setBackground(CharteGraphique.FOND);
        FlowLayout flowLayout_11 = new FlowLayout();
        flowLayout_11.setVgap(0);
        panel1.setLayout(flowLayout_11);
        GridBagConstraints gbc_panel1 = new GridBagConstraints();
        gbc_panel1.insets = new Insets(0, 0, 5, 0);
        gbc_panel1.fill = GridBagConstraints.BOTH;
        gbc_panel1.gridx = 0;
        gbc_panel1.gridy = 5; // Changer le grid y pour le placer à un emplacement différent
        panelInfo.add(panel1, gbc_panel1);

        JLabel lblHeureFin = new JLabel("Heure : ");
        lblHeureFin.setForeground(CharteGraphique.TEXTE);
        lblHeureFin.setFont(CharteGraphique.getPolice(14, true));
        panel1.add(lblHeureFin);

        spinner1 = new JSpinner();
        spinner1.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        panel1.add(spinner1);

        JLabel lblHeureFinh = new JLabel("h");
        lblHeureFinh.setForeground(CharteGraphique.TEXTE);
        lblHeureFinh.setFont(CharteGraphique.getPolice(14, true));
        panel1.add(lblHeureFinh);

        spinner_11 = new JSpinner();
        spinner_11.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        panel1.add(spinner_11);
		
		JPanel panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.fill = GridBagConstraints.BOTH;
		gbc_panelAjouter.gridx = 0;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelAjouter, gbc_panelAjouter);
		panelAjouter.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelEquipes = new JPanel();
		panelEquipes.setBorder(new EmptyBorder(0, 0, 0, 5));
		panelEquipes.setBackground(CharteGraphique.FOND);
		panelAjouter.add(panelEquipes);
		panelEquipes.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitreEquipes = new JLabel("Equipes inscrites");
		lblTitreEquipes.setForeground(CharteGraphique.TEXTE);
		lblTitreEquipes.setFont(CharteGraphique.getPolice(19, true));
		panelEquipes.add(lblTitreEquipes, BorderLayout.NORTH);
		
		JPanel panelBtn = new JPanel();
		panelBtn.setBackground(CharteGraphique.FOND);
		panelEquipes.add(panelBtn, BorderLayout.SOUTH);
		
		JButtonTheme btnAjouterEquipe = new JButtonTheme(JButtonTheme.Types.PRIMAIRE,"Ajouter une équipe");
		btnAjouterEquipe.addActionListener(controleur);
		panelBtn.add(btnAjouterEquipe);
		
		JScrollPane scrollPaneListeEquipes = new JScrollPane();
		scrollPaneListeEquipes.setBorder(null);
		scrollPaneListeEquipes.setBackground(CharteGraphique.FOND_SECONDAIRE);
		panelEquipes.add(scrollPaneListeEquipes, BorderLayout.CENTER);
		
		listeEquipes = new JList<>(listModelEquipes);
		listeEquipes.setCellRenderer(new ListCellRenderer());
		listeEquipes.addListSelectionListener(controleur);
		listeEquipes.setBackground(CharteGraphique.FOND_SECONDAIRE);
		listeEquipes.setForeground(CharteGraphique.TEXTE);
		listeEquipes.setFont(CharteGraphique.getPolice(16, false));
		scrollPaneListeEquipes.setViewportView(listeEquipes);
		
		JPanel panelArbitres = new JPanel();
		panelArbitres.setBorder(new EmptyBorder(5, 0, 0, 0));
		panelArbitres.setBackground(CharteGraphique.FOND);
		panelAjouter.add(panelArbitres);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitreArbitre = new JLabel("Arbitres désignés");
		lblTitreArbitre.setForeground(CharteGraphique.TEXTE);
		lblTitreArbitre.setFont(CharteGraphique.getPolice(19, true));
		panelArbitres.add(lblTitreArbitre, BorderLayout.NORTH);
		
		JPanel panelBtnAjouterArbitre = new JPanel();
		panelBtnAjouterArbitre.setBackground(CharteGraphique.FOND);
		panelArbitres.add(panelBtnAjouterArbitre, BorderLayout.SOUTH);
		
		JButtonTheme btnAjouterArbitre = new JButtonTheme(JButtonTheme.Types.PRIMAIRE,"Ajouter un arbitre");
		btnAjouterArbitre.addActionListener(controleur);
		panelBtnAjouterArbitre.add(btnAjouterArbitre);
		
		JScrollPane scrollPaneListeArbitre = new JScrollPane();
		scrollPaneListeArbitre.setBackground(CharteGraphique.FOND_SECONDAIRE);
		scrollPaneListeArbitre.setBorder(null);
		panelArbitres.add(scrollPaneListeArbitre, BorderLayout.CENTER);
		
		listeArbitres = new JList<>(listModelArbitres);
		listeArbitres.setBackground(CharteGraphique.FOND_SECONDAIRE);
		listeArbitres.setForeground(CharteGraphique.TEXTE);
		listeArbitres.setFont(CharteGraphique.getPolice(16, false));
		listeArbitres.setCellRenderer(new ListCellRenderer());
		listeArbitres.addListSelectionListener(controleur);
		scrollPaneListeArbitre.setViewportView(listeArbitres);
		
		JPanel panelBtnCreer = new JPanel();
		panelBtnCreer.setBackground(CharteGraphique.FOND);
		contentPane.add(panelBtnCreer, BorderLayout.SOUTH);
		
		JButtonTheme btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Annuler");
		btnAnnuler.setMargin(new Insets(5, 10, 5, 10)); 
		btnAnnuler.addActionListener(controleur);
		panelBtnCreer.add(btnAnnuler);
		
		JButtonTheme btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Valider");
		btnValider.addActionListener(controleur);
		btnValider.setMargin(new Insets(5, 10, 5, 10)); 
		panelBtnCreer.add(btnValider);
		
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblTitre = new JLabel("Ajouter un tournoi");
		lblTitre.setFont(CharteGraphique.getPolice(30, true));
		lblTitre.setForeground(CharteGraphique.TEXTE);
		panelTitre.add(lblTitre);
	}
	
	private class ListCellRenderer extends DefaultListCellRenderer {
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        if(value instanceof Equipe) {
	        	this.setText(((Equipe) value).getNom());
	        } else if(value instanceof Arbitre) {
	        	Arbitre arbitre = (Arbitre) value;
	        	this.setText(arbitre.getNom() + " " + arbitre.getPrenom());
	        }

	        return this;
	    }
	}
	
	private class DateLabelFormatter extends AbstractFormatter {
	    private String datePattern = "dd/MM/yyyy";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }
	}
	
	public String getNomTournoi() {
        return this.txtNom.getText().trim();
    }

	public long getDateTimeDebut() {
	    // Récupérer la date du modèle
	    Date dateDebut = this.modelDateDebut.getValue();
	    
	    // Récupérer l'heure depuis les spinners
	    int heure = (int) spinner.getValue();
	    int minute = (int) spinner_1.getValue();
	    
	    // Combiner la date et l'heure pour former un objet Calendar
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateDebut);
	    calendar.set(Calendar.HOUR_OF_DAY, heure);
	    calendar.set(Calendar.MINUTE, minute);
	    
	    // Obtenir le timestamp en millisecondes
	    long timestamp = calendar.getTimeInMillis() / 1000;
	    
	    // Retourner le timestamp correspondant à la date et l'heure de début en secondes
	    return timestamp;
	}

	public long getDateTimeFin() {
	    // Récupérer la date du modèle
	    Date dateFin = this.modelDateFin.getValue();
	    
	    // Récupérer l'heure depuis les spinners
	    int heure = (int) spinner.getValue();
	    int minute = (int) spinner_1.getValue();
	    
	    // Combiner la date et l'heure pour former un objet Calendar
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(dateFin);
	    calendar.set(Calendar.HOUR_OF_DAY, heure);
	    calendar.set(Calendar.MINUTE, minute);
	    
	    // Obtenir le timestamp en millisecondes
	    long timestamp = calendar.getTimeInMillis() / 1000;
	    
	    // Retourner le timestamp correspondant à la date et l'heure de début en secondes
	    return timestamp;
	}

    public String getIdentifiant() {
        return txtIdentifiantArbitres.getText().trim();
    }

    public String getMotDePasse() {
        return new String(motDePasseArbitres.getPassword());
    }
    
	public Notoriete getNotoriete() {
		return Notoriete.valueOfLibelle((String) this.cboxNotoriete.getSelectedItem());
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
		if(!this.listModelEquipes.contains(equipe)) {
			this.listModelEquipes.addElement(equipe);
		}
	}
	
	public void supprimerEquipe(Equipe equipe) {
		this.listModelEquipes.removeElement(equipe);
	}
	
	public void ajouterArbitre(Arbitre arbitre) {
		if(!this.listModelArbitres.contains(arbitre)) {
			this.listModelArbitres.addElement(arbitre);
		}
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
	
	public List<Equipe> getEquipes() {
	    List<Equipe> equipes = new ArrayList<>();
	    for (int i = 0; i < listModelEquipes.size(); i++) {
	        equipes.add(listModelEquipes.getElementAt(i));
	    }
	    return equipes;
	}
	
	public List<Arbitre> getArbitres() {
	    List<Arbitre> arbitres = new ArrayList<>();
	    for (int i = 0; i < listModelArbitres.size(); i++) {
	        arbitres.add(listModelArbitres.getElementAt(i));
	    }
	    return arbitres;
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
	
	public boolean tousChampsRemplis() {
	    return !this.getNomTournoi().isEmpty()
	    		&& !this.getIdentifiant().isEmpty()
	    		&& !this.getMotDePasse().isEmpty()
	    		&& modelDateDebut.getValue() != null
		        && modelDateFin.getValue() != null;
	}

}
