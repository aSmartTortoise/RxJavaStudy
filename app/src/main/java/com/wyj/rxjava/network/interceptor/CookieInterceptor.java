package com.wyj.rxjava.network.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.wyj.rxjava.network.CookieManager;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request.newBuilder().build());
        List<String> cookieHeader = response.headers("set-cookie");
        if (request.url().toString().contains("user/login") && cookieHeader != null && !cookieHeader.isEmpty()) {
            for (String element : cookieHeader) {
                Log.d("CookieInterceptor", "intercept: wyj element:" + element);
            }
            CookieManager.encodeCookie(cookieHeader);
        }
        return response;
    }
}
