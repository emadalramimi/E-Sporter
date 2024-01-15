package vue.theme;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import vue.VueBase;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Extension de JFrame de Swing avec ajout d'une titlebar personnalisée
 */
public class JFrameTheme extends JFrame {

    private JPanel contentPane;
    private JPanel wrapperPane;
    private JPanel panelTitleBar;
    private Point clicInitial;
    private boolean estAgrandie = false;
    private Rectangle bornesPrecedentes;
    private JButtonTitlebar btnReduire;
    private JButtonTitlebar btnToggleAgrandir;
    private JButtonTitlebar btnFermer;
    private boolean agrandissable;
    
    private List<JFrameTheme> fenetresEnfant;

    /**
     * Crée une JFrameTheme avec titlebar personnalisée
     */
    public JFrameTheme() {
    	// Liste des fenêtres enfant pour gérer leur fermeture à la fermeture de la fenêtre parente
    	this.fenetresEnfant = new LinkedList<>();
        this.agrandissable = true;
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        
        // Retirer la barre Windows
        setUndecorated(true);
        
        // ContentPane avec la titlebar au nord et le contenu au center
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(CharteGraphique.BORDURE_FENETRE, 1));
        contentPane.setBackground(CharteGraphique.FOND);

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Panel de la titlebar
        panelTitleBar = new JPanel();
        FlowLayout fl_panelTitleBar = (FlowLayout) panelTitleBar.getLayout();
        fl_panelTitleBar.setVgap(0);
        fl_panelTitleBar.setHgap(0);
        fl_panelTitleBar.setAlignment(FlowLayout.RIGHT);
        panelTitleBar.setBackground(CharteGraphique.FOND);
        contentPane.add(panelTitleBar, BorderLayout.NORTH);

        // Bouton réduire de la titlebar
        btnReduire = new JButtonTitlebar();
        setButtonProperties(btnReduire);
        btnReduire.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/reduire.png")));
        btnReduire.addActionListener(new ActionListener() {
        	/**
        	 * Réduction de la fenêtre dans la barre des tâches
        	 * @param e
        	 */
            public void actionPerformed(ActionEvent e) {
                setState(JFrame.ICONIFIED);
            }
        });
        btnReduire.addMouseListener(new LightHoverMouseAdapter());
        panelTitleBar.add(btnReduire);

        // Bouton agrandir de la titlebar
        btnToggleAgrandir = new JButtonTitlebar();
        setButtonProperties(btnToggleAgrandir);
        btnToggleAgrandir.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/agrandir.png")));
        btnToggleAgrandir.addActionListener(new ActionListener() {
        	/**
        	 * Agrandit la fenêtre ou la remet à son ancienne position
        	 * @param e
        	 */
            public void actionPerformed(ActionEvent e) {
                if(JFrameTheme.this.agrandissable) {
                    toggleAgrandir();
                }
            }
        });
        btnToggleAgrandir.addMouseListener(new LightHoverMouseAdapter());
        panelTitleBar.add(btnToggleAgrandir);

        // Bouton fermer de la titlebar
        btnFermer = new JButtonTitlebar();
        setButtonProperties(btnFermer);
        btnFermer.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/fermer_gris.png")));
        btnFermer.addActionListener(new ActionListener() {
        	/**
        	 * Applique le code de fermeture spécifique de la fenêtre
        	 * @param e
        	 */
            public void actionPerformed(ActionEvent e) {
                JFrameTheme.this.fermerFenetre();
            }
        });
        btnFermer.addMouseListener(new RedHoverMouseAdapter());
        panelTitleBar.add(btnFermer);

