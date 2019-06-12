package fr.dorian.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class créée le 11/03/2019 à 15:59
 * par Jullian Dorian
 */
public class Parent extends Personne {

    private List<Eleve> enfants = new ArrayList<>(); //Liste des enfants

    public Parent(int id_entite, String nom, String prenom, Date date_naissance) {
        super(id_entite, nom, prenom, date_naissance);
    }

    public Parent(int id_entite, String nom, String prenom, Date date_naissance, String telephone, String email) {
        super(id_entite, nom, prenom, date_naissance, telephone, email);
    }

    /**
     * Ajoute un enfant a la liste des enfants du parent
     * @param eleve
     */
    public void addEnfant(Eleve eleve) {
        enfants.add(eleve);
    }

    /**
     * Supprime un enfant de la liste des enfants du parent si celui-ci existe
     * @param eleve
     */
    public void removeEnfant(Eleve eleve){
        enfants.removeIf(filter -> (eleve.getId() == filter.getId()));
    }

    public List<Eleve> getEnfants() {
        return enfants;
    }

    @Override
    public String toString() {
        return getNom() + " " + getPrenom();
    }
}
