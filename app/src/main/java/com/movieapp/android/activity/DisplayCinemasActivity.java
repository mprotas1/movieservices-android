package com.movieapp.android.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;
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

    private FusedLocationProviderClient fusedLocationClient;

    private final List<Cinema> cinemas = new ArrayList<>();
    private LocationPosition position;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cinemas);
        getUserLocationAndFetchCinemas();
    }

    private void getUserLocationAndFetchCinemas() {
        this.progressBar = findViewById(R.id.cinemas_progress_bar);
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        RecyclerView recyclerView = findViewById(R.id.display_cinemas_recycler_view);

        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, location -> {
                    if (location == null) {
                        hideProgressBar();
                        Toast.makeText(DisplayCinemasActivity.this, "Failed to get location.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    position = new LocationPosition(location.getLatitude(), location.getLongitude());
                    TextView locationText = findViewById(R.id.current_location_help);
                    locationText.setText("Your location: " + position.getLatitude() + ", " + position.getLongitude());
                    fetchAndDisplayCinemas(recyclerView);
                })
                .addOnFailureListener(this, e -> {
                    hideProgressBar();
                    Toast.makeText(DisplayCinemasActivity.this, "Error getting location: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void fetchAndDisplayCinemas(RecyclerView recyclerView) {
        CinemaClient api = new CinemaClient();
        Call<List<Cinema>> call = api.getCinemas();

        call.enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cinema> responseCinemas = response.body();
                    Log.d(TAG, "Cinemas fetched: " + responseCinemas.size());

                    List<Cinema> sortedCinemas = responseCinemas.stream()
                            .sorted(Comparator.comparing(cinema -> HaversineDistanceCalculator.calculate(position, cinema.getLocation())))
                            .collect(Collectors.toList());

                    cinemas.addAll(sortedCinemas);
                    hideProgressBar();
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new CinemaAdapter(sortedCinemas, cinema -> {
                        Log.d(TAG, String.format("Selected cinema: %s - going to the ShowCinemaScreeningsActivity", cinema.getName()));
                        Intent intent = new Intent(DisplayCinemasActivity.this, ShowCinemaScreeningsActivity.class);
                        intent.putExtra("cinemaId", cinema.getId());
                        startActivity(intent);
                    }));
                } else {
                    Toast.makeText(DisplayCinemasActivity.this, "Error fetching cinemas.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Cinema>> call, @NonNull Throwable t) {
                hideProgressBar();
                Toast.makeText(DisplayCinemasActivity.this, "Error fetching cinemas: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}
