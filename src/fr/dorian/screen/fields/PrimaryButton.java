package fr.dorian.screen.fields;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 11/03/2019 à 16:42
 * par Jullian Dorian
 */
public class PrimaryButton extends JButton {

    public PrimaryButton(String text) {
        this.setText(text);
        this.setFont(new Font("SansSerif", Font.PLAIN, 12));
        this.setPreferredSize(new Dimension(200, 35));
    }

}
