package fr.dorian.screen.panels.seance;

import fr.dorian.Application;
import fr.dorian.content.Seance;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.fields.PrimaryTextField;
import fr.dorian.screen.panels.DateBornLabelFormatter;
import fr.dorian.screen.panels.DateTimeModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.util.Date;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class SeanceCreatePanel extends JPanel{

    public SeanceCreatePanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new GridLayout(0, 2, 10, 10));

        Label date_debut = new Label("Date de début *");
        DateTimeModel dateModel = new DateTimeModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel.getModel(), dateModel.getProp());
        JDatePickerImpl date_debut_field = new JDatePickerImpl(datePanel, new DateBornLabelFormatter());
        Label date_fin = new Label("Date de fin *");
        JDatePickerImpl date_fin_field = new JDatePickerImpl(datePanel, new DateBornLabelFormatter());

        this.add(date_debut);
        this.add(date_debut_field);
        this.add(date_fin);
        this.add(date_fin_field);

        Label obg = new Label("* : Champs obligatoire");
        obg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        this.add(obg);
        this.add(new Label());

        PrimaryButton confirm = new PrimaryButton("Créer la salle");
        confirm.addActionListener(e -> {

            final Seance seance = new Seance(0, (Date) date_debut_field.getModel().getValue(), (Date) date_fin_field.getModel().getValue());

         /*   if (seance.register()) {
                Application.getSeanceList().put(seance.getId(), seance);
                System.out.println("Bien enregistré");
                //Fermer la fenetre
                frame.dispose();
            } else {
                System.out.println("Impossible d'enregistrer");
            }*/

        });

        this.add(confirm);

    }

    private void errorField(JComponent label) {
        label.setBackground(new Color(255, 74, 76));
    }

    private void resetField(JComponent label) {
        label.setBackground(new Color(255,255,255));
    }


}
