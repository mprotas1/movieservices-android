package com.movieapp.android.adapter;

import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movieapp.android.R;
import com.movieapp.android.listener.OnCinemaClickedListener;
import com.movieapp.android.model.Cinema;

class CinemaViewHolder extends ViewHolder {
    TextView cinemaName;
    TextView cinemaAddress;

    public CinemaViewHolder(@NonNull View itemView, OnCinemaClickedListener listener) {
        super(itemView);
        cinemaName = itemView.findViewById(R.id.cinema_name);
        cinemaAddress = itemView.findViewById(R.id.cinema_address);

        itemView.setOnClickListener(cinema -> {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onClick((Cinema) cinema.getTag());
            }
        });
    }

    public void setCinemaNameText(String cinemaName) {
        this.cinemaName.setText(cinemaName);
    }

    public void setCinemaAddressText(String address) {
        this.cinemaAddress.setText(address);


    }

}
