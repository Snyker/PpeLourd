package fr.dorian.screen.fields.table;

import fr.dorian.content.Professeur;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;

/**
 * Class créée le 13/03/2019 à 14:27
 * par Jullian Dorian
 */
public class ProfesseurTable extends JTable{

    public ProfesseurTable(Object[] objects) {
        this.setModel(new Model(objects));
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public void moveColumn(int column, int targetColumn) {
        //On empêche le déplacement d'une colonne
    }

    private class Model extends AbstractTableModel {

        private final String[] entete = {"#", "Nom", "Prénom", "Matière", "Date de Naissance", "Email", "Téléphone"};
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
            Professeur professeur = (Professeur) objects[rowIndex];

            switch (columnIndex) {
                case 0:
                    return "    " + professeur.getId();
                case 1:
                    return professeur.getNom();
                case 2:
                    return professeur.getPrenom();
                case 3:
                    return professeur.getMatiere().getLabel();
                case 4:
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
                    return simpleDateFormat.format(professeur.getDateNaissance().getTime());
                case 5:
                    return professeur.getEmail();
                case 6:
                    return professeur.getTelephone();
            }

            return "";
        }
    }
}
