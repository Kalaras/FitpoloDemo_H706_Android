package com.fitpolo.support.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @Date 2017/5/15
 * @Author wenzheng.liu
 * @Description Datos de paso
 * @ClassPath com.fitpolo.support.entity.DailyStep
 */
public class DailyStep implements Comparable<DailyStep> {
    public String date;// Fecha, aaaa-MM-dd
    public String count;// Pasos
    public String duration;// Tiempo de ejercicio
    public String distance;// Distancia de movimiento
    public String calories;// Calorías quemadas por ejercicio

    @Override
    public String toString() {
        return "DailyStep{" +
                "date='" + date + '\'' +
                ", count='" + count + '\'' +
                ", duration='" + duration + '\'' +
                ", distance='" + distance + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }

    public Calendar strDate2Calendar(String strDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int compareTo(DailyStep another) {
        Calendar calendar = strDate2Calendar(date, "yyyy-MM-dd");
        Calendar anotherCalendar = strDate2Calendar(another.date, "yyyy-MM-dd");
        if (calendar.getTime().getTime() > anotherCalendar.getTime().getTime()) {
            return 1;
        }
        if (calendar.getTime().getTime() < anotherCalendar.getTime().getTime()) {
            return -1;
        }
        return 0;
    }
}
