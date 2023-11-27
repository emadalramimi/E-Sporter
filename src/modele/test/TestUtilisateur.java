package modele.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import modele.ModeleAdministrateur;
import modele.exception.IdentifiantOuMdpIncorrectsException;
import modele.metier.Administrateur;

public class TestUtilisateur {

private ModeleAdministrateur modele;
    
    @Before
    public void setUp() {
        this.modele = new ModeleAdministrateur();
        if (modele.getCompteCourant() != null)
            this.modele.deconnecter();
    }
}
