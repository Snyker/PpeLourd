package fr.dorian.screen.fields.table;

import fr.dorian.content.Classe;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Class créée le 13/03/2019 à 14:27
 * par Jullian Dorian
 */
public class ClasseTable extends JTable{

    public ClasseTable(Object[] objects) {
        this.setModel(new Model(objects));
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public void moveColumn(int column, int targetColumn) {
        //On empêche le déplacement d'une colonne
    }

    private class Model extends AbstractTableModel {

        private final String[] entete = {"#", "Section", "Nb Élève"};
        private final Object[] objects;

        public Model(Object[] list) {
            this.objects = list;
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
            Classe item = (Classe) objects[rowIndex];

            switch (columnIndex) {
                case 0:
                    return "    " + item.getId();
                case 1:
                    return item.getSection();
                case 2:
                    return item.getEleves().size();
            }

            return "";
        }
    }
}
