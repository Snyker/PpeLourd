package fr.dorian.content;

import com.mysql.jdbc.Statement;
import fr.dorian.Application;
import fr.dorian.database.Database;

import java.util.*;

/**
 * Class créée le 11/03/2019 à 16:26
 * par Jullian Dorian
 */
public class Seance{

    private final Database database = Application.getDatabase();

    //private int id_seance;
    private UUID uuid = UUID.randomUUID();

    private Professeur professeur; //Le professeur actuel qui s'occupe de la seance
    private Salle salle; //La salle ou le professeur et les eleves sont
    private Classe classe; //La classe contenant les eleves

    private final Calendar date_debut; //debut de la seance
    private final Calendar date_fin; //fin de la seance

    private long timeStart;
    private long timeEnd;

    public Seance(final Calendar date_debut, final Calendar date_fin ) {
        //this.id_seance = id_seance;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.timeStart = date_debut.getTime().getTime();
        this.timeEnd = date_fin.getTime().getTime();
    }

    public Seance(final Calendar date_debut, final Calendar date_fin, Professeur professeur, Classe classe, Salle salle) {
        this(date_debut, date_fin);
        this.professeur = professeur;
        this.salle = salle;
        this.classe = classe;
    }

    public Seance(long timeStart, long timeEnd, Professeur professeur, Classe classe, Salle salle) {
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
        this.professeur = professeur;
        this.classe = classe;
        this.salle = salle;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStart);
        this.date_debut = calendar;
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeEnd);
        this.date_fin = calendar;
    }

    public boolean register(){

        final Map<String, Object> options = new HashMap<>();
        options.put("date_debut", timeStart);
        options.put("date_fin", timeEnd);
        options.put("id_classe", classe.getId());
        options.put("id_personne",professeur.getId());
        options.put("id_salle", salle.getId());

        int added = database.insert("dbo.seance", options);

        return added>0;
    }

    public Calendar getDateDebut() {
        return date_debut;
    }

    public Calendar getDateFin() {
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

    @Override
    public String toString() {
        return uuid.toString();
    }
}
