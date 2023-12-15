package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.ControleurInscriptionEquipesTournoi;
import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JOptionPaneTheme;
import vue.theme.JScrollPaneTheme;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

public class VueInscriptionEquipesTournoi extends JFrameTheme {

	private JPanel contentPane;
	
	private VueSaisieTournoiEquipeArbitre vueSaisieTournoiEquipe;
	private Tournoi tournoi;
	
	private DefaultListModel<Equipe> listModelEquipes;
	private JList<Equipe> listeEquipes;

	public VueInscriptionEquipesTournoi(VueTournois vueTournois, Tournoi tournoi) {
		ControleurInscriptionEquipesTournoi controleur = new ControleurInscriptionEquipesTournoi(this, vueTournois, tournoi);
		this.tournoi = tournoi;
		this.listModelEquipes = new DefaultListModel<>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 384);
		
		contentPane = super.getContentPane();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

		contentPane.setLayout(new BorderLayout(0, 20));
		
		JPanel panelHeader = new JPanel();
		panelHeader.setBackground(CharteGraphique.FOND);
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitre = new JPanel();
		panelTitre.setBackground(CharteGraphique.FOND);
		panelHeader.add(panelTitre, BorderLayout.WEST);
		GridBagLayout gbl_panelTitre = new GridBagLayout();
		gbl_panelTitre.columnWeights = new double[]{0.0};
		gbl_panelTitre.rowWeights = new double[]{0.0, 0.0};
		panelTitre.setLayout(gbl_panelTitre);
		
		JLabel lblTitre = new JLabel("Equipes inscrites");
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
		
		JButtonTheme btnInscrireEquipe = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Inscrire une équipe");
		btnInscrireEquipe.addActionListener(controleur);
		btnInscrireEquipe.setFont(CharteGraphique.getPolice(19, false));

		JPanel panelBtnInscrireEquipe = new JPanel();
		panelBtnInscrireEquipe.setBackground(CharteGraphique.FOND);
		panelBtnInscrireEquipe.setLayout(new BorderLayout());
		panelBtnInscrireEquipe.add(btnInscrireEquipe, BorderLayout.NORTH);

		panelHeader.add(panelBtnInscrireEquipe, BorderLayout.EAST);

		JPanel panelListe = new JPanel();
		panelListe.setBackground(CharteGraphique.FOND);
		contentPane.add(panelListe, BorderLayout.CENTER);
		panelListe.setLayout(new BorderLayout(0, 0));
		
		JScrollPaneTheme scrollPane = new JScrollPaneTheme();
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		listeEquipes = new JList<>();
		listeEquipes.addListSelectionListener(controleur);
		listeEquipes.setBackground(CharteGraphique.FOND);
		listeEquipes.setFont(CharteGraphique.getPolice(16, false));
		listeEquipes.setCellRenderer(new EquipeListCellRenderer());
		listeEquipes.addListSelectionListener(controleur);
		listeEquipes.setBackground(CharteGraphique.FOND_SECONDAIRE);
		listeEquipes.setForeground(CharteGraphique.TEXTE);
		listeEquipes.setFont(CharteGraphique.getPolice(16, false));
		listeEquipes.setModel(listModelEquipes);
		scrollPane.setViewportView(listeEquipes);

		for (Equipe equipe : tournoi.getEquipes()) {
			this.listModelEquipes.addElement(equipe);
		}

		// Création des boutons
		JButtonTheme btnFermer = new JButtonTheme(JButtonTheme.Types.SECONDAIRE, "Fermer");
		btnFermer.setFont(CharteGraphique.getPolice(19, false));
		btnFermer.addActionListener(controleur);

		JButtonTheme btnOuvrirTournoi = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ouvrir le tournoi");
		btnOuvrirTournoi.setFont(CharteGraphique.getPolice(19, false));
		btnOuvrirTournoi.addActionListener(controleur);

		// Création du panel pour les boutons
		JPanel panelBoutons = new JPanel();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(10);
		panelBoutons.setLayout(layout);
		panelBoutons.setBackground(CharteGraphique.FOND);

		// Ajout des boutons au panel
		panelBoutons.add(btnFermer);
		panelBoutons.add(btnOuvrirTournoi);

		// Ajout du panel à contentPane
		contentPane.add(panelBoutons, BorderLayout.SOUTH);
	}
	
	private class EquipeListCellRenderer extends DefaultListCellRenderer {
	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

	        if(value instanceof Equipe) {
	        	this.setText(((Equipe) value).getNom());
	        }

	        return this;
	    }
	}
	
	public boolean estListeEquipes(JList<?> liste) {
		return liste.equals(this.listeEquipes);
	}
	
	public void ajouterEquipe(Equipe equipe) {
		if(!this.listModelEquipes.contains(equipe)) {
			this.listModelEquipes.addElement(equipe);
		}
	}
	
	public void supprimerEquipe(Equipe equipe) {
		this.listModelEquipes.removeElement(equipe);
	}
	
	public List<Equipe> getEquipes() {
	    List<Equipe> equipes = new ArrayList<>();
	    for (int i = 0; i < listModelEquipes.size(); i++) {
	        equipes.add(listModelEquipes.getElementAt(i));
	    }
	    return equipes;
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
	
	public void afficherVueSaisieTournoiEquipe(Equipe[] equipes) {
		// Une seule fenêtre de saisie à la fois, si déjà ouverte elle est mise au premier plan
        if (this.vueSaisieTournoiEquipe == null || !this.vueSaisieTournoiEquipe.isVisible()) {
        	this.vueSaisieTournoiEquipe = new VueSaisieTournoiEquipeArbitre(this, equipes, this.tournoi);
        	this.ajouterFenetreEnfant(this.vueSaisieTournoiEquipe);
        	this.vueSaisieTournoiEquipe.setLocationRelativeTo(this);
        	this.vueSaisieTournoiEquipe.setVisible(true);
        } else {
        	this.vueSaisieTournoiEquipe.toFront();
        }
	}

}
