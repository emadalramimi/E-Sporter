package vue;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JFrameTheme;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import controleur.ControleurInscriptionEquipesTournoiPopup;
import controleur.ControleurSaisieTournoiArbitre;

/**
 * Vue de la saisie d'un tournoi avec une liste d'équipes ou d'arbitres
 */
public class VueSaisieTournoiEquipeArbitre extends JFrameTheme {

	private JPanel contentPane;
	private JComboBoxTheme<?> comboBox;
	private Type type;
	private Equipe[] equipes;
	private Arbitre[] arbitres;
	private ActionListener controleur;
	
	// Enum pour savoir si on affiche une liste d'équipes ou d'arbitres
	public enum Type {
		EQUIPE,
		ARBITRE
	}

	/**
     * Constructeur de l'IHM pour la saisie d'un tournoi avec une liste d'équipes
	 * @param equipes : la liste des équipes
	 * @param tournoi : le tournoi en cours de création
     */
	public VueSaisieTournoiEquipeArbitre(VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, Equipe[] equipes, Tournoi tournoi) {
		this.type = VueSaisieTournoiEquipeArbitre.Type.EQUIPE;
		this.controleur = new ControleurInscriptionEquipesTournoiPopup(this, vueInscriptionEquipesTournoi, tournoi);
		this.equipes = equipes;
		this.afficher();
	}
	
	/**
     * Constructeur de l'IHM pour la saisie d'un tournoi avec une liste d'arbitres
	 * @param vueSaisieTournoi Vue saisie tournoi
	 * @param arbitres Tableau d'arbitres
     */
	public VueSaisieTournoiEquipeArbitre(VueSaisieTournoi vueSaisieTournoi, Arbitre[] arbitres) {
		this.type = VueSaisieTournoiEquipeArbitre.Type.ARBITRE;
		this.controleur = new ControleurSaisieTournoiArbitre(this, vueSaisieTournoi);
		this.arbitres = arbitres;
		this.afficher();
	}
	
	/**
	 * Méthode pour afficher l'IHM
	 */
	private void afficher() {
		setDefaultCloseOperation(JFrameTheme.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 180);

		contentPane = super.getContentPane();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);

		// Label titre
		JLabel lblTitre = null;
		// Selon le type de la liste, on affiche un titre différent
		switch(this.type) {
		case EQUIPE:
			lblTitre = new JLabel("Sélectionner une équipe");
			break;
		case ARBITRE:
			lblTitre = new JLabel("Sélectionner un arbitre");
			break;
		}
		lblTitre.setForeground(CharteGraphique.TEXTE);
		lblTitre.setFont(CharteGraphique.getPolice(19, true));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_titre = new GridBagConstraints();
		gbc_titre.gridwidth = 2;
		gbc_titre.insets = new Insets(0, 0, 10, 0);
		gbc_titre.gridx = 0;
		gbc_titre.gridy = 0;
		contentPane.add(lblTitre, gbc_titre);

		// ComboBox pour sélectionner l'équipe ou l'arbitre
		switch(this.type) {
		case EQUIPE:
			comboBox = new JComboBoxTheme<Equipe>(equipes);
			break;
		case ARBITRE:
			comboBox = new JComboBoxTheme<Arbitre>(arbitres);
			break;
		}
		comboBox.setRenderer(new EquipeArbitreComboBoxRenderer());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 10, 0);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		contentPane.add(comboBox, gbc_comboBox);
		
		// Boutons annuler et ajouter
		JButtonTheme btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE,"Annuler");
		btnAnnuler.addActionListener(controleur);
		GridBagConstraints gbc_buttonAnnuler = new GridBagConstraints();
		gbc_buttonAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_buttonAnnuler.gridx = 0;
		gbc_buttonAnnuler.gridy = 2;
		contentPane.add(btnAnnuler, gbc_buttonAnnuler);
		
		// Selon le type de la liste, on affiche un bouton différent
		JButtonTheme btnAjouter = null;
		switch(this.type) {
		case EQUIPE:
			btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter l'équipe");
			break;
		case ARBITRE:
			btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE,"Ajouter l'arbitre");
			break;
		}
		btnAjouter.addActionListener(controleur);
		btnAjouter.setIcon(new ImageIcon(VueSaisieTournoiEquipeArbitre.class.getResource("/images/buttons/ajouter.png")));
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.gridx = 1;
		gbc_btnAjouter.gridy = 2;
		contentPane.add(btnAjouter, gbc_btnAjouter);
	}
	
	/*
	 * Classe pour afficher le nom de l'équipe ou de l'arbitre dans la ComboBox
	 */
	private class EquipeArbitreComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
		
	    public EquipeArbitreComboBoxRenderer() {
	        setOpaque(true);
	        setHorizontalAlignment(LEFT);
	        setVerticalAlignment(CENTER);
	    }

		/**
		 * Méthode pour afficher le nom de l'équipe ou de l'arbitre dans la ComboBox
		 * @param list : la liste
		 * @param value : l'objet à afficher
		 * @param index : l'index de l'objet
		 * @param isSelected : si l'objet est sélectionné
		 * @param cellHasFocus : si l'objet a le focus
		 * @return this : le JLabel
		 */
	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        if (value instanceof Equipe) {
	            setText(((Equipe) value).getNom());
	        } else if (value instanceof Arbitre) {
	        	Arbitre arbitre = (Arbitre) value;
	        	setText(arbitre.getNom() + " " + arbitre.getPrenom());
	        }

	        if (isSelected) {
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
	        } else {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }

	        return this;
	    }
	}

	 /**
	  * Méthode pour récupérer l'équipe sélectionnée
	  * @return l'équipe sélectionnée
	  * @throws IllegalArgumentException
	  */
	public Equipe getEquipe() throws IllegalArgumentException {
		if(this.type != Type.EQUIPE) {
			throw new IllegalArgumentException("Ce n'est pas une liste d'équipes");
		}
		return (Equipe) this.comboBox.getSelectedItem();
	}
	
	/**
	 * Méthode pour récupérer l'arbitre sélectionné
	 * @return l'arbitre sélectionné
	 * @throws IllegalArgumentException
	 */
	public Arbitre getArbitre() throws IllegalArgumentException {
		if(this.type != Type.ARBITRE) {
			throw new IllegalArgumentException("Ce n'est pas une liste d'arbitres");
		}
		return (Arbitre) this.comboBox.getSelectedItem();
	}

}
