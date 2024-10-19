package com.movieapp.android.service;

import com.movieapp.android.model.Cinema;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CinemaService {
    @GET
    Call<List<Cinema>> getCinemas();
}
