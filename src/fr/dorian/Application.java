package fr.dorian;

import fr.dorian.content.*;
import fr.dorian.database.Database;
import fr.dorian.screen.MainScreen;
import fr.dorian.task.LoadTask;

import java.io.File;
import java.util.*;

/**
 * Class créée le 13/02/2019 à 10:51
 * par Jullian Dorian
 */
public class Application {

    private final Scanner scanner;

    private final MainScreen mainScreen;
    private static Database database;

    private static Map<Integer, Eleve> eleveList;
    private static Map<Integer, Professeur> professeurList;
    private static Map<Integer, Parent> parentList;

    private static Map<Integer, Matiere> matiereList;
    private static Map<Integer, Classe> classeList;
    private static Map<Integer, Salle> salleList;
    private static Map<Integer, Seance> seanceList;

    /**
     * Launch the application
     * @param args
     */
    public static void main(String[] args) {
        new Application(args);
    }

    /**
     * Constructor
     * @param args
     */
    private Application(String[] args){
        this.scanner = new Scanner(System.in);

        //On enregistre la base de données
        System.out.println("Préparation de la base de données...");
        //database = new Database(Database.DRIVER_MYSQL, "localhost", 3306, "ppe_lourd", "root", "");
        database = new Database(Database.DRIVER_SQLSERVER, "localhost", 1433, "ppe_lourd", "IFU-NC4OKL4\\Arkas", "");
        System.out.println("Base de données : OK");

        //Lancement de la fenetre
        System.out.println("Ouverture de la fenêtre...");
        this.mainScreen = new MainScreen();
        System.out.println("Ouverture : OK");

        System.out.println("Chargement des données en arrière plan...");
        LoadTask loadTask = new LoadTask(database);
        loadTask.run();

        eleveList = loadTask.getEleveList();
        professeurList = loadTask.getProfesseurList();
        parentList = loadTask.getParentList();
        matiereList = loadTask.getMatiereList();
        salleList = loadTask.getSalleList();
        seanceList = loadTask.getSeanceList();
        classeList = loadTask.getClasseList();

        System.out.println("Chargement : OK");

    }

    public static Database getDatabase(){
        return database;
    }

    public static Map<Integer, Eleve> getEleveList() {
        return eleveList;
    }

    public static Map<Integer, Professeur> getProfesseurList() {
        return professeurList;
    }

    public static Map<Integer, Parent> getParentList() {
        return parentList;
    }

    public static Map<Integer, Matiere> getMatiereList() {
        return matiereList;
    }

    public static Map<Integer, Classe> getClasseList() {
        return classeList;
    }

    public static Map<Integer, Salle> getSalleList() {
        return salleList;
    }

    public static Map<Integer, Seance> getSeanceList() {
        return seanceList;
    }

    public static Object[] toTab(Collection<?> collection){

        Object[] objects = new Object[collection.size()];

        int index = 0;
        for(Object o : collection) {
            objects[index++] = o;
        }

        return objects;
    }
}
