package vue.theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import vue.VueTournois;

import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonRenderer extends JPanel implements TableCellRenderer {

    private JButtonTableau button1;
    private JButtonTableau button2;
    private JButtonTableau button3;
    private JTable table;
    private int[] teamIds;

    public ButtonRenderer(JTable table, int[] teamIds, ActionListener controleur) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));

        this.table = table;
        this.teamIds = teamIds;

        button1 = new JButtonTableau(JButtonTableau.Type.VOIR);
        button2 = new JButtonTableau(JButtonTableau.Type.MODIFIER);
        button3 = new JButtonTableau(JButtonTableau.Type.SUPPRIMER);
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

        button1.setIcon(new ImageIcon(VueTournois.class.getResource("/images/eye.png")));
        button2.setIcon(new ImageIcon(VueTournois.class.getResource("/images/pen.png")));
        button3.setIcon(new ImageIcon(VueTournois.class.getResource("/images/trash.png")));

        button1.addActionListener(controleur);
        button2.addActionListener(controleur);
        button3.addActionListener(controleur);

        add(button1);
        add(button2);
        add(button3);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
}
