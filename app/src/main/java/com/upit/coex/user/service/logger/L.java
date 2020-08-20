package com.upit.coex.user.service.logger;

import android.util.Log;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class L {
    public static void v(String TAG,String ...obj){
        StringBuilder content = new StringBuilder();
        for(String item: obj)
        {
            content.append(" ");
            content.append(item);
        }
        Log.v(TAG,content.toString());
    }


    public static void d(String TAG,String ...obj){
        StringBuilder content = new StringBuilder();
        for(String item: obj)
        {
            content.append(" ");
            content.append(item);
        }
        Log.d(TAG,content.toString());
    }

    public static void i(String TAG,String ...obj){
        StringBuilder content = new StringBuilder();
        for(String item: obj)
        {
            content.append(" ");
            content.append(item);
        }
        Log.i(TAG,content.toString());
    }

    public static void e(String TAG,Throwable e,String ...obj){
        StringBuilder content = new StringBuilder();
        for(String item: obj)
        {
            content.append(" ");
            content.append(item);
        }
        Log.e(TAG,content.toString(),e);
    }


    public static void wtf(String TAG,String ...obj){
        StringBuilder content = new StringBuilder();
        for(String item: obj)
        {
            content.append(" ");
            content.append(item);
        }
        Log.wtf(TAG,content.toString());
    }
}
