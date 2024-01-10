package modele;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.awt.print.PrinterException;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;

import modele.metier.Tournoi;
import vue.theme.JTableThemeImpression;

public class ModeleImpression {
    
    public void imprimerEtatResultatsTournoi(JTableThemeImpression table, Tournoi tournoi) {
        // Récupération de la date et heure actuelle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
        String currentDateTime = LocalDateTime.now().format(formatter);

        // Création des formats pour l'en-tête et le pied de page
        MessageFormat headerFormat = new MessageFormat("État des résultats du tournoi " + tournoi.getNomTournoi());
        MessageFormat footerFormat = new MessageFormat("Au " + currentDateTime);

        // Impression de la table
        try {
            table.print(JTable.PrintMode.NORMAL, headerFormat, footerFormat);
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

}
