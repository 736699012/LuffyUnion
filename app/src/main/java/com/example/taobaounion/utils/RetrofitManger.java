package com.example.taobaounion.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManger {
    private static final RetrofitManger ourInstance = new RetrofitManger();
    private final Retrofit mRetrofit;
    private final Retrofit mLoginRetrofit;

    public static RetrofitManger getInstance() {
        return ourInstance;
    }

    private RetrofitManger() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mLoginRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getLoginRetrofit() {
        return mLoginRetrofit;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
