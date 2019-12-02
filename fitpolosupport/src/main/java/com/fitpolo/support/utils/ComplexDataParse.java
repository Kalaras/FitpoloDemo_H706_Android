package com.fitpolo.support.utils;

import com.fitpolo.support.entity.DailyDetailStep;
import com.fitpolo.support.entity.DailySleep;
import com.fitpolo.support.entity.DailyStep;
import com.fitpolo.support.entity.HeartRate;
import com.fitpolo.support.entity.SportData;
import com.fitpolo.support.log.LogModule;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * @Date 2018/4/6
 * @Author wenzheng.liu
 * @Description Clase de análisis de datos complejos.
 * @ClassPath com.fitpolo.support.utils.ComplexDataParse
 */
public class ComplexDataParse {
    public static DailyStep parseDailyStep(byte[] value, int index) {
        // Fecha
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000 + DigitalConver.byte2Int(value[index]),
                DigitalConver.byte2Int(value[index + 1]) - 1,
                DigitalConver.byte2Int(value[index + 2]));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        String dateStr = sdf.format(date);
        // Pasos
        byte[] step = new byte[4];
        System.arraycopy(value, index + 3, step, 0, 4);
        String stepStr = DigitalConver.byteArr2Str(step);

        // Duración
        byte[] duration = new byte[2];
        System.arraycopy(value, index + 7, duration, 0, 2);
        String durationStr = DigitalConver.byteArr2Str(duration);

        // Distancia
        byte[] distance = new byte[2];
        System.arraycopy(value, index + 9, distance, 0, 2);
        String distanceStr = new DecimalFormat().format(DigitalConver.byteArr2Int(distance) * 0.1);
        // Calorías
        byte[] calories = new byte[2];
        System.arraycopy(value, index + 11, calories, 0, 2);
        String caloriesStr = DigitalConver.byteArr2Str(calories);

