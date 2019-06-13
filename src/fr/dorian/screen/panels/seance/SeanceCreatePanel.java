package fr.dorian.screen.panels.seance;

import fr.dorian.Application;
import fr.dorian.content.Classe;
import fr.dorian.content.Professeur;
import fr.dorian.content.Salle;
import fr.dorian.content.Seance;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.fields.model.SpinnerDateTimeModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class SeanceCreatePanel extends JPanel{

    public SeanceCreatePanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new GridLayout(0, 2, 10, 10));

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        calendar.getTime().setYear(date.getYear());

        Label date_debut = new Label("Date de début *");
        SpinnerDateModel model = new SpinnerDateModel();
        model.setCalendarField(Calendar.HOUR_OF_DAY);
        model.setValue(calendar.getTime());

        JSpinner dateModel = new JSpinner( model);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(dateModel, "MM-dd HH:mm");
        dateModel.setEditor(timeEditor);

        DateFormatter formatter = (DateFormatter)timeEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        dateModel.setEditor(timeEditor);

        Label date_fin = new Label("Date de fin *");
        model = new SpinnerDateModel();
        model.setCalendarField(Calendar.HOUR_OF_DAY);
        model.setValue(calendar.getTime());

        JSpinner dateModel2 = new JSpinner(model);
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(dateModel2, "MM-dd HH:mm");
        formatter = (DateFormatter)timeEditor2.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        dateModel2.setEditor(timeEditor2);

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
        this.add(dateModel);
        this.add(date_fin);
        this.add(dateModel2);

        add(classe);
        add(classe_field);
        add(professeur);
        add(professeur_field);
        add(salle);
        add(salle_field);

        JLabel obg = new JLabel("* : Champs obligatoire");
        obg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        this.add(obg);
        this.add(new JLabel());

        PrimaryButton confirm = new PrimaryButton("Créer la séance");
        confirm.addActionListener(e -> {

            final Date dateDebut = (Date) dateModel.getModel().getValue();
            final Date dateFin = (Date) dateModel2.getModel().getValue();
            final Classe c = (Classe) classe_field.getSelectedItem();
            final Professeur p = (Professeur) professeur_field.getSelectedItem();
            final Salle s = (Salle) salle_field.getSelectedItem();

            boolean canCreate = true;

            if(dateDebut == null || dateDebut.toString().isEmpty()) {
                errorField(dateModel);
                canCreate = false;
            }else
                resetField(dateModel);

            if(dateFin == null || dateFin.toString().isEmpty()) {
                errorField(dateModel2);
                canCreate = false;
            }else
                resetField(dateModel2);

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
                final Seance seance = new Seance(dateDebut.getTime(), dateFin.getTime(), p, c, s);

                if(seance.register()){
                    Application.getSeanceList().put(Application.getSeanceList().size()+1, seance);
                    System.out.println("Bien enregistré");
                    JOptionPane.showMessageDialog(this, "La séance a bien été crée.");
                    //Fermer la fenetre
                    frame.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Impossible de créer la séance.");
            }

        });

        this.add(confirm);

        createAndShowGUI();

    }

    private void createAndShowGUI() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 24); // 24 == 12 PM == 00:00:00
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(calendar.getTime());

        JSpinner spinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "MM-dd HH:mm:ss");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);

        spinner.setEditor(editor);

        JPanel content = new JPanel();
        content.add(spinner);

        JFrame frame = new JFrame("Demo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(content);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void errorField(JComponent label) {
        label.setBackground(new Color(255, 74, 76));
    }

    private void resetField(JComponent label) {
        label.setBackground(new Color(255,255,255));
    }


}
