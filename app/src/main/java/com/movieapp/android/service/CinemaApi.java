package com.movieapp.android.service;

import com.movieapp.android.model.Cinema;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CinemaApi implements CinemaRestInterface {
    public final String API_URL = "https://jsonplaceholder.typicode.com";
    public final CinemaService cinemaService;

    public CinemaApi() {
        this.cinemaService = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CinemaService.class);
    }

    public Call<List<Cinema>> getCinemas() {
        return cinemaService.getCinemas();
    }

}
