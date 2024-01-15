package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import modele.DAO.Recherchable;
import vue.RecherchableVue;
import vue.theme.JFrameTheme;

public class ControleurRecherche<T> extends KeyAdapter {
    
    private Recherchable<T> modele;
    private RecherchableVue<T> vue;

    public ControleurRecherche(Recherchable<T> modele, RecherchableVue<T> vue) {
        this.modele = modele;
        this.vue = vue;
    }

	public void traitementClicBoutonRecherche(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton bouton = (JButton) e.getSource();

			if(this.vue.estBoutonRecherche(bouton)) {
				String requeteRecherche = this.vue.getRequeteRecherche();
				if(requeteRecherche != null) {
					this.rechercher(this.vue.getRequeteRecherche());
				}
			}
		}
		
	}
	
	/**
	 * Quand on appuie sur une touche du clavier dans le champ de recherche
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		JTextField txtRecherche = (JTextField) e.getSource();
		// Si il s'agit du champ de recherche
		if (this.vue.estChampRecherche(txtRecherche)) {
			// Récupère la requête de recherche
			String requeteRecherche = this.vue.getRequeteRecherche();

			// Effectuer la recherche à l'appui de la touche entrée
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.rechercher(requeteRecherche);
			}
			
			// Lorsqu'on supprime tous les caractères dans le champ de recherche, sortir de la recherche
			else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (
					requeteRecherche != null
					&& requeteRecherche.length() == 1
					|| txtRecherche.getSelectedText() != null
					&& txtRecherche.getSelectedText().equals(requeteRecherche)
				) {
					this.rechercher("");
				}
			}
		}
	}

	/**
	 * Effectue une recherche de requête requeteRecherche
	 * @param requeteRecherche
	 */
	private void rechercher(String requeteRecherche) {
		try {
			// Mise à jour du tableau avec les résultats de recherche
            this.vue.resetFiltres();
			this.vue.remplirTableau(this.modele.getParNom(requeteRecherche));
		} catch (Exception ex) {
            JFrameTheme vueFrame = (JFrameTheme) this.vue;
			vueFrame.afficherPopupErreur("Une erreur est survenue");
			throw new RuntimeException("Erreur dans la recherche");
		}
	}

    public Recherchable<T> getModele() {
        return this.modele;
    }

}
