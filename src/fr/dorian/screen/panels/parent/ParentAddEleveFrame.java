package fr.dorian.screen.panels.parent;

import fr.dorian.content.Parent;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 11/06/2019 à 18:58
 * par Jullian Dorian
 */
public class ParentAddEleveFrame extends JFrame {

    public ParentAddEleveFrame(Parent selected) {
        super("Choisir un enfant");

        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.add(new ParentAddElevePanel(this, selected));
        this.pack();

        this.setVisible(true);
    }

}
