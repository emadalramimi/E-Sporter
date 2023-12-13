package vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VueInscriptionEquipesTournoi extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueInscriptionEquipesTournoi frame = new VueInscriptionEquipesTournoi();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VueInscriptionEquipesTournoi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 384);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 20));
		
		JPanel panelListe = new JPanel();
		contentPane.add(panelListe, BorderLayout.CENTER);
		panelListe.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelListe.add(scrollPane, BorderLayout.CENTER);
		
		JList listEquipes = new JList();
		scrollPane.setViewportView(listEquipes);
		
		JPanel panelTitre = new JPanel();
		contentPane.add(panelTitre, BorderLayout.NORTH);
		panelTitre.setLayout(new BorderLayout(0, 0));
		
		JPanel panellbls = new JPanel();
		panelTitre.add(panellbls, BorderLayout.WEST);
		GridBagLayout gbl_panellbls = new GridBagLayout();
		gbl_panellbls.columnWeights = new double[]{0.0};
		gbl_panellbls.rowWeights = new double[]{0.0, 0.0};
		panellbls.setLayout(gbl_panellbls);
		
		JLabel lblTitre = new JLabel("Equipes inscrites");
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panellbls.add(lblTitre, gbc_lblTitre);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panellbls.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JButton btnInscrireEquipe = new JButton("Inscrire équipe");
		panelTitre.add(btnInscrireEquipe, BorderLayout.EAST);
	}

}
