package vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JLabelTheme;
import vue.theme.JButtonTheme.Types;
import vue.theme.JTextFieldTheme;

/**
 * Vue générique pour l'affichage d'une page avec un titre, un bouton et un panel (contenant un tableau par exemple)
 * @param <T> Type classe métier à rechercher
 */
public class SuperVueListe<T> extends JFrameTheme implements RecherchableVue<T> {

    private EventListener controleur;
	private JPanel panelRecherche;
    private JTextFieldTheme txtRecherche;
    private JButtonTheme btnRecherche;
	private JLabelTheme lblTitre;

	/**
	 * Construit une vue générique pour l'affichage d'une page avec un titre, un bouton et un panel (contenant un tableau par exemple)
	 */
    public SuperVueListe() {
		this.panelRecherche = new JPanel();
		this.panelRecherche.setBackground(CharteGraphique.PRIMAIRE);
		this.panelRecherche.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.txtRecherche = new JTextFieldTheme(20);
		this.txtRecherche.setColumns(20);
		this.panelRecherche.add(this.txtRecherche);
		
        this.btnRecherche = new JButtonTheme(Types.PRIMAIRE, new ImageIcon(VueTournois.class.getResource("/images/actions/rechercher.png")));
		this.btnRecherche.setActionCommand("RECHERCHER");
		this.panelRecherche.add(this.btnRecherche);
	}

	/**
	 * Affiche la vue générique pour l'affichage d'une page avec un titre, un bouton et un panel (contenant un tableau par exemple)
	 * @param contentPane : le panel où afficher la vue
	 * @param titre : le titre de la page
	 * @param boutonTitre : le bouton à afficher à côté du titre
	 * @param panelCorps : le panel contenant le tableau
	 */
	public void afficherVue(JPanel contentPane, String titre, JButton boutonTitre, JPanel panelCorps) {
		// panel contient tous les éléments de la page
		JPanel panel = new JPanel();
		panel.setBackground(CharteGraphique.FOND);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1020, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0};
		panel.setLayout(gbl_panel);
		
		// panelTitre, le panel contenant le titre et le bouton à côté
		JPanel panelTitre = new JPanel();
		GridBagConstraints gbc_panelTitre = new GridBagConstraints();
		gbc_panelTitre.anchor = GridBagConstraints.NORTH;
		gbc_panelTitre.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelTitre.insets = new Insets(0, 0, 20, 0);
		gbc_panelTitre.gridx = 0;
		gbc_panelTitre.gridy = 0;
		panel.add(panelTitre, gbc_panelTitre);
		panelTitre.setBackground(CharteGraphique.FOND);
		panelTitre.setLayout(new GridLayout(1, 0, 0, 0));
		
		// lblTitre, le label contenant le titre
		this.lblTitre = new JLabelTheme(titre, 30, true);
		this.lblTitre.setHorizontalAlignment(SwingConstants.LEFT);
		this.lblTitre.setFont(CharteGraphique.getPolice(30, true));
		this.lblTitre.setForeground(CharteGraphique.TEXTE);
		panelTitre.add(this.lblTitre);
		
		// panelBouton, le panel contenant le bouton à côté du titre
		if(boutonTitre != null) {
			JPanel panelBouton = new JPanel();
			panelBouton.setBackground(CharteGraphique.FOND);
			FlowLayout flowLayout = (FlowLayout) panelBouton.getLayout();
			flowLayout.setVgap(0);
			flowLayout.setHgap(0);
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelTitre.add(panelBouton);
			
			boutonTitre.setHorizontalAlignment(SwingConstants.RIGHT);
			panelBouton.add(boutonTitre);
		}

		// Placement du panel corps
		GridBagConstraints gbc_panelCorps = new GridBagConstraints();
		gbc_panelCorps.fill = GridBagConstraints.BOTH;
		gbc_panelCorps.gridx = 0;
		gbc_panelCorps.gridy = 2;
		panel.add(panelCorps, gbc_panelCorps);
	}

	/**
	 * Retourne un modèle de panel pour le corps
	 * @return un modèle de panel pour le corps
	 */
	public JPanel getPanelCorps() {
		JPanel panelCorps = new JPanel();
		panelCorps.setBackground(CharteGraphique.FOND);
		panelCorps.setLayout(new BorderLayout(0, 20));

		return panelCorps;
	}

	/**
	 * Retourne le panel de recherche
	 * @return le panel de recherche
	 */
    public JPanel getPanelRecherche() {
		return this.panelRecherche;
	}

	/**
	 * Ajoute un contrôleur à la vue
	 * @param controleur le contrôleur à ajouter
	 */
    public void setControleur(EventListener controleur) {
        this.controleur = controleur;
		this.txtRecherche.addKeyListener((KeyListener) controleur);
        this.btnRecherche.addActionListener((ActionListener) controleur);
    }

	/**
	 * Retourne le contrôleur de la vue
	 * @return le contrôleur de la vue
	 */
    public EventListener getControleur() {
        return this.controleur;
    }

	/**
	 * Modifie le titre de la page
	 * @param titre le nouveau titre de la page
	 */
	public void setTitre(String titre) {
		this.lblTitre.setText(titre);

	}

    /**
	 * Vérifie si le bouton est le bouton de recherche
	 * @param bouton : bouton à vérifier
	 * @return true si le bouton est le bouton de recherche, false sinon
	 */
	@Override
	public boolean estBoutonRecherche(JButton bouton) {
		return bouton.getActionCommand().equals("RECHERCHER");
	}
	
	/**
	 * Vérifie si le champ est le champ de recherche
	 * @param champ : champ à vérifier
	 * @return true si le champ est le champ de recherche, false sinon
	 */
	@Override
	public boolean estChampRecherche(JTextField champ) {
		return this.txtRecherche.equals(champ);
	}

	/**
	 * Remet à zéro le champ de recherche
	 */
	@Override
	public void resetChampRecherche() {
		this.txtRecherche.setText("");
	}
	
	/**
	 * Retourne la requête de recherche tapée par l'utilisateur
	 * @return la requête de recherche tapée par l'utilisateur
	 */
	@Override
	public String getRequeteRecherche() {
		return this.txtRecherche.getText().trim();
	}

	/**
	 * Remet à zéro les filtres (à override au besoin)
	 */
	public void resetFiltres() {}

    @Override
    public void remplirTableau(List<T> valeurs) {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }

}