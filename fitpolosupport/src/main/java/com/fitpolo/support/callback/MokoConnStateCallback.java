package com.fitpolo.support.callback;

/**
 * @Date 2017/5/10
 * @Author wenzheng.liu
 * @Description Devolución de llamada de conexión de pantalla frontal
 * @ClassPath com.fitpolo.support.callback.MokoConnStateCallback
 */
public interface MokoConnStateCallback {
    /**
     * @Date 2017/5/10
     * @Author wenzheng.liu
     * @Description Conectado exitosamente
     */
    void onConnectSuccess();

    /**
     * @Date 2017/5/10
     * @Author wenzheng.liu
     * @Description Desconectar
     */
    void onDisConnected();

    /**
     * @Date 2017/8/29
     * @Author wenzheng.liu
     * @Description Reconectar tiempo de espera
     */
    void onConnTimeout(int reConnCount);

    /**
     * @Date 2018/10/8
     * @Author wenzheng.liu
     * @Description Buscando un telefono
     */
    void onFindPhone();
}
