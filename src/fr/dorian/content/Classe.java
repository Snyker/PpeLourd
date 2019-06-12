package fr.dorian.content;

import fr.dorian.Application;
import fr.dorian.database.Database;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class créée le 11/03/2019 à 16:19
 * par Jullian Dorian
 */
public class Classe implements ITable{

    private final Database database = Application.getDatabase();

    private int id_classe;
    private String section;

    private final List<Eleve> eleves = new ArrayList<>();

    public Classe(int id_classe, String section) {
        this.id_classe = id_classe;
        this.section = section;
    }

    @Override
    public int getId(){
        return id_classe;
    }

    public String getSection() {
        return section;
    }

    @Override
    public boolean register() {

        final Map<String, Object> options = new HashMap<>();
        options.put("section", section);

        this.id_classe = database.insert("dbo.classes", options, Statement.RETURN_GENERATED_KEYS);
        return getId() > 0;
    }

    public List<Eleve> getEleves() {
        return eleves;
    }

    public void addEleve(Eleve eleve){
        eleves.add(eleve);
    }

    public void removeEleve(Eleve eleve) {
        eleves.removeIf(filter -> (eleve.getId() == filter.getId()));
    }

    @Override
    public String toString() {
         return section.replaceAll(" ", "");
    }
}
