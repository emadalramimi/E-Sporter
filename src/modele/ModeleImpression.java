package modele;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.print.PrinterException;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;

import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.theme.JTableThemeImpression;

public class ModeleImpression {
    
    public void imprimerEtatResultatsTournoi(JTableThemeImpression table, Tournoi tournoi) throws PrinterException {
        // Création des formats pour l'en-tête 
        MessageFormat headerFormat = new MessageFormat("État des résultats du Palmarès " + tournoi.getNomTournoi());
       
        // Impression de la table
        this.imprimer(table, headerFormat);
    }

    public void imprimerPalmares(JTableThemeImpression table) throws PrinterException {
        // Création des formats pour l'en-tête 
        MessageFormat headerFormat = new MessageFormat("État des résultats de la saison " + LocalDate.now().getYear());

        // Impression de la table
        this.imprimer(table, headerFormat);
    }

    public void imprimerHistoriquePoints(JTableThemeImpression table, Equipe equipe) throws PrinterException {
        // Création des formats pour l'en-tête 
        MessageFormat headerFormat = new MessageFormat("Historique points " + equipe.getNom() + " saison " + LocalDate.now().getYear());

        // Impression de la table
        this.imprimer(table, headerFormat);
    }

     /**
      * Imprime une JTableThemeImpression
      * @param table : table à imprimer
      * @param headerFormat : format de l'en-tête
      * @param footerFormat : format du pied de page
      */
    private void imprimer(JTableThemeImpression table, MessageFormat headerFormat) throws PrinterException {
        System.out.println(table);

        // Récupération de la date et heure actuelle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY à HH:mm");
        String currentDateTime = LocalDateTime.now().format(formatter);

        // Création du format pour le pied de page
        MessageFormat footerFormat = new MessageFormat("Au " + currentDateTime);

        table.print(JTable.PrintMode.NORMAL, headerFormat, footerFormat);
    }

}
