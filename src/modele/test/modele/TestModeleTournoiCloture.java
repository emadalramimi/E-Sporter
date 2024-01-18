package modele.test.modele;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoi;
import modele.ModeleTournoiCloture;
import modele.ModeleTournoiOuverture;
import modele.ModeleUtilisateur;

import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.DAO.DAORencontre;
import modele.DAO.DAORencontreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;

import modele.metier.Arbitre;
import modele.metier.EnumPoints;
import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;
import modele.test.SuperTest;

/**
 * Classe de test pour le modèle TournoiCloture
 * @see ModeleTournoiCloture
 */
public class TestModeleTournoiCloture extends SuperTest {
	
	private ModeleTournoiCloture modele;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private ModeleUtilisateur modeleUtilisateur;
	private ModeleTournoi modeleTournoi;
	
	private DAOTournoi daoTournoi;
	private DAOEquipe daoEquipe;
	private DAORencontre daoRencontre;
	
	/**
	 * Configure l'environnement de test avant chaque cas de test
	 * @throws Exception exceptions des DAO
	 */
	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleTournoiCloture();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();
		this.modeleUtilisateur = new ModeleUtilisateur();
		this.modeleTournoi = new ModeleTournoi();
		
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.daoRencontre = new DAORencontreImpl();
		
		Tournoi tournoiInit = new Tournoi(
			"Tournoi",
			Notoriete.LOCAL,
			this.getDateCourante() + 3600,
			this.getDateCourante() + 6600,
			"arbitre",
			"mdp",
			new ArrayList<>()
		);

