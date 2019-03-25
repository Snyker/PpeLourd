package fr.dorian.screen.panels.classe;

import javax.swing.*;

/**
 * Class créée le 23/03/2019 à 17:31
 * par Jullian Dorian
 */
public class ClasseAddEleveFrame extends JFrame {

    private ClasseMainPanel mainPanel;

    public ClasseAddEleveFrame(ClasseMainPanel mainPanel){
        this.mainPanel = mainPanel;

        add(new ClasseAddElevePanel());
    }



}
