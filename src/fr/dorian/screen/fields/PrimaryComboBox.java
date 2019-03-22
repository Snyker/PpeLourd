package fr.dorian.screen.fields;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class créée le 18/03/2019 à 15:16
 * par Jullian Dorian
 */
public class PrimaryComboBox extends JComboBox {

    private List<Object> objectList = new ArrayList<>();

    public PrimaryComboBox(){}

    @Override
    public void addItem(Object item) {
        super.addItem(item);
        objectList.add(item);
    }

    @Override
    public Object getItemAt(int index) {
        //on ne retourne pas
        return super.getItemAt(index);
    }
}
