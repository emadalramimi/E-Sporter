import vue.VueConnexion;

/**
 * Class de point d'entrée de l'application
 */
public class Application {

	/**
	 * Point d'entrée de l'application
	 * @param args
	 */
	public static void main(String[] args) {
		// Empêcher le redimensionnement de la fenêtre par Windows (éviter images floues)
		System.setProperty("sun.java2d.uiScale", "1.0");
		
		// Ouverture de la fenêtre de connexion
		VueConnexion vue = new VueConnexion();
		vue.afficher();

		//test
	}

}
