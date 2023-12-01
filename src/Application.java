import vue.VueConnexion;

public class Application {

	public static void main(String[] args) {
		System.setProperty("sun.java2d.uiScale", "1.0");
		
		try {
			VueConnexion frame = new VueConnexion();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
