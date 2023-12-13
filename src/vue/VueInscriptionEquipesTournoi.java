package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vue.theme.JFrameTheme;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VueInscriptionEquipesTournoi extends JFrameTheme {

	private JPanel contentPane;

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
		
		JPanel panelHeader = new JPanel();
		contentPane.add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitre = new JPanel();
		panelHeader.add(panelTitre, BorderLayout.WEST);
		GridBagLayout gbl_panelTitre = new GridBagLayout();
		gbl_panelTitre.columnWeights = new double[]{0.0};
		gbl_panelTitre.rowWeights = new double[]{0.0, 0.0};
		panelTitre.setLayout(gbl_panelTitre);
		
		JLabel lblTitre = new JLabel("Equipes inscrites");
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 0;
		panelTitre.add(lblTitre, gbc_lblTitre);
		
		JLabel lblTournoi = new JLabel("New label");
		GridBagConstraints gbc_lblTournoi = new GridBagConstraints();
		gbc_lblTournoi.insets = new Insets(0, 0, 0, 5);
		gbc_lblTournoi.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblTournoi.gridx = 0;
		gbc_lblTournoi.gridy = 1;
		panelTitre.add(lblTournoi, gbc_lblTournoi);
		
		JButton btnInscrireEquipe = new JButton("Inscrire équipe");
		panelHeader.add(btnInscrireEquipe, BorderLayout.EAST);
	}
	


}
