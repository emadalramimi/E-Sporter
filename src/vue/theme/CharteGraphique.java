package vue.theme;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class CharteGraphique {
	
	public final static Color FOND = new Color(7,10,20);
	public final static Color FOND_SECONDAIRE = new Color(22,20,69);
	public final static Color TEXTE = new Color(255,255,255);
	public final static Color FOND_BTN_TITLEBAR = new Color(55,57,97);
	public final static Color PRIMAIRE = new Color(0,104,249);
	public final static Color BORDURE = new Color(30,45,98);
	public final static Color BORDURE_FENETRE = new Color(32,35,44);
	
	public static Font getPolice(float taille, boolean estGras) {
	    Font police = null;
	    try {
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

	    if (police == null) {
	    	if(!estGras) {
	    		return Font.getFont(Font.SANS_SERIF).deriveFont(taille);
	    	}
	    	return Font.getFont(Font.SANS_SERIF).deriveFont(Font.BOLD, taille);
	    }
        return police.deriveFont(taille);
	}
	
}
