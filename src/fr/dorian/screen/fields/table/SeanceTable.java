package fr.dorian.screen.fields.table;

import fr.dorian.content.Salle;
import fr.dorian.content.Seance;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class créée le 13/03/2019 à 14:27
 * par Jullian Dorian
 */
public class SeanceTable extends JTable{

    public SeanceTable(Object[] objects) {
        this.setModel(new Model(objects));
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public void moveColumn(int column, int targetColumn) {
        //On empêche le déplacement d'une colonne
    }

    public Seance getSelected() {
        return ((Model) getModel()).getSelected();
    }

    /**
     * On supprime une valeur
     * @param selectedRow
     */
    public void removeFrom(int selectedRow) {
        List<Object> list = new ArrayList<>();

        for(int i = 0; i < ((SeanceTable.Model) getModel()).getObjects().length; i++){
            Seance seance = (Seance) ((SeanceTable.Model) getModel()).getObjects()[i];
            if(i != selectedRow) {
                list.add(seance);
            }
        }

        ((SeanceTable.Model) getModel()).setObjects(list.toArray());
    }

    private class Model extends AbstractTableModel {

        private final String[] entete = {"#", "Date de début", "Date de fin", "Professeur", "Classe", "N*Salle"};
        private Object[] objects;

        public Model(Object[] list) {
            this.objects = list;
        }

        void setObjects(Object[] objects) {
            this.objects = objects;
        }

        Object[] getObjects() {
            return objects;
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
            Seance item = (Seance) objects[rowIndex];

            switch (columnIndex) {
                case 0:
                    return (rowIndex+1);
                case 1:
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
                    return simpleDateFormat.format(item.getDateDebut().getTime().getTime());
                case 2:
                    simpleDateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm");
                    return simpleDateFormat.format(item.getDateFin().getTime());
                case 3:
                    return item.getProfesseur().getNom() + " " + item.getProfesseur().getPrenom();
                case 4:
                    return item.getClasse().getSection();
                case 5:
                    return item.getSalle().getNSalle();
            }

            return "";
        }

        public Seance getSelected() {
            return (Seance) this.objects[getSelectedRow()];
        }
    }
}
