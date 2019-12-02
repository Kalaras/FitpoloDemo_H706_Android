package com.fitpolo.support.entity;

import java.io.Serializable;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description Enumeración de comandos
 * @ClassPath com.fitpolo.support.entity.OrderEnum
 */
public enum OrderEnum implements Serializable {
    READ_NOTIFY("打开读取通知", 0), // Abrir notificación de lectura
    WRITE_NOTIFY("打开设置通知", 0), // Notificación de configuración abierta
    STEP_NOTIFY("打开记步通知", 0), // Activa las notificaciones de pasos
    HEART_RATE_NOTIFY("打开心率通知", 0), // Activa las notificaciones de frecuencia cardíaca

    Z_READ_ALARMS("读取闹钟", 0x01), // Alarma de lectura
    Z_READ_SIT_ALERT("读取久坐提醒", 0x04), // Leer recordatorio sedentario
    Z_READ_STEP_TARGET("读取记步目标", 0x06), // Leer paso objetivo
    Z_READ_UNIT_TYPE("读取单位类型", 0x07), // Leer tipo de unidad
    Z_READ_TIME_FORMAT("读取时间格式", 0x08), // Leer formato de hora
    Z_READ_LAST_SCREEN("读取显示上次屏幕", 0x0A), // Leer mostrar última pantalla
    Z_READ_HEART_RATE_INTERVAL("读取心率间隔", 0x0B), // Leer intervalo de frecuencia cardíaca
    Z_READ_AUTO_LIGHTEN("读取翻腕亮屏", 0x0D),  // Leer la pantalla brillante de la muñeca
    Z_READ_USER_INFO("读取个人信息", 0x0E),  // Leer información personal
    Z_READ_PARAMS("读取硬件参数", 0x10),  // Leer parámetros de hardware
    Z_READ_VERSION("读取版本", 0x11),  // Leer versión
    Z_READ_SLEEP_GENERAL("读取睡眠概况", 0x12),  // Leer perfil de sueño
    Z_READ_SLEEP_DETAIL("读取睡眠详情", 0x14),  // Leer detalles del sueño
    Z_READ_LAST_CHARGE_TIME("读取上次充电时间", 0x18),  // Leer el último tiempo de carga
    Z_READ_BATTERY("读取电量", 0x19),  // Leer la batería
    Z_READ_DIAL("读取表盘", 0x0F),  // Lee el dial
    Z_READ_SHAKE_STRENGTH("读取震动强度", 0x1E),  // Lectura de intensidad de vibración
    Z_READ_DATE_FORMAT("读取日期制式", 0x1D),  // Leer formato de fecha
    Z_READ_CUSTOM_SORT_SCREEN("读取自定义屏幕排序", 0x1F),  // Leer clasificación de pantalla personalizada
    Z_READ_NODISTURB("读取勿扰模式", 0x0C),  // Leer no molestar
    Z_READ_SPORTS("读取运动数据", 0x16),  // Leer datos de movimiento
    Z_WRITE_FIND_PHONE("打开寻找手机", 0x16),  // Abierto para telefono
    Z_WRITE_LANGUAGE("设置手环语言", 0x1B),  // Establece el idioma de tu pulsera

    Z_WRITE_ALARMS("设置闹钟", 0x01),  // Establecer alarma
    Z_WRITE_SIT_ALERT("设置久坐提醒", 0x04),  // Establecer recordatorio sedentario
    Z_WRITE_STEP_TARGET("设置记步目标", 0x06),  // Establece tu objetivo de paso
    Z_WRITE_UNIT_TYPE("设置单位类型", 0x07),  // Establecer el tipo de unidad
    Z_WRITE_TIME_FORMAT("设置时间格式", 0x08),  // Establecer formato de hora
    Z_WRITE_LAST_SCREEN("设置显示上次屏幕", 0x0A),  // Configurar para mostrar la última pantalla
    Z_WRITE_HEART_RATE_INTERVAL("设置心率间隔", 0x0B),  // Establecer el intervalo de frecuencia cardíaca
    Z_WRITE_AUTO_LIGHTEN("设置翻腕亮屏", 0x0D),  // Configurar la pantalla brillante de la muñeca
    Z_WRITE_USER_INFO("设置个人信息", 0x0E),  // Establecer información personal
    Z_WRITE_SYSTEM_TIME("设置系统时间", 0x0F),  // Establecer la hora del sistema
    Z_WRITE_SHAKE("设置手环震动", 0x13),  // Configura la pulsera para que vibre
    Z_WRITE_NOTIFY("设置通知", 0x14),  // Configurar notificaciones
    Z_WRITE_COMMON_NOTIFY("设置通用通知", 0x1F),  // Configurar notificaciones generales
    Z_WRITE_DIAL("设置表盘", 0x10),  // Configurar la esfera del reloj
    Z_WRITE_SHAKE_STRENGTH("设置震动强度", 0x1D),  // Establecer intensidad de vibración
    Z_WRITE_DATE_FORMAT("设置日期制式", 0x1C),  // Establecer el formato de fecha
    Z_WRITE_CUSTOM_SORT_SCREEN("设置自定义屏幕排序", 0x1E),  // Establecer clasificación de pantalla personalizada
    Z_WRITE_NODISTURB("设置勿扰模式", 0x0C),  // Establecer no molestar
    Z_WRITE_STEP_INTERVAL("设置记步间隔", 0x21),  // Establecer el intervalo de paso

    Z_READ_STEPS("读取记步", 0x01),  // Pasos de lectura
    Z_STEPS_CHANGES_LISTENER("监听记步", 0x03),  // Escucha los pasos
    Z_READ_STEP_INTERVAL("读取记步间隔", 0x05),  // Leer intervalo de paso
    Z_READ_HEART_RATE("读取心率", 0x01),  // Leer frecuencia cardíaca
    Z_READ_SPORTS_HEART_RATE("读取运动心率", 0x04),  // Lectura ejercicio frecuencia cardíaca

    RESET_DATA("恢复出厂设置", 0x0A),  // Restablecimiento de fábrica
    Z_WRITE_CLOSE("关机", 0x12),  // Apagado
    Z_WRITE_RESET("恢复出厂设置", 0x20),  // Restablecimiento de fábrica
    ;


    private String orderName;
    private int orderHeader;

    OrderEnum(String orderName, int orderHeader) {
        this.orderName = orderName;
        this.orderHeader = orderHeader;
    }

    public int getOrderHeader() {
        return orderHeader;
    }

    public String getOrderName() {
        return orderName;
    }
}
