package vue;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

public interface RecherchableVue<T> {
    
	public boolean estBoutonRecherche(JButton bouton);
	
	public boolean estChampRecherche(JTextField champ);

	public void resetChampRecherche();
	
	public String getRequeteRecherche();

	public default void resetFiltres() {}

	public void remplirTableau(List<T> valeurs);

}
