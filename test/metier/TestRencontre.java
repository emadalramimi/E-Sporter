package metier;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modele.metier.Equipe;
import modele.metier.Joueur;
import modele.metier.Pays;
import modele.metier.Rencontre;

/**
 * Classe de tests de la classe Rencontre.
 * @see modele.metier.Rencontre
 */
public class TestRencontre {

	private Rencontre rencontre;
	private Rencontre autreConstructeur;
	private List<Joueur> joueurs;

	@Before
	public void setUp() {
		this.joueurs = new ArrayList<>(Arrays.asList(new Joueur(1, "Joueur1", 2), new Joueur(2, "Joueur2", 2),
				new Joueur(3, "Joueur3", 2), new Joueur(4, "Joueur4", 2), new Joueur(5, "Joueur5", 2)));

		Equipe equipeA = new Equipe(50, "Equipe A", Pays.FRANCE, 5, 5, "2020", this.joueurs);
		Equipe equipeB = new Equipe(51, "Equipe B", Pays.MAROC, 5, 5, "2020", this.joueurs);
		Equipe[] equipes = { equipeA, equipeB };

		this.rencontre = new Rencontre(50, 50, 50, equipes);
		this.autreConstructeur = new Rencontre(equipes);
	}

	@Test
	public void testGetIdRencontre() {
		assertEquals(this.rencontre.getIdRencontre(), 50);
	}

	@Test
	public void testSetIdRencontre() {
		this.rencontre.setIdRencontre(52);
		assertEquals(this.rencontre.getIdRencontre(), 52);
	}

	@Test
	public void testGetIdPoule() {
		assertEquals(this.rencontre.getIdPoule(), 50);
	}

	@Test
	public void testSetIdPoule() {
		this.rencontre.setIdPoule(51);
		assertEquals(this.rencontre.getIdPoule(), 51);
	}

	@Test
	public void testGetEquipes() {
		Equipe equipeA = new Equipe(50, "Equipe A", Pays.FRANCE, 5, 5, "2020", this.joueurs);
		Equipe equipeB = new Equipe(51, "Equipe B", Pays.MAROC, 5, 5, "2020", this.joueurs);
		Equipe[] equipes2 = { equipeA, equipeB };
		assertArrayEquals(this.rencontre.getEquipes(), equipes2);
		assertArrayEquals(this.autreConstructeur.getEquipes(), equipes2);
	}

	@Test
	public void testSetEquipes() {
		Equipe equipeC = new Equipe(3, "Equipe1", Pays.CANADA, 2, 2, "Saison 2023", this.joueurs);
		Equipe equipeD = new Equipe(4, "Equipe1", Pays.CANADA, 2, 2, "Saison 2023", this.joueurs);
		Equipe[] equipes2 = { equipeC, equipeD };
		this.rencontre.setEquipes(equipes2);
		this.autreConstructeur.setEquipes(equipes2);
		assertArrayEquals(this.rencontre.getEquipes(), equipes2);
		assertArrayEquals(this.autreConstructeur.getEquipes(), equipes2);
	}

	@Test
	public void testGetIdEquipeGagnante() {
		assertEquals(this.rencontre.getIdEquipeGagnante(), 50);
	}

	@Test
	public void testSetIdEquipeGagnante() {
		this.rencontre.setIdEquipeGagnante(51);
		assertEquals(this.rencontre.getIdEquipeGagnante(), 51);
	}
}
