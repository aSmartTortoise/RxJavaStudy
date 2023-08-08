package com.wyj.rxjava.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder newBuilder = chain.request().newBuilder();
        newBuilder.addHeader("Content-Type", "application/json; charset=utf-8");
        return chain.proceed(newBuilder.build());
    }
}
