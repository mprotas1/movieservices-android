package com.movieapp.android.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.movieapp.android.R;

import java.util.Objects;
import java.util.UUID;

public class ShowCinemaScreeningsActivity extends AppCompatActivity {
    private final String TAG = "ShowCinemaScreeningsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID cinemaId = getCinemaId();
        Log.d(TAG, "Showing screenings for cinema with ID: " + cinemaId);

        setContentView(R.layout.show_cinema_screenings_activity);
    }

    private UUID getCinemaId() {
        Object cinemaId = Objects.requireNonNull(getIntent().getExtras()).get("cinemaId");
        if(!(cinemaId instanceof UUID)) {
            throw new IllegalArgumentException("Cinema ID is required to show cinema screenings.");
        }

        return (UUID) cinemaId;
    }

}