package fr.dorian.screen.panels.eleve;

import fr.dorian.content.Eleve;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 12/06/2019 à 19:39
 * par Jullian Dorian
 */
public class EleveEmploiFrame extends JFrame {

    public EleveEmploiFrame(Eleve selected) {
        super("Emploi du temps de l'élève");

        this.setLayout(new FlowLayout());
        this.add(new EleveEmploiPanel(this, selected));
        this.pack();

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
