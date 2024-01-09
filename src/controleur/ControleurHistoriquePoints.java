package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.ModeleEquipe;
import modele.ModeleHistoriquePoints;
import modele.metier.Equipe;
import vue.VueHistoriquePoints;

public class ControleurHistoriquePoints extends KeyAdapter implements ActionListener, ListSelectionListener {
    
    private VueHistoriquePoints vue;
    private ModeleEquipe modeleEquipe;
    private ModeleHistoriquePoints modeleHistoriquePoints;

    public ControleurHistoriquePoints(VueHistoriquePoints vue) {
        this.vue = vue;
        this.modeleEquipe = new ModeleEquipe();
        this.modeleHistoriquePoints = new ModeleHistoriquePoints();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JTable tableEquipes = this.vue.getTableEquipes();
        if(e.getSource() == tableEquipes.getSelectionModel() && !e.getValueIsAdjusting()) {
            int idEquipe = (int) tableEquipes.getValueAt(tableEquipes.getSelectedRow(), 0);
            try {
                this.vue.remplirTableauHistoriquePoints(this.modeleHistoriquePoints.getParEquipe(idEquipe));
            } catch(Exception err) {
                this.vue.afficherPopupErreur("Une erreur est survenue lors de la récupération des historiques de points de l'équipe");
                throw new RuntimeException(err);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton bouton = (JButton) e.getSource();
        if(this.vue.estBoutonRecherche(bouton)) {
            String requeteRecherche = this.vue.getRequeteRecherche();
            if(requeteRecherche != null) {
                this.rechercher(this.vue.getRequeteRecherche());
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
			this.vue.remplirTableauEquipes(this.modeleEquipe.getParNom(requeteRecherche));
		} catch (Exception ex) {
			this.vue.afficherPopupErreur("Une erreur est survenue");
			throw new RuntimeException("Erreur dans la recherche");
		}
	}

    /**
	 * Retourne la liste de toutes les équipes de la saison pour VueEquipes
	 * @return La liste de toutes les équipes de la siaosn
	 */
    public List<Equipe> getEquipes() {
        try {
            return this.modeleEquipe.getEquipesSaison();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

}
