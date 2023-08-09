package com.wyj.rxjava.practise;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wyj.rxjava.R;
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

/**
 *  https://www.jianshu.com/p/83f679efa15a
 */
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
                        mTvContent.setText(sb.toString());
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
