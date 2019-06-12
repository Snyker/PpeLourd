package fr.dorian.screen.panels.classe;

import fr.dorian.content.Classe;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 12/06/2019 à 13:45
 * par Jullian Dorian
 */
public class ClasseListEleveFrame extends JFrame {

    public ClasseListEleveFrame(Classe classe) {
        super("Liste des élèves dans la classe");

        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.add(new ClasseListElevePanel(this, classe));
        this.pack();

        this.setVisible(true);
    }

}
