package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import controleur.ControleurSaisieTournoi;
import modele.metier.Arbitre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JDatePickerImplTheme;
import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JPasswordFieldTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

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
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;

import javax.swing.SpinnerNumberModel;

/**
 * Vue de la saisie d'un tournoi
 */
public class VueSaisieTournoi extends JFrameTheme {

	private JPanel contentPane;
	
	private JList<Arbitre> listeArbitres;
	private DefaultListModel<Arbitre> listModelArbitres;
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

	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiArbitre;

	/**
     * Constructeur de l'IHM pour la saisie d'un tournoi
     * @param vueTournois La vue principale des tournois
     * @param tournoiOptionnel Un tournoi optionnel pour initialiser le formulaire (pour modification)
     */
	public VueSaisieTournoi(VueTournois vueTournois, Optional<Tournoi> tournoiOptionnel) {
		// Initialisation du contrôleur avec les paramètres nécessaires
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
		
		// Panel Titre
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTitre, BorderLayout.NORTH);
		
		// Label Titre
		JLabelTheme lblTitre;
		if (tournoi == null) {
			lblTitre = new JLabelTheme("Création d'un tournoi", 30, true);
		} else {
			lblTitre = new JLabelTheme("Modification d'un tournoi", 30, true);
		}
		panelTitre.add(lblTitre);

		// Panel centre
		JPanel panelCentre = new JPanel(); 
		panelCentre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[]{0, 0};
		gbl_panelCentre.rowHeights = new int[]{0, 0, 0};
		gbl_panelCentre.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelCentre.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		// Panel saisie
		JPanel panelSaisie = new JPanel();
		panelSaisie.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelSaisie = new GridBagConstraints();
		gbc_panelSaisie.insets = new Insets(0, 0, 5, 0);
		gbc_panelSaisie.fill = GridBagConstraints.BOTH;
		gbc_panelSaisie.gridx = 0;
		gbc_panelSaisie.gridy = 0;
		panelCentre.add(panelSaisie, gbc_panelSaisie);
		panelSaisie.setLayout(new GridLayout(1, 0, 0, 0));
		
		// Panel Info
		JPanel panelInfo2 = new JPanel();
		panelInfo2.setBackground(CharteGraphique.FOND);
		panelSaisie.add(panelInfo2);
		GridBagLayout gbl_panelInfo2 = new GridBagLayout();
		gbl_panelInfo2.columnWeights = new double[]{1.0};
		gbl_panelInfo2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panelInfo2.setLayout(gbl_panelInfo2);

		// Label tournoi
		JLabelTheme lblNom = new JLabelTheme("Nom du tournoi", 19, true);
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 0;
		panelInfo2.add(lblNom, gbc_lblNom);
		
		// Champ nom du tournoi
		txtNom = new JTextFieldTheme(20);
		txtNom.setColumns(10);
		GridBagConstraints gbc_txtNom = new GridBagConstraints();
		gbc_txtNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNom.insets = new Insets(0, 0, 5, 0);
		gbc_txtNom.gridx = 0;
		gbc_txtNom.gridy = 1;
		panelInfo2.add(txtNom, gbc_txtNom);

		// Panel saisie info
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(CharteGraphique.FOND);
        panelSaisie.add(panelInfo);
        GridBagLayout gbl_panelInfo = new GridBagLayout();
        gbl_panelInfo.columnWeights = new double[]{1.0};
        gbl_panelInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panelInfo.setLayout(gbl_panelInfo);
		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Label Notoriété
		JLabelTheme lblNotoriete = new JLabelTheme("Notoriété", 19, true);
		GridBagConstraints gbc_lblNotoriete = new GridBagConstraints();
		gbc_lblNotoriete.insets = new Insets(0, 0, 3, 0);
		gbc_lblNotoriete.gridx = 0;
		gbc_lblNotoriete.gridy = 0;
		panelInfo.add(lblNotoriete, gbc_lblNotoriete);
		
		// Liste Notoriétés
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
		
		// Label identifiant de les arbitres
		JLabelTheme lblIdentifiant = new JLabelTheme("Identifiant arbitres", 19, true);
		GridBagConstraints gbc_lblIdentifiant = new GridBagConstraints();
		gbc_lblIdentifiant.insets = new Insets(0, 0, 5, 0);
		gbc_lblIdentifiant.gridx = 0;
		gbc_lblIdentifiant.gridy = 2;
		panelInfo2.add(lblIdentifiant, gbc_lblIdentifiant);
		
