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
import modele.ModeleUtilisateur;
import modele.metier.Equipe;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleRencontre {
	
	private ModeleRencontre modele;
    private ModeleTournoi modeleTournoi;
    private ModelePoule modelePoule;
    private ModeleUtilisateur modeleUtilisateur;
	
    @Before
    public void setUp() {
        modele = new ModeleRencontre();
        modeleTournoi = new ModeleTournoi();
        modelePoule = new ModelePoule();
        modeleUtilisateur = new ModeleUtilisateur();
        
        
    }
	
	@Test(expected=IllegalArgumentException.class)
	public void testResetEquipeGagnanteTournoiExistePas() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = {
				modeleEquipe.getParId(1).get(),
				modeleEquipe.getParId(2).get(),
		};
		
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
		
		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m", modeleArbitre.getTout());
		modeleTournoi.ajouter(tournoi);
		
		Rencontre rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(
				rencontre));		
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);
		
		for (int i=1;i<5;i++)
		modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		modeleUtilisateur.connecter("admin", "mdp");
		
		modeleTournoi.ouvrirTournoi(modeleTournoi.getParId(tournoi.getIdTournoi()).get());
		Rencontre rencontreTest = new Rencontre(equipes);
		modele.resetEquipeGagnante(rencontreTest);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testResetEquipeGagnanteTournoiCloture() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = {
				modeleEquipe.getParId(1).get(),
				modeleEquipe.getParId(2).get(),
		};
		
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
		
		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m", modeleArbitre.getTout());
		modeleTournoi.ajouter(tournoi);
		
		Rencontre rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(
				rencontre));		
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);
		
		for (int i=1;i<5;i++)
		modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		modeleUtilisateur.connecter("admin", "mdp");
		
		modeleTournoi.ouvrirTournoi(modeleTournoi.getParId(tournoi.getIdTournoi()).get());
		modele.resetEquipeGagnante(modele.getParId(1).get());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testResetEquipeGagnanteMauvaisUtilisateur() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = {
				modeleEquipe.getParId(1).get(),
				modeleEquipe.getParId(2).get(),
		};
		
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
		
		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m", modeleArbitre.getTout());
		modeleTournoi.ajouter(tournoi);
		
		Rencontre rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(
				rencontre));		
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);
		
		for (int i=1;i<5;i++)
		modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		modeleUtilisateur.connecter("admin", "mdp");
		
		modeleTournoi.ouvrirTournoi(modeleTournoi.getParId(tournoi.getIdTournoi()).get());		
		modele.resetEquipeGagnante(rencontre);
	}
	
	@Test
	public void testResetEquipeGagnante() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = {
				modeleEquipe.getParId(1).get(),
				modeleEquipe.getParId(2).get(),
		};
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
		
		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m", modeleArbitre.getTout());
		modeleTournoi.ajouter(tournoi);
		
		Rencontre rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(
				rencontre));		
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);
		
		for (int i=1;i<5;i++)
		modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		
		modeleUtilisateur.connecter("admin", "mdp");
		modeleTournoi.ouvrirTournoi(modeleTournoi.getParId(tournoi.getIdTournoi()).get());
		modeleUtilisateur.deconnecter();
		modeleUtilisateur.connecter("a", "m");
		modele.resetEquipeGagnante(rencontre);
	}
	
	@Test
	public void testSetEquipeGagnante() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = {
				modeleEquipe.getParId(1).get(),
				modeleEquipe.getParId(2).get(),
		};
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
		
		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m", modeleArbitre.getTout());
		modeleTournoi.ajouter(tournoi);
		
		Rencontre rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(
				rencontre));		
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);
		
		for (int i=1;i<5;i++)
		modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		
		modeleUtilisateur.connecter("admin", "mdp");
		modeleTournoi.ouvrirTournoi(modeleTournoi.getParId(tournoi.getIdTournoi()).get());
		modeleUtilisateur.deconnecter();
		modeleUtilisateur.connecter("a", "m");
		modele.setEquipeGagnante(rencontre,equipes[0].getNom());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetEquipeGagnanteInnexistant() throws Exception {
		ModeleEquipe modeleEquipe = new ModeleEquipe();
		Equipe[] equipes = {
				modeleEquipe.getParId(1).get(),
				modeleEquipe.getParId(2).get(),
		};
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
		
		Tournoi tournoi = new Tournoi("tournoi", Notoriete.LOCAL, timestampDebut, timestampFin, "a", "m", modeleArbitre.getTout());
		modeleTournoi.ajouter(tournoi);
		
		Rencontre rencontre = new Rencontre(equipes);
		List<Rencontre> rencontres = new ArrayList<>(Arrays.asList(
				rencontre));		
		Poule poule = new Poule(0, false, false, tournoi.getIdTournoi(), rencontres);
		modelePoule.ajouter(poule);
		
		for (int i=1;i<5;i++)
		modeleEquipe.inscrireEquipe(modeleEquipe.getParId(i).get(), tournoi);
		
		modeleUtilisateur.connecter("admin", "mdp");
		modeleTournoi.ouvrirTournoi(modeleTournoi.getParId(tournoi.getIdTournoi()).get());
		modeleUtilisateur.deconnecter();
		modeleUtilisateur.connecter("a", "m");
		modele.setEquipeGagnante(rencontre,"fausseEquipe");
	}
	
	@After
    public void tearsDown() throws Exception {
		modeleUtilisateur.deconnecter();
		//Réinitialise les rencontres
        List<Integer> idREncontreAGarder = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        modele.getTout().stream()
                .filter(rencontre -> !idREncontreAGarder.contains(rencontre.getIdRencontre()))
                .forEach(rencontre -> {
                    try {
                        modele.supprimer(rencontre);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        });
        
        //Réinitialise les tournoi
        List<Integer> idTournoiAGarder = Arrays.asList(1, 2, 3, 4, 5, 6);
        modeleTournoi.getTout().stream()
                .filter(tournoi -> !idTournoiAGarder.contains(tournoi.getIdTournoi()))
                .forEach(tournoi -> {
                    try {
                    	modeleTournoi.supprimer(tournoi);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        });
        
        //Réinitialise les poules
        List<Integer> idPouleAGarder = Arrays.asList(1, 2);
        modelePoule.getTout().stream()
                .filter(poule -> !idPouleAGarder.contains(poule.getIdPoule()))
                .forEach(poule -> {
                    try {
                        modelePoule.supprimer(poule);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
        });
    }
}
