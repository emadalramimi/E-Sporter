package modele.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleArbitre;
import modele.ModeleEquipe;
import modele.ModelePoule;
import modele.ModeleRencontre;
import modele.ModeleTournoi;
import modele.ModeleTournoiOuverture;
import modele.ModeleUtilisateur;
import modele.exception.DroitsInsuffisantsException;
import modele.exception.TournoiClotureException;
import modele.exception.TournoiInexistantException;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleRencontre {

	private ModeleRencontre modele;
	private ModeleTournoi modeleTournoi;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private ModelePoule modelePoule;
	private ModeleUtilisateur modeleUtilisateur;
	private ModeleEquipe modeleEquipe;
	private Rencontre rencontre;
	private int rencontresTotal;

	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleRencontre();
		this.modeleTournoi = new ModeleTournoi();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();
		this.modelePoule = new ModelePoule();
		this.modeleUtilisateur = new ModeleUtilisateur();
		this.rencontresTotal = this.modele.getTout().size();

		this.modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = { this.modeleEquipe.getParId(1).get(), this.modeleEquipe.getParId(2).get(), };

		ModeleArbitre modeleArbitre = new ModeleArbitre();

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
		this.modeleTournoi.ajouter(tournoi);

		this.rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(this.rencontre));
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		this.modelePoule.ajouter(poule);

		for (int i = 1; i < 5; i++)
			this.modeleEquipe.inscrireEquipe(this.modeleEquipe.getParId(i).get(), tournoi);
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleTournoiOuverture.ouvrirTournoi(this.modeleTournoi.getParId(tournoi.getIdTournoi()).get());
	}

	@Test(expected = TournoiInexistantException.class)
	public void testResetEquipeGagnanteTournoiExistePas() throws Exception {
		Equipe[] equipes = { this.modeleEquipe.getParId(1).get(), this.modeleEquipe.getParId(2).get(), };
		Rencontre rencontreTest = new Rencontre(equipes);
		this.modele.resetEquipeGagnante(rencontreTest);
	}

	@Test(expected = TournoiClotureException.class)
	public void testResetEquipeGagnanteTournoiCloture() throws Exception {
		this.modele.resetEquipeGagnante(this.modele.getParId(1).get());
	}

	@Test(expected = DroitsInsuffisantsException.class)
	public void testResetEquipeGagnanteMauvaisUtilisateur() throws Exception {
		this.modele.resetEquipeGagnante(this.rencontre);
	}

	@Test
	public void testResetEquipeGagnante() throws Exception {
		this.modeleUtilisateur.deconnecter();
		this.modeleUtilisateur.connecter("a", "m");
		this.modele.resetEquipeGagnante(this.rencontre);
	}

	@Test
	public void testSetEquipeGagnante() throws Exception {
		Equipe[] equipes = { this.modeleEquipe.getParId(1).get(), this.modeleEquipe.getParId(2).get(), };
		this.modeleUtilisateur.deconnecter();
		this.modeleUtilisateur.connecter("a", "m");
		this.modele.setEquipeGagnante(this.rencontre, equipes[0].getNom());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetEquipeGagnanteInnexistant() throws Exception {
		this.modeleUtilisateur.deconnecter();
		this.modeleUtilisateur.connecter("a", "m");
		this.modele.setEquipeGagnante(this.rencontre, "fausseEquipe");
	}

	@After
	public void tearsDown() throws Exception {
		this.modeleUtilisateur.deconnecter();
		// Réinitialise les rencontres
		List<Integer> idREncontreAGarder = new ArrayList<>();
		for (int i = 1; i < this.rencontresTotal; i++) {
			idREncontreAGarder.add(i);
		}
		this.modele.getTout().stream().filter(rencontre -> !idREncontreAGarder.contains(rencontre.getIdRencontre()))
				.forEach(rencontre -> {
					try {
						this.modele.supprimer(rencontre);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		// Réinitialise les tournoi
		List<Integer> idTournoiAGarder = Arrays.asList(1, 2, 3, 4, 5, 6);
		this.modeleTournoi.getTout().stream().filter(tournoi -> !idTournoiAGarder.contains(tournoi.getIdTournoi()))
				.forEach(tournoi -> {
					try {
						this.modeleTournoi.supprimer(tournoi);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});

		// Réinitialise les poules
		List<Integer> idPouleAGarder = Arrays.asList(1, 2);
		this.modelePoule.getTout().stream().filter(poule -> !idPouleAGarder.contains(poule.getIdPoule()))
				.forEach(poule -> {
					try {
						this.modelePoule.supprimer(poule);
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}
}