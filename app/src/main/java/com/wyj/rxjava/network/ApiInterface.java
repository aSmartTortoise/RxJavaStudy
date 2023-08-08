package com.wyj.rxjava.network;

import com.wyj.rxjava.model.BaseResponse;
import com.wyj.rxjava.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
/**
 * 登录
 * @param username  用户名
 * @param password  密码
 */
    @FormUrlEncoded
    @POST("/user/login")
    Observable<BaseResponse<User>> login(
            @Field("username")String username,
            @Field("password")String password);
}
