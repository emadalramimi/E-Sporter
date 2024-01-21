package modele;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.Color;
import java.awt.print.PrinterException;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.UIManager;

import modele.metier.Equipe;
import modele.metier.Tournoi;
import vue.theme.JTableThemeImpression;

/**
 * Modèle impression des résultats
 */
public class ModeleImpression {
    
    /**
     * Imprimer état des résultats d'un tournoi
     * @param table : table des résultats à imprimer
     * @param tournoi : tournoi dont on veut imprimer l'état des résultats
     * @throws PrinterException Erreur d'impression
     */
    public void imprimerEtatResultatsTournoi(JTableThemeImpression table, Tournoi tournoi) throws PrinterException {
        // Création des formats pour l'en-tête 
        MessageFormat headerFormat = new MessageFormat("État des résultats du tournoi " + tournoi.getNomTournoi());
       
        // Impression de la table
        this.imprimer(table, headerFormat);
    }

    /**
     * Imprimer palmarès de la saison
     * @param table : table des résultats à imprimer
     * @throws PrinterException Erreur d'impression
     */
    public void imprimerPalmares(JTableThemeImpression table) throws PrinterException {
        // Création des formats pour l'en-tête 
        MessageFormat headerFormat = new MessageFormat("Palmarès de la saison " + LocalDate.now().getYear());

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
      * Imprimer une JTableThemeImpression
      * @param table : table à imprimer
      * @param headerFormat : format de l'en-tête de la page
      * @throws PrinterException Erreur d'impression
      */
    private void imprimer(JTableThemeImpression table, MessageFormat headerFormat) throws PrinterException {
        // Récupération de la date et heure actuelle
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY à HH:mm");
        String currentDateTime = LocalDateTime.now().format(formatter);

        // Création du format pour le pied de page
        MessageFormat footerFormat = new MessageFormat("Au " + currentDateTime);

        // Pour éviter un bug d'affichage de la modal d'impression
        UIManager.put("Panel.background", Color.WHITE);

        table.print(JTable.PrintMode.NORMAL, headerFormat, footerFormat);
    }

}
