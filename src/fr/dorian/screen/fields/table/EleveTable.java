package fr.dorian.screen.fields.table;

import fr.dorian.content.Eleve;
import fr.dorian.content.Entite;
import fr.dorian.content.Parent;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

/**
 * Class créée le 13/03/2019 à 14:27
 * par Jullian Dorian
 */
public class EleveTable extends JTable{

    public EleveTable(Object[] objects) {
        this.setModel(new Model(objects));
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public void moveColumn(int column, int targetColumn) {
        //On empêche le déplacement d'une colonne
    }

    private class Model extends AbstractTableModel {

        private final String[] entete = {"#", "Nom", "Prénom", "Date de Naissance", "Email", "Téléphone"};
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
            Eleve item = (Eleve) objects[rowIndex];

            switch (columnIndex) {
                case 0:
                    return "    " + item.getId();
                case 1:
                    return item.getNom();
                case 2:
                    return item.getPrenom();
                case 3:
                    return item.getDateNaissance().toString();
                case 4:
                    return item.getEmail();
                case 5:
                    return item.getTelephone();
            }

            return "";
        }
    }
}
