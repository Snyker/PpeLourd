package fr.dorian.screen.fields.table;

import fr.dorian.content.Salle;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Class créée le 13/03/2019 à 14:27
 * par Jullian Dorian
 */
public class SalleTable extends JTable{

    public SalleTable(Object[] objects) {
        this.setModel(new Model(objects));
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public void moveColumn(int column, int targetColumn) {
        //On empêche le déplacement d'une colonne
    }

    public Salle getSelected() {
        return ((Model) getModel()).getSelected();
    }

    /**
     * On supprime une valeur
     * @param id
     */
    public void removeFrom(int id) {
        List<Object> list = new ArrayList<>();

        for(int i = 0; i < ((SalleTable.Model) getModel()).getObjects().length; i++){
            Salle professeur = (Salle) ((SalleTable.Model) getModel()).getObjects()[i];
            if(professeur.getId() != id) {
                list.add(professeur);
            }
        }

        ((SalleTable.Model) getModel()).setObjects(list.toArray());
    }

    private class Model extends AbstractTableModel {

        private final String[] entete = {"#", "Etage", "Salle", "Place max"};
        private Object[] objects;

        public Model(Object[] list) {
            this.objects = list;
        }

        Object[] getObjects() {
            return objects;
        }

        void setObjects(Object[] objects) {
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
            Salle item = (Salle) objects[rowIndex];

            switch (columnIndex) {
                case 0:
                    return item.getId();
                case 1:
                    return "n* "+item.getNEtage();
                case 2:
                    return "n* "+item.getNSalle();
                case 3:
                    return item.getPlace()+ " max";
            }

            return "";
        }

        public Salle getSelected() {
            return (Salle) this.objects[getSelectedRow()];
        }
    }
}
