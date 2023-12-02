package vue.theme;

import javax.swing.JButton;

/**
 * Extension du JButton de Swing pour les JTable
 */
public class JButtonTable extends JButton {

	/**
	 * Énumération du type du bouton cliqué
	 */
	public enum Type {
		VOIR,
		MODIFIER,
		SUPPRIMER
	};
	
	private Type type;
	private int idElement;
	
	/**
	 * Crée le JButton
	 * @param type : type du bouton
	 * @param idElement : identifiant dans la BDD de l'élément sélectionné
	 */
	public JButtonTable(Type type, int idElement) {
		this.type = type;
		this.idElement = idElement;
	}

	/**
	 * @return le type du bouton
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * @return l'identifiant dand la BDD de l'élément sélectionné
	 */
	public int getIdElement() {
		return this.idElement;
	}
	
}
