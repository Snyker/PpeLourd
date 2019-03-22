package fr.dorian.screen.panels.professeur;

import fr.dorian.screen.MainScreen;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.panels.DefaultMainPanel;
import fr.dorian.screen.panels.eleve.EleveListFrame;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class créée le 11/03/2019 à 23:21
 * par Jullian Dorian
 */
public class ProfesseurMainPanel extends DefaultMainPanel {

    protected JFrame currentFrame;

    public ProfesseurMainPanel(MainScreen mainScreen){
        super("Gestion des professeurs", mainScreen);

        //setLayout(new GridLayout(0, 1, 25, 25));

        PrimaryButton liste = new PrimaryButton("Liste des professeurs");
        liste.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ProfesseurListFrame(this);
            }
        });
        add(liste);

        PrimaryButton create = new PrimaryButton("Créer un professeur");
        create.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ProfesseurCreateFrame(this);
            }
        });
        add(create);

        PrimaryButton delete = new PrimaryButton("Supprimer un professeur");
        delete.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ProfesseurCreateFrame(this);
            }
        });
        add(delete);

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
