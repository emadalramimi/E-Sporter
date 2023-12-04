package vue.theme;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * Extension de JComboBox de Swing
 */
public class JComboBoxTheme<T> extends JComboBox<T> {

    public JComboBoxTheme(T[] items) {
        super(items);

        // Personnalisation
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        this.setForeground(CharteGraphique.TEXTE);
        this.setFont(CharteGraphique.getPolice(16, false));
        
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();
                button.setBackground(CharteGraphique.FOND);
                button.setBorder(BorderFactory.createLineBorder(CharteGraphique.BORDURE));
                return button;
            }

            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                popup.getList().setBackground(CharteGraphique.FOND);
                popup.getList().setForeground(CharteGraphique.TEXTE);
                return popup;
            }
        });

        UIManager.put("ComboBox.selectionBackground", CharteGraphique.PRIMAIRE);
        UIManager.put("ComboBox.selectionForeground", CharteGraphique.TEXTE);
        UIManager.put("ComboBox.border", CharteGraphique.BORDURE);
    }
}