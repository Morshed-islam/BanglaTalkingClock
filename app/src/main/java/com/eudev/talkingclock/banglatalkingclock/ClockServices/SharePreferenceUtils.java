package com.eudev.talkingclock.banglatalkingclock.ClockServices;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Morshed on 9/2/2017.
 */

public class SharePreferenceUtils {

    private static final int MODE = 4;
    public static final String name = "HmSoftTalkingClock";

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int def) {
        return context.getSharedPreferences(name, MODE).getInt(key, def);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(name, MODE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        return context.getSharedPreferences(name, MODE).getBoolean(key, def);
    }
}
