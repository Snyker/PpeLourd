package fr.dorian.screen.panels.seance;

import fr.dorian.Application;
import fr.dorian.content.Classe;
import fr.dorian.content.Professeur;
import fr.dorian.content.Salle;
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
        DateTimeModel dateModel2 = new DateTimeModel();
        JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModel2.getModel(), dateModel2.getProp());
        JDatePickerImpl date_fin_field = new JDatePickerImpl(datePanel2, new DateBornLabelFormatter());

        Label classe = new Label("Classe");
        JComboBox classe_field = new JComboBox();
        Application.getClasseList().values().forEach(e ->{
            classe_field.addItem(e);
        });

        Label professeur = new Label("Professeur");
        JComboBox professeur_field = new JComboBox();
        Application.getProfesseurList().values().forEach(e ->{
            professeur_field.addItem(e);
        });

        Label salle = new Label("Salle");
        JComboBox salle_field = new JComboBox();
        Application.getSalleList().values().forEach(e ->{
            salle_field.addItem(e);
        });

        this.add(date_debut);
        this.add(date_debut_field);
        this.add(date_fin);
        this.add(date_fin_field);

        add(classe);
        add(classe_field);
        add(professeur);
        add(professeur_field);
        add(salle);
        add(salle_field);

        Label obg = new Label("* : Champs obligatoire");
        obg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        this.add(obg);
        this.add(new Label());

        PrimaryButton confirm = new PrimaryButton("Créer la salle");
        confirm.addActionListener(e -> {

            final Date dateDebut = (Date) date_debut_field.getModel().getValue();
            final Date dateFin = (Date) date_fin_field.getModel().getValue();
            final Classe c = (Classe) classe_field.getSelectedItem();
            final Professeur p = (Professeur) professeur_field.getSelectedItem();
            final Salle s = (Salle) salle_field.getSelectedItem();

            boolean canCreate = true;

            if(dateDebut == null || dateDebut.toString().isEmpty()) {
                errorField(date_debut_field);
                canCreate = false;
            }else
                resetField(date_debut_field);

            if(dateFin == null || dateFin.toString().isEmpty()) {
                errorField(date_fin_field);
                canCreate = false;
            }else
                resetField(date_fin_field);

            if(c == null) {
                errorField(classe_field);
                canCreate = false;
            }else
                resetField(classe_field);

            if(p == null) {
                errorField(professeur_field);
                canCreate = false;
            }else
                resetField(professeur_field);

            if(s == null) {
                errorField(salle_field);
                canCreate = false;
            }else
                resetField(salle_field);

            if(canCreate){
                final Seance seance = new Seance(0, dateDebut, dateFin);

                seance.setClasse(c);
                seance.setProfesseur(p);
                seance.setSalle(s);

                if(seance.register()){
                    Application.getSeanceList().put(seance.getId(), seance);
                    System.out.println("Bien enregistré");
                    //Fermer la fenetre
                    frame.dispose();
                } else {
                    System.out.println("Impossible d'enregistrer");
                }

            }

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
