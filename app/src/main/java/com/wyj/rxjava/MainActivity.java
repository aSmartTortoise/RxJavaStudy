package com.wyj.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 参考文章
 * https://blog.yorek.xyz/android/other/RxJava/#21
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_rxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useRxJava();
            }
        });
        findViewById(R.id.btn_subscribe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxJavaSubscribe();
            }
        });
        findViewById(R.id.btn_thread_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedulers.newThread().scheduleDirect(new Runnable() {
                    @Override
                    public void run() {
                        threadSwitch();
                    }
                });
            }
        });
        findViewById(R.id.btn_input_search).setOnClickListener(
                view -> {
                    Intent intent = new Intent(MainActivity.this, CheeseActivity.class);
                    MainActivity.this.startActivity(intent);
                }
        );
    }

    private void threadSwitch() {
        Log.d(TAG, "threadSwitch: thread:" + Thread.currentThread().getName());
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Log.d(TAG, "subscribe: thread:" + Thread.currentThread().getName());
                        emitter.onNext("1");
                        emitter.onNext("2");
                        emitter.onComplete();
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: wyj thread:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: thread:" + Thread.currentThread().getName() + " s:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: thread:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: thread:" + Thread.currentThread().getName());
                    }
                });
    }

    private void rxJavaSubscribe() {
        Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("1");
                        emitter.onNext("2");
                        emitter.onNext("3");
                        emitter.onComplete();
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.valueOf(s) * 10;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: integer:" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: wyj e:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: wyj");
                    }
                });

        Observer<Integer> resultObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: integer:" + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: wyj e:" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: wyj");
            }
        };
        ObservableOnSubscribe<String> source = new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        };

        Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.valueOf(s) * 10;
            }
        };
        Observable.create(source).map(function).subscribe(resultObserver);
    }

    private void useRxJava() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "subscribe: thread:" + Thread.currentThread().getName());
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onNext(4);
                        emitter.onComplete();
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Function<Integer, String>() {

                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.d(TAG, "apply: thread:" + Thread.currentThread().getName() + " integer:" + integer);
                        return String.valueOf(integer);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: thread:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: thread:" + Thread.currentThread().getName() + " String:" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: thread:" + Thread.currentThread().getName() + " Throwable:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: thread:" + Thread.currentThread().getName());
                    }
                });
    }
}