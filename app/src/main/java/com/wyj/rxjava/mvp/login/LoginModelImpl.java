package com.wyj.rxjava.mvp.login;

import android.util.Log;

import com.wyj.rxjava.model.ArticleInfo;
import com.wyj.rxjava.model.ArticleList;
import com.wyj.rxjava.model.BaseResponse;
import com.wyj.rxjava.model.User;
import com.wyj.rxjava.network.ApiInterface;
import com.wyj.rxjava.network.HttpManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginModelImpl implements LoginContract.Model {
    private static final String TAG = "LoginModelImpl";
    @Override
    public void login(String account, String pwd, LoginContract.OnLoginFinishListener listener) {
                HttpManager.getInstance().create(ApiInterface.class).login(account, pwd)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseResponse<User>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(BaseResponse<User> userBaseResponse) {
                                if (userBaseResponse != null) {
                                    User userInfo = userBaseResponse.getData();
                                    if (userInfo == null) {
                                        if (listener != null) {
                                            listener.onLoginFailed("服务器返回的数据异常。");
                                        }
                                    } else {
                                        if (listener != null) {
                                            listener.onLoginSuccess(userInfo);
                                        }
                                    }
                                } else {
                                    if (listener != null) {
                                        listener.onLoginFailed("服务器返回的数据异常。");
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (listener != null) {
                                    listener.onLoginFailed(e.getMessage());
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
    }

    private void getHomeList(String account, String pwd) {
        Observable<BaseResponse<User>> loginObservable =
                HttpManager.getInstance().create(ApiInterface.class).login(account,
                        pwd);

        loginObservable
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<User>, ObservableSource<BaseResponse<ArticleList>>>() {
                    @Override
                    public ObservableSource<BaseResponse<ArticleList>> apply(BaseResponse<User> userBaseResponse) throws Exception {
                        return HttpManager.getInstance().create(ApiInterface.class)
                                .getHomeList(0, 20);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse<ArticleList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: wyj");
                    }

                    @Override
                    public void onNext(BaseResponse<ArticleList> articleListBaseResponse) {
                        ArticleList articleList = articleListBaseResponse.getData();
                        List<ArticleInfo> articleInfos = articleList.getDatas();
                        StringBuilder sb = new StringBuilder();
                        for (ArticleInfo articleInfo : articleInfos) {
                            boolean collect = articleInfo.isCollect();
                            if (collect) {
                                String title = articleInfo.getTitle();
                                sb.append(title).append(";");
                            }
                        }

                        Log.d(TAG, "onNext: wyj");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: wyj");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
