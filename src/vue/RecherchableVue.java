package vue;

import java.util.EventListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Interface des vues contenant une recherche
 * @param <T> Type classe métier à rechercher
 */
public interface RecherchableVue<T> {

	public JPanel getPanelRecherche();

    public void setControleur(EventListener controleur);

    public EventListener getControleur();
    
	public boolean estBoutonRecherche(JButton bouton);
	
	public boolean estChampRecherche(JTextField champ);

	public void resetChampRecherche();
	
	public String getRequeteRecherche();

	public void resetFiltres();

	public void remplirTableau(List<T> valeurs);

}
