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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class créée le 12/06/2019 à 13:45
 * par Jullian Dorian
 */
public class ClasseListElevePanel extends JPanel {

    public ClasseListElevePanel(JFrame parent, Classe classe) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        Database database = Application.getDatabase();

        JLabel name = new JLabel(classe.getSection());
        name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        add(name, BorderLayout.NORTH);

        List<Eleve> eleves = new ArrayList<>();

        Application.getClasseList().forEach((k, v) -> {
            if(k == classe.getId()) {
                eleves.addAll(classe.getEleves());
            }
        });

        //Table
        EleveTable table = new EleveTable(eleves.toArray());

        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton delete = new JButton("Supprimer un élève");
        delete.addActionListener(e -> {
            Eleve selected = table.getSelected();

            if(selected != null) {

                database.connectDb();

                try {
                    PreparedStatement statement = database.getConnection().prepareStatement("DELETE FROM etre_dans WHERE id_classe="+classe.getId()+" AND id_personne="+selected.getId());
                    if(statement.executeUpdate() > 0) {
                        classe.removeEleve(selected);
                        Application.getEleveList().get(selected.getId()).setClasse(null);
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

        parent.pack();
    }

}
