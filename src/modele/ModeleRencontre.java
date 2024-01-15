package modele;

import modele.metier.Rencontre;
import modele.metier.Tournoi;

/**
 * Modèle rencontre
 */
public class ModeleRencontre {
    
	/**
	 * Récupère une rencontre en mémoire
	 * @param tournoi : tournoi dans lequel se trouve la rencontre
	 * @param idRencontre : id de la rencontre
	 * @return la rencontre
	 */
	public Rencontre getRencontreInMemory(Tournoi tournoi, int idRencontre) {
		return tournoi.getPoules().stream()
			.flatMap(poule -> poule.getRencontres().stream())
			.filter(r -> r.getIdRencontre() == idRencontre)
			.findFirst()
			.orElse(null);
	}

}
