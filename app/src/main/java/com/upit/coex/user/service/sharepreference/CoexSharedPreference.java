package com.upit.coex.user.service.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.upit.coex.user.CoexApplication;

import static com.upit.coex.user.constants.CommonConstants.PREFS_NAME;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexSharedPreference {

    private static volatile CoexSharedPreference sInstance = null;

    private Context mContext;

    private SharedPreferences mCoexPreferences;

    private CoexSharedPreference() {
        mCoexPreferences = CoexApplication.self().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static CoexSharedPreference getInstance(){
        if(sInstance == null){
            synchronized (CoexSharedPreference.class){
                sInstance = new CoexSharedPreference();
            }
        }
        return sInstance;
    }

    //put
    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = mCoexPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, CoexApplication.self().getGSon().toJson(data));
        }
        editor.apply();
    }

    //get
    public <T> T get(String key, Class<T> neededData) {
        if (neededData == String.class) {
            return (T) mCoexPreferences.getString(key, "");
        } else if (neededData == Boolean.class) {
            return (T) Boolean.valueOf(mCoexPreferences.getBoolean(key, false));
        } else if (neededData == Float.class) {
            return (T) Float.valueOf(mCoexPreferences.getFloat(key, 0));
        } else if (neededData == Integer.class) {
            return (T) Integer.valueOf(mCoexPreferences.getInt(key, 0));
        } else if (neededData == Long.class) {
            return (T) Long.valueOf(mCoexPreferences.getLong(key, 0));
        } else {
            return (T) CoexApplication.self().getGSon().fromJson(mCoexPreferences.getString(key, ""), neededData);
        }
    }

}
