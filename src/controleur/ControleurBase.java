package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import modele.ModeleUtilisateur;
import modele.DAO.BDD;
import modele.metier.Administrateur;
import modele.metier.Tournoi;
import modele.metier.Utilisateur;
import vue.VueBase;
import vue.VueConnexion;
import vue.theme.JButtonMenu;

/**
 * Contrôleur de la vue de base
 * @see VueBase
 */
public class ControleurBase extends MouseAdapter {

	/**
	 * Enumérations des menus disponibles
	 */
	public enum Menus {
		TOURNOIS(true), EQUIPES(true), HISTORIQUE(true), PALMARES(true), UTILISATEUR(false), DECONNEXION(false);
		
		private boolean estActivable;
		
		/**
		 * Un menu peut être activable (entraînera une nouvelle fenêtre au clic et passera en activé) ou non (exemple : ouverture d'une popup)
		 * @param estActivable : état d'activabilité d'un menu
		 */
		Menus(boolean estActivable) {
			this.estActivable = estActivable;
		}
		
		/**
		 * Retourne true si un menu est activable, false sinon
		 * @return True si un menu est activable, false sinon
		 */
		public boolean getEstActivable() {
			return this.estActivable;
		}
	}
	
	private List<JButtonMenu> boutonsMenu;
	private JButtonMenu boutonActif;
	private VueBase vue;
	private ModeleUtilisateur modeleUtilisateur;
	private String nomUtilisateur;
	private String nomUtilisateurTooltip;
	
	/**
	 * Constructeur du controleur
	 * @param vue : vueBase
	 */
	public ControleurBase(VueBase vue) {
		this.boutonsMenu = new LinkedList<>();
		this.boutonActif = null;
		this.vue = vue;
		this.modeleUtilisateur = new ModeleUtilisateur();

		Utilisateur compteCourant = ModeleUtilisateur.getCompteCourant();
		if(compteCourant.getRole() == Utilisateur.Role.ADMINISTRATEUR) {
			Administrateur administrateurCourant = (Administrateur) compteCourant;
			this.nomUtilisateur = administrateurCourant.getPrenom();
			this.nomUtilisateurTooltip = administrateurCourant.getPrenom() + " " + administrateurCourant.getNom();
		} else if(compteCourant.getRole() == Utilisateur.Role.ARBITRE) {
			this.nomUtilisateur = "Arbitre";
			this.nomUtilisateurTooltip = ((Tournoi) compteCourant).getNomTournoi();
		}
	}

	/**
	 * Ajoute un bouton au menu
	 * @param bouton : bouton à ajouter
	 */
	public void ajouterBoutonMenu(JButtonMenu bouton) {
		this.boutonsMenu.add(bouton);
	}
	
	/**
	 * Retourne la liste des boutons du menu
	 * @return Liste des boutons du menu
	 */
	public JButtonMenu getBoutonActif() {
		return this.boutonActif;
	}
	
	/**
	 * Modifie l'unique bouton activé (hors survol) du menu
	 * @param boutonActif Bouton actif
	 */
	public void setBoutonActif(JButtonMenu boutonActif) {
		for(JButtonMenu bouton : boutonsMenu) {
	    	bouton.activerIconeBouton(false);
		}
		this.boutonActif = boutonActif;
		boutonActif.activerIconeBouton(true);
	}

	/**
	 * Ferme la connexion à la base de données
	 */
	public void fermerConnexionBDD() {
		BDD.fermerConnexion();
	}

	/**
	 * Retourne le nom d'utilisateur actuel
	 * @return Nom d'utilisateur actuel
	 */
	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	/**
	 * Retourne le nom d'utilisateur actuel pour tooltip
	 * @return Nom d'utilisateur actuel pour tooltip
	 */
	public String getNomUtilisateurTooltip() {
		return this.nomUtilisateurTooltip;
	}

	/**
	 * Si un menu non activé est cliqué, il passe en actif (les autres sont désactivés) puis son traitement est exécuté
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		JButtonMenu boutonClique = (JButtonMenu) e.getSource();
		if(boutonClique != this.boutonActif) {
			if(boutonClique.getMenu().getEstActivable()) {
				// Changement d'onglet
				this.setBoutonActif(boutonClique);
				this.vue.fermerFenetresEnfant();
				this.vue.changerOnglet(boutonClique.getMenu());
			} else if (boutonClique.getMenu() == Menus.DECONNEXION) {
				// Quand on clique sur se déconnecter
				this.modeleUtilisateur.deconnecter();
				VueConnexion vueConnexion = new VueConnexion();
				vueConnexion.afficher();
				this.vue.fermerFenetre();
			}
		}
	}

	/**
	 * Mise en activité (survol) d'un menu
	 */
	@Override
    public void mouseEntered(MouseEvent e) {
		JButtonMenu bouton = (JButtonMenu) e.getSource();
        if (bouton.getIcon() != null && bouton != this.getBoutonActif()) {
        	bouton.activerIconeBouton(true);
        }
    }
	
	/**
	 * Mise hors activité (survol) d'un menu
	 */
    @Override
    public void mouseExited(MouseEvent e) {
    	JButtonMenu bouton = (JButtonMenu) e.getSource();
        if (bouton.getIcon() != null && bouton != this.getBoutonActif()) {
        	bouton.activerIconeBouton(false);
        }
    }
	
}
