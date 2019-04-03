package fr.dorian.content;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Class créée le 11/03/2019 à 16:14
 * par Jullian Dorian
 */
public class Professeur extends Personne {

    private Matiere matiere;

    public Professeur(int id_entite, String nom, String prenom, Date date_naissance, Matiere matiere){
        super(id_entite, nom, prenom, date_naissance);
        this.matiere = matiere;
    }

    public Professeur(int id_entite, String nom, String prenom, Date date_naissance, String telephone, String email, Matiere matiere) {
        super(id_entite, nom, prenom, date_naissance, telephone, email);
        this.matiere = matiere;
    }

    @Override
    public boolean register() {
        boolean createEntite = super.register();

        final Map<Object, Object> keys = new HashMap<>();
        keys.put("id_personne", getId());
        keys.put("id_matiere", getMatiere().getId());

        boolean createProf = this.database.insert("dbo.professeurs", keys) == 1; //Si une ligne est crée c'est vrai

        return createEntite && createProf;
    }

    public Matiere getMatiere() {
        return matiere;
    }
}
