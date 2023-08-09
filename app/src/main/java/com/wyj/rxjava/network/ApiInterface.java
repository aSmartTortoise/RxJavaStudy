package com.wyj.rxjava.network;

import com.wyj.rxjava.model.ArticleList;
import com.wyj.rxjava.model.BaseResponse;
import com.wyj.rxjava.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseResponse<User>> login(@Field("username") String username, @Field("password") String password);

    /**
     * 首页资讯
     *
     * @param page     页码
     * @param pageSize 每页数量
     */
    @GET("/article/list/{page}/json")
    Observable<BaseResponse<ArticleList>> getHomeList(@Path("page") int page, @Query("page_size") int pageSize);
}
