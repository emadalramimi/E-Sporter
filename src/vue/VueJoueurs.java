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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Liste des joueurs");
        setBounds(100, 100, 400, 200);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        JList<String> list = new JList<>(listModel);

        for (Joueur joueur : joueurs) {
            listModel.addElement(joueur.getPseudo());
        }

        scrollPane.setViewportView(list);
    }
}