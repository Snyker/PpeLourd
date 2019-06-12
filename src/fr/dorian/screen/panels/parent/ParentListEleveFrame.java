package fr.dorian.screen.panels.parent;

import fr.dorian.content.Parent;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 11/06/2019 à 19:46
 * par Jullian Dorian
 */
public class ParentListEleveFrame extends JFrame {

    public ParentListEleveFrame(Parent parent) {
        super("Liste des enfants");

        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        this.add(new ParentListElevePanel(this, parent));
        this.pack();

        this.setVisible(true);
    }

}
