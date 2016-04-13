package com.example.laura.cyclingtracker.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by laura on 07/04/16.
 */
public class MySettings {


    private static final String SHARED_PREF = "MyKeyFile";
    private static final String SESSION_KEY = "sessionKey";

    public static void save(Context context, String sessionKey) {
       SharedPreferences settings = context.getSharedPreferences(SHARED_PREF, 0);
        context.getSharedPreferences(SHARED_PREF, 0).edit().clear().commit();
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(SESSION_KEY);
        editor.putString(SESSION_KEY, sessionKey).commit();
    }

    public static String getSessionKey(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SHARED_PREF, 0);
        return settings.getString(SESSION_KEY, null);

    }



}
