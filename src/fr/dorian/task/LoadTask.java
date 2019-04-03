package fr.dorian.task;

import fr.dorian.content.*;
import fr.dorian.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Class créée le 15/03/2019 à 09:26
 * par Jullian Dorian
 */
public class LoadTask implements Runnable {

    private final Database database;

    private Map<Integer, Eleve> eleveList = new HashMap<>();
    private Map<Integer, Professeur> professeurList = new HashMap<>();
    private Map<Integer, Parent> parentList = new HashMap<>();

    private Map<Integer, Matiere> matiereList = new HashMap<>();
    private Map<Integer, Classe> classeList = new HashMap<>();
    private Map<Integer, Salle> salleList = new HashMap<>();
    private Map<Integer, Seance> seanceList = new HashMap<>();

    public LoadTask(Database database) {
        this.database = database;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //connection
        database.connectDb();

        if(!database.isConnected()) return;

        Connection connection = database.getConnection();

        try {
            //Chargement des eleves
            final PreparedStatement statementEleve = connection.prepareStatement("SELECT e.* FROM dbo.eleves INNER JOIN dbo.personne e on eleves.id_personne = e.id_personne");
            ResultSet resultSet = statementEleve.executeQuery();

            while(resultSet.next()) {
                Eleve eleve = new Eleve(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );

                this.eleveList.put(eleve.getId(), eleve);
            }
            statementEleve.close();
            /* ---- */

            final PreparedStatement statementMat = connection.prepareStatement("SELECT * FROM dbo.matieres");
            resultSet = statementMat.executeQuery();

            while(resultSet.next()) {
                Matiere matiere = new Matiere(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );

                this.matiereList.put(matiere.getId(), matiere);
            }

            statementMat.close();
            /* ---- */

            final PreparedStatement statementProfessor = connection.prepareStatement("SELECT e.*, id_matiere FROM dbo.professeurs INNER JOIN dbo.personne e on dbo.professeurs.id_personne = e.id_personne");
            resultSet = statementProfessor.executeQuery();

            while(resultSet.next()) {
                Professeur professeur = new Professeur(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        matiereList.get(resultSet.getInt(7)) //On récupère la matière via l'id-> key
                );

                this.professeurList.put(professeur.getId(), professeur);
            }

            statementProfessor.close();
            /* ---- */
            final PreparedStatement statementParent = connection.prepareStatement("SELECT * FROM dbo.personne WHERE id_personne NOT IN (SELECT e.id_personne FROM dbo.eleves e) AND id_personne NOT IN (SELECT p.id_personne FROM dbo.professeurs p) ");
            resultSet = statementParent.executeQuery();

            while(resultSet.next()) {
                Parent parent = new Parent(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );

                this.parentList.put(parent.getId(), parent);
            }

            statementParent.close();
            /* ---- */
            final PreparedStatement statementSalle = connection.prepareStatement("SELECT * FROM dbo.salles");
            resultSet = statementSalle.executeQuery();

            while(resultSet.next()) {
                Salle salle = new Salle(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4)
                );

                this.salleList.put(salle.getId(), salle);
            }

            statementSalle.close();
            /* ---- */
            final PreparedStatement statementClasse = connection.prepareStatement("SELECT * FROM dbo.classes");
            resultSet = statementClasse.executeQuery();

            while(resultSet.next()) {
                Classe classe = new Classe(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                );

                this.classeList.put(classe.getId(), classe);
            }

            statementClasse.close();
            /* ---- MISE EN PLACE DE LA RELATION parent<->eleve ---- */
            final PreparedStatement statementRelPE = connection.prepareStatement("SELECT * FROM dbo.avoir_parent");
            resultSet = statementRelPE.executeQuery();

            while(resultSet.next()) {

                Eleve eleve = eleveList.get(resultSet.getInt("id_personne"));
                Parent parent = parentList.get(resultSet.getInt("id_parent"));

                eleve.addParent(parent);
                parent.addEnfant(eleve);
            }

            statementRelPE.close();
            /* ---- MISE EN PLACE DE LA RELATION classe<->eleve ---- */
            final PreparedStatement statementRelEC = connection.prepareStatement("SELECT * FROM dbo.etre_dans");
            resultSet = statementRelEC.executeQuery();

            while(resultSet.next()) {

                String groupe = resultSet.getString("groupe");
                Eleve eleve = eleveList.get(resultSet.getInt("id_personne"));
                Classe classe = classeList.get(resultSet.getInt("id_classe"));

                eleve.setGroupe(groupe);
                classe.addEleve(eleve);
            }

            statementRelEC.close();
            /* ---- SEANCE ---- */
            final PreparedStatement statementSeance = connection.prepareStatement("SELECT * FROM dbo.seance ORDER BY date_debut ASC");
            resultSet = statementSeance.executeQuery();

            while(resultSet.next()) {

                Seance seance = new Seance(
                        resultSet.getInt("id_seance"),
                        resultSet.getDate("date_debut"),
                        resultSet.getDate("date_fin"),
                        professeurList.get(resultSet.getInt("id_personne")),
                        classeList.get(resultSet.getInt("id_classe")),
                        salleList.get(resultSet.getInt("id_salle"))
                );

                seanceList.put(seance.getId(), seance);
            }

            statementSeance.close();

            System.out.println("Aucune erreur n'a été trouvé lors de la récupération des données.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //deconnexion
        database.disconnectDb();
    }

    public Map<Integer, Eleve> getEleveList() {
        return eleveList;
    }

    public Map<Integer, Professeur> getProfesseurList() {
        return professeurList;
    }

    public Map<Integer, Parent> getParentList() {
        return parentList;
    }

    public Map<Integer, Classe> getClasseList() {
        return classeList;
    }

    public Map<Integer, Salle> getSalleList() {
        return salleList;
    }

    public Map<Integer, Seance> getSeanceList() {
        return seanceList;
    }

    public Map<Integer, Matiere> getMatiereList() {
        return matiereList;
    }
}
