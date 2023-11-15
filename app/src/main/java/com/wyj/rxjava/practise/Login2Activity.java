package com.wyj.rxjava.practise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wyj.rxjava.R;
import com.wyj.rxjava.databinding.ActivityLogin2Binding;
import com.wyj.rxjava.model.User;
import com.wyj.rxjava.mvvm.login.LoadStatus;
import com.wyj.rxjava.mvvm.login.LoginViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

/**
 *  mvvm https://juejin.cn/post/6901200799242649607 正确认识 MVC/MVP/MVVM
 */
public class Login2Activity extends AppCompatActivity {
    private static final String TAG = "Login2Activity";

    private ActivityLogin2Binding mLogin2Binding;
    private LoginViewModel mLoginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogin2Binding = DataBindingUtil.setContentView(this, R.layout.activity_login2);
        mLoginModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mLoginModel.getTitleData().setValue(getString(R.string.login_know_more_android));
        mLogin2Binding.setViewModel(mLoginModel);
        initListener();
    }

    private void initListener() {
        setEditTextChange(mLogin2Binding.etPhone);
        setEditTextChange(mLogin2Binding.etPassword);
        mLogin2Binding.cbAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            updateLoginState();
        });
        setLoginClickListener(mLogin2Binding.tvLogin);
        mLoginModel.getLoadStatusData().observe(this, loadStatus -> {
            if (loadStatus == LoadStatus.STAR) {
                Log.d(TAG, "onChanged: onLoading");
            } else if (loadStatus == LoadStatus.FAIL) {
                Log.d(TAG, "onChanged: load fail");
            } else if (loadStatus == LoadStatus.SUCCESS) {
                Toast.makeText(Login2Activity.this, "登录成功", Toast.LENGTH_LONG).show();
            }
        });

        mLoginModel.getUserInfoData().observe(this, user -> {
            Log.d(TAG, "initListener: userName:" + user.getUsername());
            finish();
        });
    }

    private void setEditTextChange(EditText etPhone) {
        Disposable disposable = Observable.create((ObservableOnSubscribe<CharSequence>) emitter -> {
                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            emitter.onNext(s);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    };
                    etPhone.addTextChangedListener(textWatcher);
                    emitter.setCancellable(() -> etPhone.removeTextChangedListener(textWatcher));
                })
                .filter(charSequence -> !TextUtils.isEmpty(charSequence))
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> {
                    Log.d(TAG, "setEditTextChange: charSequence:" + charSequence);
                    updateLoginState();
                });
    }

    private void updateLoginState() {
        String phone = mLogin2Binding.etPhone.getText().toString().trim();
        String pwd = mLogin2Binding.etPassword.getText().toString().trim();
        mLogin2Binding.tvLogin.setSelected(!TextUtils.isEmpty(phone)
                && (phone.length() == 11)
                && !TextUtils.isEmpty(pwd)
                && mLogin2Binding.cbAgreement.isChecked()
        );
    }

    private void setLoginClickListener(AppCompatTextView tvLogin) {
        Disposable disposable = Observable.create(emitter -> {
                    View.OnClickListener clickListener = v -> emitter.onNext(new Object());
                    tvLogin.setOnClickListener(clickListener);
                    emitter.setCancellable(() -> tvLogin.setOnClickListener(null));
                })
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    toLogin();
                });
    }

    private void toLogin() {
        String account = mLogin2Binding.etPhone.getText().toString().trim();
        String pwd = mLogin2Binding.etPassword.getText().toString().trim();
        mLoginModel.login(account, pwd);
    }

}