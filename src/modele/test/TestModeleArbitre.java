package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleArbitre;
import modele.ModeleTournoi;
import modele.metier.Arbitre;
import modele.metier.Tournoi;

public class TestModeleArbitre {
	private ModeleArbitre modele;
	private ModeleTournoi modeleTournoi;
	private Optional<Tournoi> tournoi;

	@Before
	public void setUp() throws Exception {
		this.modele = new ModeleArbitre();
		this.modeleTournoi = new ModeleTournoi();
		this.tournoi = this.modeleTournoi.getParId(1);
	}

	@Test
	public void testGetTout() throws Exception {
		List<Arbitre> resultat = this.modele.getTout();
		assertNotNull(resultat);
		assertFalse(resultat.isEmpty());
		assertNotNull(this.modele.getTout());
		Arbitre arbitreBDD = this.modele.getParId(1).orElse(null);
		assertNotNull(arbitreBDD);
		assertEquals(arbitreBDD, resultat.get(0));
	}

	@Test
	public void testGetParId() throws Exception {
		Arbitre arbitreDBB = this.modele.getParId(1).orElse(null);
		assertNotNull(arbitreDBB);
		Optional<Arbitre> resultat = this.modele.getParId(arbitreDBB.getIdArbitre());
		assertTrue(resultat.isPresent());
		assertNotNull(this.modele.getParId(1).orElse(null));
		assertEquals(arbitreDBB, resultat.get());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAjouter() throws Exception {
		this.modele.ajouter(new Arbitre(5, "Willem", "Leboss"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testModifier() throws Exception {
		this.modele.modifier(new Arbitre(1, "Willem", "Leboss"));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSupprimer() throws Exception {
		this.modele.supprimer(this.modele.getParId(1).orElse(null));
	}

	@Test
	public void testGetArbitresTournoi() throws Exception {
		List<Arbitre> resultat = this.modele.getArbitresTournoi(1);
		assertNotNull(resultat);
		assertFalse(resultat.isEmpty());
		assertEquals(resultat, this.tournoi.get().getArbitres());
	}

	@Test
	public void testGetTableauArbitres() throws Exception {
		List<Arbitre> arbitresNonEligibles = this.tournoi.get().getArbitres();
		Arbitre[] resultat = this.modele.getTableauArbitres(arbitresNonEligibles);
		for (Arbitre arbitre : arbitresNonEligibles) {
			assertFalse(Arrays.asList(resultat).contains(arbitre));
		}

		assertNotNull(resultat);
	}
}
