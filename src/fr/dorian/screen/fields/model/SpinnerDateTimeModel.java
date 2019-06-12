package fr.dorian.screen.fields.model;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Class créée le 10/06/2019 à 15:42
 * par Jullian Dorian
 */
public class SpinnerDateTimeModel extends SpinnerDateModel {

    public SpinnerDateTimeModel() {
        super(new Date(), null, null, Calendar.HOUR);
    }

    @Override
    public Comparable getEnd() {
        Date toEnd = new Date();
        toEnd.setHours(17);
        toEnd.setMinutes(0);
        toEnd.setSeconds(0);
        toEnd.setYear(2099);
        toEnd.setMonth(7);
        toEnd.setDate(1);

        return toEnd;
    }

    @Override
    public Comparable getStart() {
        Date today = new Date();
        today.setHours(8);
        today.setMinutes(0);
        today.setSeconds(0);

        return today;
    }

    @Override
    public Object getNextValue() {

        Calendar value = (Calendar) getValue();
        Date date = value.getTime();

        int hours = date.getHours();

        if(hours+1 > 17) {
            value.add(Calendar.HOUR, 15); //On ajoute 15 heures pour arriver a 8 heures
        } else {
            value.add(Calendar.HOUR, 1); //On ajoute une heure
            System.out.println("add 1h");
        }

        System.out.println("next value");

        return value.getTime();
    }

    @Override
    public Object getPreviousValue() {
        Calendar value = (Calendar) getValue();
        Date date = value.getTime();

        int hours = date.getHours();

        if(hours-1 < 8) {
            value.add(Calendar.HOUR, -15); //On enleve 15 heures pour arriver a 17 heures
        } else {
            value.add(Calendar.HOUR, -1); //On enleve une heure
            System.out.println("shrink 1h");
        }

        System.out.println("prev value");

        return value.getTime();
    }
}
