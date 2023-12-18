package vue.theme;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import vue.VueTournois;

/**
 * Extension de JDatePickerImpl pour changer le style du composant.
 */
public class JDatePickerImplTheme extends JDatePickerImpl {
    /**
     * Créer un nouveau composant JDatePickerImpl avec un style personnalisé.
     * @param datePanel Le panel de sélection de la date.
     * @param formatter Le formatteur de la date.
     */
    public JDatePickerImplTheme(JDatePanelImpl datePanel, JFormattedTextField.AbstractFormatter formatter) {
        super(datePanel, formatter);
        
        // Changement le style du panel
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, CharteGraphique.FOND_SECONDAIRE));
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        
        // Changement le style du champ de texte
        JFormattedTextField champ = super.getJFormattedTextField();
        champ.setForeground(CharteGraphique.TEXTE);
        champ.setFont(CharteGraphique.getPolice(19, false));
        champ.setBackground(CharteGraphique.FOND_SECONDAIRE);
        champ.setBorder(null);
        
        // Changement le style du bouton
        JButton bouton = super.getButton();
        bouton.setBackground(null);
        bouton.setForeground(CharteGraphique.TEXTE);
        bouton.setFont(CharteGraphique.getPolice(17, false));
        bouton.setBorder(new EmptyBorder(3, 3, 3, 3));
        bouton.setPreferredSize(null);
        bouton.setText(null);
        bouton.setIcon(new ImageIcon(VueTournois.class.getResource("/images/calendrier/calendrier.png")));
        bouton.setFocusPainted(false);
        bouton.setContentAreaFilled(false);
    }
}