package com.fitpolo.support.entity;

/**
 * @Date 2018/4/9
 * @Author wenzheng.liu
 * @Description No molestar
 * @ClassPath com.fitpolo.support.entity.NoDisturb
 */
public class NoDisturb {
    public int noDisturb; // Interruptor de modo No molestar, 1: encendido; 0: apagado;
    public String startTime;// Hora de inicio, formato: HH: mm;
    public String endTime;// Hora de finalización, formato: HH: mm;

    @Override
    public String toString() {
        return "NoDisturb{" +
                "noDisturb=" + noDisturb +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
