package com.movieapp.android.service;

import android.util.Log;

import com.movieapp.android.model.AuthenticationRequest;
import com.movieapp.android.model.AuthenticationResponse;
import com.movieapp.android.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationClient {
    private final String TAG = "AuthenticationClient";
    private final String API_URL = "http://10.0.2.2:8080/";
    private final AuthenticationService authenticationService;

    public AuthenticationClient() {
        this.authenticationService = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthenticationService.class);
    }

    public Call<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        Log.d(TAG, "Authenticating user with email: " + request.getEmail());
        return authenticationService.authenticate(request);
    }

    public Call<AuthenticationResponse> register(RegisterRequest request) {
        Log.d(TAG, "Registering user with email: " + request.getEmail());
        return authenticationService.register(request);
    }

}
