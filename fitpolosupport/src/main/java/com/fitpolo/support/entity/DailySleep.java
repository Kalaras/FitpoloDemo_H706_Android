package com.fitpolo.support.entity;

import java.util.List;

/**
 * @Date 2017/5/15
 * @Author wenzheng.liu
 * @Description Datos del sueño
 * @ClassPath com.fitpolo.support.entity.DailySleep
 */
public class DailySleep {
    public String date;// Fecha, aaaa-MM-dd
    public String startTime;// Hora de inicio, aaaa-MM-dd HH: mm
    public String endTime;// Hora de finalización, aaaa-MM-dd HH: mm
    public String deepDuration;// Duración del sueño profundo, unidad min.
    public String lightDuration;// Duración ligera del sueño, unidad min.
    public String awakeDuration;// Despertar duración en min
    public List<String> records;// Registro de sueño


    @Override
    public String toString() {
        return "DailySleep{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", deepDuration='" + deepDuration + '\'' +
                ", lightDuration='" + lightDuration + '\'' +
                ", awakeDuration='" + awakeDuration + '\'' +
                ", record='" + records + '\'' +
                '}';
    }
}
