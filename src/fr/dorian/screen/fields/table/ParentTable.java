package fr.dorian.screen.fields.table;

import fr.dorian.content.Parent;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class créée le 13/03/2019 à 14:27
 * par Jullian Dorian
 */
public class ParentTable extends JTable{

    public ParentTable(Object[] objects) {
        this.setModel(new Model(objects));
        getTableHeader().setReorderingAllowed(false);
    }

    public Parent getSelected() {
        return ((Model) getModel()).getSelected();
    }

    @Override
    public void moveColumn(int column, int targetColumn) {
        //On empêche le déplacement d'une colonne
    }

    /**
     * On supprime une valeur
     * @param id
     */
    public void removeFrom(int id) {
        List<Object> list = new ArrayList<>();

        for(int i = 0; i < ((Model) getModel()).getObjects().length; i++){
            Parent parent = (Parent) ((Model) getModel()).getObjects()[i];
            if(parent.getId() != id) {
                list.add(parent);
            }
        }

        ((Model) getModel()).setObjects(list.toArray());
    }

    public class Model extends AbstractTableModel {

        private final String[] entete = {"#", "Nom", "Prénom", "Date de Naissance", "Email", "Téléphone"};
        private Object[] objects;

        public Model(Object[] list) {
            this.objects = list;
        }

        Object[] getObjects() {
            return objects;
        }

        void setObjects(Object[] objects){
            this.objects = objects;
        }

        /**
         * Returns the number of rows in the model. A
         * <code>JTable</code> uses this method to determine how many rows it
         * should display.  This method should be quick, as it
         * is called frequently during rendering.
         *
         * @return the number of rows in the model
         * @see #getColumnCount
         */
        @Override
        public int getRowCount() {
            return objects.length;
        }

        /**
         * Returns the number of columns in the model. A
         * <code>JTable</code> uses this method to determine how many columns it
         * should create and display by default.
         *
         * @return the number of columns in the model
         * @see #getRowCount
         */
        @Override
        public int getColumnCount() {
            return entete.length;
        }

        @Override
        public String getColumnName(int column) {
            return (String) this.entete[column];
        }

        /**
         * Returns the value for the cell at <code>columnIndex</code> and
         * <code>rowIndex</code>.
         *
         * @param rowIndex    the row whose value is to be queried
         * @param columnIndex the column whose value is to be queried
         * @return the value Object at the specified cell
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Parent item = (Parent) objects[rowIndex];

            switch (columnIndex) {
                case 0:
                    return item.getId();
                case 1:
                    return item.getNom();
                case 2:
                    return item.getPrenom();
                case 3:
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
                    return simpleDateFormat.format(item.getDateNaissance().getTime());
                case 4:
                    return item.getEmail();
                case 5:
                    return item.getTelephone();
            }

            return "";
        }

        public Parent getSelected() {
            return (Parent) objects[getSelectedRow()];
        }
    }

}
