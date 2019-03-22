package fr.dorian.screen.panels;

import org.jdatepicker.impl.UtilDateModel;

import java.util.Properties;

/**
 * Class créée le 12/03/2019 à 14:22
 * par Jullian Dorian
 */
public class DateTimeModel {

    private UtilDateModel model;
    private Properties properties;

    public DateTimeModel() {
        UtilDateModel model = new UtilDateModel();
        model.setYear(2019);
        model.setMonth(1);
        this.model= model;

        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        this.properties = properties;
    }

    public UtilDateModel getModel() {
        return model;
    }

    public Properties getProp() {
        return properties;
    }
}
