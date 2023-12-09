package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import modele.ModeleEquipe;
import modele.metier.Arbitre;
import modele.metier.Equipe;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JFrameTheme;

/**
 * IHM liste des equipes
 */
public class VueSaisieTournoiArbitre extends JFrameTheme {

    private JPanel contentPane;
    private VueSaisieTournoi vue;
    private JComboBoxTheme<String> comboBoxArbitres;
    private JButtonTheme btnAjouter;
    private ModeleArbitre modeleArbitre;
    private DefaultListModel<String> listeModel;
    private DefaultListModel<Arbitre> listeArbitres;


	public VueSaisieTournoiArbitre(VueSaisieTournoi vueSaisieTournoi, DefaultListModel<String> listeModel,
			DefaultListModel<Arbitre> listeArbitres) {
        this.vue = vueSaisieTournoi;
        this.listeModel = listeModel;
        this.listeArbitres = listeArbitres;
        this.ModeleArbitre = new ModeleArbitre();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 100); 

        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        JLabel lblTitre = new JLabel("Choisir un arbitre");
        lblTitre.setForeground(CharteGraphique.TEXTE);
        lblTitre.setFont(CharteGraphique.getPolice(19, true));
        titlePanel.add(lblTitre);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        btnAjouter = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
        buttonPanel.add(btnAjouter);

        JPanel combinedPanel = new JPanel(new BorderLayout());
        combinedPanel.add(titlePanel, BorderLayout.WEST);
        combinedPanel.add(buttonPanel, BorderLayout.EAST);
        combinedPanel.setBackground(CharteGraphique.FOND_SECONDAIRE);

        contentPane.add(combinedPanel, BorderLayout.NORTH);

        // Combo Box arbitres
        String[] NomsArbitres = getArbitresNoms();
        comboBoxArbitres = new JComboBoxTheme<>(NomsArbitres);
        contentPane.add(comboBoxArbitres, BorderLayout.CENTER);

        btnAjouter.addActionListener(e -> {
			try {
				ajouterArbitreAction();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

	private String[] getArbitresNoms() throws Exception {
        List<Arbitre> arbitres = modeleArbitre.getTout();
        List<String> noms = arbitres.stream().map(Arbitre::getNom).collect(Collectors.toList());
        return noms.toArray(new String[0]);
    }

    private void ajouterArbitreAction() throws Exception {
        String selectedArbitreNom = (String) comboBoxArbitres.getSelectedItem();
        List<Arbitre> arbitres = modeleArbitre.getParNom(selectedArbitreNom);

        if (!arbitres.isEmpty()) {
            Arbitre arbitreToAdd = arbitres.get(0);
            listeModel.addElement(arbitreToAdd.getNom());
            listeArbitres.addElement(arbitreToAdd);
        } 
    }

    

}
