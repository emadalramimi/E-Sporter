package modele.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import modele.ModeleEquipe;
import modele.DAO.DAOEquipe;
import modele.DAO.DAOEquipeImpl;
import modele.DAO.DAOTournoi;
import modele.DAO.DAOTournoiImpl;
import modele.exception.InscriptionEquipeTournoiException;
import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Tournoi;
import modele.metier.Tournoi.Notoriete;

public class TestModeleEquipe {
	
	private ModeleEquipe modele;
	private DAOEquipe daoEquipe;
	private DAOTournoi daoTournoi;

	@Before
	public void setUp() {
		this.modele = new ModeleEquipe();
		this.daoEquipe = new DAOEquipeImpl();
		this.daoTournoi = new DAOTournoiImpl();
	}
	
	/**
	 * Renvoie la date courante en secondes
	 */
	private long getDateCourante() {
		return (System.currentTimeMillis() / 1000);
	}
	
	@Test
	public void testModifier() throws Exception {
		Equipe equipeInit = daoEquipe.getParId(1).get();
		List<String> nomsEquipe = new ArrayList<>();
		for (Joueur joueur:equipeInit.getJoueurs()) {
			nomsEquipe.add(joueur.getPseudo());
		}
		Equipe equipeTest = new Equipe(1, "Autre", Pays.FRANCE, 3, 414, "2024", equipeInit.getJoueurs());
		
		modele.modifier(daoEquipe.getParId(1).get(), "Autre", Pays.FRANCE, nomsEquipe);
		assertEquals(daoEquipe.getParId(1).get(), equipeTest);
		
		modele.modifier(daoEquipe.getParId(1).get(), "CFO Academy", Pays.TAIWAN, nomsEquipe);
	}
	
	@Test (expected = InscriptionEquipeTournoiException.class)
	public void testModifierEquipeInscrite() throws Exception {
		Tournoi tournoi = new Tournoi("Test", Notoriete.LOCAL,
				this.getDateCourante()+3600, this.getDateCourante()+6600,
				"Arbitre", "mdp", new ArrayList<>());
		daoTournoi.ajouter(tournoi);
		daoEquipe.inscrireEquipe(daoEquipe.getParId(1).get(), tournoi);
		
		Equipe equipeInit = daoEquipe.getParId(1).get();
		List<String> nomsEquipe = new ArrayList<>();
		for (Joueur joueur:equipeInit.getJoueurs()) {
			nomsEquipe.add(joueur.getPseudo());
		}
		daoTournoi.ouvrirTournoi(tournoi);
		modele.modifier(daoEquipe.getParId(1).get(), "Nom", Pays.AFRIQUE_DU_SUD, nomsEquipe);
	}
	
	@Test
	public void testCreerJoueurs() throws Exception {
		Equipe equipeInit = daoEquipe.getParId(1).get();
		List<String> nomsEquipe = new ArrayList<>();
		for (Joueur joueur : equipeInit.getJoueurs()) {
			nomsEquipe.add(joueur.getPseudo());
		}
		for (int i=1; i<6;i++) {
			assertEquals(modele.creerJoueurs(nomsEquipe).get(i-1).getPseudo(),daoEquipe.getParId(1).get().getJoueurs().get(i-1).getPseudo());
		}
	}
	
	@Test
	public void testGetParNom() throws Exception {
		assertEquals(daoEquipe.getParId(1).get(), modele.getParNom("CFO Academy").get(0));
	}
	
	// Réinitialise les tournoi
		@After
		public void tearsDown() throws Exception {
			List<Integer> idAGarder = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
			for (Tournoi tournoi : this.daoTournoi.getTout()) {
				if (!idAGarder.contains(tournoi.getIdTournoi())) {
					this.daoTournoi.supprimer(tournoi);
				}
			}
		}
}
