package vue.theme;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Extension de JOptionPane de Swing (popup de choix)
 */
public class JOptionPaneTheme extends JOptionPane {
    
    public JOptionPaneTheme() {
        super();
        this.personnaliser();
    }
    
    public JOptionPaneTheme(Object message, int messageType) {
        super(message, messageType);
        this.personnaliser();
    }

    /**
     * Ouvre un message dialog
     */
    public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
        JOptionPaneTheme optionPane = new JOptionPaneTheme();
        optionPane.setMessage(message);
        optionPane.setMessageType(messageType);
        optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);

        javax.swing.JDialog dialog = optionPane.createDialog(parentComponent, title);
        dialog.setVisible(true);
    }
    
    /**
     * Ouvre un option dialog
     */
    public static int showOptionDialog(Component parentComponent, Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
        JOptionPaneTheme optionPane = new JOptionPaneTheme(message, messageType);
        optionPane.setOptionType(optionType);
        
        optionPane.setIcon(icon);
        optionPane.setOptions(options);
        optionPane.setInitialValue(initialValue);

        javax.swing.JDialog dialog = optionPane.createDialog(parentComponent, title);
        dialog.setVisible(true);

        Object selectedValue = optionPane.getValue();
        if (selectedValue == null)
            return JOptionPane.CLOSED_OPTION;
        for (int counter = 0, maxCounter = options.length; counter < maxCounter; counter++) {
            if (options[counter].equals(selectedValue))
                return counter;
        }
        return JOptionPane.CLOSED_OPTION;
    }
    
    /**
     * Personnalise la popup
     */
    private void personnaliser() {
    	this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        UIManager.put("Panel.background", CharteGraphique.FOND_SECONDAIRE);
        UIManager.put("OptionPane.background", CharteGraphique.FOND_SECONDAIRE);
        UIManager.put("OptionPane.messageForeground", CharteGraphique.TEXTE);
    }
}
