package modele.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import modele.ModeleUtilisateur;

/**
 * Class de gestion de la base de données
 */
public class BDD {
	
	private static Connection connection;
	
	/**
	 * Singleton de connexion à la base de données
	 * @return la connexion à la base de données
	 */
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
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	
	/**
	 * Ferme la connexion en cours (à la fermeture de la fenêtre)
	 */
	public static void fermerConnexion() {
	    if (connection != null) {
	        try {
	            if (!connection.isClosed()) {
	                connection.commit();
	                connection.close();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        connection = null;
	    }
	}

	/**
	 * Point d'entrée de BDD (pour remettre à zéro la base de données et insérer les données d'exemple)
	 * @param args Arguments
	 */
	public static void main(String[] args) {
		try {
			Statement st = BDD.getConnexion().createStatement();
			
			System.out.println("Connexion OK");

			// Construit la BDD
			BDD bdd = new BDD();
			bdd.construireTables(st);
			bdd.insererDonnees(st);
			
			try {
				BDD.getConnexion().commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			System.out.println("Création BDD OK");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remet à zéro la base de données puis construit les tables
	 * @param st
	 */
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
		            + "motDePasse VARCHAR(72), "
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
		            + "motDePasse VARCHAR(72), "
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
		            + "idPoule INT NOT NULL, "
		            + "idEquipeGagnante INT, "
		            + "PRIMARY KEY(idRencontre), "
		            + "FOREIGN KEY(idPoule) REFERENCES poule(idPoule), "
		            + "FOREIGN KEY(idEquipeGagnante) REFERENCES equipe(idEquipe)"
		            + ")");
		} catch (SQLException e) {
		    System.err.println("Erreur de création de la table rencontre");
		    e.printStackTrace();
		}

		try {
		    st.executeUpdate("CREATE TABLE participer("
		            + "idTournoi INT, "
		            + "idEquipe INT, "
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
						
	/**
	 * Insère les données d'exemple dans la base de données (environnement de développement)
	 * @param st Statement BDD
	 */
	public void insererDonnees(Statement st) {
		try {
			//Premier tournoi
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'PCL 2023',"
					+ "'international',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'Pcl2023',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("$Pcl2023") + "'"
					+ ")");
			//Deuxième tournoi
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'Asia Star Challengers Invitational 2023',"
					+ "'international',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'AsiaStar',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("$AsiaStar") + "'"
					+ ")");
			//Troisième tournoi
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'ULTRALIGA SEASON 10 - PROMOTION - PLAYOFFS',"
					+ "'national',"
					+ "1675551600,"
					+ "1677193200,"
					+ "TRUE,"
					+ "'Ultraliga',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("$Ultraliga") + "'"
					+ ")");
			//Quatrième tournoi
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'LVP SUPERLIGA 2023 SUMMER - PROMOTION - PLAYOFFS',"
					+ "'régional',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'Superliga',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("$Superliga") + "'"
					+ ")");
			//Cinquième tournoi
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'LCD 2023 AUTUMN',"
					+ "'international',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'Lcd2023Autumn',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("$Lcd2023Autumn") + "'"
					+ ")");
			//Sixième tournoi
			st.executeUpdate(
					"INSERT INTO tournoi VALUES ("
					+ "NEXT VALUE FOR idTournoi,"
					+ "'ELITE SERIES 2023 SUMMER - 2024 PROMOTION - PLAYOFFS',"
					+ "'régional',"
					+ "1673478000,"
					+ "1675292400,"
					+ "TRUE,"
					+ "'EliteSeries',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("$EliteSeries") + "'"
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
					+ "'Taïwan',"
					+ "3,"
					+ "414,"
					+ "'2024'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'DCG Academy',"
					+ "'Taïwan',"
					+ "1,"
					+ "362,"
					+ "'2024'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'Taipei Bravo',"
					+ "'Taïwan',"
					+ "4,"
					+ "275,"
					+ "'2024'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'PSG Academy',"
					+ "'Taïwan',"
					+ "2,"
					+ "237,"
					+ "'2024'"
					+ ")");
			st.executeUpdate(
					"INSERT INTO equipe VALUES ("
					+ "NEXT VALUE FOR idEquipe,"
					+ "'OM Academy',"
					+ "'France',"
					+ "5,"
					+ "632,"
					+ "'2023'"
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
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Zidane',"
					+ "5"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'SCH',"
					+ "5"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Asus',"
					+ "5"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Jul',"
					+ "5"
					+ ")");
			st.executeUpdate(
					"INSERT INTO joueur VALUES ("
					+ "NEXT VALUE FOR idJoueur,"
					+ "'Liam',"
					+ "5"
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
					+ "'Istrateur',"
					+ "'Admin',"
					+ "'admin',"
					+ "'" + ModeleUtilisateur.chiffrerMotDePasse("mdp") + "')"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table administrateur");
		    e.printStackTrace();
		}
		
		//Historique premier tournoi
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2700,"
					+ "1,"
					+ "1)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2025,"
					+ "1,"
					+ "4)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1305,"
					+ "1,"
					+ "3)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1046.25,"
					+ "1,"
					+ "2)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints du premier tournoi");
		    e.printStackTrace();
		}


