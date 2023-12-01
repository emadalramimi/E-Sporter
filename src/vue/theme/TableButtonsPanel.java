package vue.theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableCellRenderer;

import vue.VueTournois;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class TableButtonsPanel extends JPanel implements TableCellRenderer {

    private JButtonTable button1;
    private JButtonTable button2;
    private JButtonTable button3;

    public TableButtonsPanel(JTable table, ActionListener controleur, int idElement) {
    	setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        
        button1 = new JButtonTable(JButtonTable.Type.VOIR, idElement);
        button2 = new JButtonTable(JButtonTable.Type.MODIFIER, idElement);
        button3 = new JButtonTable(JButtonTable.Type.SUPPRIMER, idElement);
        setBackground(CharteGraphique.FOND);
        
        

        button1.setBackground(CharteGraphique.FOND);
        button2.setBackground(CharteGraphique.FOND);
        button3.setBackground(CharteGraphique.FOND);

        button1.setBorder(new EmptyBorder(0, 5, 0, 5));
        button2.setBorder(new EmptyBorder(0, 5, 0, 5));
        button3.setBorder(new EmptyBorder(0, 5, 0, 5));

        Dimension buttonSize = new Dimension(40, 40);
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);

        setBorder(BorderFactory.createLineBorder(CharteGraphique.BORDURE));

        // TODO PASSER EN FRANCAIS
        button1.setIcon(new ImageIcon(VueTournois.class.getResource("/images/actions/vue.png")));
        button2.setIcon(new ImageIcon(VueTournois.class.getResource("/images/actions/modifier.png")));
        button3.setIcon(new ImageIcon(VueTournois.class.getResource("/images/actions/supprimer.png")));
        
        button1.addMouseListener(new ButtonMouseAdapter(button1, "/images/actions/vue_actif.png", "/images/actions/vue.png"));
        button2.addMouseListener(new ButtonMouseAdapter(button2, "/images/actions/modifier_actif.png", "/images/actions/modifier.png"));
        button3.addMouseListener(new ButtonMouseAdapter(button3, "/images/actions/supprimer_actif.png", "/images/actions/supprimer.png"));

        button1.addActionListener(controleur);
        button2.addActionListener(controleur);
        button3.addActionListener(controleur);
        
        button1.setContentAreaFilled(false);
        button2.setContentAreaFilled(false);
        button3.setContentAreaFilled(false);

        add(button1);
        add(button2);
        add(button3);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	if (row % 2 == 0) {
            setBackground(CharteGraphique.FOND_SECONDAIRE);
            button1.setBackground(CharteGraphique.FOND_SECONDAIRE);
            button2.setBackground(CharteGraphique.FOND_SECONDAIRE);
            button3.setBackground(CharteGraphique.FOND_SECONDAIRE);
        } else {
            setBackground(CharteGraphique.FOND);
            button1.setBackground(CharteGraphique.FOND);
            button2.setBackground(CharteGraphique.FOND);
            button3.setBackground(CharteGraphique.FOND);
        }

        return this;
    }
    private static class ButtonMouseAdapter extends MouseInputAdapter {
        private final JButton button;
        private final String activeIconPath;
        private final String defaultIconPath;

        public ButtonMouseAdapter(JButton button, String activeIconPath, String defaultIconPath) {
            this.button = button;
            this.activeIconPath = activeIconPath;
            this.defaultIconPath = defaultIconPath;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            setActiveIcon();
        }

        private void setActiveIcon() {
            button.setIcon(new ImageIcon(VueTournois.class.getResource(activeIconPath)));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setContentAreaFilled(true);
            button.setFocusPainted(false);
            button.setIcon(new ImageIcon(VueTournois.class.getResource(defaultIconPath)));
        }
    }


}
