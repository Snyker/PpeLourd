package fr.dorian.screen.panels.professeur;

import fr.dorian.Application;
import fr.dorian.content.Matiere;
import fr.dorian.content.Professeur;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.fields.PrimaryTextField;
import fr.dorian.screen.fields.model.DateBornLabelFormatter;
import fr.dorian.screen.fields.model.DateBornModel;
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
public class ProfesseurCreatePanel extends JPanel{

    public ProfesseurCreatePanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new GridLayout(0, 2, 10, 10));

        Label name = new Label("Nom de famille *");
        PrimaryTextField name_field = new PrimaryTextField();
        Label surname = new Label("Prénom *");
        PrimaryTextField surname_field = new PrimaryTextField();
        Label date_naissance = new Label("Date de naissance *");
        DateBornModel dateModel = new DateBornModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel.getModel(), dateModel.getProp());
        JDatePickerImpl date_naissance_field = new JDatePickerImpl(datePanel, new DateBornLabelFormatter());
        Label matiere = new Label("Matière *");
        JComboBox matiere_field = new JComboBox();
        Application.getMatiereList().values().forEach(e->{
            matiere_field.addItem(e);
        });

        Label email = new Label("Email");
        PrimaryTextField email_field = new PrimaryTextField();
        Label telephone = new Label("Téléphone");
        PrimaryTextField telephone_field = new PrimaryTextField();
        this.add(name);
        this.add(name_field);
        this.add(surname);
        this.add(surname_field);
        this.add(date_naissance);
        this.add(date_naissance_field);
        this.add(matiere);
        this.add(matiere_field);
        this.add(email);
        this.add(email_field);
        this.add(telephone);
        this.add(telephone_field);

        JLabel obg = new JLabel("* : Champs obligatoire");
        obg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        this.add(obg);
        this.add(new JLabel());

        PrimaryButton confirm = new PrimaryButton("Créer le professeur");
        confirm.addActionListener(e -> {

            final String nameValue = name_field.getText();
            final String surnameValue = surname_field.getText();
            final Matiere matiereValue = (Matiere) (matiere_field.getSelectedItem());
            final Date dateNaissance = (Date) date_naissance_field.getModel().getValue();
            final String telephoneValue = telephone_field.getText();
            final String emailValue = email_field.getText();

            boolean canReg = true;

            if(nameValue.isEmpty() || !nameValue.matches("[a-zA-Z]+")) {
                errorField(name_field);
                canReg = false;
            } else
                resetField(name_field);

            if(surnameValue.isEmpty() || !surnameValue.matches("[a-zA-Z]+")) {
                errorField(surname_field);
                canReg = false;
            }else
                resetField(surname_field);

            if(dateNaissance == null || dateNaissance.toString().isEmpty()) {
                errorField(date_naissance_field);
                canReg = false;
            }else
                resetField(date_naissance_field);

            if(canReg) {
                final Professeur professeur = new Professeur(0, nameValue, surnameValue, dateNaissance, matiereValue);

                boolean addOptions = true;

                if(!telephoneValue.isEmpty()) {
                    if(telephoneValue.matches("[0-9]{10}")){
                        professeur.setTelephone(telephoneValue);
                        resetField(telephone_field);
                    } else {
                        errorField(telephone_field);
                        addOptions = false;
                    }
                }


                if(!emailValue.isEmpty()) {
                    if(emailValue.matches("[a-zA-Z_0-9.-]+[@]{1}[a-zA-Z.]+")) {
                        professeur.setEmail(emailValue);
                        resetField(email_field);
                    } else {
                        errorField(email_field);
                        addOptions = false;
                    }
                }

                if(addOptions) {
                    if (professeur.register()) {
                        Application.getProfesseurList().put(professeur.getId(), professeur);
                        System.out.println("Bien enregistré");
                        //Fermer la fenetre
                        frame.dispose();
                        JOptionPane.showMessageDialog(this,"Le professeur a bien été crée.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible de créer le professeur.");
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
