package fr.dorian.screen.panels.parent;

import fr.dorian.Application;
import fr.dorian.screen.fields.table.EleveTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class ParentListPanel extends JPanel{

    public ParentListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        EleveTable table= new EleveTable(Application.toTab(Application.getEleveList().values()));

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(850, 350));
        add(jScrollPane, BorderLayout.NORTH);

        frame.pack();
    }

}
