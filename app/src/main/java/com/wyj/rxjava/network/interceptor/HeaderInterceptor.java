package com.wyj.rxjava.network.interceptor;

import android.text.TextUtils;

import com.wyj.rxjava.util.UserPreference;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        newBuilder.addHeader("Content-Type", "application/json; charset=utf-8");
        String host = request.url().host();
        String url = request.url().toString();
        if (!TextUtils.isEmpty(host) && url.contains("article")) {
            String cookie = UserPreference.readString("cookie", null);
            if (!TextUtils.isEmpty(cookie)) {
                newBuilder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(newBuilder.build());
    }
}