		// Champ identifiant de les arbitres
		txtIdentifiantArbitres = new JTextFieldTheme(20);
		txtIdentifiantArbitres.setColumns(10);
		GridBagConstraints gbc_txtIdentifiantArbitres = new GridBagConstraints();
		gbc_txtIdentifiantArbitres.insets = new Insets(0, 0, 5, 0);
		gbc_txtIdentifiantArbitres.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIdentifiantArbitres.gridx = 0;
		gbc_txtIdentifiantArbitres.gridy = 3;
		panelInfo2.add(txtIdentifiantArbitres, gbc_txtIdentifiantArbitres);
		panelInfo2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Label Mot de passe de les arbitres
		JLabelTheme lblMDP = new JLabelTheme("Mot de passe arbitres", 19, true);
		GridBagConstraints gbc_lblMDP = new GridBagConstraints();
		gbc_lblMDP.insets = new Insets(0, 0, 5, 0);
		gbc_lblMDP.gridx = 0;
		gbc_lblMDP.gridy = 2;
		panelInfo.add(lblMDP, gbc_lblMDP);
		
		// Champ Mot de passe de les arbitres
		motDePasseArbitres = new JPasswordFieldTheme(20);
		motDePasseArbitres.setPreferredSize(new Dimension(motDePasseArbitres.getPreferredSize().width, 45));
		GridBagConstraints gbc_motDePasseArbitres = new GridBagConstraints();
		gbc_motDePasseArbitres.insets = new Insets(0, 0, 5, 0);
		gbc_motDePasseArbitres.fill = GridBagConstraints.HORIZONTAL;
		gbc_motDePasseArbitres.gridx = 0;
		gbc_motDePasseArbitres.gridy = 3;
		panelInfo.add(motDePasseArbitres, gbc_motDePasseArbitres);

		// Label date debut
        JLabelTheme lblDateDebut = new JLabelTheme("Date début", 19, true);
        GridBagConstraints gbc_lblDateDebut = new GridBagConstraints();
        gbc_lblDateDebut.insets = new Insets(0, 0, 5, 0);
        gbc_lblDateDebut.gridx = 0;
        gbc_lblDateDebut.gridy = 4;
        panelInfo2.add(lblDateDebut, gbc_lblDateDebut);
		
		// Champ date debut
        modelDateDebut = new UtilDateModel();
        JDatePanelImpl dateDebutPanel = new JDatePanelImpl(modelDateDebut, properties);
        dateDebutPanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        JDatePickerImplTheme dateDebutPicker = new JDatePickerImplTheme(dateDebutPanel, new DateLabelFormatter());
        dateDebutPicker.setBackground(CharteGraphique.FOND_SECONDAIRE);
        GridBagConstraints gbc_dateDebutPicker = new GridBagConstraints();
        gbc_dateDebutPicker.anchor = GridBagConstraints.NORTH;
        gbc_dateDebutPicker.insets = new Insets(0, 0, 5, 0);
        gbc_dateDebutPicker.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateDebutPicker.gridx = 0;
        gbc_dateDebutPicker.gridy = 5;
        panelInfo2.add(dateDebutPicker, gbc_dateDebutPicker);

		// Panel heure debut
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

		// Label heure debut (heures)
        JLabelTheme lblHeure = new JLabelTheme("Heure : ", 14, true);
        panelHeureDebut.add(lblHeure);

		// Spinner heure debut (heures)
        spinnerHeuresDebut = new JSpinner();
        spinnerHeuresDebut.setBackground(CharteGraphique.FOND_SECONDAIRE);
        spinnerHeuresDebut.setForeground(CharteGraphique.TEXTE); 
        spinnerHeuresDebut.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        panelHeureDebut.add(spinnerHeuresDebut);

		// Label heure debut (minutes)
        JLabelTheme lblHeurDebut = new JLabelTheme("h", 14, true);
        panelHeureDebut.add(lblHeurDebut);

		// Spinner heure debut (minutes)
        spinnerMinutesDebut = new JSpinner();
        spinnerMinutesDebut.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        panelHeureDebut.add(spinnerMinutesDebut);

		// Label date fin
        JLabelTheme lblDateFin = new JLabelTheme("Date fin", 19, true);
        GridBagConstraints gbc_lblDateFin = new GridBagConstraints();
        gbc_lblDateFin.insets = new Insets(0, 0, 5, 0);
        gbc_lblDateFin.gridx = 0;
        gbc_lblDateFin.gridy = 5;
        panelInfo.add(lblDateFin, gbc_lblDateFin);

		// Champ date fin
        modelDateFin = new UtilDateModel();
        JDatePanelImpl dateFinPanel = new JDatePanelImpl(modelDateFin, properties);
        JDatePickerImplTheme dateFinPicker = new JDatePickerImplTheme(dateFinPanel, new DateLabelFormatter());
        GridBagConstraints gbc_dateFinPicker = new GridBagConstraints();
        gbc_dateFinPicker.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateFinPicker.gridx = 0;
        gbc_dateFinPicker.gridy = 6;
        panelInfo.add(dateFinPicker, gbc_dateFinPicker);

		// Panel heure fin
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

