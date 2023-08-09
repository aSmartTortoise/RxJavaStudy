package com.wyj.rxjava.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.wyj.rxjava.MyApplication;


public class UserPreference {
    private static SharedPreferences mUserPreferences;
    private static final String USER_PREFERENCE = "user_preference";
    private static Editor editor = ensureIntializePreference().edit();

    public static SharedPreferences ensureIntializePreference() {
        if (mUserPreferences == null) {
            mUserPreferences = MyApplication.sContext.getSharedPreferences(USER_PREFERENCE, 0);
        }
        return mUserPreferences;
    }

    public static void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static String readString(String key, String defaultvalue) {
        return ensureIntializePreference().getString(key, defaultvalue);
    }

    public static void saveLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public static long readLong(String key, long defaultvalue) {
        return ensureIntializePreference().getLong(key, defaultvalue);
    }

    public static void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int readInt(String key, int defaultvalue) {
        return ensureIntializePreference().getInt(key, defaultvalue);
    }

    public static void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean readBoolean(String key, boolean defaultvalue) {
        return ensureIntializePreference().getBoolean(key, defaultvalue);
    }

    public static void saveFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float readFloat(String key, float defaultvalue) {
        return ensureIntializePreference().getFloat(key, defaultvalue);
    }


    public static String getPath(Context context) {
//        String majorName = context.getString(R.string.major_name);
//        String path;
//        int integer = 0;
//        if (context instanceof Activity) {
//            Activity activity = (Activity) context;
//            integer = activity.getResources().getInteger(R.integer.isHeJiProject);
//        }
//        if (0 < integer) {//是合集工程  小合集或大合集
//            path =  UserPreference.readString(UserPreferenceKeys.SELECTED_MAJOR_PATH, "");
//        }else {//单个app工程 初会 二建等等
//            path = majorName;
//        }
//        return path;
        return null;
    }

}
