package fr.dorian.screen.panels.eleve;

import fr.dorian.Application;
import fr.dorian.content.Eleve;
import fr.dorian.screen.fields.table.EleveTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class EleveListPanel extends JPanel{

    public EleveListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        EleveTable table= new EleveTable(Application.toTab(Application.getEleveList().values()));

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(850, 350));
        add(jScrollPane, BorderLayout.NORTH);

        frame.pack();
    }

}
