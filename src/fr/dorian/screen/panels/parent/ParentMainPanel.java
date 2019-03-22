package fr.dorian.screen.panels.parent;

import fr.dorian.screen.MainScreen;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.panels.DefaultMainPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class créée le 11/03/2019 à 23:21
 * par Jullian Dorian
 */
public class ParentMainPanel extends DefaultMainPanel {

    protected JFrame currentFrame;

    public ParentMainPanel(MainScreen mainScreen){
        super("Gestion des parents", mainScreen);

        //setLayout(new GridLayout(0, 1, 25, 25));

        PrimaryButton liste = new PrimaryButton("Liste des parents");
        liste.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ParentListFrame(this);
            }
        });
        add(liste);

        PrimaryButton create = new PrimaryButton("Créer un parent");
        create.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ParentCreateFrame(this);
            }
        });
        add(create);

        PrimaryButton delete = new PrimaryButton("Supprimer un parent");
        delete.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ParentCreateFrame(this);
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
