package com.wyj.rxjava.mvp.login;

import com.wyj.rxjava.model.User;

public interface LoginContract {

    interface View {
        void onLoginSuccess(User userInfo);
        void onLoginFailed(String msg);
        void showLoading();
        void hideLoading();
    }

    interface Presenter {
        void attachView(View view);
        void detachView();
        boolean isViewAttached();
        void login(String account, String pwd);
    }

    interface OnLoginFinishListener {
        void onLoginFailed(String msg);
        void onLoginSuccess(User userInfo);
    }

    interface Model {
        void login(String account, String pwd, OnLoginFinishListener listener);
    }
}
