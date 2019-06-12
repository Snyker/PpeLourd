package fr.dorian.screen.panels.eleve;

import fr.dorian.Application;
import fr.dorian.content.Eleve;
import fr.dorian.content.Parent;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.EleveTable;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class EleveListPanel extends JPanel{

    public EleveListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        Database database = Application.getDatabase();

        EleveTable table = new EleveTable(Application.toTab(Application.getEleveList().values()));
        table.setAutoCreateRowSorter(true);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        sorter.setSortable(1, true);
        sorter.setSortable(2, true);
        sorter.setSortsOnUpdates(true);
        table.setRowSorter(sorter);

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton delete = new JButton("Supprimer l'élève");
        delete.addActionListener(e -> {
            Eleve selected = table.getSelected();
            if(selected != null) {

                boolean confirm = JOptionPane.showConfirmDialog(this, "Êtes vous sûr de vouloir supprimer l'élève ?", "Confirmer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                if(confirm) {
                    database.connectDb();

                    try {
                        PreparedStatement statement = database.getConnection().prepareStatement("DELETE FROM dbo.eleves WHERE id_personne = " + selected.getId());

                        if(statement.executeUpdate() > 0) {
                            Application.getEleveList().remove(selected.getId());
                            table.removeFrom(selected.getId());
                            table.updateUI();

                            JOptionPane.showMessageDialog(this, "L'élève a bien été supprimé.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Impossible de supprimer l'élève.");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        database.disconnectDb();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "L'élève n'a pas été supprimé.");
                }
            }
        });
        panel.add(delete);

        JButton btn = new JButton("Voir le parent");
        btn.addActionListener(e -> {
            Eleve selected = table.getSelected();
            if(selected != null && selected.getParents().size() > 0) {
                Parent parent = selected.getParents().get(0);
                JOptionPane.showMessageDialog(this, parent.getNom() + " " + parent.getPrenom() + " | " + parent.getEmail() + " | " + parent.getTelephone(), "Informations du parent", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(btn);

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
