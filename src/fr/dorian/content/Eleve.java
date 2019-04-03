package fr.dorian.content;

import java.util.*;

/**
 * Class créée le 11/03/2019 à 15:58
 * par Jullian Dorian
 */
public class Eleve extends Personne {

    private final List<Parent> parents = new ArrayList<>(); //Liste des parents

    private String groupe = "";

    public Eleve(int id_entite, String nom, String prenom, Date date_naissance) {
        super(id_entite, nom, prenom, date_naissance);
    }

    public Eleve(int id_entite, String nom, String prenom, Date date_naissance, String telephone, String email) {
        super(id_entite, nom, prenom, date_naissance, telephone, email);
    }

    /**
     * Ajoute un parent à la liste des parents de l'elève
     * @param parent
     */
    public void addParent(Parent parent) {
        parents.add(parent);
    }

    /**
     * Supprime un parent de la liste des parents de l'elève si celui-ci existe
     * @param parent
     */
    public void removeParent(Parent parent){
        parents.removeIf(filter -> (parent.getId() == filter.getId()));
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getGroupe() {
        return groupe;
    }

    @Override
    public boolean register() {
        boolean createEntite = super.register();

        final Map<Object, Object> keys = new HashMap<>();
        keys.put("id_personne", getId());

        boolean createEleve = this.database.insert("dbo.eleves", keys) == 1; //Si une ligne est crée c'est vrai

        return createEntite && createEleve;
    }
}
