package modele;

import modele.metier.Rencontre;
import modele.metier.Tournoi;

public class ModeleRencontre {
    
	public Rencontre getRencontreInMemory(Tournoi tournoi, int idRencontre) {
		return tournoi.getPoules().stream()
			.flatMap(poule -> poule.getRencontres().stream())
			.filter(r -> r.getIdRencontre() == idRencontre)
			.findFirst()
			.orElse(null);
	}

}
