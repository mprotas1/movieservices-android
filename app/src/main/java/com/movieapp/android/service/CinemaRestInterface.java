package com.movieapp.android.service;

import com.movieapp.android.model.Cinema;

import java.util.List;

import retrofit2.Call;

public interface CinemaRestInterface {
    Call<List<Cinema>> getCinemas();
}
