package fr.dorian.screen.panels.eleve;

import fr.dorian.Application;
import fr.dorian.content.Eleve;
import fr.dorian.content.Seance;
import fr.dorian.database.Database;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Class créée le 12/06/2019 à 19:40
 * par Jullian Dorian
 */
public class EleveEmploiPanel extends JPanel {

    public EleveEmploiPanel(EleveEmploiFrame frame, Eleve eleve) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        List<Seance> seanceList = new ArrayList<>();

        Database database = Application.getDatabase();
        database.connectDb();

        if(eleve.getClasse() == null)
            frame.dispose();

        int idClasse = eleve.getClasse().getId();

        try {
            PreparedStatement statement = database.getConnection()
                    .prepareStatement("SELECT se.* FROM dbo.seance se where se.id_classe = " + idClasse + " ORDER BY date_debut asc");

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {

                Seance recup = new Seance(
                        resultSet.getLong(1), resultSet.getLong(2),
                        Application.getProfesseurList().get(resultSet.getInt(3)),
                        Application.getClasseList().get(resultSet.getInt(4)),
                        Application.getSalleList().get(resultSet.getInt(5))
                );

                seanceList.add(recup);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            database.disconnectDb();
        }

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(6, 5, 10, 10));

        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 6; j++) {
                int index = j + (i*6);
                if(index < seanceList.size()) {
                    Seance seance = seanceList.get(j + (i * 5));

                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(4, 1, 15, 15));
                    panel.setBackground(seance.getProfesseur().getMatiere().getColor());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                    panel.add(new JLabel(simpleDateFormat.format(seance.getDateDebut().getTime()) + "-"+simpleDateFormat.format(seance.getDateFin().getTime())));

                    panel.add(new JLabel(seance.getProfesseur().getNom()+" "+seance.getProfesseur().getPrenom()));
                    panel.add(new JLabel(seance.getProfesseur().getMatiere().getLabel()));

                    panel.add(new JLabel("Salle "+seance.getSalle().getNSalle() + "-" + seance.getSalle().getNEtage()+" Etage"));

                    content.add(panel);
                }
            }
        }

        add(content,BorderLayout.CENTER);

        //EmploiTable table = new EmploiTable(seanceList.toArray());
        //JScrollPane scrollPane = new JScrollPane(table);
        //add(scrollPane, BorderLayout.CENTER);

        frame.pack();
    }
}
