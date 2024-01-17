package vue.theme;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 * Classe de charte graphique de l'application
 * Permet la centralisation de tous les choix de style
 */
public class CharteGraphique {
	
	// Couleurs
	public final static Color FOND = new Color(7, 10, 20);
	public final static Color FOND_SECONDAIRE = new Color(22, 20, 69);
	public final static Color TEXTE = new Color(255, 255, 255);
	public final static Color FOND_BTN_TITLEBAR = new Color(55, 57, 97);
	public final static Color PRIMAIRE = new Color(0, 104, 249);
	public final static Color BORDURE = new Color(30, 45, 98);
	public final static Color BORDURE_FENETRE = new Color(32, 35, 44);
	public final static Color TOURNOI_OUVERT = new Color(111, 227, 96);
	public final static Color TOURNOI_CLOTURE = new Color(255, 0, 0);
	public final static Color OR = new Color(255, 215, 0);
	public final static Color ARGENT = new Color(192, 192, 192);
	public final static Color BRONZE = new Color(205, 127, 50);
	
	/**
	 * Retourne la police générale du logiciel
	 * @param taille Taille de la police
	 * @param estGras True pour afficher une police grasse, false sinon
	 * @return la police générale du logiciel
	 */
	public static Font getPolice(float taille, boolean estGras) {
	    Font police = null;
	    try {
	    	// Création de la police à partir de son fichier ttf
	    	if(!estGras) {
	    		police = Font.createFont(Font.TRUETYPE_FONT, CharteGraphique.class.getResourceAsStream("/fonts/Poppins.ttf"));
	    	} else {
	    		police = Font.createFont(Font.TRUETYPE_FONT, CharteGraphique.class.getResourceAsStream("/fonts/Poppins_Bold.ttf"));
	    		
	    	}
	    } catch (FontFormatException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Si la police n'est pas trouvée, on utilise une police générique
	    if (police == null) {
	    	if(!estGras) {
	    		return Font.getFont(Font.SANS_SERIF).deriveFont(taille);
	    	}
	    	return Font.getFont(Font.SANS_SERIF).deriveFont(Font.BOLD, taille);
	    }
	    
	    // On change la taille de la police qu'on retourne
        return police.deriveFont(taille);
	}
	
}
