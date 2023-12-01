package vue.theme;

import javax.swing.JFrame;
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

public class JFrameTheme extends JFrame {

    private JPanel contentPane;
    private JPanel wrapperPane;
    private Point initialClick;
    private boolean wasMaximized = false;
    private Rectangle previousBounds;
    private JPanel panel;
    private JButtonTitlebar btnReduire;
    private JButtonTitlebar btnToggleAgrandir;
    private JButtonTitlebar btnFermer;
    
    private List<JFrame> fenetresEnfant;

    public JFrameTheme() {
    	this.fenetresEnfant = new LinkedList<>();
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1009, 607);
        setUndecorated(true);
        contentPane = new JPanel();
        contentPane.setBorder(new LineBorder(CharteGraphique.BORDURE_FENETRE, 1));
        contentPane.setBackground(CharteGraphique.FOND);

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        panel = new JPanel();
        FlowLayout fl_panel = (FlowLayout) panel.getLayout();
        fl_panel.setVgap(0);
        fl_panel.setHgap(0);
        fl_panel.setAlignment(FlowLayout.RIGHT);
        panel.setBackground(CharteGraphique.FOND);
        contentPane.add(panel, BorderLayout.NORTH);

        btnReduire = new JButtonTitlebar();
        setButtonProperties(btnReduire);
        btnReduire.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/reduire.png")));
        btnReduire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setState(JFrame.ICONIFIED);
            }
        });
        btnReduire.addMouseListener(new LightHoverMouseAdapter());
        panel.add(btnReduire);

        btnToggleAgrandir = new JButtonTitlebar();
        setButtonProperties(btnToggleAgrandir);
        btnToggleAgrandir.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/agrandir.png")));
        btnToggleAgrandir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleMaximize();
            }
        });
        btnToggleAgrandir.addMouseListener(new LightHoverMouseAdapter());
        panel.add(btnToggleAgrandir);

        btnFermer = new JButtonTitlebar();
        setButtonProperties(btnFermer);
        btnFermer.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/fermer_gris.png")));
        btnFermer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for(JFrame fenetreEnfant : JFrameTheme.this.fenetresEnfant) {
            		fenetreEnfant.dispose();
            	}
                JFrameTheme.this.dispose();
            }
        });
        btnFermer.addMouseListener(new RedHoverMouseAdapter());
        panel.add(btnFermer);

        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = thisX + (e.getX() - initialClick.x);
                int yMoved = thisY + (e.getY() - initialClick.y);

                setLocation(xMoved, yMoved);
            }
        });
        
        wrapperPane = new JPanel();
        wrapperPane.setBackground(CharteGraphique.FOND);
        wrapperPane.setBorder(null);
        wrapperPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(wrapperPane, BorderLayout.CENTER);
    }

    public JPanel getContentPane() {
        return this.wrapperPane;
    }
    
    public void ajouterFenetreEnfant(JFrame fenetre) {
    	this.fenetresEnfant.add(fenetre);
    }
    
    public void retirerFenetreEnfant(JFrame fenetre) {
    	this.fenetresEnfant.remove(fenetre);
    }

    private void setButtonProperties(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(CharteGraphique.FOND);
        button.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void toggleMaximize() {
        if (!wasMaximized) {
            previousBounds = getBounds();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        } else {
            setBounds(previousBounds);
        }
        wasMaximized = !wasMaximized;
        adjustSizeForTaskBar();
    }

    private void adjustSizeForTaskBar() {
        int taskBarHeight = Toolkit.getDefaultToolkit().getScreenSize().height - getGraphicsConfiguration().getBounds().height;

        if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0) {
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
            Rectangle screenBounds = getGraphicsConfiguration().getBounds();

            int x = screenBounds.x + insets.left;
            int y = screenBounds.y + insets.top;
            int width = screenBounds.width - insets.left - insets.right;
            int height = screenBounds.height - insets.top - insets.bottom - taskBarHeight;

            setBounds(x, y, width, height);
        }
    }
    
    private class LightHoverMouseAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            ((JButton) e.getSource()).setBackground(CharteGraphique.FOND_BTN_TITLEBAR);
        }
        
        public void mouseExited(MouseEvent e) {
            ((JButton) e.getSource()).setBackground(CharteGraphique.FOND);
        }
    }
    
    private class RedHoverMouseAdapter extends MouseAdapter {
        public void mouseEntered(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/fermer_blanc.png")));
            bouton.setBackground(new Color(255, 0, 0));
        }

        public void mouseExited(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setIcon(new ImageIcon(VueBase.class.getResource("/images/titlebar/fermer_gris.png")));
            bouton.setBackground(CharteGraphique.FOND);
        }
    }
    
    private class JButtonTitlebar extends JButton {
        public JButtonTitlebar() {
            super.setContentAreaFilled(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        public void setContentAreaFilled(boolean b) {}
    }
}