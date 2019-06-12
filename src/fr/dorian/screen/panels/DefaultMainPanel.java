package fr.dorian.screen.panels;

import fr.dorian.screen.MainPanel;
import fr.dorian.screen.MainScreen;
import fr.dorian.screen.fields.PrimaryButton;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 11:24
 * par Jullian Dorian
 */
public class DefaultMainPanel extends JPanel {

    protected static MainScreen mainScreen;
    protected PrimaryButton back;
    protected Label title;

    public DefaultMainPanel(String title, MainScreen mainScreen){
        this.setUI(new BasicPanelUI());

        this.mainScreen = mainScreen;

        this.back = new PrimaryButton("Retour en arrière");
        this.back.addActionListener(e -> {
            mainScreen.changePanel(new MainPanel(mainScreen));
        });

        this.title = new Label(title);
        this.title.setFont(new Font("SansSerif", Font.PLAIN, 17));
        this.title.setAlignment(Label.CENTER);

        this.add(this.title); //le titre sera tout en haut
    }

}
