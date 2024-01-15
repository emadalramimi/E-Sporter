package modele.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleTournoi;
import modele.ModeleTournoiCloture;
import modele.ModeleTournoiOuverture;
import modele.ModeleUtilisateur;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAORencontre;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleTournoiCloture {
	
	private ModeleTournoiCloture modele;
	private ModeleTournoi modeleTournoi;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private ModeleUtilisateur modeleUtilisateur;
	
	private DAOTournoi daoTournoi;
	private DAOEquipe daoEquipe;
	private DAORencontre daoRencontre;
	
	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleTournoiCloture();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();
		this.modeleUtilisateur = new ModeleUtilisateur();
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.daoEquipe = new DAOEquipeImpl();
		
		Tournoi tournoiInit = new Tournoi("Tournoi", Notoriete.LOCAL,
				getDateCourante()+3600, getDateCourante()+6600,
				"test", "mdp", new ArrayList<>());
		daoTournoi.ajouter(tournoiInit);
		for (int i = 1; i < 5; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getParId(i).get(), tournoiInit);
		}
		modeleTournoiOuverture.ouvrirTournoi(daoTournoi.getParIdentifiant("test").get());
	}
	
	/*
	 * Renvoie la date courante en secondes
	 */
	private long getDateCourante() {
		return (System.currentTimeMillis() / 1000);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCloturerPouleMatchNonJoue() throws Exception {
		modele.cloturerPoule(daoTournoi.getParIdentifiant("test").get());
	}
	
	@Test
	public void testCloturerPouleQualification() throws Exception {
		modeleUtilisateur.connecter("arbitre", "mdp");
		for (Rencontre rencontre : daoTournoi.getParIdentifiant("test").get().getPouleActuelle().getRencontres()) {
			daoRencontre.setEquipeGagnante(rencontre, rencontre.getEquipes()[0].getNom());
		}
		modele.cloturerPoule(daoTournoi.getParIdentifiant("test").get());
	}
	
	@Test
	public void testCloturerPouleFinale() throws Exception {
		
		modeleUtilisateur.connecter("arbitre", "mdp");
		for (Rencontre rencontre : daoTournoi.getParIdentifiant("test").get().getPouleActuelle().getRencontres()) {
			daoRencontre.setEquipeGagnante(rencontre, rencontre.getEquipes()[0].getNom());
		}
		modele.cloturerPoule(daoTournoi.getParIdentifiant("test").get());
		
		Rencontre finale = daoTournoi.getParIdentifiant("test").get().getPouleActuelle().getRencontres().get(0);
		daoRencontre.setEquipeGagnante(finale, finale.getEquipes()[0].getNom());
		//modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}

	// Réinitialise les tournoi
		@After
		public void tearsDown() throws Exception {
			if (modeleUtilisateur.getCompteCourant() != null) {
				modeleUtilisateur.deconnecter();
			}
			
			List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
			for (Tournoi tournoi : this.daoTournoi.getTout()) {
				if (!idAGarder.contains(tournoi.getIdTournoi())) {
					this.daoTournoi.supprimer(tournoi);
				}
			}
		}
}