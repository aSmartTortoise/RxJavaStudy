package com.wyj.rxjava.mvvm.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wyj.rxjava.model.User;
import com.wyj.rxjava.mvp.login.LoginContract;
import com.wyj.rxjava.mvp.login.LoginModelImpl;

public class LoginViewModel extends ViewModel implements LoginContract.OnLoginFinishListener {
    private MutableLiveData<String> titleData = new MutableLiveData<>();
    private MutableLiveData<LoadStatus> loadStatusData = new MutableLiveData<>(LoadStatus.INITIALIZED);
    private LoginContract.Model mLoginModel;
    private MutableLiveData<User> userInfoData = new MutableLiveData<>();

    public LoginViewModel() {
        mLoginModel = new LoginModelImpl();
    }

    public MutableLiveData<String> getTitleData() {
        return titleData;
    }

    public MutableLiveData<LoadStatus> getLoadStatusData() {
        return loadStatusData;
    }

    public MutableLiveData<User> getUserInfoData() {
        return userInfoData;
    }

    public void login(String account, String pwd) {
        loadStatusData.postValue(LoadStatus.STAR);
        mLoginModel.login(account, pwd, this);
    }

    @Override
    public void onLoginFailed(String msg) {
        loadStatusData.postValue(LoadStatus.FAIL);
    }

    @Override
    public void onLoginSuccess(User userInfo) {
        loadStatusData.postValue(LoadStatus.SUCCESS);
        userInfoData.postValue(userInfo);
        Log.d("TAG", "onLoginSuccess: " + userInfo.getUsername());
    }
}
