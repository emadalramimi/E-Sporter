package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import modele.metier.Arbitre;
import modele.metier.Equipe;
import vue.theme.JFrameTheme;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

import controleur.ControleurSaisieTournoiEquipeArbitre;

public class VueSaisieTournoiEquipeArbitre extends JFrameTheme {

	private JPanel contentPane;
	private JComboBox<?> comboBox;
	private Type type;
	private VueSaisieTournoi vueSaisieTournoi;
	private Equipe[] equipes;
	private Arbitre[] arbitres;
	
	public enum Type {
		EQUIPE,
		ARBITRE
	}

	public VueSaisieTournoiEquipeArbitre(Type type, VueSaisieTournoi vueSaisieTournoi, Equipe[] equipes) {
		this.type = type;
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.equipes = equipes;
		this.afficher();
	}
	
	public VueSaisieTournoiEquipeArbitre(Type type, VueSaisieTournoi vueSaisieTournoi, Arbitre[] arbitres) {
		this.type = type;
		this.vueSaisieTournoi = vueSaisieTournoi;
		this.arbitres = arbitres;
		this.afficher();
	}
	
	private void afficher() {
		ControleurSaisieTournoiEquipeArbitre controleur = new ControleurSaisieTournoiEquipeArbitre(this, this.vueSaisieTournoi);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 242, 138);

		contentPane = super.getContentPane();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = null;
		switch(this.type) {
		case EQUIPE:
			lblNewLabel = new JLabel("Sélectionner une équipe");
			break;
		case ARBITRE:
			lblNewLabel = new JLabel("Sélectionner un arbitre");
			break;
		}
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 10, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		switch(this.type) {
		case EQUIPE:
			comboBox = new JComboBox<Equipe>(equipes);
			break;
		case ARBITRE:
			comboBox = new JComboBox<Arbitre>(arbitres);
			break;
		}
		comboBox.setRenderer(new EquipeArbitreComboBoxRenderer());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 10, 0);
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		contentPane.add(comboBox, gbc_comboBox);
		
		JButton btnNewButton_1 = new JButton("Annuler");
		btnNewButton_1.addActionListener(controleur);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 2;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton = null;
		switch(this.type) {
		case EQUIPE:
			btnNewButton = new JButton("Ajouter l'équipe");
			break;
		case ARBITRE:
			btnNewButton = new JButton("Ajouter l'arbitre");
			break;
		}
		btnNewButton.addActionListener(controleur);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(btnNewButton, gbc_btnNewButton);
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
