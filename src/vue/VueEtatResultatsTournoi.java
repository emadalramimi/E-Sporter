package vue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import vue.theme.JFrameTheme;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.ControleurEtatResultatsTournoi;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.Insets;
import java.util.Vector;

public class VueEtatResultatsTournoi extends JFrameTheme {

	private JTableTheme table;

	public VueEtatResultatsTournoi(Tournoi tournoi) {
        ControleurEtatResultatsTournoi controleur = new ControleurEtatResultatsTournoi(this);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 450);

		JPanel contentPane = super.getContentPane();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		contentPane.setLayout(new BorderLayout(0, 20));

		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(CharteGraphique.FOND);
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));

		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		panelHeader.add(panelTitre, BorderLayout.WEST);
		GridBagLayout gbl_panelTitre = new GridBagLayout();
		gbl_panelTitre.columnWeights = new double[] { 0.0 };
		gbl_panelTitre.rowWeights = new double[] { 0.0, 0.0 };
		panelTitre.setLayout(gbl_panelTitre);

		JLabel lblTitre = new JLabel("État des résultats du tournoi");
		lblTitre.setFont(CharteGraphique.getPolice(19, true));
		lblTitre.setForeground(CharteGraphique.TEXTE);
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panelTitre.add(lblTitre, gbc_lblTitre);

		JLabel lblTournoi = new JLabel(tournoi.getNomTournoi());
		lblTournoi.setFont(CharteGraphique.getPolice(16, false));
		lblTournoi.setForeground(CharteGraphique.TEXTE);
		GridBagConstraints gbc_lblTournoi = new GridBagConstraints();
		gbc_lblTournoi.insets = new Insets(0, 0, 0, 5);
		gbc_lblTournoi.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTournoi.gridx = 0;
		gbc_lblTournoi.gridy = 1;
		panelTitre.add(lblTournoi, gbc_lblTournoi);

		JPanel panelTableau = new JPanel();
		panelTableau.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTableau, BorderLayout.CENTER);
		panelTableau.setLayout(new BorderLayout(0, 0));

		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panelTableau.add(scrollPane, BorderLayout.CENTER);

		// Création de la table
		this.table = new JTableTheme();
		this.table.setBackground(CharteGraphique.FOND);
		this.table.setFont(CharteGraphique.getPolice(16, false));

		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {}, 
			new String[] { "Équipe", "Matchs joués", "Matchs gagnés", "Ratio" }
		) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Ajout des lignes
		for (StatistiquesEquipe statistiquesEquipe : controleur.getResultatsTournoi(tournoi)) {
			Vector<Object> row = new Vector<>();
			row.add(statistiquesEquipe.getEquipe().getNom());
			row.add(statistiquesEquipe.getNbMatchsJoues());
            row.add(statistiquesEquipe.getNbMatchsGagnes());
            row.add(statistiquesEquipe.getRatioPourcentage());
			model.addRow(row);
		}
		this.table.setModel(model);

		// Ajout de la table à scrollPane
		scrollPane.setViewportView(this.table);

		// Création des boutons
		JButtonTheme btnFermer = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Fermer");
		btnFermer.addActionListener(controleur);
		btnFermer.setFont(CharteGraphique.getPolice(17, false));

		// Création du panel pour les boutons
		JPanel panelBoutons = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(10);
		panelBoutons.setLayout(layout);
		panelBoutons.setBackground(CharteGraphique.FOND);

		// Ajout des boutons au panel
		panelBoutons.add(btnFermer);

		// Ajout du panel à contentPane
		contentPane.add(panelBoutons, BorderLayout.SOUTH);
	}

}