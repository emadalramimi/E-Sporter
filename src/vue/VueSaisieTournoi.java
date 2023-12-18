package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controleur.ControleurSaisieTournoi;
import modele.metier.Arbitre;
import modele.metier.Tournoi;
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
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import java.util.TimeZone;
import java.util.Optional;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import java.awt.FlowLayout;
import javax.swing.SpinnerNumberModel;

public class VueSaisieTournoi extends JFrameTheme {

	private JPanel contentPane;
	
	private JList<Arbitre> listeArbitres;
	private DefaultListModel<Arbitre> listModelArbitres;
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiArbitre;
	private JTextFieldTheme txtNom;
	private JTextFieldTheme txtIdentifiantArbitres;
	private JPasswordFieldTheme motDePasseArbitres;
	private JComboBoxTheme<String> cboxNotoriete;
	private UtilDateModel modelDateDebut;
	private UtilDateModel modelDateFin;
	private JSpinner spinnerHeuresDebut;
	private JSpinner spinnerMinutesDebut;
	private JSpinner spinnerHeuresFin;
	private JSpinner spinnerMinutesFin;
	private JButtonTheme btnAjouterArbitre;

	public VueSaisieTournoi(VueTournois vueTournois, Optional<Tournoi> tournoiOptionnel) {
		ControleurSaisieTournoi controleur = new ControleurSaisieTournoi(this, vueTournois, tournoiOptionnel);

		// Récupère le tournoi fourni, sinon null
		Tournoi tournoi = tournoiOptionnel.orElse(null);
		
		this.listModelArbitres = new DefaultListModel<>();

        Properties properties = new Properties();
        properties.put("text.today", "Aujourd'hui");
        properties.put("text.month", "Mois");
        properties.put("text.year", "Année");
        
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 670);
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
		

		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTitre, BorderLayout.NORTH);
		
		JLabel lblTitre;
		if (tournoi == null) {
			lblTitre = new JLabel("Création d'un tournoi");
		} else {
			lblTitre = new JLabel("Modification d'un tournoi");
		}
		lblTitre.setFont(CharteGraphique.getPolice(30, true));
		lblTitre.setForeground(CharteGraphique.TEXTE);
		panelTitre.add(lblTitre);

		JPanel panelSaisie = new JPanel();
		panelSaisie.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelSaisie = new GridBagConstraints();
		gbc_panelSaisie.insets = new Insets(0, 0, 5, 0);
		gbc_panelSaisie.fill = GridBagConstraints.BOTH;
		gbc_panelSaisie.gridx = 0;
		gbc_panelSaisie.gridy = 0;
		panelCentre.add(panelSaisie, gbc_panelSaisie);
		panelSaisie.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelInfo2 = new JPanel();
		panelInfo2.setBackground(CharteGraphique.FOND);
		panelSaisie.add(panelInfo2);
		GridBagLayout gbl_panelInfo2 = new GridBagLayout();
		gbl_panelInfo2.columnWeights = new double[]{1.0};
		gbl_panelInfo2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelInfo2.setLayout(gbl_panelInfo2);
		
		JLabel lblIdentifiant = new JLabel("Identifiant arbitres");
		lblIdentifiant.setForeground(CharteGraphique.TEXTE);
		lblIdentifiant.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 2;
		panelInfo2.add(lblIdentifiant, gbc_lblIdentifiant);
		
		txtIdentifiantArbitres = new JTextFieldTheme(20);
		txtIdentifiantArbitres.setColumns(10);
		GridBagConstraints gbc_txtIdentifiantArbitres = new GridBagConstraints();
		gbc_txtIdentifiantArbitres.insets = new Insets(0, 0, 5, 0);
		gbc_txtIdentifiantArbitres.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIdentifiantArbitres.gridx = 0;
		gbc_txtIdentifiantArbitres.gridy = 3;
		panelInfo2.add(txtIdentifiantArbitres, gbc_txtIdentifiantArbitres);
		panelInfo2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(CharteGraphique.FOND);
        panelSaisie.add(panelInfo);
        GridBagLayout gbl_panelInfo = new GridBagLayout();
        gbl_panelInfo.columnWeights = new double[]{1.0};
        gbl_panelInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panelInfo.setLayout(gbl_panelInfo);
		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel lblMDP = new JLabel("Mot de passe arbitres");
		lblMDP.setForeground(CharteGraphique.TEXTE);
		lblMDP.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblMDP = new GridBagConstraints();
		gbc_lblMDP.insets = new Insets(0, 0, 5, 0);
		gbc_lblMDP.gridx = 0;
		gbc_lblMDP.gridy = 2;
		panelInfo.add(lblMDP, gbc_lblMDP);
		
		motDePasseArbitres = new JPasswordFieldTheme(20);
		motDePasseArbitres.setPreferredSize(new Dimension(motDePasseArbitres.getPreferredSize().width, 45));
		GridBagConstraints gbc_motDePasseArbitres = new GridBagConstraints();
		gbc_motDePasseArbitres.insets = new Insets(0, 0, 5, 0);
		gbc_motDePasseArbitres.fill = GridBagConstraints.HORIZONTAL;
		gbc_motDePasseArbitres.gridx = 0;
		gbc_motDePasseArbitres.gridy = 3;
		panelInfo.add(motDePasseArbitres, gbc_motDePasseArbitres);
		
		JLabel lblNotoriete = new JLabel("Notoriété");
		lblNotoriete.setForeground(CharteGraphique.TEXTE);
		lblNotoriete.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblNotoriete = new GridBagConstraints();
		gbc_lblNotoriete.insets = new Insets(0, 0, 3, 0);
		gbc_lblNotoriete.gridx = 0;
		gbc_lblNotoriete.gridy = 0;
		panelInfo.add(lblNotoriete, gbc_lblNotoriete);
		
		List<String> notorietes = new ArrayList<>();
		for (Notoriete notoriete : Notoriete.values()) {
			notorietes.add(notoriete.getLibelle());
		}
		cboxNotoriete = new JComboBoxTheme<String>(notorietes.toArray(new String[0]));
		Dimension d = cboxNotoriete.getPreferredSize();
		d.height = 45;
		cboxNotoriete.setPreferredSize(d);
				
		GridBagConstraints gbc_cboxNotoriete = new GridBagConstraints();
		gbc_cboxNotoriete.insets = new Insets(0, 0, 5, 0);
		gbc_cboxNotoriete.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboxNotoriete.gridx = 0;
		gbc_cboxNotoriete.gridy = 1;
		panelInfo.add(cboxNotoriete, gbc_cboxNotoriete);
		
		JLabel lblNom = new JLabel("Nom du tournoi");
		lblNom.setForeground(CharteGraphique.TEXTE);
		lblNom.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfo2.add(lblNom, gbc_lblNom);
		
		txtNom = new JTextFieldTheme(20);
		txtNom.setColumns(10);
		GridBagConstraints gbc_txtNom = new GridBagConstraints();
		gbc_txtNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNom.insets = new Insets(0, 0, 5, 0);
		gbc_txtNom.gridx = 0;
		gbc_txtNom.gridy = 1;
		panelInfo2.add(txtNom, gbc_txtNom);

        JLabel lblDateDebut = new JLabel("Date début");
        lblDateDebut.setForeground(CharteGraphique.TEXTE);
        lblDateDebut.setFont(CharteGraphique.getPolice(19, true));
        GridBagConstraints gbc_lblDateDebut = new GridBagConstraints();
        gbc_lblDateDebut.insets = new Insets(0, 0, 5, 0);
        gbc_lblDateDebut.gridx = 0;
        gbc_lblDateDebut.gridy = 4;
        panelInfo2.add(lblDateDebut, gbc_lblDateDebut);

        modelDateDebut = new UtilDateModel();
        JDatePanelImpl dateDebutPanel = new JDatePanelImpl(modelDateDebut, properties);
        dateDebutPanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        JDatePickerImpl dateDebutPicker = new JDatePickerImpl(dateDebutPanel, new DateLabelFormatter());
        dateDebutPicker.setBackground(CharteGraphique.FOND_SECONDAIRE);
        GridBagConstraints gbc_dateDebutPicker = new GridBagConstraints();
        gbc_dateDebutPicker.anchor = GridBagConstraints.NORTH;
        gbc_dateDebutPicker.insets = new Insets(0, 0, 5, 0);
        gbc_dateDebutPicker.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateDebutPicker.gridx = 0;
        gbc_dateDebutPicker.gridy = 5;
        panelInfo2.add(dateDebutPicker, gbc_dateDebutPicker);

        JPanel panelHeureDebut = new JPanel();
        panelHeureDebut.setBackground(CharteGraphique.FOND);
        FlowLayout flowLayout_1 = new FlowLayout();
        flowLayout_1.setVgap(0);
        panelHeureDebut.setLayout(flowLayout_1);
        GridBagConstraints gbc_panelHeureDebut = new GridBagConstraints();
        gbc_panelHeureDebut.insets = new Insets(0, 0, 5, 0);
        gbc_panelHeureDebut.fill = GridBagConstraints.BOTH;
        gbc_panelHeureDebut.gridx = 0;
        gbc_panelHeureDebut.gridy = 6;
        panelInfo2.add(panelHeureDebut, gbc_panelHeureDebut);

        JLabel lblHeure = new JLabel("Heure : ");
        lblHeure.setForeground(CharteGraphique.TEXTE);
        lblHeure.setFont(CharteGraphique.getPolice(14, true));
        panelHeureDebut.add(lblHeure);

        spinnerHeuresDebut = new JSpinner();
        spinnerHeuresDebut.setBackground(CharteGraphique.FOND_SECONDAIRE);
        spinnerHeuresDebut.setForeground(CharteGraphique.TEXTE); 
        spinnerHeuresDebut.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        panelHeureDebut.add(spinnerHeuresDebut);

        JLabel lblHeurDebut = new JLabel("h");
        lblHeurDebut.setForeground(CharteGraphique.TEXTE);
        lblHeurDebut.setFont(CharteGraphique.getPolice(14, true));
        panelHeureDebut.add(lblHeurDebut);

        spinnerMinutesDebut = new JSpinner();
        spinnerMinutesDebut.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        panelHeureDebut.add(spinnerMinutesDebut);

        JLabel lblDateFin = new JLabel("Date fin");
        lblDateFin.setForeground(CharteGraphique.TEXTE);
        lblDateFin.setFont(CharteGraphique.getPolice(19, true));
        GridBagConstraints gbc_lblDateFin = new GridBagConstraints();
        gbc_lblDateFin.insets = new Insets(0, 0, 5, 0);
        gbc_lblDateFin.gridx = 0;
        gbc_lblDateFin.gridy = 5;
        panelInfo.add(lblDateFin, gbc_lblDateFin);

        modelDateFin = new UtilDateModel();
        JDatePanelImpl dateFinPanel = new JDatePanelImpl(modelDateFin, properties);
        JDatePickerImpl dateFinPicker = new JDatePickerImpl(dateFinPanel, new DateLabelFormatter());
        GridBagConstraints gbc_dateFinPicker = new GridBagConstraints();
        gbc_dateFinPicker.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateFinPicker.gridx = 0;
        gbc_dateFinPicker.gridy = 6;
        panelInfo.add(dateFinPicker, gbc_dateFinPicker);

        JPanel panelHeureFin = new JPanel();
        panelHeureFin.setBackground(CharteGraphique.FOND);
        FlowLayout flowLayout_11 = new FlowLayout();
        flowLayout_11.setVgap(0);
        panelHeureFin.setLayout(flowLayout_11);
        GridBagConstraints gbc_panelHeureFin = new GridBagConstraints();
        gbc_panelHeureFin.insets = new Insets(5, 0, 5, 0);
        gbc_panelHeureFin.fill = GridBagConstraints.BOTH;
        gbc_panelHeureFin.gridx = 0;
        gbc_panelHeureFin.gridy = 7;
        panelInfo.add(panelHeureFin, gbc_panelHeureFin);

        JLabel lblHeureFin = new JLabel("Heure : ");
        lblHeureFin.setForeground(CharteGraphique.TEXTE);
        lblHeureFin.setFont(CharteGraphique.getPolice(14, true));
        panelHeureFin.add(lblHeureFin);

        spinnerHeuresFin = new JSpinner();
        spinnerHeuresFin.setBackground(CharteGraphique.FOND);
        spinnerHeuresFin.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        panelHeureFin.add(spinnerHeuresFin);

        JLabel lblHeureFinh = new JLabel("h");
        lblHeureFinh.setForeground(CharteGraphique.TEXTE);
        lblHeureFinh.setFont(CharteGraphique.getPolice(14, true));
        panelHeureFin.add(lblHeureFinh);
        
        spinnerMinutesFin = new JSpinner();
        spinnerMinutesFin.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        panelHeureFin.add(spinnerMinutesFin);

		JPanel panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.fill = GridBagConstraints.BOTH;
		gbc_panelAjouter.gridx = 0;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelAjouter, gbc_panelAjouter);
		panelAjouter.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panelArbitres = new JPanel();
		panelArbitres.setBorder(new EmptyBorder(5, 0, 0, 0));
		panelArbitres.setBackground(CharteGraphique.FOND);
		panelAjouter.add(panelArbitres);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		panelArbitres.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
		
		JLabel lblTitreArbitre = new JLabel("Arbitres désignés");
		lblTitreArbitre.setForeground(CharteGraphique.TEXTE);
		lblTitreArbitre.setFont(CharteGraphique.getPolice(19, true));
		
		JPanel panelTitleAndButton = new JPanel(new GridBagLayout());
		panelTitleAndButton.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.anchor = GridBagConstraints.WEST; 
		panelTitleAndButton.add(lblTitreArbitre, gbc);
		gbc.gridx = 1;
		gbc.weightx = 1; 
		JPanel fillerPanel = new JPanel();
		fillerPanel.setOpaque(false);  
		panelTitleAndButton.add(fillerPanel, gbc);

		btnAjouterArbitre = new JButtonTheme(JButtonTheme.Types.PRIMAIRE,"Ajouter un arbitre");
		btnAjouterArbitre.addActionListener(controleur);
		btnAjouterArbitre.setBorder(new EmptyBorder(8,8,8,8));
		gbc.gridx = 2; 
		gbc.gridy = 0; 
		gbc.anchor = GridBagConstraints.EAST; 
		gbc.insets = new Insets(0, 0, 5, 0);
		panelTitleAndButton.add(btnAjouterArbitre, gbc);
		panelArbitres.add(panelTitleAndButton, BorderLayout.NORTH);

		if (controleur.getArbitresEligibles().length == 0) {
			this.btnAjouterArbitre.setEnabled(false);
		}
		
		JScrollPane scrollPaneListeArbitre = new JScrollPane();
		scrollPaneListeArbitre.setBackground(CharteGraphique.FOND_SECONDAIRE);
		scrollPaneListeArbitre.setBorder(null);
		panelArbitres.add(scrollPaneListeArbitre, BorderLayout.CENTER);
		
		listeArbitres = new JList<>(listModelArbitres);
		listeArbitres.setBackground(CharteGraphique.FOND_SECONDAIRE);
		listeArbitres.setForeground(CharteGraphique.TEXTE);
		listeArbitres.setFont(CharteGraphique.getPolice(16, false));
		listeArbitres.setCellRenderer(new ArbitreListCellRenderer());
		listeArbitres.addListSelectionListener(controleur);
		scrollPaneListeArbitre.setViewportView(listeArbitres);
		
		JPanel panelBtnValider = new JPanel();
		panelBtnValider.setBackground(CharteGraphique.FOND);
		panelBtnValider.setLayout(new BoxLayout(panelBtnValider, BoxLayout.X_AXIS));
		contentPane.add(panelBtnValider, BorderLayout.SOUTH);
		panelBtnValider.add(Box.createHorizontalGlue());

		JButtonTheme btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Annuler");
		btnAnnuler.setMargin(new Insets(5, 10, 5, 10)); 
		btnAnnuler.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnAnnuler.addActionListener(controleur);
		panelBtnValider.add(btnAnnuler);
		panelBtnValider.add(Box.createRigidArea(new Dimension(10, 0)));
		
		JButtonTheme btnValider;
		if (tournoi == null) {
			btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Valider");
		} else {
			btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Modifier");
		}
		btnValider.addActionListener(controleur);
		btnValider.setMargin(new Insets(5, 10, 5, 10)); 
		btnValider.setBorder(new EmptyBorder(10,10,10,10));
		panelBtnValider.add(btnValider);
		panelBtnValider.add(Box.createHorizontalGlue());
		
		// Remplissage des champs du formulaire si un tournoi est renseigné pour modification
		if (tournoi != null) {
			this.txtNom.setText(tournoi.getNomTournoi());
			this.txtIdentifiantArbitres.setText(tournoi.getIdentifiant());
			this.modelDateDebut.setValue(new Date(tournoi.getDateTimeDebut() * 1000));
			this.modelDateFin.setValue(new Date(tournoi.getDateTimeFin() * 1000));
			this.cboxNotoriete.setSelectedItem(tournoi.getNotoriete().getLibelle());
			System.out.println(tournoi.getArbitres());
			for (Arbitre arbitre : tournoi.getArbitres()) {
				this.listModelArbitres.addElement(arbitre);
			}
		}
	}
	
	private class ArbitreListCellRenderer extends DefaultListCellRenderer {
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        if(value instanceof Arbitre) {
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

	private long getDateTime(Date date, int heure, int minute) {
		// Combiner la date et l'heure pour former un objet Calendar
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, heure);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	
		// Obtenir le timestamp en secondes
		long timestamp = calendar.getTimeInMillis() / 1000;
	
		// Retourner le timestamp correspondant à la date et l'heure en secondes
		return timestamp;
	}
	
	public long getDateTimeDebut() {
		// Récupérer la date du modèle
		Date dateDebut = this.modelDateDebut.getValue();
	
		// Récupérer l'heure depuis les spinners
		int heure = (int) spinnerHeuresDebut.getValue();
		int minute = (int) spinnerMinutesDebut.getValue();
	
		return getDateTime(dateDebut, heure, minute);
	}
	
	public long getDateTimeFin() {
		// Récupérer la date du modèle
		Date dateFin = this.modelDateFin.getValue();
	
		// Récupérer l'heure depuis les spinners
		int heure = (int) spinnerHeuresFin.getValue();
		int minute = (int) spinnerMinutesFin.getValue();
	
		return getDateTime(dateFin, heure, minute);
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
	
	public void afficherVueSaisieTournoiArbitre(Arbitre[] arbitres) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
        if (this.vueSaisieTournoiArbitre == null || !this.vueSaisieTournoiArbitre.isVisible()) {
        	this.vueSaisieTournoiArbitre = new VueSaisieTournoiEquipeArbitre(this, arbitres);
        	this.ajouterFenetreEnfant(this.vueSaisieTournoiArbitre);
        	this.vueSaisieTournoiArbitre.setLocationRelativeTo(this);
        	this.vueSaisieTournoiArbitre.setVisible(true);
        } else {
        	this.vueSaisieTournoiArbitre.toFront();
        }
	}
	
	public void ajouterArbitre(Arbitre arbitre) {
		if(!this.listModelArbitres.contains(arbitre)) {
			this.listModelArbitres.addElement(arbitre);
		}
	}
	
	public void supprimerArbitre(Arbitre arbitre) {
		this.listModelArbitres.removeElement(arbitre);
	}
	
	public boolean estListeArbitres(JList<?> liste) {
		return liste.equals(this.listeArbitres);
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
	
	public void setBtnAjouterArbitreActif(boolean actif) {
		this.btnAjouterArbitre.setEnabled(actif);
	}

}
