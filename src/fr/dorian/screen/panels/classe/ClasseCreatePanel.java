package fr.dorian.screen.panels.classe;

import fr.dorian.Application;
import fr.dorian.content.Classe;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.fields.PrimaryTextField;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class ClasseCreatePanel extends JPanel{

    public ClasseCreatePanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new GridLayout(0, 2, 10, 10));

        Label section = new Label("Section *");
        PrimaryTextField section_field = new PrimaryTextField();

        this.add(section);
        this.add(section_field);

        Label obg = new Label("* : Champs obligatoire");
        obg.setFont(new Font("SansSerif", Font.PLAIN, 9));
        this.add(obg);
        this.add(new Label());

        PrimaryButton confirm = new PrimaryButton("Créer la classe");
        confirm.addActionListener(e -> {

            final String sectionValue = section_field.getText();

            boolean canReg = true;

            if(sectionValue.isEmpty() || !sectionValue.matches("[a-zA-Z0-9]+")) {
                errorField(section_field);
                canReg = false;
            } else
                resetField(section_field);

            if(canReg) {
                final Classe classe = new Classe(0, sectionValue);

                if (classe.register()) {
                    Application.getClasseList().put(classe.getId(), classe);
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
