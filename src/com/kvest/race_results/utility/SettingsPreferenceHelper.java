package com.kvest.race_results.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: Kvest
 * Date: 12.01.14
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class SettingsPreferenceHelper {
    private static final String SHARED_PREFERENCES_NAME = "com.kvest.race_results.utility.SETTINGS";
    private static final String LOAD_FORMAT_KEY = "load_format";

    public static final int LOAD_FORMAT_JSON = 0;
    public static final int LOAD_FORMAT_XML= 1;
    public static final int LOAD_FORMAT_TXT = 2;
    private static final int DEFAULT_LOAD_FORMAT = LOAD_FORMAT_JSON;

    public static void setLoadFormat(Context context, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
        try {
            editor.putInt(LOAD_FORMAT_KEY, value);
        } finally {
            editor.commit();
        }
    }

    public static int getLoadFormat(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(LOAD_FORMAT_KEY, DEFAULT_LOAD_FORMAT);
    }
}
