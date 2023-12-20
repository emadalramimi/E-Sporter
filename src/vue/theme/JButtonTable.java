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
	 */
	public JButtonTable(Type type) {
		this.type = type;
	}

	/**
	 * Retourne le type du bouton
	 * @return le type du bouton
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * Retourne l'identifiant dans la BDD de l'élément sélectionné
	 * @return l'identifiant dans la BDD de l'élément sélectionné
	 */
	public int getIdElement() {
		return this.idElement;
	}

	/**
	 * Modifie l'identifiant dans la BDD de l'élément sélectionné
	 * @param idElement : identifiant dans la BDD de l'élément sélectionné
	 */
	public void setIdElement(int idElement) {
		this.idElement = idElement;
	}
	
}