		daoTournoi.ajouter(tournoiInit);
		for (int i = 1; i < 5; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getParId(i).get(), tournoiInit);
		}
		modeleTournoiOuverture.ouvrirTournoi(modeleTournoi.getParNom("Tournoi").get(0));
	}

	/**
	 * Teste la méthode cloturerPoule sans tous les matchs joués
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoiCloture#cloturerPoule(Tournoi)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCloturerPouleMatchNonJoue() throws Exception {
		modeleUtilisateur.connecter("arbitre", "mdp");
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}
	
	/**
	 * Teste la méthode cloturerPoule sans être arbitre
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoiCloture#cloturerPoule(Tournoi)
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCloturerPouleNonArbitre() throws Exception {
		modeleUtilisateur.connecter("admin", "mdp");
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}

	/**
	 * Teste la méthode cloturerPoule avec poule qualifiations
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoiCloture#cloturerPoule(Tournoi)
	 */
	@Test
	public void testCloturerPouleQualification() throws Exception {
		modeleUtilisateur.connecter("arbitre", "mdp");
		for (Rencontre rencontre : modeleTournoi.getParNom("Tournoi").get(0).getPouleActuelle().getRencontres()) {
			daoRencontre.setEquipeGagnante(rencontre, rencontre.getEquipes()[0].getNom());
		}
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}

	/**
	 * Teste la méthode cloturerPoule avec poule finale
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoiCloture#cloturerPoule(Tournoi)
	 */
	@Test
	public void testCloturerPouleFinale() throws Exception {
		modeleUtilisateur.connecter("arbitre", "mdp");
		for (Rencontre rencontre : modeleTournoi.getParNom("Tournoi").get(0).getPouleActuelle().getRencontres()) {
			daoRencontre.setEquipeGagnante(rencontre, rencontre.getEquipes()[0].getNom());
		}
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
		
		Rencontre finale = modeleTournoi.getParNom("Tournoi").get(0).getPouleActuelle().getRencontres().get(0);
		daoRencontre.setEquipeGagnante(finale, finale.getEquipes()[0].getNom());
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}

	/**
	 * Teste la méthode getNombrePointsParClassement
	 * @throws Exception si une erreur se produit pendant le test
	 * @see ModeleTournoiCloture#getNombrePointsParClassement(Map, Notoriete)
	 */
	@Test
	public void testGetNombrePointsParClassement() {
		// Création des équipes
		Equipe equipe1 = new Equipe(1, "Equipe 1", Pays.FRANCE, 1, 1, "2024", new ArrayList<>());
		Equipe equipe2 = new Equipe(2, "Equipe 2", Pays.FRANCE, 2, 2, "2024", new ArrayList<>());
		Equipe equipe3 = new Equipe(3, "Equipe 3", Pays.FRANCE, 3, 3, "2024", new ArrayList<>());
		Equipe equipe4 = new Equipe(4, "Equipe 4", Pays.FRANCE, 4, 4, "2024", new ArrayList<>());
		Equipe equipe5 = new Equipe(5, "Equipe 5", Pays.FRANCE, 5, 5, "2024", new ArrayList<>());

		// On teste toutes les notoriétés
		for(Notoriete notoriete : Notoriete.values()) {
			// Mise en place des points par match par équipe (simulation)
			Map<Equipe, Float> nbPointsParEquipe = new HashMap<>();
			nbPointsParEquipe.put(equipe1, 50F);
			nbPointsParEquipe.put(equipe2, 40F);
			nbPointsParEquipe.put(equipe3, 30F);
			nbPointsParEquipe.put(equipe4, 20F);
			nbPointsParEquipe.put(equipe5, 10F);
			
			// Récupération du nombre points par classement
			Map<Equipe, Float> nbPointsParEquipeClasse = modele.getNombrePointsParClassement(nbPointsParEquipe, notoriete);

			// Calcul des points attendus
			float ptsAttendusEquipe1 = 50F * 10F;
			ptsAttendusEquipe1 += EnumPoints.CLASSEMENT_PREMIER.getPoints();
			ptsAttendusEquipe1 *= notoriete.getMultiplicateur();

			float ptsAttendusEquipe2 = 40F * 10F;
			ptsAttendusEquipe2 += EnumPoints.CLASSEMENT_DEUXIEME.getPoints();
			ptsAttendusEquipe2 *= notoriete.getMultiplicateur();

			float ptsAttendusEquipe3 = 30F * 10F;
			ptsAttendusEquipe3 += EnumPoints.CLASSEMENT_TROISIEME.getPoints();
			ptsAttendusEquipe3 *= notoriete.getMultiplicateur();

			float ptsAttendusEquipe4 = 20F * 10F;
			ptsAttendusEquipe4 += EnumPoints.CLASSEMENT_QUATRIEME.getPoints();
			ptsAttendusEquipe4 *= notoriete.getMultiplicateur();

			float ptsAttendusEquipe5 = 10F * 10F;
			ptsAttendusEquipe5 *= notoriete.getMultiplicateur();
			
			// Vérification des points par rapport aux pts attendus
			assertEquals(ptsAttendusEquipe1, (float) nbPointsParEquipeClasse.get(equipe1), 0.1F);
			assertEquals(ptsAttendusEquipe2, (float) nbPointsParEquipeClasse.get(equipe2), 0.1F);
			assertEquals(ptsAttendusEquipe3, (float) nbPointsParEquipeClasse.get(equipe3), 0.1F);
			assertEquals(ptsAttendusEquipe4, (float) nbPointsParEquipeClasse.get(equipe4), 0.1F);
			assertEquals(ptsAttendusEquipe5, (float) nbPointsParEquipeClasse.get(equipe5), 0.1F);
		}
	}

	@Test
	public void testGetNombrePointsMatchsParEquipe() {
		// Création des équipes
		Equipe equipe1 = new Equipe(1, "Equipe 1", Pays.FRANCE, 1, 1, "2024", new ArrayList<>());
		Equipe equipe2 = new Equipe(2, "Equipe 2", Pays.FRANCE, 2, 2, "2024", new ArrayList<>());

		// Création des rencontres
		Rencontre rencontre1 = new Rencontre(1, 1, equipe1.getIdEquipe(), new Equipe[]{equipe1, equipe2}); // equipe1 gagne
		rencontre1.setIdEquipeGagnante(1);
		Rencontre rencontre2 = new Rencontre(2, 1, equipe2.getIdEquipe(), new Equipe[]{equipe1, equipe2}); // equipe2 gagne
		rencontre2.setIdEquipeGagnante(2);

		// Création des poules
		Poule poule = new Poule(true, false, 1, Arrays.asList(rencontre1, rencontre2));

		Rencontre rencontre3 = new Rencontre(3, 1, equipe1.getIdEquipe(), new Equipe[]{equipe1, equipe2}); // equipe1 gagne
		rencontre3.setIdEquipeGagnante(1);

		Poule poule2 = new Poule(false, true, 2, Arrays.asList(rencontre3));

		// Création du tournoi
		Tournoi tournoi = new Tournoi(
			"TournoiTest",
			Notoriete.NATIONAL,
			System.currentTimeMillis() / 1000 + 3600,
			System.currentTimeMillis() / 1000 + 7200,
			"arbitre",
			"password",
			Arrays.asList(new Arbitre(1, "Willem", "Miled"))
		);
		tournoi.setPoules(Arrays.asList(poule, poule2));

		Map<Equipe, Float> nbPointsParEquipe = modele.getNombrePointsMatchsParEquipe(tournoi);

		assertEquals(EnumPoints.MATCH_VICTOIRE.getPoints() * 2 + EnumPoints.MATCH_DEFAITE.getPoints(), nbPointsParEquipe.get(equipe1), 0.1F);
		assertEquals(EnumPoints.MATCH_VICTOIRE.getPoints() + EnumPoints.MATCH_DEFAITE.getPoints() * 2, nbPointsParEquipe.get(equipe2), 0.1F);
	}

	// Réinitialise les rencontres, les poules et les tournoi
	@After
	public void tearsDown() throws Exception {
		if (ModeleUtilisateur.getCompteCourant() != null) {
			modeleUtilisateur.deconnecter();
		}
		
		List<Integer> idAGarderRencontre = new ArrayList<>();
		for (int i=1;i<43;i++) {
			idAGarderRencontre.add(i);
		}
		for (Rencontre rencontre : this.daoRencontre.getTout()) {
			if (!idAGarderRencontre.contains(rencontre.getIdRencontre())) {
				this.daoRencontre.supprimer(rencontre);
			}
		}
		
		List<Integer> idAGarderTournoi = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
		for (Tournoi tournoi : this.daoTournoi.getTout()) {
			if (!idAGarderTournoi.contains(tournoi.getIdTournoi())) {
				this.daoTournoi.supprimer(tournoi);
			}
		}	
		
		DAOPoule daoPoule = new DAOPouleImpl();
		List<Integer> idAGarderPoule = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
		for (Poule poule : daoPoule.getTout()) {
			if (!idAGarderPoule.contains(poule.getIdPoule())) {
				daoPoule.supprimer(poule);
			}
		}
	}

}