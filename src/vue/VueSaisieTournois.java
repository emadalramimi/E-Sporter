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

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

/**
 * IHM saisie un tournoi
 */
public class VueSaisieTournois extends JFrameTheme {
    
    private JPanel contentPane;
    private VueTournois vue;
    private DefaultListModel<String> listeModel;
    
    
    public VueSaisieTournois(VueTournois vue) {
        this.vue = vue;
        this.listeModel = new DefaultListModel<>();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);

        contentPane = super.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(CharteGraphique.FOND_SECONDAIRE);

        // Titre
        JLabel lblTitre = new JLabel("Liste des Equipes");
        lblTitre.setForeground(CharteGraphique.TEXTE);
        lblTitre.setFont(CharteGraphique.getPolice(19, true));
        topPanel.add(lblTitre, BorderLayout.CENTER);
        
        
        // Panel pour le bouton
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(CharteGraphique.FOND_SECONDAIRE);

        // Ajouter du bouton
        JButtonTheme monBouton = new JButtonTheme(JButtonTheme.Types.PRIMAIRE, "Ajouter");
        monBouton.addActionListener(e -> {
			try {
				ouvrirVueAjouterEquipes();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
        buttonPanel.add(monBouton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        contentPane.add(topPanel, BorderLayout.NORTH);
        
        // ScrollPane de la liste des equipes
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(CharteGraphique.FOND_SECONDAIRE);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Crée la liste des equipes
        DefaultListModel<String> listeModel = new DefaultListModel<>();
        listeModel.addElement("Equipe Alpha");
        listeModel.addElement("Equipe Beta");

        JList<String> liste = new JList<>(listeModel);
        liste.setCellRenderer(new TexteCentreListCellRenderer());
        liste.setBackground(CharteGraphique.FOND_SECONDAIRE);
        liste.setForeground(CharteGraphique.TEXTE);
        liste.setFont(CharteGraphique.getPolice(16, false));

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
    
    private void ouvrirVueAjouterEquipes() throws Exception {
        VueAjouterEquipes vueAjouterEquipes = new VueAjouterEquipes(vue, listeModel);
        vueAjouterEquipes.setVisible(true);
    }
    
    /**
     * Centrer le pseudo des equipes dans la liste
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