package fr.dorian.screen.fields.model;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class créée le 12/03/2019 à 14:18
 * par Jullian Dorian
 */
public class DateBornLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private SimpleDateFormat dateFormatter;

    public DateBornLabelFormatter(){
        String datePattern = "yyyy-MM-dd";
        dateFormatter = new SimpleDateFormat(datePattern);
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
