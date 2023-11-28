package vue.theme;

import javax.swing.JButton;

public class JButtonTable extends JButton {

	public enum Type {
		VOIR,
		MODIFIER,
		SUPPRIMER
	};
	
	private Type type;
	private int idElement;
	
	public JButtonTable(Type type, int idElement) {
		this.type = type;
		this.idElement = idElement;
	}

	public Type getType() {
		return this.type;
	}

	public int getIdElement() {
		return this.idElement;
	}
	
}
