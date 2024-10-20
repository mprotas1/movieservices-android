package com.movieapp.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.movieapp.android.R;
import com.movieapp.android.listener.OnCinemaClickedListener;
import com.movieapp.android.model.Cinema;

import java.util.List;

public class CinemaAdapter extends Adapter<CinemaViewHolder> {
    private final List<Cinema> cinemas;
    private final OnCinemaClickedListener listener;

    public CinemaAdapter(List<Cinema> cinemas, OnCinemaClickedListener listener) {
        this.cinemas = cinemas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new CinemaViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        Cinema cinema = cinemas.get(position);
        holder.setCinemaNameText(cinema.getName());
        holder.setCinemaAddressText(cinema.getAddress());
        holder.itemView.setTag(cinema);
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

}
