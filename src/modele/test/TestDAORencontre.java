package modele.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoiOuverture;
import modele.ModeleUtilisateur;
import modele.DAO.DAOArbitre;
import modele.DAO.DAOArbitreImpl;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.DAO.DAORencontre;
import modele.DAO.DAORencontreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.DroitsInsuffisantsException;
import modele.exception.TournoiClotureException;
import modele.exception.TournoiInexistantException;
import modele.metier.Equipe;
import modele.metier.Pays;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestDAORencontre {

	private DAORencontre daoRencontre;
	private DAOTournoi daoTournoi;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private DAOPoule daoPoule;
	private ModeleUtilisateur modeleUtilisateur;
	private DAOEquipe daoEquipe;
	private Rencontre rencontre;
	private int rencontresTotal;

	@Before
	public void setUp() throws Exception {
		this.daoRencontre = new DAORencontreImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();
		this.daoPoule = new DAOPouleImpl();
		this.modeleUtilisateur = new ModeleUtilisateur();
		this.rencontresTotal = this.daoRencontre.getTout().size();

		this.daoEquipe = new DAOEquipeImpl();
		Equipe[] equipes = { this.daoEquipe.getParId(1).get(), this.daoEquipe.getParId(2).get(), };

		DAOArbitre modeleArbitre = new DAOArbitreImpl();

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Obtenir le timestamp en secondes
		long timestampDebut = calendar.getTimeInMillis() / 1000;

		calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		// Obtenir le timestamp en secondes
		long timestampFin = calendar.getTimeInMillis() / 1000;

		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m",
				modeleArbitre.getTout());
		this.daoTournoi.ajouter(tournoi);

		this.rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(this.rencontre));
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		this.daoPoule.ajouter(poule);

		for (int i = 1; i < 5; i++)
			this.daoEquipe.inscrireEquipe(this.daoEquipe.getParId(i).get(), tournoi);
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleTournoiOuverture.ouvrirTournoi(this.daoTournoi.getParId(tournoi.getIdTournoi()).get());
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testModifier() throws Exception {
		daoRencontre.modifier(rencontre);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAjouterTropEquipe() throws Exception {
		Equipe[] equipes = { new Equipe("Equipe 1", Pays.ALGERIE, null),
				new Equipe("Equipe 2", Pays.ALGERIE, null),
				new Equipe("Equipe 3", Pays.ALGERIE, null)
		};
		Rencontre rencontre = new Rencontre(equipes);
		daoRencontre.ajouter(rencontre);
	}

	@Test(expected = TournoiInexistantException.class)
	public void testResetEquipeGagnanteTournoiExistePas() throws Exception {
		Equipe[] equipes = { this.daoEquipe.getParId(1).get(), this.daoEquipe.getParId(2).get(), };
		Rencontre rencontreTest = new Rencontre(equipes);
		this.daoRencontre.resetEquipeGagnante(rencontreTest);
	}

	@Test(expected = TournoiClotureException.class)
	public void testResetEquipeGagnanteTournoiCloture() throws Exception {
		this.daoRencontre.resetEquipeGagnante(this.daoRencontre.getParId(1).get());
	}

	@Test(expected = DroitsInsuffisantsException.class)
	public void testResetEquipeGagnanteMauvaisUtilisateur() throws Exception {
		this.daoRencontre.resetEquipeGagnante(this.rencontre);
	}

	@Test
	public void testResetEquipeGagnante() throws Exception {
		this.modeleUtilisateur.deconnecter();
		this.modeleUtilisateur.connecter("a", "m");
		this.daoRencontre.resetEquipeGagnante(this.rencontre);
	}

	@Test
	public void testSetEquipeGagnante() throws Exception {
		Equipe[] equipes = { this.daoEquipe.getParId(1).get(), this.daoEquipe.getParId(2).get(), };
		this.modeleUtilisateur.deconnecter();
		this.modeleUtilisateur.connecter("a", "m");
		this.daoRencontre.setEquipeGagnante(this.rencontre, equipes[0].getNom());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEquipeGagnanteInnexistant() throws Exception {
		this.modeleUtilisateur.deconnecter();
		this.modeleUtilisateur.connecter("a", "m");
		this.daoRencontre.setEquipeGagnante(this.rencontre, "fausseEquipe");
	}

	@After
	public void tearsDown() throws Exception {
		this.modeleUtilisateur.deconnecter();
		// Réinitialise les rencontres
		List<Integer> idREncontreAGarder = new ArrayList<>();
		for (int i = 1; i < 42; i++) {
			idREncontreAGarder.add(i);
		}
		this.daoRencontre.getTout().stream().filter(rencontre -> !idREncontreAGarder.contains(rencontre.getIdRencontre()))
				.forEach(rencontre -> {
					try {
						this.daoRencontre.supprimer(rencontre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		// Réinitialise les tournoi
		List<Integer> idTournoiAGarder = Arrays.asList(1, 2, 3, 4, 5, 6);
		this.daoTournoi.getTout().stream().filter(tournoi -> !idTournoiAGarder.contains(tournoi.getIdTournoi()))
				.forEach(tournoi -> {
					try {
						this.daoTournoi.supprimer(tournoi);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		// Réinitialise les poules
		List<Integer> idPouleAGarder = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
		this.daoPoule.getTout().stream().filter(poule -> !idPouleAGarder.contains(poule.getIdPoule()))
				.forEach(poule -> {
					try {
						this.daoPoule.supprimer(poule);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}
}