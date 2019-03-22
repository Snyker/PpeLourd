package fr.dorian.database;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import fr.dorian.content.Eleve;
import fr.dorian.content.Entite;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Class créée le 13/02/2019 à 14:35
 * par Jullian Dorian
 */
public class Database {

    /* DRIVER MYSQL */
    public static final String DRIVER_MYSQL = "jdbc:mysql";

    /* DRIVER SQLSERVER */
    public static final String DRIVER_SQLSERVER = "jdbc:sqlserver";

    private final String driver;
    private final String host;
    private final String database;
    private final int port;
    private final String user;
    private final String password;

    private Connection connection = null;

    public Database(String driver, String host, int port, String database, String user, String password) {
        this.driver = driver;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void connectDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(this.driver + "://" + this.host + ":" + this.port + "/" + this.database + "?charset=utf8&useSSL=false", this.user, this.password);
            System.out.println("La base de données a bien été connecté.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void disconnectDb(){
        try {
            if(!connection.isClosed()) {
                this.connection.close();
                this.connection = null;
                System.out.println("La base de données a été deconnecté.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected(){
        return this.connection != null;
    }

    /*
    REQUESTS
     */

    public <T> Collection<? extends T> select(String table) {

        final Collection<T> collection = new ArrayList<>();

        connectDb();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT e.* FROM " + table + " t INNER JOIN entite e ON e.id_entite=t.id_entite");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Eleve eleve = new Eleve(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6));

                collection.add((T) eleve);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        disconnectDb();

        return collection;
    }

    /**
     *
     * @param request
     */
    public void request(String request){

    }

    /**
     *
     * @param table
     * @param request
     * @return int Retourne le dernier ID
     */
    public int insert(String table, Map<?,?> request) {

        connectDb();

        String keys = checkParams(request);
        String keysInt = checkParamsInt(request);
        Object[] values = checkValues(request);

        int rowInserted = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + table + "("+keys+") VALUES ("+keysInt+")");
            applyValues(preparedStatement, values);

            rowInserted = preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        disconnectDb();
        return rowInserted;
    }

    /**
     *
     * @param table
     * @param request
     * @param statement
     * @return int Retourne le dernier ID
     */
    public int insert(String table, Map<?,?> request, int statement) {

        connectDb();

        String keys = checkParams(request);
        String keysInt = checkParamsInt(request);
        Object[] values = checkValues(request);

        int lastInsertId = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + table + "("+keys+") VALUES ("+keysInt+")", statement);
            applyValues(preparedStatement, values);

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            lastInsertId = resultSet.getInt(1);
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        disconnectDb();
        return lastInsertId;
    }

    /**
     * Retourne la liste des paramètres de la liste des objets
     * @param objects
     * @return String params
     */
    private String checkParams(Map<?,?> objects) {

        StringBuilder keys = new StringBuilder();

        for (Object o : objects.keySet()){
            if(o instanceof String)
                keys.append(o).append(", ");
        }

        keys.delete(keys.length()-2,keys.length());

        return keys.toString();
    }

    /**
     * Retourne une chaine de caractère de ' ? ' selon le nombre d'objet
     * @param objects
     * @return
     */
    private String checkParamsInt(Map<?,?> objects) {

        StringBuilder keys = new StringBuilder();

        for (Object o : objects.keySet())
            keys.append("?").append(", ");

        keys.delete(keys.length()-2,keys.length());

        return keys.toString();
    }

    /**
     * Retourne les valeurs de la list d'objet
     * @param objects
     * @return objects
     */
    private Object[] checkValues(Map<?,?> objects) {

        Object[] values = new Object[objects.size()];

        int index = 0;
        for(Object o : objects.values()) {
            values[index++] = o;
        }

        return values;
    }

    private void applyValues(PreparedStatement statement, Object[] values) throws SQLException {

        int index = 1;

        for(Object o : values){
            if(o instanceof String) {
                statement.setString(index, (String) o);
            }

            if(o instanceof Integer){
                statement.setInt(index, (Integer) o);
            }

            if(o instanceof Double) {
                statement.setDouble(index, (Double) o);
            }

            if(o instanceof Float) {
                statement.setFloat(index, (Float) o);
            }

            if(o instanceof Long) {
                statement.setLong(index, (Long) o);
            }

            if(o instanceof Date) {
                Date date = (Date) o;
                date.setHours(0); date.setMinutes(0); date.setSeconds(0);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                statement.setDate(index, sqlDate);
            }

            if(o instanceof Boolean) {
                statement.setBoolean(index, (Boolean) o);
            }

            if(o == null) {
                statement.setObject(index, null);
            }

            index++;
        }

    }
}
