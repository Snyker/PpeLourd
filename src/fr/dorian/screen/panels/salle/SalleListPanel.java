package fr.dorian.screen.panels.salle;

import fr.dorian.Application;
import fr.dorian.content.Professeur;
import fr.dorian.content.Salle;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.ClasseTable;
import fr.dorian.screen.fields.table.SalleTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class SalleListPanel extends JPanel{

    public SalleListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        Database database = Application.getDatabase();

        SalleTable table = new SalleTable(Application.toTab(Application.getSalleList().values()));
        table.setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.NORTH);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout());

        JButton delete = new JButton("Supprimer");
        delete.addActionListener(e -> {
            Salle selected = table.getSelected();

            if(selected != null) {

                boolean confirm = JOptionPane.showConfirmDialog(this, "Êtes vous sûr de vouloir supprimer la salle ? Les séances comportant la salle seront définit sur null.", "Confirmer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                if(confirm) {

                    database.connectDb();

                    try {
                        PreparedStatement statement = database.getConnection().prepareStatement(
                                "DELETE FROM dbo.salles WHERE dbo.salles.id_salle = " + selected.getId());

                        if (statement.executeUpdate() > 0) {
                            //Suppression de la liste
                            Application.getProfesseurList().remove(selected.getId());
                            table.removeFrom(selected.getId());
                            table.updateUI();

                            JOptionPane.showMessageDialog(this, "La salle a bien été supprimé.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Impossible de supprimer la salle.");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        database.disconnectDb();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La salle n'a pas été supprimé.");
                }
            }
        });
        westPanel.add(delete);

        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(100, 20));
        field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                sorter.setRowFilter(RowFilter.regexFilter(field.getText()));
            }
        });

        westPanel.add(new JLabel("Filtrer : "));
        westPanel.add(field);

        add(westPanel, BorderLayout.SOUTH);

        frame.pack();
    }

}
