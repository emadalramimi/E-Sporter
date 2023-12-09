package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

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

import controleur.ControleurSaisieTournoiEquipe;

public class VueSaisieTournoiEquipe extends JFrameTheme {

	private JPanel contentPane;
	private JComboBox<Equipe> comboBox;

	public VueSaisieTournoiEquipe(VueSaisieTournoi vueSaisieTournoi, Equipe[] equipes) {
		ControleurSaisieTournoiEquipe controleur = new ControleurSaisieTournoiEquipe(this, vueSaisieTournoi);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 242, 138);

		contentPane = super.getContentPane();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblNewLabel = new JLabel("Sélectionner une équipe");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 10, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		comboBox = new JComboBox<>(equipes);
		comboBox.setRenderer(new EquipeComboBoxRenderer());
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
		
		JButton btnNewButton = new JButton("Ajouter l'équipe");
		btnNewButton.addActionListener(controleur);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 2;
		contentPane.add(btnNewButton, gbc_btnNewButton);
	}
	
	private class EquipeComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {

	    public EquipeComboBoxRenderer() {
	        setOpaque(true);
	        setHorizontalAlignment(LEFT);
	        setVerticalAlignment(CENTER);
	    }

	    @Override
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        if (value instanceof Equipe) {
	            Equipe equipe = (Equipe) value;
	            setText(equipe.getNom()); // Afficher uniquement le nom de l'équipe
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
	
	public Equipe getEquipe() {
		return (Equipe) this.comboBox.getSelectedItem();
	}

}
