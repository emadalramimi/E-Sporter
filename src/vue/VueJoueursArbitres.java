package vue;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modele.metier.Arbitre;
import modele.metier.Joueur;
import vue.theme.CharteGraphique;
import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

/**
 * IHM liste de joueurs d'une équipe ou liste d'arbitres d'un tournoi
 */
public class VueJoueursArbitres extends JFrameTheme {

    private VueEquipes vue;
    
    public VueJoueursArbitres(List<Joueur> joueurs, VueEquipes vue) {
    	// Obtention de la vue pour retirer cette fenêtre de la liste des fenêtres enfant à sa fermeture
    	this.vue = vue;
        this.afficherVue(joueurs.stream().map(Joueur::getPseudo).collect(Collectors.toList()), "Liste des joueurs");
    }

    public VueJoueursArbitres(List<Arbitre> arbitres) {
        this.afficherVue(arbitres.stream().map(arbitre -> arbitre.getNom() + " " + arbitre.getPrenom()).collect(Collectors.toList()), "Liste des arbitres");
    }

    private void afficherVue(List<String> elements, String titre) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 210);

        JPanel contentPane = super.getContentPane();

        // Label de titre
        JLabelTheme lblTitre = new JLabelTheme(titre, 19, true);

        // Panel de titre
        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titrePanel.add(lblTitre);
        titrePanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        contentPane.add(titrePanel, BorderLayout.NORTH);

        // ScrollPane de la liste des éléments
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(CharteGraphique.FOND_SECONDAIRE);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(scrollPane);

        // Crée la liste des éléments
        DefaultListModel<String> listeModel = new DefaultListModel<>();

        JList<String> liste = new JList<>(listeModel);
        liste.setCellRenderer(new TexteCentreListCellRenderer());
        liste.setBackground(CharteGraphique.FOND_SECONDAIRE);
        liste.setForeground(CharteGraphique.TEXTE);
        liste.setFont(CharteGraphique.getPolice(16, false));

        // Ajoute les éléments à la liste
        for (String element : elements) {
            listeModel.addElement(element);
        }

        scrollPane.setViewportView(liste);
    }
    
    /**
     * Retire la fenêtre de la liste des fenêtres enfant dans la fenêtre parente si type joueur puis ferme la fenêtre courante
     */
    @Override
    public void fermerFenetre() {
        if(this.vue != null) {
            this.vue.retirerFenetreEnfant(this);
        }
        this.dispose();
    }
    
    /**
     * Centrer le pseudo des joueurs/arbitres dans la liste
     */
    private class TexteCentreListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
}