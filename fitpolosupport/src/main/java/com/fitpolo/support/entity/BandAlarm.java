package com.fitpolo.support.entity;

/**
 * @Date 2017/5/14 0014
 * @Author wenzheng.liu
 * @Description Pulsera reloj despertador
 * @ClassPath com.fitpolo.support.entity.BandAlarm
 */
public class BandAlarm {
    public String time;// 时间，格式：HH:mm Hora, formato: HH: mm
    // 状态 Estado
    // bit[7]：0：关闭；1：打开； bit [7]: 0: apagado; 1: encendido;
    // bit[6]：1：周日；         bit [6]: 1: domingo;
    // bit[5]：1：周六；         bit [5]: 1: sábado;
    // bit[4]：1：周五；         bit [4]: ​​1: viernes;
    // bit[3]：1：周四；         bit [3]: 1: jueves;
    // bit[2]：1：周三；         bit [2]: 1: miércoles;
    // bit[1]：1：周二；         bit [1]: 1: martes;
    // bit[0]：1：周一；         bit [0]: 1: lunes;
    // ex：每周日打开：11000000；每周一到周五打开10011111；
    // ej .: abierto todos los domingos: 11000000; abierto de lunes a viernes 10011111;
    public String state;

    // Tipo: 0: tomar medicamentos; 1: tomar agua; 3: normal; 4: dormir; 5: tomar medicamentos; 6: ejercicio
    public int type;// 类型，0：吃药；1：喝水；3：普通；4：睡觉；5：吃药；6：锻炼


    @Override
    public String toString() {
        return "BandAlarm{" +
                "time='" + time + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
