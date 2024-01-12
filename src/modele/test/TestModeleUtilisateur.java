package modele.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleUtilisateur;

public class TestModeleUtilisateur {

	private ModeleUtilisateur modeleUtilisateur;

	@Before
	public void setUp() {
		this.modeleUtilisateur = new ModeleUtilisateur();
		if (ModeleUtilisateur.getCompteCourant() != null)
			this.modeleUtilisateur.deconnecter();
	}

	@Test
	public void testChiffrerMotDePasse() {
		String motDePasse = "mdp";
		assertNotEquals(ModeleUtilisateur.chiffrerMotDePasse(motDePasse), motDePasse);
	}

	/*
	 * Test l'erreur IllegalArgumentException lorsqu'on essaie de se connecter si
	 * l'on l'est déjà
	 */
	@Test(expected = IllegalStateException.class)
	public void testConnexionDejaConnecte() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleUtilisateur.connecter("admin", "mdp");
	}

	/*
	 * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se
	 * connecter et que l'identifiant est invalide
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConnexionIdentifiantInvalide() throws Exception {
		this.modeleUtilisateur.connecter("fauxadmin", "mdp");
	}

	/*
	 * Test l'erreur IdentifiantOuMdpIncorrectsException lorsqu'on essaie de se
	 * connecter et que le mot de passe est invalide
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConnexionMotDePasseInvalide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "*admin");
	}

	/*
	 * Test si la connexion fonctionne lorsqu'on rentre un identifiant et mot de
	 * passe valide
	 */
	@Test
	public void testConnexionValide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		assertTrue(this.modeleUtilisateur != null);
	}

	/*
	 * Test si le compte courant de l'admin est bien null lorsqu'il est déconnecté
	 */
	@Test
	public void testDeconnexionValide() throws Exception {
		this.modeleUtilisateur.connecter("admin", "mdp");
		this.modeleUtilisateur.deconnecter();
		assertTrue(ModeleUtilisateur.getCompteCourant() == null);
	}

	/*
	 * Test l'erreur IllegalArgumentException lorsqu'on essaie de se déconnecter
	 * alors que l'on n'était pas connecté
	 */
	@Test(expected = IllegalStateException.class)
	public void testDeconnexionInvalide() throws IllegalStateException {
		this.modeleUtilisateur.deconnecter();
	}

	/*
	 * Test du getter getCompteCourant
	 */
	@Test
	public void testGetCompteCourant() {
		assertEquals(ModeleUtilisateur.getCompteCourant(), null);
	}
}
