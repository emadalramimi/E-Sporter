package vue;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.EventListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vue.theme.CharteGraphique;
import vue.theme.JButtonTheme;
import vue.theme.JFrameTheme;
import vue.theme.JButtonTheme.Types;
import vue.theme.JTextFieldTheme;

public class RecherchableVue<T> extends JFrameTheme implements RecherchableVueInterface<T> {

    private EventListener controleur;
	private JPanel panelRecherche;
    private JTextFieldTheme txtRecherche;
    private JButtonTheme btnRecherche;

	/**
	 * Construit une vue recherchable
	 */
    public RecherchableVue() {
		this.panelRecherche = new JPanel();
		this.panelRecherche.setBackground(CharteGraphique.PRIMAIRE);
		this.panelRecherche.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.txtRecherche = new JTextFieldTheme(20);
		this.txtRecherche.setColumns(20);
		this.panelRecherche.add(this.txtRecherche);
		
        this.btnRecherche = new JButtonTheme(Types.PRIMAIRE, new ImageIcon(VueTournois.class.getResource("/images/actions/rechercher.png")));
		this.panelRecherche.add(this.btnRecherche);
	}

	/**
	 * Récupère le panel de recherche
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
	 * Récupère le contrôleur de la vue
	 * @return le contrôleur de la vue
	 */
    public EventListener getControleur() {
        return this.controleur;
    }

    /**
	 * Vérifie si le bouton est le bouton de recherche
	 * @param bouton : bouton à vérifier
	 * @return true si bouton est le bouton de recherche, false sinon
	 */
	@Override
	public boolean estBoutonRecherche(JButton bouton) {
		if(bouton instanceof JButtonTheme && bouton.getIcon() != null) {
			String iconeRecherche = VueTournois.class.getResource("/images/actions/rechercher.png").toString();
		    return bouton.getIcon().toString().equals(iconeRecherche);
		}
		return false;
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
	 * Remet à zéro les filtres (à override)
	 */
	public void resetFiltres() {}

    @Override
    public void remplirTableau(List<T> valeurs) {
        throw new UnsupportedOperationException("Méthode non implémentée");
    }

}