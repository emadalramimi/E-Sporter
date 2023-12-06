package vue.theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableCellRenderer;

import vue.VueTournois;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Extension de JPanel de Swing pour afficher les boutons d'actions des tableaux
 */
public class TableButtonsPanel extends JPanel implements TableCellRenderer {

    private JButtonTable boutonVoir;
    private JButtonTable boutonModifier;
    private JButtonTable boutonSupprimer;

    public TableButtonsPanel(JTable table, ActionListener controleur, int idElement) {
    	setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        
    	// Création des boutons avec leur action
        boutonVoir = new JButtonTable(JButtonTable.Type.VOIR, idElement);
        boutonModifier = new JButtonTable(JButtonTable.Type.MODIFIER, idElement);
        boutonSupprimer = new JButtonTable(JButtonTable.Type.SUPPRIMER, idElement);

        // Fond du panel
        setBackground(CharteGraphique.FOND);
        boutonVoir.setBackground(CharteGraphique.FOND);
        boutonModifier.setBackground(CharteGraphique.FOND);
        boutonSupprimer.setBackground(CharteGraphique.FOND);

        // Espacement des boutons
        boutonVoir.setBorder(new EmptyBorder(0, 5, 0, 5));
        boutonModifier.setBorder(new EmptyBorder(0, 5, 0, 5));
        boutonSupprimer.setBorder(new EmptyBorder(0, 5, 0, 5));

        // Taille des boutons
        Dimension tailleBoutons = new Dimension(40, 40);
        boutonVoir.setPreferredSize(tailleBoutons);
        boutonModifier.setPreferredSize(tailleBoutons);
        boutonSupprimer.setPreferredSize(tailleBoutons);

        // Bordure de la cellule du tableau
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CharteGraphique.BORDURE));

        // Icones des boutons
        boutonVoir.setIcon(new ImageIcon(VueTournois.class.getResource("/images/actions/vue.png")));
        boutonModifier.setIcon(new ImageIcon(VueTournois.class.getResource("/images/actions/modifier.png")));
        boutonSupprimer.setIcon(new ImageIcon(VueTournois.class.getResource("/images/actions/supprimer.png")));
        
        // Mise en place d'un MouseListener aux boutons (passage en bleu au clic)
        boutonVoir.addMouseListener(new ButtonMouseAdapter(boutonVoir, "/images/actions/vue_actif.png", "/images/actions/vue.png"));
        boutonModifier.addMouseListener(new ButtonMouseAdapter(boutonModifier, "/images/actions/modifier_actif.png", "/images/actions/modifier.png"));
        boutonSupprimer.addMouseListener(new ButtonMouseAdapter(boutonSupprimer, "/images/actions/supprimer_actif.png", "/images/actions/supprimer.png"));

        // Mise en place du controleur (ActionListener) aux boutons
        boutonVoir.addActionListener(controleur);
        boutonModifier.addActionListener(controleur);
        boutonSupprimer.addActionListener(controleur);
        
        // Remplace la couleur de fond de base et de la bordure des bouton
        boutonVoir.setContentAreaFilled(false);
        boutonModifier.setContentAreaFilled(false);
        boutonSupprimer.setContentAreaFilled(false);
        boutonVoir.setFocusPainted(false);
        boutonModifier.setFocusPainted(false);
        boutonSupprimer.setFocusPainted(false);

        // Ajout des boutons au panel
        add(boutonVoir);
        add(boutonModifier);
        add(boutonSupprimer);
    }

    /**
     * Change la couleur de fond de la cellule pour s'aligner sur le tableau
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	if (row % 2 == 0) {
            setBackground(CharteGraphique.FOND_SECONDAIRE);
            boutonVoir.setBackground(CharteGraphique.FOND_SECONDAIRE);
            boutonModifier.setBackground(CharteGraphique.FOND_SECONDAIRE);
            boutonSupprimer.setBackground(CharteGraphique.FOND_SECONDAIRE);
        } else {
            setBackground(CharteGraphique.FOND);
            boutonVoir.setBackground(CharteGraphique.FOND);
            boutonModifier.setBackground(CharteGraphique.FOND);
            boutonSupprimer.setBackground(CharteGraphique.FOND);
        }
    	this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, CharteGraphique.BORDURE));

        return this;
    }
    
    /**
     * Classe interne de gestion de clic sur un bouton du tableau
     */
    private static class ButtonMouseAdapter extends MouseInputAdapter {
    	
        private final JButton bouton;
        private final String cheminIconeActive;
        private final String cheminIcone;

        /**
         * Crée un MouseAdapter pour le bouton avec le chemin de ses icones état actif et inactif
         * @param bouton
         * @param cheminIconeActive
         * @param cheminIcone
         */
        public ButtonMouseAdapter(JButton bouton, String cheminIconeActive, String cheminIcone) {
            this.bouton = bouton;
            this.cheminIconeActive = cheminIconeActive;
            this.cheminIcone = cheminIcone;
            bouton.setContentAreaFilled(false);
            bouton.setFocusPainted(false);
        }

        /**
         * Au pressage du clic de la souris, on active l'icone
         */
        @Override
        public void mousePressed(MouseEvent e) {
        	bouton.setIcon(new ImageIcon(VueTournois.class.getResource(cheminIconeActive)));
        }

        /**
         * Au relachement du clic de la souris, on désactive l'icone
         */
        @Override
        public void mouseExited(MouseEvent e) {
            bouton.setIcon(new ImageIcon(VueTournois.class.getResource(cheminIcone)));
        }
    }


}
