package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BDD {
	
	private static Connection connection;
	
	public static synchronized Connection getConnexion() {
		if(connection == null) {
			try {
				// Mise en place de l'environnement
				String dirProjetJava = System.getProperty("user.dir");
				System.setProperty("derby.system.home", dirProjetJava + "/BDD");
				
				// Enregistrement du Driver
				DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
				
				// URLString
				String urlConnexion = "jdbc:derby:BDD;create=true";
				
				// Création d'une connexion
				connection = DriverManager.getConnection(urlConnexion);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public static void main(String[] args) {
		try {
			Statement st = BDD.getConnexion().createStatement();
			
			System.out.println("Connexion OK");
			
			BDD bdd = new BDD();
			bdd.construireTables(st);
			bdd.insererDonnees(st);
			System.out.println("Connexion fermée");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void construireTables(Statement st) {
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
		            + "nomTournoi VARCHAR(60) NOT NULL, "
		            + "notoriete VARCHAR(20), "
		            + "dateDebut INT, "
		            + "dateFin INT, "
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
		            + "nom VARCHAR(20), "
		            + "prenom VARCHAR(20), "
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
		            + "dateHeureDebut INT, "
		            + "dateHeureFin INT, "
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
						
	
	public void insererDonnees(Statement st) {
		try {
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'PCL 2023',"
					+ "'international',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'Pcl2023',"
					+ "'$Pcl2023'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'Asia Star Challengers Invitational 2023',"
					+ "'international',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'AsiaStar',"
					+ "'$AsiaStar'"
					+ ")");
			
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'ULTRALIGA SEASON 10 - PROMOTION - PLAYOFFS',"
					+ "'national',"
					+ "1675551600,"
					+ "1677193200,"
					+ "TRUE,"
					+ "'Ultraliga',"
					+ "'$Ultraliga'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'LVP SUPERLIGA 2023 SUMMER - PROMOTION - PLAYOFFS',"
					+ "'régional',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'Superliga',"
					+ "'$Superliga'"
					+ ")");
					
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'LCD 2023 AUTUMN',"
					+ "'international',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'Lcd2023Autumn',"
					+ "'$Lcd2023Autumn'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'ELITE SERIES 2023 SUMMER - 2024 PROMOTION - PLAYOFFS',"
					+ "'régional',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'EliteSeries',"
					+ "'$EliteSeries'"
					+ ")");
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table tournoi");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'CFO Academy',"
					+ "'Taiwan',"
					+ "3,"
					+ "414,"
					+ "'Saison 2023'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'DCG Academy',"
					+ "'Taiwan',"
					+ "1,"
					+ "362,"
					+ "'Saison 2023'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'Taipei Bravo',"
					+ "'Taiwan',"
					+ "4,"
					+ "275,"
					+ "'Saison 2023'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'PSG Academy',"
					+ "'Taiwan',"
					+ "2,"
					+ "237,"
					+ "'Saison 2023'"
					+ ")");
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table equipe");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'KokerHan',"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Hasu7',"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Hitchy',"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Billy',"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Hermes',"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'GuanGuan',"
					+ "2"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'1SSUE',"
					+ "2"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'HotPot',"
					+ "2"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Kanashim1',"
					+ "2"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Cheng9',"
					+ "2"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'pinwei',"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Mob',"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'ZhifeiG',"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Yuuto',"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Cause',"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Wen',"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'TNS',"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Chich1',"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'mikey',"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Adizai',"
					+ "4"
					+ ")");
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table joueur");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO arbitre VALUES ("
					+ "NEXT VALUE FOR idArbitre,"
					+ "'Fazbear',"
					+ "'Freddy'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO arbitre VALUES ("
					+ "NEXT VALUE FOR idArbitre,"
					+ "'Krugger',"
					+ "'Freddy'"
					+ ")");
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table arbitre");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO administrateur VALUES ("
					+ "NEXT VALUE FOR idAdministrateur,"
					+ "'Admin',"
					+ "'Istrateur',"
					+ "'admin',"
					+ "'mdp')"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table administrateur");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "50,"
					+ "1,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "120,"
					+ "2,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "150,"
					+ "3,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "70,"
					+ "4,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "180,"
					+ "5,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "130,"
					+ "4,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "150,"
					+ "1,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "70,"
					+ "3,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "100,"
					+ "5,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "70,"
					+ "1,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "110,"
					+ "4,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "130,"
					+ "3,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "70,"
					+ "6,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "120,"
					+ "1,"
					+ "4)"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "FALSE,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "TRUE,"
					+ "1"
					+ ")"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table poule");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692764100,"
					+ "1692768720,"
					+ "1,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692768720,"
					+ "1692772320,"
					+ "1,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692772320,"
					+ "1692775920,"
					+ "1,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692775920,"
					+ "1692779520,"
					+ "1,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692779520,"
					+ "1692786720,"
					+ "1,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692786720,"
					+ "1692790320,"
					+ "1,"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1692790320,"
					+ "1692797520,"
					+ "1,"
					+ "1"
					+ ")");
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table rencontre");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "1,1,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "1,"
					+ "2,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "1,"
					+ "3,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "1,"
					+ "4,"
					+ "2)"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table participer");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "1,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "1,"
					+ "2)"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table arbitrer");
		    e.printStackTrace();
		}
		
		try {
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "5)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "5)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "6)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "6)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "7)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "7)"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer");
		    e.printStackTrace();
		}
	}
}