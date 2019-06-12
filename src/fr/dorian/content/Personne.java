package fr.dorian.content;

import fr.dorian.Application;
import fr.dorian.database.Database;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class créée le 11/03/2019 à 15:54
 * par Jullian Dorian
 */
public class Personne implements ITable{

    protected final Database database = Application.getDatabase();

    private int id_entite;
    private String nom;
    private String prenom;
    private Date date_naissance = null;
    private String telephone = null;
    private String email = null;

    public Personne(int id_entite, String nom, String prenom, Date date_naissance) {
        this.id_entite = id_entite;
        this.nom = nom;
        this.prenom = prenom;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
            this.date_naissance = simpleDateFormat.parse(simpleDateFormat.format(date_naissance));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Personne(int id_entite, String nom, String prenom, Date date_naissance, String telephone, String email){
        this(id_entite, nom, prenom, date_naissance);
        this.telephone = telephone;
        this.email = email;
    }

    /**
     * Créer une nouvelle entité dans la base de données
     */
    @Override
    public boolean register() {

        Map<String, Object> __values= new HashMap<>();
        __values.put("nom", nom);
        __values.put("prenom", prenom);
        __values.put("date_naissance", date_naissance);
        if(email != null) __values.put("email", email);
        if(telephone != null) __values.put("telephone", telephone);

        this.id_entite = database.insert("dbo.personne", __values, Statement.RETURN_GENERATED_KEYS);

        return getId() > 0;
    }

    @Override
    public int getId(){
        return this.id_entite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return date_naissance;
    }

    public void setDateNaissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
