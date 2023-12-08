package vue;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import modele.ModeleEquipe;
import modele.metier.Equipe;
import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JComboBoxTheme;
import vue.theme.JFrameTheme;

/**
 * IHM liste des equipes
 */
public class VueAjouterEquipes extends JFrameTheme {

    private JPanel contentPane;
    private VueTournois vue;
    private JComboBoxTheme<String> comboBoxEquipes;
    private JButtonTheme btnAjouter;
    private ModeleEquipe modeleEquipe;
    private DefaultListModel<String> listeModel;

    public VueAjouterEquipes(VueTournois vue, DefaultListModel<String> listeModel) throws Exception {
        this.vue = vue;
        this.listeModel = listeModel;
        this.modeleEquipe = new ModeleEquipe();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 100); 

        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        JLabel lblTitre = new JLabel("Choisir une equipe");
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

        // Combo Box Equipes
        String[] equipeNames = getEquipeNames();
        comboBoxEquipes = new JComboBoxTheme<>(equipeNames);
        contentPane.add(comboBoxEquipes, BorderLayout.CENTER);

        btnAjouter.addActionListener(e -> ajouterEquipeAction());
    }

    private String[] getEquipeNames() throws Exception {
        List<Equipe> equipes = modeleEquipe.getTout();
        List<String> names = equipes.stream().map(Equipe::getNom).collect(Collectors.toList());
        return names.toArray(new String[0]);
    }

    private void ajouterEquipeAction() {
        String selectedEquipe = (String) comboBoxEquipes.getSelectedItem();
        listeModel.addElement(selectedEquipe);
    }
    
    @Override
    public void fermerFenetre() {
        this.vue.retirerFenetreEnfant(this);
        this.dispose();
    }
}
