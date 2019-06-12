package fr.dorian.screen.panels.classe;

import fr.dorian.Application;
import fr.dorian.content.Classe;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.ClasseTable;

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
public class ClasseListPanel extends JPanel{

    public ClasseListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        Database database = Application.getDatabase();

        ClasseTable table = new ClasseTable(Application.toTab(Application.getClasseList().values()));
        table.setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton delete = new JButton("Supprimer");
        delete.addActionListener(e -> {
            Classe selected = table.getSelected();
            if(selected != null) {

                boolean confirm = JOptionPane.showConfirmDialog(this, "Êtes vous sûr de vouloir supprimer la classe ? Toutes les séances ayant la classe seront supprimés.", "Confirmer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                if(confirm) {
                    database.connectDb();

                    try {
                        PreparedStatement statement = database.getConnection().prepareStatement(
                                "DELETE FROM dbo.classes WHERE id_classe=" + selected.getId());

                        if(statement.executeUpdate() > 0) {
                            Application.getEleveList().forEach((k, v) -> {
                                if(v != null && v.getClasse() != null){
                                    if(v.getClasse().getId() == selected.getId()) {
                                        v.setClasse(null);
                                        v.setGroupe("");
                                    }
                                }
                            });
                            Application.getClasseList().remove(selected.getId());
                            table.removeFrom(selected.getId());
                            table.updateUI();

                            JOptionPane.showMessageDialog(this, "La classe a bien été supprimé.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Impossible de supprimer la classe.");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        database.disconnectDb();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La classe n'a pas été supprimé.");
                }
            }
        });
        panel.add(delete);

        JButton list = new JButton("Liste des élèves");
        list.addActionListener(e -> {
            Classe selected = table.getSelected();

            if(selected != null) {
                new ClasseListEleveFrame(selected);
            }
        });
        panel.add(list);

        JButton add = new JButton("Ajouter un élève");
        add.addActionListener(e -> {
            Classe selected = table.getSelected();
            if(selected != null) {
                new ClasseAddEleveFrame(selected);
            }
        });
        panel.add(add);

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

        panel.add(new JLabel("Filtrer : "));
        panel.add(field);
        add(panel, BorderLayout.SOUTH);

        frame.pack();
    }

}
