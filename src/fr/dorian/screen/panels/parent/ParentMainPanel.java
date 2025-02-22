package fr.dorian.screen.panels.parent;

import fr.dorian.screen.MainScreen;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.panels.DefaultMainPanel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Class créée le 11/03/2019 à 23:21
 * par Jullian Dorian
 */
public class ParentMainPanel extends DefaultMainPanel {

    protected static JFrame currentFrame;

    public ParentMainPanel(MainScreen mainScreen){
        super("Gestion des parents", mainScreen);

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
