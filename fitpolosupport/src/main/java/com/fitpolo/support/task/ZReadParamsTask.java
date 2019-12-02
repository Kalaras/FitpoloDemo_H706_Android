package com.fitpolo.support.task;

import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.FirmwareParams;
import com.fitpolo.support.entity.OrderEnum;
import com.fitpolo.support.entity.OrderType;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.DigitalConver;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description Leer parámetros de hardware
 * @ClassPath com.fitpolo.support.task.ZReadParamsTask
 */
public class ZReadParamsTask extends OrderTask {
    private static final int ORDERDATA_LENGTH = 3;

    private byte[] orderData;

    public ZReadParamsTask(MokoOrderTaskCallback callback) {
        super(OrderType.READ_CHARACTER, OrderEnum.Z_READ_PARAMS, callback, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
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
        if (0x08 != DigitalConver.byte2Int(value[2])) {
            return;
        }
        LogModule.i(order.getOrderName() + "El éxito");
        orderStatus = OrderTask.ORDER_STATUS_SUCCESS;

        FirmwareParams params = new FirmwareParams();
        params.test = DigitalConver.byte2binaryString(value[3]);
        byte[] reflectiveThreshold = new byte[2];
        System.arraycopy(value, 4, reflectiveThreshold, 0, 2);
        params.reflectiveThreshold = DigitalConver.byteArr2Int(reflectiveThreshold);
        byte[] reflectiveValue = new byte[2];
        System.arraycopy(value, 6, reflectiveValue, 0, 2);
        params.reflectiveValue = DigitalConver.byteArr2Int(reflectiveValue);
        params.batchYear = 2000 + DigitalConver.byte2Int(value[8]);
        params.batchWeek = DigitalConver.byte2Int(value[9]);
        params.speedUnit = DigitalConver.byte2Int(value[10]);

        LogModule.i("estado de flash：" + params.test.substring(7, 8));
        LogModule.i("Estado del sensor G：" + params.test.substring(6, 7));
        LogModule.i("estado de la hora：" + params.test.substring(5, 6));
        LogModule.i("Umbral reflexivo actual：" + params.reflectiveThreshold);
        LogModule.i("Valor de reflexión actual：" + params.reflectiveValue);
        LogModule.i("Año de producción por lotes：" + params.batchYear);
        LogModule.i("Semana de producción por lotes：" + params.batchWeek);
        LogModule.i("Unidad de velocidad de conexión Bluetooth：" + params.speedUnit);

        MokoSupport.getInstance().setProductBatch(String.format("%d.%d", params.batchYear, params.batchWeek));
        MokoSupport.getInstance().setParams(params);

        MokoSupport.getInstance().pollTask();
        callback.onOrderResult(response);
        MokoSupport.getInstance().executeTask(callback);
    }
}
