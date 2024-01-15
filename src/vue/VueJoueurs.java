package vue;

import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modele.metier.Joueur;
import vue.theme.CharteGraphique;
import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

/**
 * IHM liste de joueurs d'une équipe
 */
public class VueJoueurs extends JFrameTheme {
	
    private JPanel contentPane;
    private VueEquipes vue;
    
    public VueJoueurs(List<Joueur> joueurs, VueEquipes vue) {
    	// Obtention de la vue pour retirer cette fenêtre de la liste des fenêtres enfant à sa fermeture
    	this.vue = vue;
    	
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 210);
        
        contentPane = super.getContentPane();
        
        // Titre
        JLabelTheme lblTitre = new JLabelTheme("Liste des joueurs", 19, true);

        // Panel de titre
        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titrePanel.add(lblTitre);
        titrePanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        contentPane.add(titrePanel, BorderLayout.NORTH);

        // ScrollPane de la liste des joueurs
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(CharteGraphique.FOND_SECONDAIRE);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(scrollPane);

        // Crée la liste des joueurs
        DefaultListModel<String> listeModel = new DefaultListModel<>();

        JList<String> liste = new JList<>(listeModel);
        liste.setCellRenderer(new TexteCentreListCellRenderer());
        liste.setBackground(CharteGraphique.FOND_SECONDAIRE);
        liste.setForeground(CharteGraphique.TEXTE);
        liste.setFont(CharteGraphique.getPolice(16, false));

        // Ajoute les joueurs à la liste
        for (Joueur joueur : joueurs) {
            listeModel.addElement(joueur.getPseudo());
        }

        scrollPane.setViewportView(liste);
    }
    
    /**
     * Retire la fenêtre de la liste des fenêtres enfant dans la fenêtre parente
     * puis ferme la fenêtre courante
     */
    @Override
    public void fermerFenetre() {
    	this.vue.retirerFenetreEnfant(this);
    	this.dispose();
    }
    
    /**
     * Centrer le pseudo des joueurs dans la liste
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