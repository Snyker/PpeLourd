package fr.dorian.database;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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

            switch (this.driver){
                case DRIVER_MYSQL:
                    System.out.println("DRIVER MYSQL DETECTED");

                    Class.forName("com.mysql.jdbc.Driver");
                    this.connection = DriverManager.getConnection(this.driver + "://" + this.host + ":" + this.port + "/" + this.database + "?charset=utf8&useSSL=false", this.user, this.password);
                    break;
                case DRIVER_SQLSERVER:
                    System.out.println("DRIVER SQLSERVER DETECTED");

                    String uri = this.driver + "://" + this.host + ":"+port+";databaseName="+ this.database + ";instanceName=IFU-NC4OKL4;integratedSecurity=true;";

                    this.connection = DriverManager.getConnection(uri);
                    break;
            }
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
            preparedStatement = applyValues(preparedStatement, values);

            int affRow = preparedStatement.executeUpdate();

            if(affRow <= 0) {
                System.out.println("Aucune ligne n'a été ajoutée.");
            } else {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                resultSet.next();
                lastInsertId = resultSet.getInt(1);
            }
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            disconnectDb();
        }

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
            if(o instanceof String && !((String) o).isEmpty())
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

    private PreparedStatement applyValues(PreparedStatement statement, Object[] values) throws SQLException {

        int index = 1;

        for(Object o : values){
            if(o instanceof String) {
                statement.setString(index, (String) o);
            } else
            if(o instanceof Integer){
                statement.setInt(index, (Integer) o);
            } else
            if(o instanceof Double) {
                statement.setDouble(index, (Double) o);
            } else
            if(o instanceof Float) {
                statement.setFloat(index, (Float) o);
            } else
            if(o instanceof Long) {
                statement.setLong(index, (Long) o);
            } else
            if(o instanceof Date) {
                Date date = (Date) o;
                date.setHours(0); date.setMinutes(0); date.setSeconds(0);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                statement.setDate(index, sqlDate);
            } else
            if(o instanceof Boolean) {
                statement.setBoolean(index, (Boolean) o);
            } else
            if(o == null) {
                statement.setObject(index, null);
            } else {
                statement.setObject(index, o);
            }

            index++;
        }

        return statement;
    }

    private String convert(Object[] objects) {
        return convert(objects, true);
    }

    private String convert(Object[] objects, boolean useAnd) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < objects.length; i++) {
            Object o = objects[i];
            if(i % 2 == 0) {
                builder.append((String) o).append("=");
            } else {
                builder.append(o.toString());
            }

            if(i != 0 && i % 2 == 0 && i != objects.length-1) {
                if(useAnd) builder.append(" AND ");
                else builder.append(", ");
            }

        }

        return builder.toString();
    }

    public boolean update(String table, Object[] objects, Object[] objects1) {
        String set = convert(objects, false);
        String where = convert(objects1);

        boolean ret = false;

        connectDb();

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE "+table+" SET "+ set + " WHERE " + where);
            ret = statement.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnectDb();
        }

        return ret;
    }
}
