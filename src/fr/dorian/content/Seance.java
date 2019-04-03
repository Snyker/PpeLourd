package fr.dorian.content;

import com.mysql.jdbc.Statement;
import fr.dorian.Application;
import fr.dorian.database.Database;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class créée le 11/03/2019 à 16:26
 * par Jullian Dorian
 */
public class Seance implements ITable{

    private final Database database = Application.getDatabase();

    private int id_seance;

    private Professeur professeur; //Le professeur actuel qui s'occupe de la seance
    private Salle salle; //La salle ou le professeur et les eleves sont
    private Classe classe; //La classe contenant les eleves

    private final Date date_debut; //debut de la seance
    private final Date date_fin; //fin de la seance

    public Seance(int id_seance,Date date_debut, Date date_fin ) {
        this.id_seance = id_seance;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Seance(int id_seance, Date date_debut, Date date_fin, Professeur professeur, Classe classe, Salle salle) {
        this(id_seance, date_debut, date_fin);
        this.professeur = professeur;
        this.salle = salle;
        this.classe = classe;
    }

    @Override
    public boolean register(){

        final Map<String, Object> options = new HashMap<>();
        options.put("date_debut", date_debut);
        options.put("date_fin", date_fin);
        options.put("id_classe", classe.getId());
        options.put("id_personne",professeur.getId());
        options.put("id_salle", salle.getId());

        this.id_seance = database.insert("dbo.seance", options, Statement.RETURN_GENERATED_KEYS);

        return getId()>0;
    }

    @Override
    public int getId() {
        return id_seance;
    }

    public Date getDateDebut() {
        return date_debut;
    }

    public Date getDateFin() {
        return date_fin;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
