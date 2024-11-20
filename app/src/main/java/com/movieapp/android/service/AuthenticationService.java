package com.movieapp.android.service;

import com.movieapp.android.model.AuthenticationRequest;
import com.movieapp.android.model.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("/users/auth")
    Call<AuthenticationResponse> authenticate(@Body AuthenticationRequest request);

    @POST("/users/auth/register")
    Call<String> register();
}
