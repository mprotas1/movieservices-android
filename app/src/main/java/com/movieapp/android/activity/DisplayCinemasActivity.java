package com.movieapp.android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.movieapp.android.R;
import com.movieapp.android.adapter.CinemaAdapter;
import com.movieapp.android.model.Cinema;

import java.util.List;
import java.util.UUID;

class DisplayCinemasActivity extends AppCompatActivity {
    private final String TAG = "DisplayCinemasActivity";
    //private final CinemaApi cinemaApi;

    public DisplayCinemasActivity() {
        //this.cinemaApi = new CinemaApi();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cinemas);
        RecyclerView recyclerView = findViewById(R.id.display_cinemas_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Cinema> cinemas = List.of(new Cinema(UUID.randomUUID(), "Multikino Szczecin", "Szczecin"),
                                       new Cinema(UUID.randomUUID(), "Multikino Warszawa", "Warszawa"),
                                       new Cinema(UUID.randomUUID(), "Multikino Wrocław", "Wrocław"));
        CinemaAdapter adapter = new CinemaAdapter(cinemas);
        recyclerView.setAdapter(adapter);
    }

    /*
    private List<Cinema> getCinemas() {
        Call<List<Cinema>> cinemas = cinemaApi.getCinemas();
        List<Cinema> result = new ArrayList<>();
        cinemas.enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                if(response.body() != null && response.isSuccessful()) {
                    List<Cinema> cinemasBody = response.body();
                    Log.d(TAG, String.format("Successfully fetched %d cinemas", cinemasBody.size()));
                    result.addAll(cinemasBody);
                    return;
                }

                throw new RuntimeException("Failed to fetch cinemas");
            }

            @Override
            public void onFailure(Call<List<Cinema>> call, Throwable t) {
                throw new RuntimeException("Failed to fetch cinemas");
            }
        });
        return result;
    }

     */
}
