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

import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import controleur.ControleurInscriptionEquipesTournoiPopup;
import controleur.ControleurSaisieTournoiEquipeArbitre;

public class VueSaisieTournoiEquipeArbitre extends JFrameTheme {

	private JPanel contentPane;
	private JComboBoxTheme<?> comboBox;
	private Type type;
	private Equipe[] equipes;
	private Arbitre[] arbitres;
	private ActionListener controleur;
	
	public enum Type {
		EQUIPE,
		ARBITRE
	}

	public VueSaisieTournoiEquipeArbitre(VueInscriptionEquipesTournoi vueInscriptionEquipesTournoi, Equipe[] equipes, Tournoi tournoi) {
		this.type = VueSaisieTournoiEquipeArbitre.Type.EQUIPE;
		this.controleur = new ControleurInscriptionEquipesTournoiPopup(this, vueInscriptionEquipesTournoi, tournoi);
		this.equipes = equipes;
		this.afficher();
	}
	
	public VueSaisieTournoiEquipeArbitre(VueSaisieTournoi vueSaisieTournoi, Arbitre[] arbitres) {
		this.type = VueSaisieTournoiEquipeArbitre.Type.ARBITRE;
		this.controleur = new ControleurSaisieTournoiEquipeArbitre(this, vueSaisieTournoi);
		this.arbitres = arbitres;
		this.afficher();
	}
	
	private void afficher() {
		setDefaultCloseOperation(JFrameTheme.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 242, 138);

		contentPane = super.getContentPane();
		// TODO Border interne
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblTitre = null;
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
		
		JButtonTheme btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE,"Annuler");
		btnAnnuler.addActionListener(controleur);
		GridBagConstraints gbc_buttonAnnuler = new GridBagConstraints();
		gbc_buttonAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_buttonAnnuler.gridx = 0;
		gbc_buttonAnnuler.gridy = 2;
		contentPane.add(btnAnnuler, gbc_buttonAnnuler);
		
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
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.gridx = 1;
		gbc_btnAjouter.gridy = 2;
		contentPane.add(btnAjouter, gbc_btnAjouter);
	}
	
	private class EquipeArbitreComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {

	    public EquipeArbitreComboBoxRenderer() {
	        setOpaque(true);
	        setHorizontalAlignment(LEFT);
	        setVerticalAlignment(CENTER);
	    }

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
	
	public Equipe getEquipe() throws IllegalArgumentException {
		if(this.type != Type.EQUIPE) {
			throw new IllegalArgumentException("Ce n'est pas une liste d'équipes");
		}
		return (Equipe) this.comboBox.getSelectedItem();
	}
	
	public Arbitre getArbitre() throws IllegalArgumentException {
		if(this.type != Type.ARBITRE) {
			throw new IllegalArgumentException("Ce n'est pas une liste d'arbitres");
		}
		return (Arbitre) this.comboBox.getSelectedItem();
	}

}
