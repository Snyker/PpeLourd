package fr.dorian.screen.panels.seance;

import fr.dorian.screen.MainScreen;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.panels.DefaultMainPanel;
import fr.dorian.screen.panels.salle.SalleCreateFrame;
import fr.dorian.screen.panels.salle.SalleListFrame;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class créée le 11/03/2019 à 23:21
 * par Jullian Dorian
 */
public class SeanceMainPanel extends DefaultMainPanel {

    protected JFrame currentFrame;

    public SeanceMainPanel(MainScreen mainScreen){
        super("Gestion des séances", mainScreen);

        //setLayout(new GridLayout(0, 1, 25, 25));

        PrimaryButton liste = new PrimaryButton("Liste des séances");
        liste.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new SeanceListFrame(this);
            }
        });
        add(liste);

        PrimaryButton create = new PrimaryButton("Créer une séance");
        create.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new SeanceCreateFrame(this);
            }
        });
        add(create);

        final ActionListener backAction = this.back.getActionListeners()[0];
        this.back.removeActionListener(backAction);
        this.back.addActionListener(e -> {
            if(currentFrame == null) {
                backAction.actionPerformed(e); //on récupère le bouton qui reviens en arrière
            }
        });
        add(this.back);

        this.setVisible(true);
    }


}
