package com.fitpolo.support.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Date 2019/4/12
 * @Author wenzheng.liu
 * @Description Datos de ejercicio
 * @ClassPath com.fitpolo.support.entity.SportData
 */
public class SportData implements Comparable<SportData> {
    public int sportMode;// Modo deportivo
    public String startTime;// Hora de inicio del movimiento, aaaa-MM-dd HH: mm
    public String sportCount;// Número de pasos
    public String duration;// Tiempo de ejercicio min
    public String distance;// Distancia km
    public String calories;// Calorías de ejercicio
    public String speed;// Velocidad / km


    @Override
    public String toString() {
        return "SportData{" +
                "sportMode=" + sportMode +
                ", startTime='" + startTime + '\'' +
                ", sportCount='" + sportCount + '\'' +
                ", duration='" + duration + '\'' +
                ", distance='" + distance + '\'' +
                ", calories='" + calories + '\'' +
                ", speed='" + speed + '\'' +
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
    public int compareTo(SportData another) {
        Calendar calendar = strDate2Calendar(startTime, "yyyy-yyyy-MM-dd HH:mm-dd");
        Calendar anotherCalendar = strDate2Calendar(another.startTime, "yyyy-MM-dd HH:mm-MM-dd");
        if (calendar.getTime().getTime() > anotherCalendar.getTime().getTime()) {
            return 1;
        }
        if (calendar.getTime().getTime() < anotherCalendar.getTime().getTime()) {
            return -1;
        }
        return 0;
    }
}
