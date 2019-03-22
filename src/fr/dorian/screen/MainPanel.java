package fr.dorian.screen;

import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.panels.classe.ClasseMainPanel;
import fr.dorian.screen.panels.eleve.EleveMainPanel;
import fr.dorian.screen.panels.parent.ParentMainPanel;
import fr.dorian.screen.panels.professeur.ProfesseurMainPanel;
import fr.dorian.screen.panels.salle.SalleMainPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 11:44
 * par Jullian Dorian
 */
public class MainPanel extends JPanel {

    private MainScreen mainScreen;

    public MainPanel(MainScreen mainScreen) {
        this.setUI(new BasicPanelUI());

        this.mainScreen = mainScreen;

        components();
    }

    private void components(){
        Label welcome_message = new Label();
        welcome_message.setText("Panel de gestion de l'école de Barbe Grise");
        welcome_message.setFont(new Font("SansSerif", Font.PLAIN, 17));
        welcome_message.setAlignment(Label.CENTER);
        add(welcome_message);

        PrimaryButton gest_elev = new PrimaryButton("Gestion des élèves");
        gest_elev.addActionListener(e -> {
            EleveMainPanel eleveMainPanel = new EleveMainPanel(mainScreen);
            mainScreen.changePanel(eleveMainPanel);
        });
        add(gest_elev);
        PrimaryButton gest_par = new PrimaryButton("Gestion des parents");
        gest_par.addActionListener(e -> {
            ParentMainPanel parentMainPanel = new ParentMainPanel(mainScreen);
            mainScreen.changePanel(parentMainPanel);
        });
        add(gest_par);
        PrimaryButton gest_prof = new PrimaryButton("Gestion des professeurs");
        gest_prof.addActionListener(e -> {
            ProfesseurMainPanel professeurMainPanel = new ProfesseurMainPanel(mainScreen);
            mainScreen.changePanel(professeurMainPanel);
        });
        add(gest_prof);
        PrimaryButton gest_cla = new PrimaryButton("Gestion des classes");
        gest_cla.addActionListener(e -> {
            ClasseMainPanel classeMainPanel = new ClasseMainPanel(mainScreen);
            mainScreen.changePanel(classeMainPanel);
        });
        add(gest_cla);
        PrimaryButton gest_salle = new PrimaryButton("Gestion des salles");
        gest_salle.addActionListener(e -> {
            SalleMainPanel salleMainPanel = new SalleMainPanel(mainScreen);
            mainScreen.changePanel(salleMainPanel);
        });
        add(gest_salle);
    }

}
