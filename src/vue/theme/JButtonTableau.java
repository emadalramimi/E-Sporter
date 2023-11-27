package vue.theme;

import javax.swing.JButton;

public class JButtonTableau extends JButton {

	public enum Type {
		VOIR,
		MODIFIER,
		SUPPRIMER
	};
	
	private Type type;
	
	public JButtonTableau(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
}
