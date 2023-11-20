package vue.theme;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class JOptionPaneTheme extends JOptionPane {
    
    public JOptionPaneTheme() {
        super();
        this.setBackground(CharteGraphique.FOND_SECONDAIRE);
        UIManager.put("Panel.background", CharteGraphique.FOND_SECONDAIRE);
        UIManager.put("OptionPane.messageForeground", CharteGraphique.TEXTE);
    }

    public static void showMessageDialog(java.awt.Component parentComponent, Object message, String title, int messageType) {
        JOptionPaneTheme optionPane = new JOptionPaneTheme();
        optionPane.setMessage(message);
        optionPane.setMessageType(messageType);
        optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);

        javax.swing.JDialog dialog = optionPane.createDialog(parentComponent, title);
        dialog.setVisible(true);
    }

}
