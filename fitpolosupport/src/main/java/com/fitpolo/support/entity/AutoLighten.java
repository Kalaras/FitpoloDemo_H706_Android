package com.fitpolo.support.entity;

/**
 * @Date 2018/4/9
 * @Author wenzheng.liu
 * @Description Enciende tu muñeca
 * @ClassPath com.fitpolo.support.entity.AutoLighten
 */
public class AutoLighten {
    public int autoLighten; // Encienda el interruptor de pantalla brillante de la muñeca, 1: encendido; 0: apagado;
    public String startTime;//Hora de inicio, formato: HH: mm;
    public String endTime;// Hora de finalización, formato: HH: mm;


    @Override
    public String toString() {
        return "AutoLighten{" +
                "autoLighten=" + autoLighten +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
