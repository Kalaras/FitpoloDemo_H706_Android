package com.fitpolo.demo.h706.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;

import com.fitpolo.demo.h706.AppConstants;
import com.fitpolo.support.MokoConstants;
import com.fitpolo.support.MokoSupport;
import com.fitpolo.support.callback.MokoConnStateCallback;
import com.fitpolo.support.callback.MokoOrderTaskCallback;
import com.fitpolo.support.entity.OrderTaskResponse;
import com.fitpolo.support.handler.BaseMessageHandler;
import com.fitpolo.support.log.LogModule;

/**
 * @Date 2017/5/17
 * @Author wenzheng.liu
 * @Description
 * @ClassPath com.fitpolo.demo.h706.service.MokoService
 */
public class MokoService extends Service implements MokoConnStateCallback, MokoOrderTaskCallback {
    @Override
    public void onConnectSuccess() {
        Intent intent = new Intent(MokoConstants.ACTION_DISCOVER_SUCCESS);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    public void onDisConnected() {
        Intent intent = new Intent(MokoConstants.ACTION_CONN_STATUS_DISCONNECTED);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    public void onConnTimeout(int reConnCount) {
        Intent intent = new Intent(MokoConstants.ACTION_DISCOVER_TIMEOUT);
        intent.putExtra(AppConstants.EXTRA_CONN_COUNT, reConnCount);
        sendBroadcast(intent);
    }

    private MediaPlayer mediaPlayer;

    @Override
    public void onFindPhone() {
        Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        if (vib.hasVibrator()) {
            vib.vibrate(new long[]{500, 1000, 500, 1000, 500, 1000, 500, 1000}, -1);
        }
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
            if (mediaPlayer == null) {
                LogModule.i("No hay tono de llamada establecido");
                return;
            }
            mediaPlayer.start();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }, 10000);

            return;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
            mediaPlayer.start();
            mHandler.removeMessages(0);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }, 10000);
        } else {
            mediaPlayer = MediaPlayer.create(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
            if (mediaPlayer == null) {
                LogModule.i("No hay tono de llamada establecido");
                return;
            }
            mediaPlayer.start();
            mHandler.removeMessages(0);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }, 10000);
        }
    }

    @Override
    public void onOrderResult(OrderTaskResponse response) {

        Intent intent = new Intent(new Intent(MokoConstants.ACTION_ORDER_RESULT));
        intent.putExtra(MokoConstants.EXTRA_KEY_RESPONSE_ORDER_TASK, response);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    public void onOrderTimeout(OrderTaskResponse response) {
        Intent intent = new Intent(new Intent(MokoConstants.ACTION_ORDER_TIMEOUT));
        intent.putExtra(MokoConstants.EXTRA_KEY_RESPONSE_ORDER_TASK, response);
        sendBroadcast(intent);
    }

    @Override
    public void onOrderFinish() {
        sendBroadcast(new Intent(MokoConstants.ACTION_ORDER_FINISH));
    }

    @Override
    public void onCreate() {
        LogModule.i("Crear MokoService...onCreate");
        super.onCreate();
    }

    public void connectBluetoothDevice(String address) {
        MokoSupport.getInstance().connDevice(this, address, this);
    }

    /**
     * @Date 2017/5/23
     * @Author wenzheng.liu
     * @Description Desconecta el brazalete
     */
    public void disConnectBle() {
        MokoSupport.getInstance().setReconnectCount(0);
        MokoSupport.getInstance().disConnectBle();
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    public boolean isSyncData() {
        return MokoSupport.getInstance().isSyncData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogModule.i("Inicie MokoService...onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        LogModule.i("Encuadernación MokoService...onBind");
        return mBinder;
    }

    @Override
    public void onLowMemory() {
        LogModule.i("Memoria apretada, destruye MokoService...onLowMemory");
        disConnectBle();
        super.onLowMemory();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogModule.i("Desenlazar MokoService...onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogModule.i("Destruye MokoService...onDestroy");
        disConnectBle();
        MokoSupport.getInstance().setOpenReConnect(false);
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public MokoService getService() {
            return MokoService.this;
        }
    }

    public ServiceHandler mHandler;

    public class ServiceHandler extends BaseMessageHandler<MokoService> {

        public ServiceHandler(MokoService service) {
            super(service);
        }

        @Override
        protected void handleMessage(MokoService service, Message msg) {
        }
    }
}