		// Label heure fin (heures)
        JLabelTheme lblHeureFin = new JLabelTheme("Heure : ", 14, true);
        panelHeureFin.add(lblHeureFin);

		// Spinner heure fin (heures)
        spinnerHeuresFin = new JSpinner();
        spinnerHeuresFin.setBackground(CharteGraphique.FOND);
        spinnerHeuresFin.setModel(new SpinnerNumberModel(0, 0, 23, 1));
        panelHeureFin.add(spinnerHeuresFin);

		// Label heure fin (minutes)
        JLabelTheme lblHeureFinh = new JLabelTheme("h", 14, true);
		
        panelHeureFin.add(lblHeureFinh);
        
		// Spinner heure fin (minutes)
        spinnerMinutesFin = new JSpinner();
        spinnerMinutesFin.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        panelHeureFin.add(spinnerMinutesFin);

		// Panel ajouter arbitre
		JPanel panelAjouter = new JPanel();
		panelAjouter.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.fill = GridBagConstraints.BOTH;
		gbc_panelAjouter.gridx = 0;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelAjouter, gbc_panelAjouter);
		panelAjouter.setLayout(new GridLayout(1, 0, 0, 0));
		
		// Panel arbitres
		JPanel panelArbitres = new JPanel();
		panelArbitres.setBorder(new EmptyBorder(5, 0, 0, 0));
		panelArbitres.setBackground(CharteGraphique.FOND);
		panelAjouter.add(panelArbitres);
		panelArbitres.setLayout(new BorderLayout(0, 0));
		panelArbitres.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
		
		// Label arbitres
		JLabelTheme lblTitreArbitre = new JLabelTheme("Arbitres désignés", 19, true);
		
		// Panel titre et bouton
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

		// Bouton ajouter arbitre
		btnAjouterArbitre = new JButtonTheme(JButtonTheme.Types.PRIMAIRE,"Ajouter un arbitre");
		btnAjouterArbitre.setActionCommand("AJOUTER_ARBITRE");
		btnAjouterArbitre.setIcon(new ImageIcon(VueSaisieTournoi.class.getResource("/images/buttons/ajouter.png")));
		btnAjouterArbitre.addActionListener(controleur);
		btnAjouterArbitre.setBorder(new EmptyBorder(8,8,8,8));
		gbc.gridx = 2; 
		gbc.gridy = 0; 
		gbc.anchor = GridBagConstraints.EAST; 
		gbc.insets = new Insets(0, 0, 5, 0);
		panelTitleAndButton.add(btnAjouterArbitre, gbc);
		panelArbitres.add(panelTitleAndButton, BorderLayout.NORTH);

		// Si aucun arbitre n'est éligible, le bouton est désactivé
		if (controleur.getArbitresEligibles().length == 0) {
			this.btnAjouterArbitre.setEnabled(false);
		}
		
		// Liste arbitres
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
		
		// Panel boutons valider/annuler
		JPanel panelBtnValider = new JPanel();
		panelBtnValider.setBackground(CharteGraphique.FOND);
		panelBtnValider.setLayout(new BoxLayout(panelBtnValider, BoxLayout.X_AXIS));
		contentPane.add(panelBtnValider, BorderLayout.SOUTH);
		panelBtnValider.add(Box.createHorizontalGlue());

		// Bouton annuler
		JButtonTheme btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Annuler");
		btnAnnuler.setActionCommand("ANNULER");
		btnAnnuler.setMargin(new Insets(5, 10, 5, 10)); 
		btnAnnuler.setBorder(new EmptyBorder(10, 10, 10, 10));
		btnAnnuler.addActionListener(controleur);
		panelBtnValider.add(btnAnnuler);
		panelBtnValider.add(Box.createRigidArea(new Dimension(10, 0)));
		
		// Bouton valider
		JButtonTheme btnValider;
		// Si aucun tournoi n'est renseigné, on affiche un bouton "Valider"
		if (tournoi == null) {
			btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Valider");
			btnValider.setActionCommand("VALIDER");
		} else {
			btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Modifier");
			btnValider.setActionCommand("MODIFIER");
		}
		btnValider.addActionListener(controleur);
		btnValider.setIcon(new ImageIcon(VueSaisieTournoi.class.getResource("/images/buttons/valider.png")));
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
			
			Calendar calendarDebut = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
			calendarDebut.setTimeInMillis(tournoi.getDateTimeDebut() * 1000L);

			Calendar calendarFin = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
			calendarFin.setTimeInMillis(tournoi.getDateTimeFin() * 1000L);

			this.spinnerHeuresDebut.setValue(calendarDebut.get(Calendar.HOUR_OF_DAY));
			this.spinnerMinutesDebut.setValue(calendarDebut.get(Calendar.MINUTE));
			this.spinnerHeuresFin.setValue(calendarFin.get(Calendar.HOUR_OF_DAY));
			this.spinnerMinutesFin.setValue(calendarFin.get(Calendar.MINUTE));

			this.cboxNotoriete.setSelectedItem(tournoi.getNotoriete().getLibelle());
			System.out.println(tournoi.getArbitres());
			for (Arbitre arbitre : tournoi.getArbitres()) {
				this.listModelArbitres.addElement(arbitre);
			}
		}
	}

	/*
	 * Classe interne pour afficher les arbitres dans la liste
	 */
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
	
	/*
	 * Classe interne pour formatter les dates
	 */
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
	
	/**
	 * Retourne le contenu du champ nom du tournoi
	 * @return La valeur du champ
	 */
	public String getNomTournoi() {
        return this.txtNom.getText().trim();
    }

	/**
	 * Retourne la date et l'heure sous forme de timestamp en secondes
	 * @param date La date
	 * @param heure L'heure
	 * @param minute Les minutes
	 * @return Le timestamp correspondant à la date et l'heure en secondes
	 */
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
	
	/**
	 * Retourne la date et l'heure de début sous forme de timestamp en secondes 
	 * @return La valeur du champ
	 */
	public long getDateTimeDebut() {
		// Récupérer la date du modèle
		Date dateDebut = this.modelDateDebut.getValue();
	
		// Récupérer l'heure depuis les spinners
		int heure = (int) spinnerHeuresDebut.getValue();
		int minute = (int) spinnerMinutesDebut.getValue();
	
		return getDateTime(dateDebut, heure, minute);
	}

	/**
	 * Retourne la date et l'heure de fin sous forme de timestamp en secondes
	 * @return La valeur du champ
	 */
	public long getDateTimeFin() {
		// Récupérer la date du modèle
		Date dateFin = this.modelDateFin.getValue();
	
		// Récupérer l'heure depuis les spinners
		int heure = (int) spinnerHeuresFin.getValue();
		int minute = (int) spinnerMinutesFin.getValue();
	
		return getDateTime(dateFin, heure, minute);
	}

	/**
	 * Retourne l'identifiant de l'arbitre 
	 * @return La valeur du champ
	 */
    public String getIdentifiant() {
        return txtIdentifiantArbitres.getText().trim();
    }

	/**
	 * Retourne le mot de passe de l'arbitre
	 * @return La valeur du champ
	 */
    public String getMotDePasse() {
        return new String(motDePasseArbitres.getPassword());
    }
    
	/**
	 * Retourne la notoriété du tournoi
	 * @return La valeur du champ
	 */
	public Notoriete getNotoriete() {
		return Notoriete.valueOfLibelle((String) this.cboxNotoriete.getSelectedItem());
	}
	
	/**
	 * Afficher la vue de saisie d'un arbitre
	 * @param arbitres La liste des arbitres
	 */
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
	
	/**
	 * Ajouter un arbitre à la liste
	 * @param arbitre L'arbitre à ajouter
	 */
	public void ajouterArbitre(Arbitre arbitre) {
		if(!this.listModelArbitres.contains(arbitre)) {
			this.listModelArbitres.addElement(arbitre);
		}
	}
	
	/**
	 * Supprimer un arbitre de la liste
	 * @param arbitre L'arbitre à supprimer
	 */
	public void supprimerArbitre(Arbitre arbitre) {
		this.listModelArbitres.removeElement(arbitre);
	}
	
	/**
	 * Verifier si la liste est la liste des arbitres
	 * @param liste La liste à vérifier
	 * @return true si la liste est la liste des arbitres
	 */
	public boolean estListeArbitres(JList<?> liste) {
		return liste.equals(this.listeArbitres);
	}
	
	/**
	 * Retourner la liste des arbitres sélectionnés
	 * @return La liste des arbitres
	 */
	public List<Arbitre> getArbitres() {
	    List<Arbitre> arbitres = new ArrayList<>();
	    for (int i = 0; i < listModelArbitres.size(); i++) {
	        arbitres.add(listModelArbitres.getElementAt(i));
	    }
	    return arbitres;
	}

	/**
	 * Afficher un message 
	 * @param message Le message à afficher
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
	 * Verifier si tous les champs sont remplis
	 * @return true si tous les champs sont remplis
	 */
	public boolean tousChampsRemplis() {
		return !this.getNomTournoi().isEmpty()
			&& !this.getIdentifiant().isEmpty()
			&& !this.getMotDePasse().isEmpty()
			&& modelDateDebut.getValue() != null
			&& modelDateFin.getValue() != null;
	}
	
	/**
	 * Méthode pour activer ou désactiver le bouton d'ajout d'arbitre
	 * @param actif true pour activer le bouton, false pour le désactiver
	 */
	public void setBtnAjouterArbitreActif(boolean actif) {
		this.btnAjouterArbitre.setEnabled(actif);
	}

}
