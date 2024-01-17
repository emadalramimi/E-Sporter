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
import modele.DAO.DAOPoule;
import modele.DAO.DAOPouleImpl;
import modele.DAO.DAORencontre;
import modele.DAO.DAORencontreImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.metier.Poule;
import modele.metier.Rencontre;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleTournoiCloture {
	
	private ModeleTournoiCloture modele;
	private ModeleTournoiOuverture modeleTournoiOuverture;
	private ModeleUtilisateur modeleUtilisateur;
	private ModeleTournoi modeleTournoi;
	
	private DAOTournoi daoTournoi;
	private DAOEquipe daoEquipe;
	private DAORencontre daoRencontre;
	
	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleTournoiCloture();
		this.modeleTournoiOuverture = new ModeleTournoiOuverture();
		this.modeleUtilisateur = new ModeleUtilisateur();
		this.modeleTournoi = new ModeleTournoi();
		
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
		this.daoRencontre = new DAORencontreImpl();
		
		Tournoi tournoiInit = new Tournoi("Tournoi", Notoriete.LOCAL,
				this.getDateCourante()+3600, getDateCourante()+6600,
				"arbitre", "mdp", new ArrayList<>());
		daoTournoi.ajouter(tournoiInit);
		for (int i = 1; i < 5; i++) {
			daoEquipe.inscrireEquipe(daoEquipe.getParId(i).get(), tournoiInit);
		}
		modeleTournoiOuverture.ouvrirTournoi(modeleTournoi.getParNom("Tournoi").get(0));
	}
	
	/*
	 * Renvoie la date courante en secondes
	 */
	private long getDateCourante() {
		return (System.currentTimeMillis() / 1000);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testCloturerPouleMatchNonJoue() throws Exception {
		modeleUtilisateur.connecter("arbitre", "mdp");
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCloturerPouleNonArbitre() throws Exception {
		modeleUtilisateur.connecter("admin", "mdp");
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}

	@Test
	public void testCloturerPouleQualification() throws Exception {
		modeleUtilisateur.connecter("arbitre", "mdp");
		for (Rencontre rencontre : modeleTournoi.getParNom("Tournoi").get(0).getPouleActuelle().getRencontres()) {
			daoRencontre.setEquipeGagnante(rencontre, rencontre.getEquipes()[0].getNom());
		}
		modele.cloturerPoule(modeleTournoi.getParNom("Tournoi").get(0));
	}

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