package fr.dorian.screen.panels.salle;

import fr.dorian.Application;
import fr.dorian.screen.fields.table.ClasseTable;
import fr.dorian.screen.fields.table.SalleTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class SalleListPanel extends JPanel{

    public SalleListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        SalleTable table = new SalleTable(Application.toTab(Application.getSalleList().values()));

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(550, 350));
        add(jScrollPane, BorderLayout.NORTH);

        frame.pack();
    }

}
