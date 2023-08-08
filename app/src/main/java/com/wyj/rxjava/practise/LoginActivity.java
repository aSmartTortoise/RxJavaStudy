package com.wyj.rxjava.practise;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wyj.rxjava.R;
import com.wyj.rxjava.model.BaseResponse;
import com.wyj.rxjava.model.User;
import com.wyj.rxjava.network.ApiInterface;
import com.wyj.rxjava.network.HttpManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextView mTvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_login).setOnClickListener(view -> {
            toLogin();
        });
        mTvContent = findViewById(R.id.tv_content);
    }

    private void toLogin() {
        Observable<BaseResponse<User>> loginObservable =
                HttpManager.getInstance().create(ApiInterface.class).login("13163268087",
                "123456");
        loginObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<User> userBaseResponse) {
                        User userInfo = userBaseResponse.getData();
                        Log.d(TAG, "onNext: wyj userInfo:" + userInfo);
                        mTvContent.setText(userInfo.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: wyj e:" + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
