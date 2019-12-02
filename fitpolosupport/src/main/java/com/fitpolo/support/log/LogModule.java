package com.fitpolo.support.log;

import android.content.Context;
import android.os.Environment;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.flattener.PatternFlattener;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.naming.ChangelessFileNameGenerator;

import java.io.File;

/**
 * @Date 2017/5/9
 * @Author wenzheng.liu
 * @Description Módulo de registro
 * @ClassPath com.fitpolo.support.log.LogModule
 */
public class LogModule {
    private static final String TAG = "fitpoloDemoH706";
    private static final String LOG_FOLDER = "fitpoloDemoH706";
    private static String PATH_LOGCAT;

    public static void init(Context context) {
        // Inicializar Xlog
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // Guardar en la tarjeta SD primero
            PATH_LOGCAT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + LOG_FOLDER;
        } else {
            // Si la tarjeta SD no existe, guárdela en el directorio de la aplicación
            PATH_LOGCAT = context.getFilesDir().getAbsolutePath() + File.separator + LOG_FOLDER;
        }
        Printer filePrinter = new FilePrinter.Builder(PATH_LOGCAT)
                .fileNameGenerator(new ChangelessFileNameGenerator(TAG))
                .backupStrategy(new ClearLogBackStrategy())
                .logFlattener(new PatternFlattener("{d yyyy-MM-dd HH:mm:ss} {l}/{t}: {m}"))
                .build();
        LogConfiguration config = new LogConfiguration.Builder()
                .tag(TAG)
                .logLevel(LogLevel.ALL)
                .build();
        XLog.init(config, new AndroidPrinter(), filePrinter);
    }

    public static void v(String msg) {
        XLog.v(msg);
    }

    public static void d(String msg) {
        XLog.d(msg);
    }

    public static void i(String msg) {
        XLog.i(msg);
    }

    public static void w(String msg) {
        XLog.w(msg);
    }

    public static void e(String msg) {
        XLog.e(msg);
    }
}
