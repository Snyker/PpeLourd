package fr.dorian.content;

import fr.dorian.Application;
import fr.dorian.database.Database;

import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Class créée le 11/03/2019 à 16:23
 * par Jullian Dorian
 */
public class Salle implements ITable{

    private final Database database = Application.getDatabase();

    private int id_salle;
    private int n_salle;
    private int n_etage;
    private int nb_place;

    public Salle(int id_salle, int n_salle, int n_etage, int nb_place) {
        this.id_salle= id_salle;
        this.n_salle = n_salle;
        this.n_etage = n_etage;
        this.nb_place = nb_place;
    }

    @Override
    public int getId() {
        return id_salle;
    }

    public int getNSalle(){
        return n_salle;
    }

    public int getNEtage(){
        return n_etage;
    }

    public int getPlace() {
        return nb_place;
    }

    @Override
    public boolean register() {

        final Map<String, Object> options = new HashMap<>();
        options.put("n_salle", n_salle);
        options.put("n_etage", n_etage);
        options.put("nb_place", nb_place);

        this.id_salle = database.insert("dbo.salles", options, Statement.RETURN_GENERATED_KEYS);
        return getId() > 0;
    }

    @Override
    public String toString() {
        return n_salle+ "";
    }
}
