package fr.dorian.screen.panels.classe;

import fr.dorian.screen.MainScreen;
import fr.dorian.screen.fields.PrimaryButton;
import fr.dorian.screen.panels.DefaultMainPanel;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Class créée le 11/03/2019 à 23:21
 * par Jullian Dorian
 */
public class ClasseMainPanel extends DefaultMainPanel {

    protected JFrame currentFrame;

    public ClasseMainPanel(MainScreen mainScreen){
        super("Gestion des classes", mainScreen);

        //setLayout(new GridLayout(0, 1, 25, 25));

        PrimaryButton liste = new PrimaryButton("Liste des classes");
        liste.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ClasseListFrame(this);
            }
        });
        add(liste);

        PrimaryButton create = new PrimaryButton("Créer une classe");
        create.addActionListener(e ->{
            if(currentFrame == null) {
                this.currentFrame = new ClasseCreateFrame(this);
            }
        });
        add(create);

        PrimaryButton delete = new PrimaryButton("Supprimer un classe");
        delete.addActionListener(e ->{
            if(currentFrame == null) {
                //this.currentFrame = new ParentCreateFrame(this);
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
