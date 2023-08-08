package com.wyj.rxjava.network;

import android.util.Log;

import com.wyj.rxjava.BuildConfig;
import com.wyj.rxjava.MyApplication;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static HttpManager mInstance;
    private Retrofit mRetrofit;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            mInstance = new HttpManager();
        }
        return mInstance;
    }

    private HttpManager() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(initOkHttpClient())
                    .baseUrl("https://www.wanandroid.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    private OkHttpClient initOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("HttpManager", "log: wyj message:" + message);
            }
        });
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        Cache cache = new Cache(new File(MyApplication.sContext.getCacheDir(), "cache"),
                1024 * 1024 * 50);
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();
    }

    public <T> T create(Class<T> apiService) {
        return mRetrofit.create(apiService);
    }
}