        /**
         * Au clic de la titlebar (pour déplacer)
         */
        panelTitleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                clicInitial = e.getPoint();
                getComponentAt(clicInitial);
            }
        });

        /**
         * Au déplacement de la fenêtre
         */
        panelTitleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int posX = getLocation().x;
                int posY = getLocation().y;

                int xDeplacement = posX + (e.getX() - clicInitial.x);
                int yDeplacement = posY + (e.getY() - clicInitial.y);

                setLocation(xDeplacement, yDeplacement);
            }
        });
        
        // Nouveau contentPane pour le contenu de la page
        wrapperPane = new JPanel();
        wrapperPane.setBackground(CharteGraphique.FOND);
        wrapperPane.setBorder(null);
        wrapperPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(wrapperPane, BorderLayout.CENTER);
    }

    /**
     * Retourne le contentPane pour le contenu de la fenêtre    
     * @return retourne le contentPane pour le contenu de la fenêtre
     */
    public JPanel getContentPane() {
        return this.wrapperPane;
    }
    
    /**
     * Ajoute la fenêtre enfant à la liste des fenêtres enfant
     * @param fenetre Fenêtre enfant à ajouter
     */
    public void ajouterFenetreEnfant(JFrameTheme fenetre) {
    	this.fenetresEnfant.add(fenetre);
    }
    
    /**
     * Retire la fenêtre enfant à la liste des fenêtres enfant
     * @param fenetre Fenêtre enfant à retirer
     */
    public void retirerFenetreEnfant(JFrameTheme fenetre) {
    	this.fenetresEnfant.remove(fenetre);
    }
    
    /**
     * Ferme les fenêtres enfant puis la fenêtre parent
     */
    public void fermerFenetre() {
    	this.fermerFenetresEnfant();
    	this.dispose();
    }
    
    /**
     * Ferme toutes les fenêtres enfant
     */
    public void fermerFenetresEnfant() {
    	for(JFrameTheme fenetreEnfant : this.fenetresEnfant) {
    		fenetreEnfant.fermerFenetre();
    	}
    }
    
    /**
	 * Affiche un popup d'erreur
	 * @param message Message du popup
	 */
	public void afficherPopupErreur(String message) {
		JOptionPaneTheme.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Affiche un popup de message
	 * @param message Message du popup
	 */
	public void afficherPopupMessage(String message) {
		JOptionPaneTheme.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
	}

    /**
     * Définit si la fenêtre est agrandissable ou non
     * @param agrandissable true si la fenêtre est agrandissable, false sinon
     */
    public void setAgrandissable(boolean agrandissable) {
        this.agrandissable = agrandissable;
        btnToggleAgrandir.setVisible(agrandissable);
    }

    /**
     * Pour enlever la couleur de fond des boutons de la titlebar au clic
     * @param button
     */
    private void setButtonProperties(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(CharteGraphique.FOND);
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    /**
     * Agrandit la fenêtre ou la remet à sa position initiale si déjà agrandie
     */
    private void toggleAgrandir() {
        if (!estAgrandie) {
            bornesPrecedentes = getBounds();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setBounds(bornesPrecedentes);
        }
        estAgrandie = !estAgrandie;
        ajusteTailleTaskbar();
    }

    /**
     * Agrandit la fenêtre sans passer par dessus la barre des tâches Windows
     */
    private void ajusteTailleTaskbar() {
        int hauteurTaskbar = Toolkit.getDefaultToolkit().getScreenSize().height - getGraphicsConfiguration().getBounds().height;

        if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0) {
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
            Rectangle screenBounds = getGraphicsConfiguration().getBounds();

            int x = screenBounds.x + insets.left;
            int y = screenBounds.y + insets.top;
            int largeur = screenBounds.width - insets.left - insets.right;
            int hauteur = screenBounds.height - insets.top - insets.bottom - hauteurTaskbar;

            setBounds(x, y, largeur, hauteur);
        }
    }
    
    /**
     * Classe interne de survol du bouton réduire et agrandir de la titlebar
     */
    private class LightHoverMouseAdapter extends MouseAdapter {
    	/**
    	 * Change la couleur de fond du bouton au survol
    	 */
        public void mouseEntered(MouseEvent e) {
            ((JButton) e.getSource()).setBackground(CharteGraphique.FOND_BTN_TITLEBAR);
        }
        
        /**
         * Remet la couleur de base en sortie de survol
         */
        public void mouseExited(MouseEvent e) {
            ((JButton) e.getSource()).setBackground(CharteGraphique.FOND);
        }
    }
    
    /**
     * Classe interne de survol du bouton fermer de la titlebar
     */
    private class RedHoverMouseAdapter extends MouseAdapter {
    	/**
    	 * Change la couleur de fond en rouge et met l'icone en blanc au survol
    	 */
        public void mouseEntered(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/fermer_blanc.png")));
            bouton.setBackground(new Color(255, 0, 0));
        }

        /**
         * Remet à l'état initial le bouton en sortie de survol
         */
        public void mouseExited(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/fermer_gris.png")));
            bouton.setBackground(CharteGraphique.FOND);
        }
    }
    
    /**
     * Classe interne de bouton de la titlebar
     */
    private class JButtonTitlebar extends JButton {
        public JButtonTitlebar() {
        	// Désactive la couleur de fond de base au clic
            super.setContentAreaFilled(false);
        }
        
        /**
         * Désactive la couleur de fond de base au clic
         */
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        /**
         * Désactive la couleur de fond de base au clic
         */
        @Override
        public void setContentAreaFilled(boolean b) {}
    }
}