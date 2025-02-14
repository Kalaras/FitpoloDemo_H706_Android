package com.fitpolo.support.task;

import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.HeartRate;
import com.fitpolo.support.entity.OrderEnum;
import com.fitpolo.support.entity.OrderType;
import com.fitpolo.support.log.LogModule;
import com.fitpolo.support.utils.ComplexDataParse;
import com.fitpolo.support.utils.DigitalConver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @Date 2017/5/11
 * @Author wenzheng.liu
 * @Description Lectura ejercicio frecuencia cardíaca
 * @ClassPath com.fitpolo.support.task.ZReadSportsHeartRateTask
 */
public class ZReadSportsHeartRateTask extends OrderTask {
    private static final int ORDERDATA_LENGTH = 8;
    private static final int HEADER_HEART_RATE_COUNT = 0x04;
    private static final int HEADER_HEART_RATE = 0x05;

    private byte[] orderData;

    private HashMap<Integer, Boolean> heartRatesMap;
    private int heartRateCount;
    private ArrayList<HeartRate> heartRates;

    private boolean isCountSuccess;

    public ZReadSportsHeartRateTask(MokoOrderTaskCallback callback, Calendar lastSyncTime) {
        super(OrderType.HEART_RATE_CHARACTER, OrderEnum.Z_READ_SPORTS_HEART_RATE, callback, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);

        int year = lastSyncTime.get(Calendar.YEAR) - 2000;
        int month = lastSyncTime.get(Calendar.MONTH) + 1;
        int day = lastSyncTime.get(Calendar.DAY_OF_MONTH);

        int hour = lastSyncTime.get(Calendar.HOUR_OF_DAY);
        int minute = lastSyncTime.get(Calendar.MINUTE);

        orderData = new byte[ORDERDATA_LENGTH];
        orderData[0] = (byte) MokoConstants.HEADER_HEARTRATE_SEND;
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
            case HEADER_HEART_RATE_COUNT:
                isCountSuccess = true;
                byte[] count = new byte[2];
                System.arraycopy(value, 2, count, 0, 2);
                heartRateCount = DigitalConver.byteArr2Int(count);
                MokoSupport.getInstance().setSportsHeartRatesCount(heartRateCount);
                LogModule.i("Si" + heartRateCount + "Datos de frecuencia cardíaca");
                MokoSupport.getInstance().initSportsHeartRatesList();
                heartRatesMap = MokoSupport.getInstance().getSportsHeartRatesMap();
                heartRates = MokoSupport.getInstance().getSportsHeartRates();
                if (heartRateCount != 0) {
                    // Después de obtener el número, inicie la tarea de tiempo de espera
                    heartRatesMap.put(heartRateCount, false);
                    MokoSupport.getInstance().setSportsHeartRatesMap(heartRatesMap);
                    MokoSupport.getInstance().timeoutHandler(this);
                }
                break;
            case HEADER_HEART_RATE:
                if (heartRateCount > 0) {
                    if (value.length <= 2 || orderStatus == OrderTask.ORDER_STATUS_SUCCESS)
                        return;
                    if (heartRates == null) {
                        heartRates = new ArrayList<>();
                    }
                    if (heartRatesMap == null) {
                        heartRatesMap = new HashMap<>();
                    }
                    heartRatesMap.put(heartRateCount, true);
                    ComplexDataParse.parseHeartRate(value, heartRates);
                    heartRateCount--;

                    MokoSupport.getInstance().setSportsHeartRatesCount(heartRateCount);
                    MokoSupport.getInstance().setSportsHeartRates(heartRates);
                    if (heartRateCount > 0) {
                        LogModule.i("Y" + heartRateCount + "Los datos de frecuencia cardíaca no están sincronizados");
                        heartRatesMap.put(heartRateCount, false);
                        MokoSupport.getInstance().setSportsHeartRatesMap(heartRatesMap);
                        orderTimeoutHandler(heartRateCount);
                        return;
                    }
                }
                break;
            default:
                return;
        }
        if (heartRateCount != 0) {
            return;
        }
        // Discrimine los datos de frecuencia cardíaca para evitar problemas de datos causados ​​por el tiempo repetido
        HashMap<String, HeartRate> removeRepeatMap = new HashMap<>();
        for (HeartRate heartRate : heartRates) {
            removeRepeatMap.put(heartRate.time, heartRate);
        }
        if (heartRates.size() != removeRepeatMap.size()) {
            heartRates.clear();
            heartRates.addAll(removeRepeatMap.values());
        }
        MokoSupport.getInstance().setSportsHeartRatesCount(heartRateCount);
        MokoSupport.getInstance().setSportsHeartRates(heartRates);
        MokoSupport.getInstance().setSportsHeartRatesMap(heartRatesMap);
        orderStatus = OrderTask.ORDER_STATUS_SUCCESS;
        MokoSupport.getInstance().pollTask();
        callback.onOrderResult(response);
        MokoSupport.getInstance().executeTask(callback);
    }

    private void orderTimeoutHandler(final int heartRateCount) {
        MokoSupport.getInstance().getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (heartRatesMap != null
                        && !heartRatesMap.isEmpty()
                        && heartRatesMap.get(heartRateCount) != null
                        && !heartRatesMap.get(heartRateCount)) {
                    orderStatus = OrderTask.ORDER_STATUS_SUCCESS;
                    LogModule.i("Obtener frecuencia cardíaca" + heartRateCount + "Tiempo de espera de datos");
                    MokoSupport.getInstance().pollTask();
                    callback.onOrderTimeout(response);
                    MokoSupport.getInstance().executeTask(callback);
                }
            }
        }, delayTime);
    }

    @Override
    public boolean timeoutPreTask() {
        if (!isCountSuccess) {
            LogModule.i(order.getOrderName() + "Tiempo de espera");
        } else {
            return false;
        }
        return super.timeoutPreTask();
    }
}
