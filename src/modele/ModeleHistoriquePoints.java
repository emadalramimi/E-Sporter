package modele;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOHistoriquePoints;
import modele.DAO.DAOHistoriquePointsImpl;
import modele.metier.Equipe;

public class ModeleHistoriquePoints {

    private DAOEquipe daoEquipe;
    private DAOHistoriquePoints daoHistoriquePoints;

    public ModeleHistoriquePoints() {
        this.daoEquipe = new DAOEquipeImpl();
        this.daoHistoriquePoints = new DAOHistoriquePointsImpl();
    }
    
    public Map<Equipe, Integer> getClassementParEquipe() throws Exception {
        // Initialisation du classement de toutes les équipes à 1000
		Map<Equipe, Integer> classementParEquipe = new HashMap<>();
        for (Equipe equipe : this.daoEquipe.getEquipesSaison()) {
            classementParEquipe.put(equipe, 1000);
        }

		// Comptage du nombre de points total de chaque équipe sur la saison
        ResultSet rs = this.daoHistoriquePoints.getClassementParEquipe();

		// Attribution du classement à chaque équipe
        int classement = 0;
        int pointsPrecedents = -1;
        while (rs.next()) {
            Equipe equipe = this.daoEquipe.getParId(rs.getInt(1)).orElse(null);
            int points = rs.getInt(2);

			// Si le nombre de points est différent du nombre de points de l'équipe précédente, on incrémente le classement
			// Sinon, on garde le même classement pour les deux équipes
            if (points != pointsPrecedents) {
                classement++;
            }

            classementParEquipe.put(equipe, classement);

			// Mise à jour des points précédents
            pointsPrecedents = points;
        }

		return classementParEquipe;
    }

}
