package fr.dorian.screen.panels.parent;

import fr.dorian.Application;
import fr.dorian.content.Parent;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.EleveTable;
import fr.dorian.screen.fields.table.ParentTable;
import javafx.scene.control.TableRow;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class ParentListPanel extends JPanel{

    public ParentListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        ParentTable table = new ParentTable(Application.toTab(Application.getParentList().values()));
        table.setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);


        JPanel westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout());

        JButton addEnfant = new JButton("Ajouter un enfant");
        addEnfant.addActionListener(e -> {
            new ParentAddEleveFrame(table.getSelected());
        });
        westPanel.add(addEnfant);

        JButton listEnfant = new JButton("Liste des enfants");
        listEnfant.addActionListener(e -> {
            new ParentListEleveFrame(table.getSelected());
        });
        westPanel.add(listEnfant);

        JButton delete = new JButton("Supprimer");
        delete.addActionListener(e -> {
            Parent parent = table.getSelected();

            if(parent != null) {

                Database database = Application.getDatabase();
                database.connectDb();

                try {
                    PreparedStatement statement = database.getConnection().prepareStatement(
                            "DELETE FROM dbo.personne WHERE dbo.personne.id_personne = " + parent.getId());

                    if(statement.executeUpdate() > 0) {
                        //Suppression de la liste
                        Application.getParentList().remove(parent.getId());
                        table.removeFrom(parent.getId());
                        table.updateUI();

                        JOptionPane.showMessageDialog(this, "Le parent a bien été supprimé.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Impossible de supprimer le parent.");
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    database.disconnectDb();
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
