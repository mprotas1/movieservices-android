package com.movieapp.android.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.movieapp.android.R;
import com.movieapp.android.adapter.CinemaAdapter;
import com.movieapp.android.model.Cinema;
import com.movieapp.android.service.CinemaApi;
import com.movieapp.android.ui.CinemasRecyclerView;

import java.util.List;
import java.util.UUID;

public class DisplayCinemasActivity extends AppCompatActivity {
    private final String TAG = "DisplayCinemasActivity";
    private final List<Cinema> templateCinemas = List.of(new Cinema(UUID.randomUUID(), "Multikino Szczecin", "Szczecin"),
            new Cinema(UUID.randomUUID(), "Multikino Warszawa", "Warszawa"),
            new Cinema(UUID.randomUUID(), "Multikino Wrocław", "Wrocław"),
            new Cinema(UUID.randomUUID(), "Multikino Kraków", "Kraków"),
            new Cinema(UUID.randomUUID(), "Multikino Gdańsk", "Gdańsk"),
            new Cinema(UUID.randomUUID(), "Multikino Poznań", "Poznań"),
            new Cinema(UUID.randomUUID(), "Multikino Łódź", "Łódź"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cinemas);
        RecyclerView recyclerView = findViewById(R.id.display_cinemas_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CinemaAdapter adapter = new CinemaAdapter(templateCinemas, cinema -> {
            Toast.makeText(this, cinema.getName() + " : " + cinema.getId(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
    }

}
