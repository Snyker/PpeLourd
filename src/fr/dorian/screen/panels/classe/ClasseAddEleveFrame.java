package fr.dorian.screen.panels.classe;

import fr.dorian.content.Classe;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 23/03/2019 à 17:31
 * par Jullian Dorian
 */
public class ClasseAddEleveFrame extends JFrame {

    public ClasseAddEleveFrame(Classe classe){
        super("Choisir un élève");

        this.setLayout(new FlowLayout());
        this.add(new ClasseAddElevePanel(this, classe));
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }



}
