package fr.dorian.screen;

import javax.swing.*;
import java.awt.*;

/**
 * Class créée le 13/02/2019 à 10:53
 * par Jullian Dorian
 */
public class MainScreen extends JFrame {

    private final int width = 1080 / 3;
    private final int height = 820 / 2;

    public MainScreen(){
        this.setTitle("Gestionnaire de l'école");
        this.setIconImage(null);

        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        this.setResizable(true);
        this.setLocationRelativeTo(null); //Centre au milieu de l'écran

        //this.setAlwaysOnTop(true); //Toujours devant

        /* PANEL */
        add(new MainPanel(this));

        /* END CONFIG */

        //On affiche la fenetre
        this.setVisible(true);

        //Fermeture de la fenetre
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Change l'affichage de la frame
     * @param jPanel
     */
    public void changePanel(JPanel jPanel){
        getContentPane().removeAll();
        setContentPane(jPanel);
        validate();
    }

}
