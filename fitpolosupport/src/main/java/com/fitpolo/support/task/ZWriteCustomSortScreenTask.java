package com.fitpolo.support.task;

import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.OrderEnum;
import com.fitpolo.support.entity.OrderType;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.DigitalConver;

import java.util.ArrayList;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description Configurar una pantalla personalizada
 * @ClassPath com.fitpolo.support.task.ZWriteCustomSortScreenTask
 */
public class ZWriteCustomSortScreenTask extends OrderTask {
    private static final int ORDERDATA_LENGTH = 20;

    private byte[] orderData;

    public ZWriteCustomSortScreenTask(MokoOrderTaskCallback callback, ArrayList<Integer> codes) {
        super(OrderType.WRITE_CHARACTER, OrderEnum.Z_WRITE_CUSTOM_SORT_SCREEN, callback, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
        orderData = new byte[ORDERDATA_LENGTH];
        orderData[0] = (byte) MokoConstants.HEADER_WRITE_SEND;
        orderData[1] = (byte) order.getOrderHeader();
        orderData[2] = (byte) 0x11;
        int size = codes.size();
        orderData[3] = (byte) size;
        for (int i = 0; i < size; i++) {
            int code = codes.get(i);
            orderData[4 + i] = (byte) code;
        }
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
        if (0x00 != DigitalConver.byte2Int(value[3])) {
            return;
        }

        LogModule.i(order.getOrderName() + "El éxito");
        orderStatus = OrderTask.ORDER_STATUS_SUCCESS;
        MokoSupport.getInstance().pollTask();
        callback.onOrderResult(response);
        MokoSupport.getInstance().executeTask(callback);
    }
}
