package com.example.projectweb.rest;

import com.example.projectweb.Token;
import com.example.projectweb.chat.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @POST("/api/auth/login/")
    Call<Token> Login(@Body User login);

    @POST("/api/auth/refresh/")
    Call<String> Refresh(@Body String refresh);

    @POST("/api/auth/signup/")
    Call<Integer> Signup(@Body User user);

    // Передаем только параметр username
    @GET("/api/app/user/")
    Call<User> GetUser(@Body User user);

    @POST("/api/app/user/")
    Call<Integer> PostUser(@Body User user);
}

