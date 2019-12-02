package com.fitpolo.support.task;

import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.OrderEnum;
import com.fitpolo.support.entity.OrderType;
import com.fitpolo.support.entity.SportData;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.ComplexDataParse;
import com.fitpolo.support.utils.DigitalConver;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description Leer datos de movimiento
 * @ClassPath com.fitpolo.support.task.ZReadSportsTask
 */
public class ZReadSportsTask extends OrderTask {
    private static final int ORDERDATA_LENGTH = 8;
    private static final int HEADER_SPORTS_COUNT = 0x16;
    private static final int HEADER_SPORTS_DATA = 0x17;

    private byte[] orderData;

    private int sportsCount;
    private ArrayList<SportData> sportDatas;

    private boolean isCountSuccess;
    private boolean isReceiveDetail;

    public ZReadSportsTask(MokoOrderTaskCallback callback, Calendar lastSyncTime) {
        super(OrderType.READ_CHARACTER, OrderEnum.Z_READ_SPORTS, callback, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);

        int year = lastSyncTime.get(Calendar.YEAR) - 2000;
        int month = lastSyncTime.get(Calendar.MONTH) + 1;
        int day = lastSyncTime.get(Calendar.DAY_OF_MONTH);

        int hour = lastSyncTime.get(Calendar.HOUR_OF_DAY);
        int minute = lastSyncTime.get(Calendar.MINUTE);

        orderData = new byte[ORDERDATA_LENGTH];
        orderData[0] = (byte) MokoConstants.HEADER_READ_SEND;
        orderData[1] = (byte) order.getOrderHeader();
        orderData[2] = (byte) 0x05;
        orderData[3] = (byte) year;
        orderData[4] = (byte) month;
        orderData[5] = (byte) day;
        orderData[6] = (byte) hour;
        orderData[7] = (byte) minute;
    }

    @Override
    public byte[] assemble() {
        return orderData;
    }

    @Override
    public void parseValue(byte[] value) {
        int header = DigitalConver.byte2Int(value[1]);
        LogModule.i(order.getOrderName() + "El éxito");
        switch (header) {
            case HEADER_SPORTS_COUNT:
                int data_length = DigitalConver.byte2Int(value[2]);
                if (data_length != 2) {
                    return;
                }
                isCountSuccess = true;
                byte[] count = new byte[2];
                System.arraycopy(value, 3, count, 0, 2);
                sportsCount = DigitalConver.byteArr2Int(count);
                MokoSupport.getInstance().setSportsCount(sportsCount);
                LogModule.i("Si" + sportsCount + "Datos de movimiento");
                MokoSupport.getInstance().initSportDatas();
                sportDatas = MokoSupport.getInstance().getSportDatas();
                delayTime = sportsCount == 0 ? DEFAULT_DELAY_TIME : DEFAULT_DELAY_TIME + 100 * sportsCount;
                // Después de obtener el número, inicie la tarea de tiempo de espera
                MokoSupport.getInstance().timeoutHandler(this);
                break;
            case HEADER_SPORTS_DATA:
                if (sportsCount > 0) {
                    if (sportDatas == null) {
                        sportDatas = new ArrayList<>();
                    }
                    ComplexDataParse.parseSportData(value, sportDatas);
                    sportsCount--;
                    MokoSupport.getInstance().setSportDatas(sportDatas);
                    MokoSupport.getInstance().setSportsCount(sportsCount);
                    if (sportsCount > 0) {
                        LogModule.i("Y" + sportsCount + "Datos de movimiento fuera de sincronización");
                        return;
                    }
                }
                break;
            default:
                return;
        }
        if (sportsCount != 0) {
            return;
        }
        MokoSupport.getInstance().setSportsCount(sportsCount);
        MokoSupport.getInstance().setSportDatas(sportDatas);
        orderStatus = OrderTask.ORDER_STATUS_SUCCESS;
        MokoSupport.getInstance().pollTask();
        callback.onOrderResult(response);
        MokoSupport.getInstance().executeTask(callback);
    }

    @Override
    public boolean timeoutPreTask() {
        if (!isReceiveDetail) {
            if (!isCountSuccess) {
                LogModule.i(order.getOrderName() + "Tiempo de espera");
            } else {
                isReceiveDetail = true;
                return false;
            }
        }
        return super.timeoutPreTask();
    }
}
