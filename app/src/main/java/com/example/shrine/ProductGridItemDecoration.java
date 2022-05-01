package com.example.shrine;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom item decoration for a vertical {@link ProductGridFragment} {@link RecyclerView}. Adds a
 * small amount of padding to the left of grid items, and a large amount of padding to the right.
 */
public class ProductGridItemDecoration extends RecyclerView.ItemDecoration {
    private final int largePadding;
    private final int smallPadding;

    public ProductGridItemDecoration(int largePadding, int smallPadding) {
        this.largePadding = largePadding;
        this.smallPadding = smallPadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = smallPadding;
        outRect.right = largePadding;
    }
}