package com.wyj.rxjava.network;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.wyj.rxjava.util.UserPreference;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class CookieManager {
    public static void encodeCookie(List<String> cookies) {
        HashSet<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] split = cookie.split(";");
            for (String element : split) {
                if (!TextUtils.isEmpty(element)) {
                    set.add(element);
                }
            }
        }
        if (!set.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : set) {
                sb.append(s).append(";");
            }
            int lastIndex = sb.lastIndexOf(";");
            if (lastIndex != -1) {
                sb.deleteCharAt(lastIndex);
            }
            UserPreference.saveString("cookie", sb.toString());
        }
    }
}
