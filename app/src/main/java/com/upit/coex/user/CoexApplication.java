package com.upit.coex.user;

import android.app.Application;

import com.google.gson.Gson;
import com.upit.coex.user.service.logger.L;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexApplication extends Application {

    private static CoexApplication sCoex;
    private Gson mGSon;

    public static CoexApplication self() {
        return sCoex;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sCoex = this;

        // Những trường hợp không rõ exception
//        Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException (Thread thread, Throwable e) {
//                handleUncaughtException (thread, e);
//            }
//        });
    }

    public void handleUncaughtException (Thread thread, Throwable e)
    {
        e.printStackTrace();
        L.d("COEX application","uncaught exception"+e.getMessage()+"k");
        System.exit(1); // kill off the crashed app
    }

    public Gson getGSon() {
        if(mGSon == null){
            synchronized (CoexApplication.class){
                mGSon = new Gson();
            }
        }

        return mGSon;
    }
}
