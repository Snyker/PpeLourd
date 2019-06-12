package fr.dorian.screen.panels.parent;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class créée le 11/06/2019 à 18:59
 * par Jullian Dorian
 */
public class ParentAddElevePanel extends JPanel {

    public ParentAddElevePanel(JFrame frame, Parent parent) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new FlowLayout());

        List<Eleve> eleves = new ArrayList<>();

        Database database = Application.getDatabase();
        database.connectDb();
        try {
            PreparedStatement statement = database.getConnection()
                    .prepareStatement("SELECT p.* FROM dbo.eleves e INNER JOIN dbo.personne p ON p.id_personne=e.id_personne WHERE e.id_personne NOT IN (SELECT id_personne FROM dbo.avoir_parent)");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Eleve eleve = new Eleve(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6));
                eleves.add(eleve);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            database.disconnectDb();
        }

        EleveTable table = new EleveTable(eleves.toArray());

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        JButton add = new JButton("Ajouter");
        add.addActionListener(e -> {
            Eleve selected = table.getSelected();

            if(selected != null) {

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("id_personne", selected.getId());
                objectMap.put("id_parent", parent.getId());

                if(database.insert("dbo.avoir_parent", objectMap) > 0) {
                    parent.addEnfant(selected);
                    Application.getEleveList().get(selected.getId()).addParent(parent);
                    JOptionPane.showMessageDialog(this, "L'enfanta été ajouté au parent.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible d'ajouter l'enfant au parent.");
                }
            }
        });
        add(add, BorderLayout.SOUTH);

        frame.pack();

    }

}
