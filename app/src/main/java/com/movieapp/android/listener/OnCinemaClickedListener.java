package com.movieapp.android.listener;

import com.movieapp.android.model.Cinema;

@FunctionalInterface
public interface OnCinemaClickedListener {
    void onClick(Cinema cinema);
}
