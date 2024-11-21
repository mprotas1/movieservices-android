package com.movieapp.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.movieapp.android.R;
import com.movieapp.android.adapter.CinemaAdapter;
import com.movieapp.android.model.Cinema;
import com.movieapp.android.model.LocationPosition;
import com.movieapp.android.service.CinemaClient;
import com.movieapp.android.service.HaversineDistanceCalculator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayCinemasActivity extends AppCompatActivity {
    private final String TAG = "DisplayCinemasActivity";
    private final List<Cinema> cinemas = new ArrayList<>();
    private LocationPosition position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cinemas);
        initRecyclerView();
        fetchCinemas();
        getLocation();
    }

    private LocationPosition getLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null && position == null) {
                    position = new LocationPosition(location.getLatitude(), location.getLongitude());
                    Log.d(TAG, "Location is: " + position.getLatitude() + ", " + position.getLongitude());

                    // Default Google services GPS long/lat is: 40.918291,-4.109010

                    for(Cinema cinema : cinemas) {
                        double distance = HaversineDistanceCalculator.calculate(position, cinema.getLocation());
                        Log.d(TAG, "Distance to " + cinema.getName() + " is: " + distance);
                    }

                    List<Cinema> sorted = cinemas.stream()
                            .sorted(Comparator.comparingDouble(c -> HaversineDistanceCalculator.calculate(position, c.getLocation())))
                            .collect(Collectors.toList());
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, "Location permission denied: " + e.getMessage());
        }

        return position;
    }

    private void initRecyclerView() {
        CinemaAdapter adapter = new CinemaAdapter(cinemas, cinema -> {
            Intent intent = new Intent(this, ShowCinemaScreeningsActivity.class);
            startActivity(intent);
        });
        RecyclerView recyclerView = findViewById(R.id.display_cinemas_recycler_view);
        recyclerView.setAdapter(adapter);
    }

    private void fetchCinemas() {
        CinemaClient api = new CinemaClient();
        Call<List<Cinema>> call = api.getCinemas();

        call.enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                if (response.isSuccessful()) {
                    List<Cinema> responseCinemas = response.body();
                    if (responseCinemas != null) {
                        cinemas.addAll(responseCinemas);
                    }
                } else {
                    Log.e(TAG, "API Error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Cinema>> call, @NonNull Throwable t) {
                Log.e(TAG, "API Error: " + t.getMessage());
                call.cancel();
            }
        });
    }

}
