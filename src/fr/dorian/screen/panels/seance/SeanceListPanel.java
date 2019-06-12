package fr.dorian.screen.panels.seance;

import fr.dorian.Application;
import fr.dorian.content.Salle;
import fr.dorian.content.Seance;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.SalleTable;
import fr.dorian.screen.fields.table.SeanceTable;

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
public class SeanceListPanel extends JPanel{

    public SeanceListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(750, 500));

        Database database = Application.getDatabase();

        SeanceTable table = new SeanceTable(Application.toTab(Application.getSeanceList().values()));
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

        JButton delete = new JButton("Supprimer");
        delete.addActionListener(e -> {
            Seance selected = table.getSelected();

            if(selected != null) {

                boolean confirm = JOptionPane.showConfirmDialog(this, "Êtes vous sûr de vouloir supprimer la séance ?", "Confirmer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                if(confirm) {

                    database.connectDb();

                    try {
                        int idPro = selected.getProfesseur().getId();
                        int idCl = selected.getClasse().getId();
                        int idSal = selected.getSalle().getId();

                        PreparedStatement statement = database.getConnection().prepareStatement(
                                "DELETE FROM dbo.seance WHERE seance.date_debut="+selected.getDateDebut().getTimeInMillis()+" AND seance.date_fin="+selected.getDateFin().getTimeInMillis()+" AND seance.id_classe="+idCl+" AND seance.id_salle="+idSal+" AND seance.id_personne="+idPro);

                        if (statement.executeUpdate() > 0) {
                            //Suppression de la liste
                            Application.getSeanceList().remove(table.getSelectedRow());
                            table.removeFrom(table.getSelectedRow());
                            table.updateUI();

                            JOptionPane.showMessageDialog(this, "La séance a bien été supprimé.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Impossible de supprimer la séance.");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        database.disconnectDb();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "La séance n'a pas été supprimé.");
                }
            }
        });
        panel.add(delete);

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
