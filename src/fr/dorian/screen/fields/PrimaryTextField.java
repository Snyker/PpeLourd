package fr.dorian.screen.fields;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 12/03/2019 à 13:14
 * par Jullian Dorian
 */
public class PrimaryTextField extends JTextField {

    public PrimaryTextField() {
        this.setFont(new Font("Raleway", Font.PLAIN, 11));
        this.setPreferredSize(new Dimension(200, 25));
    }
}
