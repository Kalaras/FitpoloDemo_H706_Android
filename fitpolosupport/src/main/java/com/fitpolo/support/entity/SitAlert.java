package com.fitpolo.support.entity;

/**
 * @Date 2017/5/14 0014
 * @Author wenzheng.liu
 * @Description Recordatorio sedentario
 * @ClassPath com.fitpolo.support.entity.SitAlert
 */
public class SitAlert {
    public int alertSwitch; // Interruptor recordatorio sedentario, 1: encendido; 0: apagado;
    public String startTime;// Hora de inicio, formato: HH: mm;
    public String endTime;// Hora de finalización, formato: HH: mm;

    @Override
    public String toString() {
        return "SitAlert{" +
                "alertSwitch=" + alertSwitch +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
