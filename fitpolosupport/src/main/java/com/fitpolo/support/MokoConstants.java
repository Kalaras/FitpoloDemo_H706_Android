package com.fitpolo.support;

/**
 * @Date 2017/5/10
 * @Author wenzheng.liu
 * @Description Constante de Bluetooth
 */
public class MokoConstants {
    // Leer encabezado de envío
    public static final int HEADER_READ_SEND = 0xB0;
    // Leer receptor
    public static final int HEADER_READ_GET = 0xB1;
    // Establecer el encabezado de envío
    public static final int HEADER_WRITE_SEND = 0xB2;
    // Establecer el encabezado de recepción
    public static final int HEADER_WRITE_GET = 0xB3;
    // Paso enviar encabezado
    public static final int HEADER_SETP_SEND = 0xB4;
    // Recibiendo encabezado
    public static final int HEADER_STEP_GET = 0xB5;
    // Emisor de frecuencia cardíaca
    public static final int HEADER_HEARTRATE_SEND = 0xB6;
    // Receptor de frecuencia cardíaca
    public static final int HEADER_HEARTRATE_GET = 0xB7;

    // Estado de descubrimiento
    public static final String ACTION_DISCOVER_SUCCESS = "com.moko.fitpolo.ACTION_DISCOVER_SUCCESS";
    public static final String ACTION_DISCOVER_TIMEOUT = "com.moko.fitpolo.ACTION_DISCOVER_TIMEOUT";
    // Desconectar
    public static final String ACTION_CONN_STATUS_DISCONNECTED = "com.moko.fitpolo.ACTION_CONN_STATUS_DISCONNECTED";
    // Resultado del comando
    public static final String ACTION_ORDER_RESULT = "com.moko.fitpolo.ACTION_ORDER_RESULT";
    public static final String ACTION_ORDER_TIMEOUT = "com.moko.fitpolo.ACTION_ORDER_TIMEOUT";
    public static final String ACTION_ORDER_FINISH = "com.moko.fitpolo.ACTION_ORDER_FINISH";
    public static final String ACTION_CURRENT_DATA = "com.moko.fitpolo.ACTION_CURRENT_DATA";

    // extra_key
    public static final String EXTRA_KEY_RESPONSE_ORDER_TASK = "EXTRA_KEY_RESPONSE_ORDER_TASK";
    public static final String EXTRA_KEY_CURRENT_DATA_TYPE = "EXTRA_KEY_CURRENT_DATA_TYPE";
}
