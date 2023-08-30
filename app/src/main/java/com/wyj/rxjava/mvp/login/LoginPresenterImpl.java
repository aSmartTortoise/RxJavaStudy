package com.wyj.rxjava.mvp.login;

import com.wyj.rxjava.model.User;

public class LoginPresenterImpl implements LoginContract.Presenter, LoginContract.OnLoginFinishListener {
    private LoginContract.View mLoginView;
    private LoginContract.Model mLoginModel;

    public LoginPresenterImpl() {
        mLoginModel = new LoginModelImpl();
    }

    @Override
    public void attachView(LoginContract.View view) {
        mLoginView = view;
    }

    @Override
    public void detachView() {
        mLoginView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mLoginView != null;
    }

    @Override
    public void login(String account, String pwd) {
        mLoginView.showLoading();
        mLoginModel.login(account, pwd, this);
    }

    @Override
    public void onLoginFailed(String msg) {
        if (isViewAttached()) {
            mLoginView.hideLoading();
            mLoginView.onLoginFailed(msg);
        }
    }

    @Override
    public void onLoginSuccess(User userInfo) {
        if (isViewAttached()) {
            mLoginView.hideLoading();
            mLoginView.onLoginSuccess(userInfo);
        }
    }
}
