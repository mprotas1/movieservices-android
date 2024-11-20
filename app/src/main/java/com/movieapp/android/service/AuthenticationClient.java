package com.movieapp.android.service;

import android.util.Log;
import android.widget.Toast;

import com.movieapp.android.model.AuthenticationRequest;
import com.movieapp.android.model.AuthenticationResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthenticationClient {
    private final String TAG = "AuthenticationClient";
    private final String API_URL = "http://localhost:8080/users/";
    private final AuthenticationService authenticationService;

    public AuthenticationClient() {
        this.authenticationService = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthenticationService.class);
    }

    public String authenticate(AuthenticationRequest request) throws IOException {
        Log.d(TAG, "Authenticating user");
        Call<AuthenticationResponse> call = authenticationService.authenticate(request);

        return "";
    }

    public String register() {
        return "";
    }

}
