package fr.dorian.content;

/**
 * Class créée le 11/03/2019 à 16:13
 * par Jullian Dorian
 */
public class Matiere {

    private final int id_matiere;
    private final String label;

    public Matiere(int id_matiere, String label) {
        this.id_matiere = id_matiere;
        this.label = label;
    }

    public int getId() {
        return id_matiere;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
