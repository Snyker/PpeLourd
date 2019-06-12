package fr.dorian.content;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class créée le 11/03/2019 à 16:13
 * par Jullian Dorian
 */
public class Matiere {

    private final int id_matiere;
    private final String label;
    private final Color color;

    public Matiere(int id_matiere, String label) {
        this.id_matiere = id_matiere;
        this.label = label;
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        this.color = new Color(rand.nextInt(150, 256), rand.nextInt(150, 256), rand.nextInt(150, 256));
    }

    public int getId() {
        return id_matiere;
    }

    public String getLabel() {
        return label;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return label;
    }
}
