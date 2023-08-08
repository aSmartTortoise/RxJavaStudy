package com.wyj.rxjava.practise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wyj.rxjava.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;

public class PractiseActivity extends AppCompatActivity {
    private static final String TAG = "PractiseActivity";
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);
        Button btnThrottle = findViewById(R.id.btn_throttle);
        mDisposable = throttleClicEvent(btnThrottle);
    }

    /**
     *  View点击事件防抖。
     */
    @NonNull
    private Disposable throttleClicEvent(Button btnThrottle) {
        return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        btnThrottle.setOnClickListener(view -> emitter.onNext("点击了throttle button。"));
                        emitter.setCancellable(new Cancellable() {
                            @Override
                            public void cancel() throws Exception {
                                btnThrottle.setOnClickListener(null);
                            }
                        });
                    }
                })
                .throttleFirst(350, TimeUnit.MILLISECONDS)
                .subscribe(result -> {
                    Log.d(TAG, "onCreate: wyj result:" + result);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}