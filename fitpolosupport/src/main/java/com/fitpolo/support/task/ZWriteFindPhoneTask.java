package com.fitpolo.support.task;

import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.OrderEnum;
import com.fitpolo.support.entity.OrderType;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.DigitalConver;

/**
 * @Date 2018/10/8
 * @Author wenzheng.liu
 * @Description Abierto para telefono
 * @ClassPath com.fitpolo.support.task.ZWriteFindPhoneTask
 */
public class ZWriteFindPhoneTask extends OrderTask {
    private static final int ORDERDATA_LENGTH = 4;

    private byte[] orderData;

    public ZWriteFindPhoneTask(MokoOrderTaskCallback callback) {
        super(OrderType.WRITE_CHARACTER, OrderEnum.Z_WRITE_FIND_PHONE, callback, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
        orderData = new byte[ORDERDATA_LENGTH];
        orderData[0] = (byte) MokoConstants.HEADER_WRITE_SEND;
        orderData[1] = (byte) order.getOrderHeader();
        orderData[2] = (byte) 0x01;
        orderData[3] = (byte) 0x01;
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
        if (0x01 != DigitalConver.byte2Int(value[2])) {
            return;
        }

        LogModule.i(order.getOrderName() + "El éxito");
        orderStatus = OrderTask.ORDER_STATUS_SUCCESS;

        MokoSupport.getInstance().pollTask();
        callback.onOrderResult(response);
        MokoSupport.getInstance().executeTask(callback);
    }
}
