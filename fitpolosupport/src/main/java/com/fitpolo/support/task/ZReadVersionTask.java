package com.fitpolo.support.task;

import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.FirmwareEnum;
import com.fitpolo.support.entity.OrderEnum;
import com.fitpolo.support.entity.OrderType;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.DigitalConver;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description Leer versión
 * @ClassPath com.fitpolo.support.task.ZReadVersionTask
 */
public class ZReadVersionTask extends OrderTask {
    private static final int ORDERDATA_LENGTH = 3;

    private byte[] orderData;

    public ZReadVersionTask(MokoOrderTaskCallback callback) {
        super(OrderType.READ_CHARACTER, OrderEnum.Z_READ_VERSION, callback, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
        orderData = new byte[ORDERDATA_LENGTH];
        orderData[0] = (byte) MokoConstants.HEADER_READ_SEND;
        orderData[1] = (byte) order.getOrderHeader();
        orderData[2] = (byte) 0x00;
    }

    @Override
    public byte[] assemble() {
        return orderData;
    }

    @Override
    public void parseValue(byte[] value) {
        if (order.getOrderHeader() != DigitalConver.byte2Int(value[1])) {
            return;
        }
        if (0x06 != DigitalConver.byte2Int(value[2])) {
            return;
        }
        LogModule.i(order.getOrderName() + "El éxito");
        orderStatus = OrderTask.ORDER_STATUS_SUCCESS;
        byte[] preCode = new byte[2];
        System.arraycopy(value, 3, preCode, 0, 2);
        byte[] middleCode = new byte[2];
        System.arraycopy(value, 5, middleCode, 0, 2);
        byte[] lastCode = new byte[2];
        System.arraycopy(value, 7, lastCode, 0, 2);

        String versionStr = DigitalConver.bytesToHexString(preCode) + "." + DigitalConver.bytesToHexString(middleCode) + "." + DigitalConver.bytesToHexString(lastCode);
        // Versión interna, utilizada para juzgar la actualización
        MokoSupport.versionCode = versionStr;
        // Versión externa, para clientes
        MokoSupport.versionCodeShow = versionStr;
        // Número de versión grande para distinguir el firmware de actualización
        MokoSupport.firmwareEnum = FirmwareEnum.fromHeader(DigitalConver.bytesToHexString(preCode));
        if (MokoSupport.firmwareEnum == null) {
            return;
        }
        // Número de versión pequeño para determinar si algunas funciones están disponibles
        MokoSupport.versionCodeLast = DigitalConver.byteArr2Int(lastCode);
        LogModule.i("Fin del número de versión：" + MokoSupport.versionCodeLast);
        // Determinar si actualizar
        MokoSupport.canUpgrade = MokoSupport.versionCodeLast < MokoSupport.firmwareEnum.getLastestVersion();

        MokoSupport.getInstance().pollTask();
        callback.onOrderResult(response);
        MokoSupport.getInstance().executeTask(callback);
    }
}
