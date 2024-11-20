package com.movieapp.android.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CinemasRecyclerView extends RecyclerView {

    public CinemasRecyclerView(@NonNull Context context, @Nullable AttributeSet attributes) {
        super(context, attributes);
        this.addItemDecoration(new OffsetDecoration());
        this.setLayoutManager(new LinearLayoutManager(context));
    }

    private static class OffsetDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(@NonNull Rect outerRectangle,
                                   @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            super.getItemOffsets(outerRectangle, view, parent, state);
            outerRectangle.bottom = 10;
            outerRectangle.top = isFirstNode(view, parent) ? 0 : 10;
        }

        private boolean isFirstNode(@NonNull View view, @NonNull RecyclerView parent) {
            return parent.getChildAdapterPosition(view) == 0;
        }

    }
}
