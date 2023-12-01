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
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

public class VueJoueurs extends JFrameTheme {
	
    private JPanel contentPane;
    private VueEquipes vue;
    
    public VueJoueurs(List<Joueur> joueurs, VueEquipes vue) {
    	this.vue = vue;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 210);
        
        contentPane = super.getContentPane();
        
        JLabel lblTitre = new JLabel("Liste des joueurs");
        lblTitre.setForeground(CharteGraphique.TEXTE);
        lblTitre.setFont(CharteGraphique.getPolice(19, true));

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titrePanel.add(lblTitre);
        titrePanel.setBackground(CharteGraphique.FOND_SECONDAIRE);
        contentPane.add(titrePanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(CharteGraphique.FOND_SECONDAIRE);
        scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(scrollPane);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        JList<String> list = new JList<>(listModel);
        list.setCellRenderer(new TexteCentreListCellRenderer());
        list.setBackground(CharteGraphique.FOND_SECONDAIRE);
        list.setForeground(CharteGraphique.TEXTE);
        list.setFont(CharteGraphique.getPolice(16, false));

        for (Joueur joueur : joueurs) {
            listModel.addElement(joueur.getPseudo());
        }

        scrollPane.setViewportView(list);
    }
    
    @Override
    public void fermerFenetre() {
    	this.vue.retirerFenetreEnfant(this);
    	this.dispose();
    }
    
    private class TexteCentreListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            return label;
        }
    }
}