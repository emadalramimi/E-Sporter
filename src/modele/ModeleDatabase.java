package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ModeleDatabase {

	public static void main(String[] args) {
		// Mise en place de l'environnement
		String dirProjetJava = System.getProperty("user.dir");
		System.setProperty("derby.system.home", dirProjetJava + "/BDD");
		
		try {
			// Enregistrement du Driver
			DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
			
			// URLString
			String urlConnexion = "jdbc:derby:BDD;create=true";
			
			// Création d'une connexion
			Connection dbConnection = DriverManager.getConnection(urlConnexion);
			Statement st = dbConnection.createStatement();
			
			System.out.println("Connexion OK");
			
			ModeleDatabase.construireTables(st);
			//ModeleDatabase.insererDonnees(st);
			
			dbConnection.close();
			System.out.println("Connexion fermée");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void construireTables(Statement st) {
		try {
			st.executeUpdate("DROP TABLE jouer");
		} catch (SQLException e) {
			System.out.println("jouer n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE participer");
		} catch (SQLException e) {
			System.out.println("participer n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE arbitrer");
		} catch (SQLException e) {
			System.out.println("arbitrer n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE historiquePoints");
		} catch (SQLException e) {
			System.out.println("historiquePoints n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE rencontre");
		} catch (SQLException e) {
			System.out.println("rencontre n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE poule");
		} catch (SQLException e) {
			System.out.println("poule n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE participer");
		} catch (SQLException e) {
			System.out.println("participer n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE arbitrer");
		} catch (SQLException e) {
			System.out.println("arbitrer n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE administrateur");
		} catch (SQLException e) {
			System.out.println("administrateur n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE arbitre");
		} catch (SQLException e) {
			System.out.println("arbitre n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE joueur");
		} catch (SQLException e) {
			System.out.println("joueur n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE equipe");
		} catch (SQLException e) {
			System.out.println("equipe n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP TABLE tournoi");
		} catch (SQLException e) {
			System.out.println("tournoi n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idJoueur RESTRICT");
		} catch (SQLException e) {
			System.out.println("idJoueur n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idEquipe RESTRICT");
		} catch (SQLException e) {
			System.out.println("idEquipe n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idArbitre RESTRICT");
		} catch (SQLException e) {
			System.out.println("idArbitre n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idAdministrateur RESTRICT");
		} catch (SQLException e) {
			System.out.println("idAdministrateur n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idHistoriquePoints RESTRICT");
		} catch (SQLException e) {
			System.out.println("idHistoriquePoints n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idRencontre RESTRICT");
		} catch (SQLException e) {
			System.out.println("idRencontre n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idPoule RESTRICT");
		} catch (SQLException e) {
			System.out.println("idPoule n'existe pas");
		}
		
		try {
			st.executeUpdate("DROP SEQUENCE idTournoi RESTRICT");
		} catch (SQLException e) {
			System.out.println("idTournoi n'existe pas");
		}
		
		try {
		    st.executeUpdate("CREATE SEQUENCE idTournoi START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idTournoi SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE tournoi("
		            + "idTournoi INTEGER, "
		            + "nomTournoi VARCHAR(20) NOT NULL, "
		            + "notoriete VARCHAR(20), "
		            + "dateDebut DATE, "
		            + "dateFin DATE, "
		            + "estCloture BOOLEAN, "
		            + "identifiant VARCHAR(20), "
		            + "motDePasse VARCHAR(20), "
		            + "PRIMARY KEY(idTournoi)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table tournoi");
		    e.printStackTrace();
		}
		
		try {
		    st.executeUpdate("CREATE SEQUENCE idEquipe START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idEquipe SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE equipe("
		            + "idEquipe INTEGER, "
		            + "nom VARCHAR(20), "
		            + "pays VARCHAR(20), "
		            + "classement INT, "
		            + "worldRanking INT, "
		            + "saison VARCHAR(20), "
		            + "PRIMARY KEY(idEquipe)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table equipe");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE SEQUENCE idJoueur START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idJoueur SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE joueur("
		            + "idJoueur INTEGER, "
		            + "pseudo VARCHAR(20), "
		            + "idEquipe INT NOT NULL, "
		            + "PRIMARY KEY(idJoueur), "
		            + "FOREIGN KEY(idEquipe) REFERENCES equipe(idEquipe)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table joueur");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE SEQUENCE idArbitre START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idArbitre SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE arbitre("
		            + "idArbitre INTEGER, "
		            + "nom VARCHAR(20), "
		            + "prenom VARCHAR(20), "
		            + "PRIMARY KEY(idArbitre)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table arbitre");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE SEQUENCE idAdministrateur START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idAdministrateur SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE administrateur("
		            + "idAdministrateur INTEGER, "
		            + "nomUtilisateur VARCHAR(20), "
		            + "identifiant VARCHAR(20), "
		            + "motDePasse VARCHAR(20), "
		            + "PRIMARY KEY(idAdministrateur)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table administrateur");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE SEQUENCE idHistoriquePoints START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idHistoriquePoints SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE historiquePoints("
		            + "idHistoriquePoints INTEGER, "
		            + "points INT, "
		            + "idTournoi INT NOT NULL, "
		            + "idEquipe INT NOT NULL, "
		            + "PRIMARY KEY(idHistoriquePoints), "
		            + "FOREIGN KEY(idTournoi) REFERENCES tournoi(idTournoi), "
		            + "FOREIGN KEY(idEquipe) REFERENCES equipe(idEquipe)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table historiquePoints");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE SEQUENCE idPoule START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idPoule SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE poule("
		            + "idPoule INTEGER, "
		            + "estCloturee BOOLEAN, "
		            + "estFinale BOOLEAN, "
		            + "idTournoi INT NOT NULL, "
		            + "PRIMARY KEY(idPoule), "
		            + "FOREIGN KEY(idTournoi) REFERENCES tournoi(idTournoi)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table poule");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE SEQUENCE idRencontre START WITH 1 INCREMENT BY 1");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de idRencontre SEQUENCE");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE rencontre("
		            + "idRencontre INTEGER, "
		            + "nom VARCHAR(20), "
		            + "dateHeureDebut TIMESTAMP, "
		            + "dateHeureFin TIMESTAMP, "
		            + "idPoule INT NOT NULL, "
		            + "idEquipe INT, "
		            + "PRIMARY KEY(idRencontre), "
		            + "FOREIGN KEY(idPoule) REFERENCES poule(idPoule), "
		            + "FOREIGN KEY(idEquipe) REFERENCES equipe(idEquipe)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table rencontre");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE participer("
		            + "idTournoi INT, "
		            + "idEquipe INT, "
		            + "classement INT, "
		            + "PRIMARY KEY(idTournoi, idEquipe), "
		            + "FOREIGN KEY(idTournoi) REFERENCES tournoi(idTournoi), "
		            + "FOREIGN KEY(idEquipe) REFERENCES equipe(idEquipe)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table participer");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE arbitrer("
		            + "idTournoi INT, "
		            + "idArbitre INT, "
		            + "PRIMARY KEY(idTournoi, idArbitre), "
		            + "FOREIGN KEY(idTournoi) REFERENCES tournoi(idTournoi), "
		            + "FOREIGN KEY(idArbitre) REFERENCES arbitre(idArbitre)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table arbitrer");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE jouer("
		            + "idEquipe INT, "
		            + "idRencontre INT, "
		            + "PRIMARY KEY(idEquipe, idRencontre), "
		            + "FOREIGN KEY(idEquipe) REFERENCES equipe(idEquipe), "
		            + "FOREIGN KEY(idRencontre) REFERENCES rencontre(idRencontre)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table jouer");
		    e.printStackTrace();
		}
	}
	
	public static void insererDonnees(Statement st) {
		// TODO
	}

}
