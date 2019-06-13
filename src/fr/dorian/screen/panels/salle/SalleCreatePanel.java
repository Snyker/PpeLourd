package fr.dorian.screen.panels.salle;

import fr.dorian.Application;
import fr.dorian.content.Classe;
import fr.dorian.content.Salle;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.fields.PrimaryTextField;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class SalleCreatePanel extends JPanel{

    public SalleCreatePanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new GridLayout(0, 2, 10, 10));

        Label num_salle = new Label("Numéro Salle *");
        PrimaryTextField num_salle_field = new PrimaryTextField();
        Label num_etage = new Label("Numéro Étage *");
        PrimaryTextField num_etage_field = new PrimaryTextField();
        Label nb_max = new Label("Place maximum *");
        PrimaryTextField nb_max_field = new PrimaryTextField();

        this.add(num_salle);
        this.add(num_salle_field);
        this.add(num_etage);
        this.add(num_etage_field);
        this.add(nb_max);
        this.add(nb_max_field);

        JLabel obg = new JLabel("* : Champs obligatoire");
        obg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        this.add(obg);
        this.add(new JLabel());

        PrimaryButton confirm = new PrimaryButton("Créer la salle");
        confirm.addActionListener(e -> {

            final String numSalleValue = num_salle_field.getText();
            final String numEtageValue = num_etage_field.getText();
            final String nbValue = nb_max_field.getText();

            boolean canReg = true;

            if(numSalleValue.isEmpty() || !numSalleValue.matches("[0-9]+")) {
                errorField(num_salle_field);
                canReg = false;
            } else
                resetField(num_salle_field);

            if(numEtageValue.isEmpty() || !numEtageValue.matches("[0-9]+")) {
                errorField(num_etage_field);
                canReg = false;
            } else
                resetField(num_etage_field);

            if(nbValue.isEmpty() || !nbValue.matches("[0-9]+")) {
                errorField(nb_max_field);
                canReg = false;
            } else
                resetField(nb_max_field);

            if(canReg) {
                final Salle salle = new Salle(0, Integer.parseInt(numSalleValue), Integer.parseInt(numEtageValue), Integer.parseInt(nbValue));

                if (salle.register()) {
                    Application.getSalleList().put(salle.getId(), salle);
                    System.out.println("Bien enregistré");
                    //Fermer la fenetre
                    frame.dispose();
                    JOptionPane.showMessageDialog(this, "La salle a bien été crée.");
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible de créer la salle.");
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
