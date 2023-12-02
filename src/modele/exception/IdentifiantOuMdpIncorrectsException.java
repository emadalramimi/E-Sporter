package modele.exception;

/**
 * Exception d'identifiant ou mot de passe incorrects (connexion)
 */
public class IdentifiantOuMdpIncorrectsException extends Exception {
	
	public IdentifiantOuMdpIncorrectsException() {
		super();
	}

	public IdentifiantOuMdpIncorrectsException(String message) {
		super(message);
	}
	
}
