package com.fitpolo.support.handler;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;

import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoResponseCallback;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.DigitalConver;

/**
 * @Date 2017/5/10
 * @Author wenzheng.liu
 * @Description Devolución de llamada de conexión Bluetooth personalizada
 * @ClassPath com.moko.support.handler.MokoConnStateHandler
 */
public class MokoConnStateHandler extends BluetoothGattCallback {

    private static volatile MokoConnStateHandler INSTANCE;

    private MokoResponseCallback mMokoResponseCallback;
    private MokoSupport.ServiceMessageHandler mHandler;

    public MokoConnStateHandler() {
    }

    public static MokoConnStateHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (MokoConnStateHandler.class) {
                if (INSTANCE == null) {
                    LogModule.i("Crear BluetoothGattCallback！");
                    INSTANCE = new MokoConnStateHandler();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        LogModule.e("onConnectionStateChange");
        LogModule.e("status : " + status);
        LogModule.e("newState : " + newState);
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                mHandler.sendEmptyMessage(MokoSupport.HANDLER_MESSAGE_WHAT_CONNECTED);
                return;
            }
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            mHandler.sendEmptyMessage(MokoSupport.HANDLER_MESSAGE_WHAT_DISCONNECTED);
            return;
        }
        mHandler.sendEmptyMessage(MokoSupport.HANDLER_MESSAGE_WHAT_DISCONNECTED);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        LogModule.e("onServicesDiscovered");
        LogModule.e("status : " + status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            mHandler.sendEmptyMessage(MokoSupport.HANDLER_MESSAGE_WHAT_SERVICES_DISCOVERED);
        } else {
            mHandler.sendEmptyMessage(MokoSupport.HANDLER_MESSAGE_WHAT_DISCONNECTED);
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        LogModule.e("onCharacteristicChanged");
        LogModule.e("device to app : " + DigitalConver.bytesToHexString(characteristic.getValue()));
        mMokoResponseCallback.onCharacteristicChanged(characteristic, characteristic.getValue());
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        LogModule.e("device to app : " + DigitalConver.bytesToHexString(characteristic.getValue()));
        mMokoResponseCallback.onCharacteristicWrite(characteristic.getValue());
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
        LogModule.e("device to app : " + DigitalConver.bytesToHexString(characteristic.getValue()));
        mMokoResponseCallback.onCharacteristicRead(characteristic.getValue());
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        mMokoResponseCallback.onDescriptorWrite();
    }

    public void setMokoResponseCallback(MokoResponseCallback mMokoResponseCallback) {
        this.mMokoResponseCallback = mMokoResponseCallback;
    }

    public void setMessageHandler(MokoSupport.ServiceMessageHandler messageHandler) {
        this.mHandler = messageHandler;
    }
}
