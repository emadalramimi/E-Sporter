package vue;

public class Application {

	public static void main(String[] args) {
		try {
			VueConnexion frame = new VueConnexion();
			frame.setTitle("E-sporter · Connexion");
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