		//Historique deuxième tournoi
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1800,"
					+ "2,"
					+ "1)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1350,"
					+ "2,"
					+ "4)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "870,"
					+ "2,"
					+ "3)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "697.5,"
					+ "2,"
					+ "2)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints du deuxième tournoi");
		    e.printStackTrace();
		}

		//Historique troisième tournoi
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2700,"
					+ "3,"
					+ "1)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2025,"
					+ "3,"
					+ "4)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1305,"
					+ "3,"
					+ "3)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1046.25,"
					+ "3,"
					+ "2)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints du troisième tournoi");
		    e.printStackTrace();
		}

		//Historique quatrième tournoi
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1800,"
					+ "4,"
					+ "1)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1350,"
					+ "4,"
					+ "4)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "870,"
					+ "4,"
					+ "3)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "697.5,"
					+ "4,"
					+ "2)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints du quatrième tournoi");
		    e.printStackTrace();
		}

		//Historique cinquième tournoi
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2700,"
					+ "5,"
					+ "1)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2025,"
					+ "5,"
					+ "4)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1305,"
					+ "5,"
					+ "3)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1046.25,"
					+ "5,"
					+ "2)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints du cinquième tournoi");
		    e.printStackTrace();
		}

		//Historique sixième tournoi
		try {
			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "2400,"
					+ "6,"
					+ "1)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1800,"
					+ "6,"
					+ "4)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "1160,"
					+ "6,"
					+ "3)"
			);

			st.executeUpdate(
					"INSERT INTO historiquePoints VALUES ("
					+ "NEXT VALUE FOR idHistoriquePoints,"
					+ "930,"
					+ "6,"
					+ "2)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table historiquePoints du sixième tournoi");
		    e.printStackTrace();
		}
		
		try {
			//Premier tournoi
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
			
			//Deuzième tournoi
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "FALSE,"
					+ "2"
					+ ")");
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "TRUE,"
					+ "2"
					+ ")"
			);
			
			//Troisième tournoi
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "FALSE,"
					+ "3"
					+ ")");
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "TRUE,"
					+ "3"
					+ ")"
			);
			
			//Quatrième tournoi
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "FALSE,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "TRUE,"
					+ "4"
					+ ")"
			);
			
			//Cinquième tournoi
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "FALSE,"
					+ "5"
					+ ")");
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "TRUE,"
					+ "5"
					+ ")"
			);
			
			//Sixième tournoi
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "FALSE,"
					+ "6"
					+ ")");
			st.executeUpdate(
					"INSERT INTO poule VALUES ("
					+ "NEXT VALUE FOR idPoule,"
					+ "TRUE,"
					+ "TRUE,"
					+ "6"
					+ ")"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table poule");
		    e.printStackTrace();
		}
		
		try {
			//Premier tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "1,"
					+ "3"
					+ ")");
			//Finale premier tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "2,"
					+ "1"
					+ ")");
			
			//Deuxième tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "3,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "3,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "3,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "3,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "3,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "3,"
					+ "3"
					+ ")");
			//Finale deuxieme tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "4,"
					+ "1"
					+ ")");
			
			//Troisieme tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "5,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "5,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "5,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "5,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "5,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "5,"
					+ "3"
					+ ")");
			//Finale troisieme tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "6,"
					+ "1"
					+ ")");
			
			//Quatrieme tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "7,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "7,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "7,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "7,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "7,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "7,"
					+ "3"
					+ ")");
			//Finale quatrieme tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "8,"
					+ "1"
					+ ")");

			//Cinquième tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "9,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "9,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "9,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "9,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "9,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "9,"
					+ "3"
					+ ")");
			//Finale cinquième tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "10,"
					+ "1"
					+ ")");
			
			//Sixième tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "11,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "11,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "11,"
					+ "4"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "11,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "11,"
					+ "1"
					+ ")");
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "11,"
					+ "3"
					+ ")");
			//Finale sixième tournoi
			st.executeUpdate(
					"INSERT INTO rencontre VALUES ("
					+ "NEXT VALUE FOR idRencontre,"
					+ "12,"
					+ "1"
					+ ")");
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table rencontre");
		    e.printStackTrace();
		}
		
		try {
			//Premier tournoi
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "1,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "1,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "1,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "1,"
					+ "4)"
			);

			//Deuxième tournoi
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "2,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "2,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "2,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "2,"
					+ "4)"
			);

			//Troisième tournoi
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "3,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "3,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "3,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "3,"
					+ "4)"
			);

			//Quatrième tournoi
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "4,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "4,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "4,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "4,"
					+ "4)"
			);

			//Cinquième tournoi
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "5,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "5,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "5,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "5,"
					+ "4)"
			);

			//Sixième tournoi
			st.executeUpdate("INSERT INTO participer VALUES ("
					+ "6,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "6,"
					+ "2)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "6,"
					+ "3)"
			);
			st.executeUpdate(
					"INSERT INTO participer VALUES ("
					+ "6,"
					+ "4)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table participer");
		    e.printStackTrace();
		}
		
		try {
			//Premier tournoi
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

			//Deuxième tournoi
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "2,"
					+ "1)"
			);

			//Troisième tournoi
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "3,"
					+ "1)"
			);

			//Quatrième tournoi
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "4,"
					+ "1)"
			);

			//Cinquième tournoi
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "5,"
					+ "1)"
			);

			//Sixième tournoi
			st.executeUpdate(
					"INSERT INTO arbitrer VALUES ("
					+ "6,"
					+ "1)"
			);

		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table arbitrer");
		    e.printStackTrace();
		}
		
		try {
			//Premier tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "1)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
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
					+ "3,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "4)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "5)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "5)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "6)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "6)"
			);
			//Finale premier tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "7)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "7)"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer tournoi 1");
		    e.printStackTrace();
		}
		
		try {			
			//Deuxième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "8)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "8)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "9)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "9)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "10)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "10)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "11)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "11)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "12)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "12)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "13)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "13)"
			);
			//Finale deuxième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "14)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "14)"
			);
			
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer tournoi 2");
		    e.printStackTrace();
		}
		
		try {
			//Troisième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "15)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "15)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "16)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "16)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "17)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "17)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "18)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "18)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "19)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "19)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "20)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "20)"
			);
			//Finale troisième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "21)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "21)"
			);
			
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer tournoi 3");
		    e.printStackTrace();
		}
		
		try {
			//Quatrième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "22)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "22)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "23)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "23)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "24)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "24)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "25)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "25)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "26)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "26)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "27)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "27)"
			);
			//Finale quatrième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "28)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "28)"
			);
			
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer tournoi 4");
		    e.printStackTrace();
		}
		
		try {
			//Cinquième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "29)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "29)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "30)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "30)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "31)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "31)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "32)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "32)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "33)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "33)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "34)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "34)"
			);
			//Finale cinquième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "35)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "35)"
			);
			
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer tournoi 5");
		    e.printStackTrace();
		}
		
		try {
			//Sixième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "36)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "36)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "37)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "37)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "38)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "38)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "39)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "39)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "40)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "40)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "2,"
					+ "41)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "3,"
					+ "41)"
			);
			//Finale sixième tournoi
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "1,"
					+ "42)"
			);
			st.executeUpdate(
					"INSERT INTO jouer VALUES ("
					+ "4,"
					+ "42)"
			);
		} catch (SQLException e) {
		    System.err.println("Erreur d'insertion de la table jouer tournoi 6");
		    e.printStackTrace();
		}
	}
}
