package vue;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modele.metier.Joueur;

public class VueJoueurs extends JFrame {
    private JPanel contentPane;

    public VueJoueurs(List<Joueur> joueurs) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 200, 200);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);

        DefaultListModel<Joueur> listModel = new DefaultListModel<>();

        JList<Joueur> list = new JList<>(listModel);

        for (Joueur joueur : joueurs) {
            listModel.addElement(joueur);
        }

        scrollPane.setViewportView(list);
    }
}