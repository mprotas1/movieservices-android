package com.movieapp.android.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.movieapp.android.model.Cinema;
import com.movieapp.android.serialize.CinemaDeserializer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CinemaClient {
    public final String API_URL = "http://10.0.2.2:8005/";
    public final CinemaService cinemaService;

    public CinemaClient() {
        this.cinemaService = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(getCinemaGson()))
                .build()
                .create(CinemaService.class);
    }

    public Call<List<Cinema>> getCinemas() {
        return cinemaService.getCinemas();
    }

    private Gson getCinemaGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Cinema.class, new CinemaDeserializer())
                .create();
    }

}
