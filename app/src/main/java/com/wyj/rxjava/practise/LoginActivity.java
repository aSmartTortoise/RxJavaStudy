package com.wyj.rxjava.practise;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wyj.rxjava.R;
import com.wyj.rxjava.model.User;
import com.wyj.rxjava.mvp.login.LoginPresenterImpl;
import com.wyj.rxjava.mvp.login.LoginContract;

/**
 *  https://www.jianshu.com/p/83f679efa15a
 */
public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    private TextView mTvContent;
    private LoginContract.Presenter mLoginPresenter;

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
        if (mLoginPresenter == null) {
            mLoginPresenter = new LoginPresenterImpl();
        }
        if (!mLoginPresenter.isViewAttached()) {
            mLoginPresenter.attachView(this);
        }
        mLoginPresenter.login("13163268087", "123456");
    }

    @Override
    public void onLoginSuccess(User userInfo) {
        String username = userInfo.getUsername();
        Log.d(TAG, "onLoginSuccess: userName:" + username);
        mTvContent.setText(username);
    }

    @Override
    public void onLoginFailed(String msg) {
        Log.d(TAG, "onLoginFailed: msg:" + msg);
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading: ");
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoginPresenter != null) {
            mLoginPresenter.detachView();
        }
    }
}
