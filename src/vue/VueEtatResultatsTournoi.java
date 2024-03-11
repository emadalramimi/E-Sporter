package vue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controleur.ControleurEtatResultatsTournoi;
import modele.metier.StatistiquesEquipe;
import modele.metier.Tournoi;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JScrollPaneTheme;
import vue.theme.JTableTheme;
import vue.theme.JTableThemeImpression;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.Insets;
import java.util.Vector;

/**
 * Vue de l'état des résultats d'un tournoi
 */
public class VueEtatResultatsTournoi extends JFrameTheme {

	private JTableTheme table;

	/**
     * Constructeur de l'IHM pour l'état des résultats d'un tournoi
	 * @param tournoi : le tournoi dont on veut afficher les résultats
     */
	public VueEtatResultatsTournoi(Tournoi tournoi) {
        ControleurEtatResultatsTournoi controleur = new ControleurEtatResultatsTournoi(this, tournoi);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 750, 450);

		JPanel contentPane = super.getContentPane();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		contentPane.setLayout(new BorderLayout(0, 20));
		
		// Panel header
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(CharteGraphique.FOND);
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		// Panel titre
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		panelHeader.add(panelTitre, BorderLayout.WEST);
		GridBagLayout gbl_panelTitre = new GridBagLayout();
		gbl_panelTitre.columnWeights = new double[] { 0.0 };
		gbl_panelTitre.rowWeights = new double[] { 0.0, 0.0 };
		panelTitre.setLayout(gbl_panelTitre);

		// Label titre
		JLabelTheme lblTitre = new JLabelTheme("État des résultats du tournoi", 19, true);
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panelTitre.add(lblTitre, gbc_lblTitre);

		// Label tournoi
		JLabelTheme lblTournoi = new JLabelTheme(tournoi.getNomTournoi(), 16, false);
		GridBagConstraints gbc_lblTournoi = new GridBagConstraints();
		gbc_lblTournoi.insets = new Insets(0, 0, 0, 5);
		gbc_lblTournoi.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTournoi.gridx = 0;
		gbc_lblTournoi.gridy = 1;
		panelTitre.add(lblTournoi, gbc_lblTournoi);

		// Panel tableau
		JPanel panelTableau = new JPanel();
		panelTableau.setBackground(CharteGraphique.FOND);
		contentPane.add(panelTableau, BorderLayout.CENTER);
		panelTableau.setLayout(new BorderLayout(0, 0));

		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panelTableau.add(scrollPane, BorderLayout.CENTER);

		// Création de la table
		this.table = new JTableTheme(
			new String[] { "Équipe", "Matchs joués", "Matchs gagnés", "Ratio", "Points" },
			null
		);
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();

		// Ajout des lignes
		for (StatistiquesEquipe statistiquesEquipe : controleur.getResultatsTournoi(tournoi)) {
			Vector<Object> row = new Vector<>();
			row.add(statistiquesEquipe.getEquipe().getNom());
			row.add(statistiquesEquipe.getNbMatchsJoues());
            row.add(statistiquesEquipe.getNbMatchsGagnes());
            row.add(statistiquesEquipe.getRatioPourcentage());
			// Les points à ce stade étant toujours des entiers
			row.add((int) statistiquesEquipe.getPoints());
			model.addRow(row);
		}
		this.table.setModel(model);

		// Ajout de la table à scrollPane
		scrollPane.setViewportView(this.table);

		// Création des boutons
		JButtonTheme btnFermer = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Fermer");
		btnFermer.setActionCommand("FERMER");
		btnFermer.addActionListener(controleur);
		btnFermer.setFont(CharteGraphique.getPolice(17, false));

		JButtonTheme btnImprimer = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Imprimer");
		btnImprimer.setActionCommand("IMPRIMER");
		btnImprimer.addActionListener(controleur);
		btnImprimer.setFont(CharteGraphique.getPolice(17, false));

		// Création du panel pour les boutons
		JPanel panelBoutons = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(10);
		panelBoutons.setLayout(layout);
		panelBoutons.setBackground(CharteGraphique.FOND);

		// Ajout des boutons au panel
		panelBoutons.add(btnFermer);
		panelBoutons.add(btnImprimer);

		// Ajout du panel à contentPane
		contentPane.add(panelBoutons, BorderLayout.SOUTH);
	}

	/**
	 * Retourne la table des résultats pour l'impression
	 * @return la table des résultats pour l'impression
	 */
	public JTableThemeImpression getTableImpression() {
		JTableThemeImpression table = new JTableThemeImpression(this.table.getModel());

		// Création d'un JFrame et pack pour rendre le tableau
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(table));
		frame.pack();

		return table;
	}

}