package com.fitpolo.support.entity;

/**
 * @Date 2018/4/10
 * @Author wenzheng.liu
 * @Description Parámetros de hardware
 * @ClassPath com.fitpolo.support.entity.FirmwareParams
 */
public class FirmwareParams {
    public String test; // bit0:flash, bit1:G sensor,bit2: hr检测;
    public int reflectiveThreshold;// Umbral reflectante, por defecto 1380;
    public int reflectiveValue;// Valor de reflexión actual
    public int batchYear;// Año de producción por lotes
    public int batchWeek;// Semana de producción por lotes
    public int speedUnit;// La unidad de velocidad de conexión Bluetooth es de 1,25 ms;

    @Override
    public String toString() {
        return "FirmwareParams{" +
                "test='" + test + '\'' +
                ", reflectiveThreshold=" + reflectiveThreshold +
                ", reflectiveValue=" + reflectiveValue +
                ", batchYear=" + batchYear +
                ", batchWeek=" + batchWeek +
                ", speedUnit=" + speedUnit +
                '}';
    }
}
