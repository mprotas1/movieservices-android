package com.movieapp.android.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CinemasRecyclerView extends RecyclerView {

    public CinemasRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.addItemDecoration(new OffsetDecoration());
    }

    public static class OffsetDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect,
                                   @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.bottom = 10;
            outRect.top = 10;

            if(parent.getChildAdapterPosition(view) == 0) {
                outRect.top = 0;
            }
        }
    }
}
