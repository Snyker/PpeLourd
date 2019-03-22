package fr.dorian.screen.panels.classe;

import fr.dorian.Application;
import fr.dorian.screen.fields.table.ClasseTable;
import fr.dorian.screen.fields.table.EleveTable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 12:19
 * par Jullian Dorian
 */
public class ClasseListPanel extends JPanel{

    public ClasseListPanel(JFrame frame) {
        this.setUI(new BasicPanelUI());
        this.setLayout(new BorderLayout());

        ClasseTable table= new ClasseTable(Application.toTab(Application.getClasseList().values()));

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setPreferredSize(new Dimension(450, 350));
        add(jScrollPane, BorderLayout.NORTH);

        frame.pack();
    }

}
