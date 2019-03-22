package fr.dorian.screen.panels.professeur;

import fr.dorian.Application;
import fr.dorian.screen.fields.table.EleveTable;
import fr.dorian.screen.fields.table.ProfesseurTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class ProfesseurListPanel extends JPanel{

    public ProfesseurListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        ProfesseurTable table= new ProfesseurTable(Application.toTab(Application.getProfesseurList().values()));

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(850, 350));
        add(jScrollPane, BorderLayout.NORTH);

        frame.pack();
    }

}