        DailyStep dailyStep = new DailyStep();
        dailyStep.date = dateStr;
        dailyStep.count = stepStr;
        dailyStep.duration = durationStr;
        dailyStep.distance = distanceStr;
        dailyStep.calories = caloriesStr;
        LogModule.i(dailyStep.toString());
        return dailyStep;
    }

    public static void parseDailyDetailStep(byte[] value, ArrayList<DailyDetailStep> dailyDetailSteps) {
        // Fecha
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 2; i++) {
            int year = DigitalConver.byte2Int(value[i * 7 + 4]);
            int month = DigitalConver.byte2Int(value[i * 7 + 5]);
            int day = DigitalConver.byte2Int(value[i * 7 + 6]);
            int hour = DigitalConver.byte2Int(value[i * 7 + 7]);
            int minute = DigitalConver.byte2Int(value[i * 7 + 8]);
            if (year == 0 && month == 0 && day == 0) {
                continue;
            }
            calendar.set(2000 + year, month - 1, day, hour, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            Date date = calendar.getTime();
            String dateStr = sdf.format(date);
            // Pasos
            byte[] step = new byte[2];
            System.arraycopy(value, i * 7 + 9, step, 0, 2);
            String stepStr = DigitalConver.byteArr2Str(step);

            DailyDetailStep dailyDetailStep = new DailyDetailStep();
            dailyDetailStep.date = dateStr;
            dailyDetailStep.count = stepStr;
            LogModule.i(dailyDetailStep.toString());
            dailyDetailSteps.add(dailyDetailStep);
        }
    }


    public static DailyStep parseCurrentStep(byte[] value) {
        if (0xb5 != DigitalConver.byte2Int(value[0])
                || 0x04 != DigitalConver.byte2Int(value[1])
                || 0x0a != DigitalConver.byte2Int(value[2])) {
            return null;
        }
        // Fecha
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = calendar.getTime();
        String dateStr = sdf.format(date);
        // Pasos
        byte[] step = new byte[4];
        System.arraycopy(value, 3, step, 0, 4);
        String stepStr = DigitalConver.byteArr2Str(step);

        // Duración
        byte[] duration = new byte[2];
        System.arraycopy(value, 7, duration, 0, 2);
        String durationStr = DigitalConver.byteArr2Str(duration);

        // Distancia
        byte[] distance = new byte[2];
        System.arraycopy(value, 9, distance, 0, 2);
        String distanceStr = new DecimalFormat().format(DigitalConver.byteArr2Int(distance) * 0.1);
        // Calorías
        byte[] calories = new byte[2];
        System.arraycopy(value, 11, calories, 0, 2);
        String caloriesStr = DigitalConver.byteArr2Str(calories);

        DailyStep dailyStep = new DailyStep();
        dailyStep.date = dateStr;
        dailyStep.count = stepStr;
        dailyStep.duration = durationStr;
        dailyStep.distance = distanceStr;
        dailyStep.calories = caloriesStr;
        return dailyStep;
    }

    public static DailySleep parseDailySleepIndex(byte[] value, HashMap<Integer, DailySleep> sleepsMap, int index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        // Hora de inicio
        calendar.set(2000 + DigitalConver.byte2Int(value[index + 1]),
                DigitalConver.byte2Int(value[index + 2]) - 1,
                DigitalConver.byte2Int(value[index + 3]),
                DigitalConver.byte2Int(value[index + 4]),
                DigitalConver.byte2Int(value[index + 5]));
        Date startDate = calendar.getTime();
        String startDateStr = sdf.format(startDate);
        // Hora final
        calendar.set(2000 + DigitalConver.byte2Int(value[index + 6]),
                DigitalConver.byte2Int(value[index + 7]) - 1,
                DigitalConver.byte2Int(value[index + 8]),
                DigitalConver.byte2Int(value[index + 9]),
                DigitalConver.byte2Int(value[index + 10]));
        Date endDate = calendar.getTime();
        String endDateStr = sdf.format(endDate);
        // Sueño profundo
        byte[] deep = new byte[2];
        System.arraycopy(value, index + 11, deep, 0, 2);
        String deepStr = DigitalConver.byteArr2Str(deep);
        // Sueño ligero
        byte[] light = new byte[2];
        System.arraycopy(value, index + 13, light, 0, 2);
        String lightStr = DigitalConver.byteArr2Str(light);
        // Sobrio
        byte[] awake = new byte[2];
        System.arraycopy(value, index + 15, awake, 0, 2);
        String awakeStr = DigitalConver.byteArr2Str(awake);

        // Registrar fecha de sueño
        String date = new SimpleDateFormat("yyy-MM-dd").format(endDate);

        // Construyendo datos de sueño
        DailySleep dailySleep = new DailySleep();
        dailySleep.date = date;
        dailySleep.startTime = startDateStr;
        dailySleep.endTime = endDateStr;
        dailySleep.deepDuration = deepStr;
        dailySleep.lightDuration = lightStr;
        dailySleep.awakeDuration = awakeStr;
        dailySleep.records = new ArrayList<>();
        LogModule.i(dailySleep.toString());
        // Datos de suspensión temporales, con índice como clave e instancia como valor, para una fácil actualización de registros;
        sleepsMap.put(DigitalConver.byte2Int(value[index]), dailySleep);
        return dailySleep;
    }

    public static void parseDailySleepRecord(byte[] value, HashMap<Integer, DailySleep> mSleepsMap, int index) {
        DailySleep dailySleep = mSleepsMap.get(DigitalConver.byte2Int(value[index]));
        if (dailySleep != null) {
            int len = DigitalConver.byte2Int(value[index + 2]);
            if (dailySleep.records == null) {
                dailySleep.records = new ArrayList<>();
            }
            for (int i = 0; i < len && index + 3 + i < value.length; i++) {
                String hex = DigitalConver.byte2HexString(value[index + 3 + i]);
                // Convertir a binario
                String binary = DigitalConver.hexString2binaryString(hex);
                for (int j = binary.length(); j > 0; ) {
                    j -= 2;
                    String status = binary.substring(j, j + 2);
                    dailySleep.records.add(status);
                }
            }
            LogModule.i(dailySleep.toString());
        }
    }

    public static void parseHeartRate(byte[] value, ArrayList<HeartRate> heartRates) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 3; i++) {
            byte year = value[i * 6 + 2];
            byte month = value[i * 6 + 3];
            byte day = value[i * 6 + 4];
            byte hour = value[i * 6 + 5];
            byte min = value[i * 6 + 6];
            byte heartRateValue = value[i * 6 + 7];
            if (DigitalConver.byte2Int(year) == 0) {
                continue;
            }
            calendar.set(2000 + DigitalConver.byte2Int(year),
                    DigitalConver.byte2Int(month) - 1,
                    DigitalConver.byte2Int(day),
                    DigitalConver.byte2Int(hour),
                    DigitalConver.byte2Int(min));
            Date time = calendar.getTime();
            String heartRateTime = sdf.format(time);
            String heartRateStr = DigitalConver.byte2Int(heartRateValue) + "";
            HeartRate heartRate = new HeartRate();
            heartRate.time = heartRateTime;
            heartRate.value = heartRateStr;
            LogModule.i(heartRate.toString());
            heartRates.add(heartRate);
        }
    }

    public static void parseSportData(byte[] value, ArrayList<SportData> sportDatas) {
        // Modo deportivo
        int mode = DigitalConver.byte2Int(value[2]);
        // Hora de inicio
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000 + DigitalConver.byte2Int(value[4]),
                DigitalConver.byte2Int(value[5]) - 1,
                DigitalConver.byte2Int(value[6]),
                DigitalConver.byte2Int(value[7]),
                DigitalConver.byte2Int(value[8]));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = calendar.getTime();
        String startTimeStr = sdf.format(date);

        // Duración
        byte[] duration = new byte[2];
        System.arraycopy(value, 9, duration, 0, 2);
        String durationStr = DigitalConver.byteArr2Str(duration);

        // Pasos
        byte[] sportCount = new byte[3];
        System.arraycopy(value, 11, sportCount, 0, 3);
        String sportCountStr = DigitalConver.byteArr2Str(sportCount);
        // Calorías
        byte[] calories = new byte[2];
        System.arraycopy(value, 14, calories, 0, 2);
        String caloriesStr = DigitalConver.byteArr2Str(calories);

        // Ritmo
        byte[] speed = new byte[2];
        System.arraycopy(value, 16, speed, 0, 2);
        String speedStr = DigitalConver.byteArr2Str(speed);

        // Distancia
        byte[] distance = new byte[2];
        System.arraycopy(value, 18, distance, 0, 2);
        String distanceStr = new DecimalFormat("#.##").format(DigitalConver.byteArr2Int(distance) * 0.01);


        SportData sportData = new SportData();
        sportData.sportMode = mode;
        sportData.startTime = startTimeStr;
        sportData.duration = durationStr;
        sportData.sportCount = sportCountStr;
        sportData.calories = caloriesStr;
        sportData.speed = speedStr;
        sportData.distance = distanceStr;
        LogModule.i(sportData.toString());
        sportDatas.add(sportData);
    }
}
