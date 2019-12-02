package com.fitpolo.support.callback;

import com.fitpolo.support.entity.BleDevice;

/**
 * @Date 2017/5/10
 * @Author wenzheng.liu
 * @Description Escanear devolución de llamada del dispositivo
 * @ClassPath com.fitpolo.support.callback.MokoScanDeviceCallback
 */
public interface MokoScanDeviceCallback {
    /**
     * @Date 2017/5/10
     * @Author wenzheng.liu
     * @Description Comience a escanear
     */
    void onStartScan();

    /**
     * @Date 2017/5/10
     * @Author wenzheng.liu
     * @Description Dispositivos escaneados
     */
    void onScanDevice(BleDevice device);

    /**
     * @Date 2017/5/10
     * @Author wenzheng.liu
     * @Description Fin de escaneo
     */
    void onStopScan();
}
