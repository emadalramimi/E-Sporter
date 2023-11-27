package vue.theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import vue.VueTournois;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonRenderer extends JPanel implements TableCellRenderer {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTable table;
    private int[] teamIds;

    public ButtonRenderer(JTable table, int[] teamIds) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));

        this.table = table;
        this.teamIds = teamIds;

        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
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

        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleViewButtonClick();
            }
        });

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleEditButtonClick();
            }
        });

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleDeleteButtonClick();
            }
        });

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

    private void handleViewButtonClick() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int teamId = teamIds[selectedRow];
            System.out.println("View clicked for Team ID: " + teamId);
        }
    }

    private void handleEditButtonClick() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int teamId = teamIds[selectedRow];
            System.out.println("Edit clicked for Team ID: " + teamId);
        }
    }

    private void handleDeleteButtonClick() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int teamId = teamIds[selectedRow];
            System.out.println("Delete clicked for Team ID: " + teamId);
        }
    }
}
