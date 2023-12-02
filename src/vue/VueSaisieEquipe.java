package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.ControleurEquipes;
import controleur.ControleurSaisieEquipe;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JFrameTheme;
import vue.theme.JTextFieldTheme;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;

/**
 * IHM de saisie d'équipe
 */
public class VueSaisieEquipe extends JFrameTheme {

	private JPanel contentPane;
	private JPanel panelEquipe;
	private JPanel panelJoueurs;
	private JLabel lblNom;
	private JTextField txtNom;
	private JLabel lblPays;
	private JComboBoxTheme<String> cboxPays;
	private JLabel lblWorldRanking;
	private JTextFieldTheme txtWorldRanking;
	private JLabel lblJoueurs;
	private JTextField txtJoueur1;
	private JTextField txtJoueur2;
	private JTextField txtJoueur3;
	private JTextField txtJoueur4;
	private JTextField txtJoueur5;
	private JPanel panelAnnuler;
	private JButton btnAnnuler;
	private JPanel panelValider;
	private JButton btnValider;
	
	private VueEquipes vueEquipes;
	
	public VueSaisieEquipe(VueEquipes vueEquipes, ControleurEquipes controleurEquipes, Optional<Equipe> equipeOptionnel) {
		ControleurSaisieEquipe controleur = new ControleurSaisieEquipe(this, vueEquipes, controleurEquipes, equipeOptionnel);
		this.vueEquipes = vueEquipes;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		
		contentPane = super.getContentPane();
		
		// Panel centre
		JPanel panelCentre = new JPanel();
		panelCentre.setBackground(CharteGraphique.FOND);
		contentPane.add(panelCentre, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentre = new GridBagLayout();
		gbl_panelCentre.columnWidths = new int[]{0, 0, 0};
		gbl_panelCentre.rowHeights = new int[]{0, 0, 0};
		gbl_panelCentre.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelCentre.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panelCentre.setLayout(gbl_panelCentre);
		
		// Panel équipe
		panelEquipe = new JPanel();
		panelEquipe.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelEquipe = new GridBagConstraints();
		gbc_panelEquipe.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelEquipe.insets = new Insets(0, 25, 25, 15);
		gbc_panelEquipe.gridx = 0;
		gbc_panelEquipe.gridy = 0;
		panelCentre.add(panelEquipe, gbc_panelEquipe);
		GridBagLayout gbl_panelEquipe = new GridBagLayout();
		gbl_panelEquipe.columnWidths = new int[] {0, 0};
		gbl_panelEquipe.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelEquipe.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelEquipe.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panelEquipe.setLayout(gbl_panelEquipe);
		
		// Label nom de l'équipe
		lblNom = new JLabel("Nom de l'équipe");
		lblNom.setForeground(Color.WHITE);
		lblNom.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblNom = new GridBagConstraints();
		gbc_lblNom.insets = new Insets(0, 0, 5, 0);
		gbc_lblNom.gridx = 0;
		gbc_lblNom.gridy = 1;
		panelEquipe.add(lblNom, gbc_lblNom);
		
		// Champ nom de l'équipe
		txtNom = new JTextFieldTheme(20);
		GridBagConstraints gbc_textNom = new GridBagConstraints();
		gbc_textNom.insets = new Insets(0, 0, 20, 0);
		gbc_textNom.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNom.gridx = 0;
		gbc_textNom.gridy = 2;
		panelEquipe.add(txtNom, gbc_textNom);
		txtNom.setColumns(10);
		
		// Label pays de l'équipe
		lblPays = new JLabel("Pays de l'équipe");
		lblPays.setForeground(Color.WHITE);
		lblPays.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblPays = new GridBagConstraints();
		gbc_lblPays.insets = new Insets(0, 0, 5, 0);
		gbc_lblPays.gridx = 0;
		gbc_lblPays.gridy = 3;
		panelEquipe.add(lblPays, gbc_lblPays);
		
		// Liste déroulante des pays
		cboxPays = new JComboBoxTheme<String>(Pays.getTout());
		GridBagConstraints gbc_txtEquipe = new GridBagConstraints();
		gbc_txtEquipe.insets = new Insets(0, 0, 20, 0);
		gbc_txtEquipe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEquipe.gridx = 0;
		gbc_txtEquipe.gridy = 4;
		panelEquipe.add(cboxPays, gbc_txtEquipe);
		
		// Label World Ranking
		lblWorldRanking = new JLabel("World Ranking (optionnel)");
		lblWorldRanking.setForeground(Color.WHITE);
		lblWorldRanking.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblWorldRancking = new GridBagConstraints();
		gbc_lblWorldRancking.insets = new Insets(0, 0, 5, 0);
		gbc_lblWorldRancking.gridx = 0;
		gbc_lblWorldRancking.gridy = 5;
		panelEquipe.add(lblWorldRanking, gbc_lblWorldRancking);
		
		// Champ World Ranking
		txtWorldRanking = new JTextFieldTheme(10);
		GridBagConstraints gbc_textWorldRanking = new GridBagConstraints();
		gbc_textWorldRanking.insets = new Insets(0, 0, 5, 0);
		gbc_textWorldRanking.fill = GridBagConstraints.HORIZONTAL;
		gbc_textWorldRanking.gridx = 0;
		gbc_textWorldRanking.gridy = 6;
		panelEquipe.add(txtWorldRanking, gbc_textWorldRanking);
		txtWorldRanking.setColumns(10);
		
		// Panel joueurs
		panelJoueurs = new JPanel();
		panelJoueurs.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelJoueurs = new GridBagConstraints();
		gbc_panelJoueurs.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelJoueurs.insets = new Insets(0, 25, 25, 15);
		gbc_panelJoueurs.gridx = 1;
		gbc_panelJoueurs.gridy = 0;
		panelCentre.add(panelJoueurs, gbc_panelJoueurs);
		GridBagLayout gbl_panelJoueurs = new GridBagLayout();
		gbl_panelJoueurs.columnWidths = new int[] {0, 0};
		gbl_panelJoueurs.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
		gbl_panelJoueurs.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelJoueurs.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelJoueurs.setLayout(gbl_panelJoueurs);
		
		// Label titre des joueurs
		lblJoueurs = new JLabel("Pseudo des joueurs");
		lblJoueurs.setForeground(Color.WHITE);
		lblJoueurs.setFont(CharteGraphique.getPolice(19, true));
		GridBagConstraints gbc_lblJoueurs = new GridBagConstraints();
		gbc_lblJoueurs.insets = new Insets(0, 0, 5, 0);
		gbc_lblJoueurs.gridx = 0;
		gbc_lblJoueurs.gridy = 0;
		panelJoueurs.add(lblJoueurs, gbc_lblJoueurs);
		
		// Champ pseudo joueur 1
		txtJoueur1 = new JTextFieldTheme(20);
		GridBagConstraints gbc_txtJoueur1 = new GridBagConstraints();
		gbc_txtJoueur1.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur1.gridx = 0;
		gbc_txtJoueur1.gridy = 1;
		panelJoueurs.add(txtJoueur1, gbc_txtJoueur1);
		txtJoueur1.setColumns(10);
		
		// Champ pseudo joueur 2
		txtJoueur2 = new JTextFieldTheme(20);
		GridBagConstraints gbc_txtJoueur2 = new GridBagConstraints();
		gbc_txtJoueur2.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur2.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur2.gridx = 0;
		gbc_txtJoueur2.gridy = 2;
		panelJoueurs.add(txtJoueur2, gbc_txtJoueur2);
		txtJoueur2.setColumns(10);
		
		// Champ pseudo joueur 3
		txtJoueur3 = new JTextFieldTheme(20);
		GridBagConstraints gbc_txtJoueur3 = new GridBagConstraints();
		gbc_txtJoueur3.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur3.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur3.gridx = 0;
		gbc_txtJoueur3.gridy = 3;
		panelJoueurs.add(txtJoueur3, gbc_txtJoueur3);
		txtJoueur3.setColumns(10);
		
		// Champ pseudo joueur 4
		txtJoueur4 = new JTextFieldTheme(20);
		GridBagConstraints gbc_txtJoueur4 = new GridBagConstraints();
		gbc_txtJoueur4.insets = new Insets(0, 0, 5, 0);
		gbc_txtJoueur4.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur4.gridx = 0;
		gbc_txtJoueur4.gridy = 4;
		panelJoueurs.add(txtJoueur4, gbc_txtJoueur4);
		txtJoueur4.setColumns(10);
		
		// Champ pseudo joueur 5
		txtJoueur5 = new JTextFieldTheme(20);
		GridBagConstraints gbc_txtJoueur5 = new GridBagConstraints();
		gbc_txtJoueur5.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtJoueur5.gridx = 0;
		gbc_txtJoueur5.gridy = 5;
		panelJoueurs.add(txtJoueur5, gbc_txtJoueur5);
		txtJoueur5.setColumns(10);
		
		// Panel du bouton annuler
		panelAnnuler = new JPanel();
		panelAnnuler.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAnnuler = new GridBagConstraints();
		gbc_panelAnnuler.anchor = GridBagConstraints.NORTHEAST;
		gbc_panelAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_panelAnnuler.gridx = 0;
		gbc_panelAnnuler.gridy = 1;
		panelCentre.add(panelAnnuler, gbc_panelAnnuler);
		
		// Bouton annuler
		btnAnnuler = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Annuler");
		btnAnnuler.addActionListener(controleur);
		panelAnnuler.add(btnAnnuler);
		
		// Panel du bouton valider
		panelValider = new JPanel();
		panelValider.setBackground(CharteGraphique.FOND);
		GridBagConstraints gbc_panelAjouter = new GridBagConstraints();
		gbc_panelAjouter.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelAjouter.gridx = 1;
		gbc_panelAjouter.gridy = 1;
		panelCentre.add(panelValider, gbc_panelAjouter);

		// Récupère l'équipe fournie, sinon null
		Equipe equipe = equipeOptionnel.orElse(null);
		
		// Si aucune équipe est renseignée afficher le bouton valider, sinon afficher le bouton modifier
		if(equipe == null) {
			btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Valider");
		} else {
			btnValider = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Modifier");
		}
		btnValider.addActionListener(controleur);
		panelValider.add(btnValider);
		
		// Remplissage des champs du formulaire si une équipe est renseignée pour modification
		if(equipe != null) {
			this.txtNom.setText(equipe.getNom());
			this.cboxPays.setSelectedItem(equipe.getPays());
			this.txtWorldRanking.setText(String.valueOf(equipe.getWorldRanking()));
			
			List<Joueur> joueurs = equipe.getJoueurs();
            this.txtJoueur1.setText(joueurs.get(0).getPseudo());
            this.txtJoueur2.setText(joueurs.get(1).getPseudo());
            this.txtJoueur3.setText(joueurs.get(2).getPseudo());
            this.txtJoueur4.setText(joueurs.get(3).getPseudo());
            this.txtJoueur5.setText(joueurs.get(4).getPseudo());
		}
	}
	
	/**
     * Retire la fenêtre de la liste des fenêtres enfant dans la fenêtre parente
     * puis ferme la fenêtre courante
     */
	@Override
	public void fermerFenetre() {
		vueEquipes.retirerFenetreEnfant(VueSaisieEquipe.this);
		this.dispose();
	}
	
	/**
	 * @return le nom renseigné
	 */
	public String getNomEquipe() {
		return this.txtNom.getText().trim();
	}
	
	/**
	 * @return le pays renseigné
	 */
	public String getPaysEquipe() {
		return (String) this.cboxPays.getSelectedItem();
	}
	
	/**
	 * @return le World Ranking renseigné
	 */
	public Integer getWorldRanking() throws NumberFormatException {
		// Traduction du contenu en Integer
		String contenu = this.txtWorldRanking.getText();
		if(contenu.length() > 0) {
			return Integer.parseInt(contenu);
		}
		return null;
	}
	
	/**
	 * @return la liste des pseudos des joueurs renseignés
	 */
	public List<String> getNomJoueurs() {
	    List<String> noms = new ArrayList<>();
	    
	    String joueur1 = this.txtJoueur1.getText().trim();
	    String joueur2 = this.txtJoueur2.getText().trim();
	    String joueur3 = this.txtJoueur3.getText().trim();
	    String joueur4 = this.txtJoueur4.getText().trim();
	    String joueur5 = this.txtJoueur5.getText().trim();
	    
	    if (!joueur1.isEmpty()) {
	        noms.add(joueur1);
	    }
	    if (!joueur2.isEmpty()) {
	        noms.add(joueur2);
	    }
	    if (!joueur3.isEmpty()) {
	        noms.add(joueur3);
	    }
	    if (!joueur4.isEmpty()) {
	        noms.add(joueur4);
	    }
	    if (!joueur5.isEmpty()) {
	        noms.add(joueur5);
	    }
	    
	    return noms;
	}
}
