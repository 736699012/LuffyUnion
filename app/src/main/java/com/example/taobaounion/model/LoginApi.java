package com.example.taobaounion.model;

import com.example.taobaounion.model.bean.LoginData;
import com.example.taobaounion.model.bean.RegisterData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginApi {

    @GET("login")
    Call<LoginData> checkLogin(@Query("username") String username, @Query("password") String password);

    @GET("register")
    Call<RegisterData> registerUser(@Query("username") String username, @Query("password") String password);
}
