package fr.dorian.screen.panels.seance;

import fr.dorian.Application;
import fr.dorian.screen.fields.table.SalleTable;
import fr.dorian.screen.fields.table.SeanceTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class SeanceListPanel extends JPanel{

    public SeanceListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        SeanceTable table = new SeanceTable(Application.toTab(Application.getSeanceList().values()));

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(750, 350));
        add(jScrollPane, BorderLayout.NORTH);

        frame.pack();
    }

}
