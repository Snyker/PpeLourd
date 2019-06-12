package fr.dorian.screen.panels.classe;

import fr.dorian.Application;
import fr.dorian.content.Classe;
import fr.dorian.content.Eleve;
import fr.dorian.database.Database;
import fr.dorian.screen.fields.table.EleveTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

/**
 * Class créée le 23/03/2019 à 17:31
 * par Jullian Dorian
 */
public class ClasseAddElevePanel extends JPanel {

    public ClasseAddElevePanel(JFrame frame, Classe classe) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        List<Eleve> eleves = new ArrayList<>();

        //Mise en place de la liste

        Database database = Application.getDatabase();
        database.connectDb();
        try {
            PreparedStatement statement = database.getConnection()
                    .prepareStatement("SELECT p.* FROM dbo.eleves e INNER JOIN dbo.personne p ON p.id_personne=e.id_personne WHERE e.id_personne NOT IN (SELECT id_personne FROM dbo.etre_dans)");

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

        //Affichage de la table

        EleveTable table = new EleveTable(eleves.toArray());

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel text = new JLabel("Choix du groupe :");

        JComboBox comboBox = new JComboBox();
        comboBox.addItem("A");
        comboBox.addItem("B");


        JButton add = new JButton("Ajouter");
        add.addActionListener(e -> {
            Eleve selected = table.getSelected();

            if(selected != null) {

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("id_personne", selected.getId());
                objectMap.put("id_classe", classe.getId());

                String selectedGroupe = (String) comboBox.getSelectedItem();

                if(database.insert("dbo.etre_dans", objectMap) > 0) {

                    try {
                        database.connectDb();

                        boolean ok = database.getConnection().createStatement().executeUpdate("UPDATE dbo.eleves SET groupe='"+selectedGroupe+"' WHERE id_personne="+selected.getId()) > 0;

                        if(ok) {
                            table.removeFrom(selected.getId());
                            table.updateUI();
                            Application.getEleveList().get(selected.getId()).setClasse(classe);
                            Application.getEleveList().get(selected.getId()).setGroupe(selectedGroupe);
                            classe.addEleve(Application.getEleveList().get(selected.getId()));
                            JOptionPane.showMessageDialog(this, "L'élève a été ajouté dans la classe.");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } finally {
                        database.disconnectDb();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Impossible d'ajouter l'élève dans la classe.");
                }
            }
        });
        panel.add(add);
        panel.add(text);
        panel.add(comboBox);

        add(panel, BorderLayout.SOUTH);

        frame.pack();
    }

}
