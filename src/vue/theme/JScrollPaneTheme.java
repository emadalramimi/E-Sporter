package vue.theme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Extension de JScrollPane de Swing
 */
public class JScrollPaneTheme extends JScrollPane {

	/**
	 * JScrollPane personnalisé au thème E-sporter
	 * Fait pour accueillir un JTableTheme
	 */
	public JScrollPaneTheme() {
		// Définition de la bordure, de la couleur de fond
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, CharteGraphique.BORDURE));
		this.getViewport().setBackground(CharteGraphique.FOND);
		
		// Modification de la barre de scroll (fond, ascenseur et couleur du coin)
		JScrollBar verticalScrollBar = this.getVerticalScrollBar();
		verticalScrollBar.setBackground(CharteGraphique.FOND);
		verticalScrollBar.setUI(new ThemeScrollBarUI());
		JPanel panel_corner = new JPanel();
		panel_corner.setBackground(CharteGraphique.FOND);
		this.setCorner(JScrollPane.UPPER_RIGHT_CORNER, panel_corner);
	}

	/**
	 * Rendu personnalisé de la barre de scroll
	 */
	public class ThemeScrollBarUI extends BasicScrollBarUI {
		/**
		 * Personnalisation du fond de la scrollbar
		 */
		@Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
	        g.setColor(CharteGraphique.FOND); // couleur du fond
	        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	    }

		/**
		 * Personnalisation de l'ascenseur de la scrollbar
		 */
	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
	            return;
	        }
	        g.setColor(CharteGraphique.BORDURE); // couleur de l'ascenseur
	        g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
	    }

	    /**
	     * Suppression du bouton descendre
	     */
	    @Override
	    protected JButton createDecreaseButton(int orientation) {
	        return supprimerBouton();
	    }

	    /**
	     * Suppression du bouton remonter
	     */
	    @Override
	    protected JButton createIncreaseButton(int orientation) {
	        return supprimerBouton();
	    }

	    /**
	     * Pour supprimer les boutons haut et bas de la scrollbar
	     * @return un bouton invisible
	     */
	    private JButton supprimerBouton() {
	        JButton button = new JButton();
	        button.setPreferredSize(new Dimension(0, 0));
	        return button;
	    }
	}
	
}
