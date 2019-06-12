package fr.dorian.screen.panels.parent;

import com.sun.javafx.css.converters.FontConverter;
import fr.dorian.Application;
import fr.dorian.content.Eleve;
import fr.dorian.content.Parent;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.EleveTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class créée le 11/06/2019 à 18:59
 * par Jullian Dorian
 */
public class ParentListElevePanel extends JPanel {

    public ParentListElevePanel(JFrame frame, Parent parent) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());


        JLabel name = new JLabel(parent.getNom() + " " + parent.getPrenom());
        name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        add(name, BorderLayout.NORTH);

        /* TABLE */

        List<Eleve> eleves = new ArrayList<>();

        Database database = Application.getDatabase();
        database.connectDb();
        try {
            PreparedStatement statement = database.getConnection()
                    .prepareStatement("SELECT p.* FROM dbo.eleves e INNER JOIN dbo.personne p ON p.id_personne=e.id_personne INNER JOIN dbo.avoir_parent a on e.id_personne = a.id_personne WHERE a.id_parent="+parent.getId());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Eleve eleve = new Eleve(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6));
                eleves.add(eleve);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.disconnectDb();
        }

        EleveTable table = new EleveTable(eleves.toArray());

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        JButton delete = new JButton("Supprimer un enfant");
        delete.addActionListener(e -> {
            Eleve selected = table.getSelected();

            if(selected != null) {

                database.connectDb();

                try {
                    PreparedStatement statement = database.getConnection().prepareStatement("DELETE FROM avoir_parent WHERE id_personne="+selected.getId());
                    if(statement.executeUpdate() > 0) {
                        parent.removeEnfant(selected);
                        Application.getEleveList().get(selected.getId()).removeParent(parent);
                        table.removeFrom(selected.getId());
                        table.updateUI();
                        JOptionPane.showMessageDialog(this, "L'enfant a bien été supprimé.");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } finally {
                    database.disconnectDb();
                }
            }
        });
        buttons.add(delete);

        add(buttons, BorderLayout.SOUTH);

        frame.pack();
    }

}
