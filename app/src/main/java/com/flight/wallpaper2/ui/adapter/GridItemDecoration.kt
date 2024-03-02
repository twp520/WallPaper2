package com.flight.wallpaper2.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class GridItemDecoration(private val edge: Int, private val colCount: Int) :
    RecyclerView.ItemDecoration() {

    private val half = edge / 2

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        (view.layoutParams as RecyclerView.LayoutParams).run {
            outRect.top = if (position in 0 until colCount) {
                edge
            } else {
                half
            }
            outRect.bottom =
                if (position in state.itemCount - colCount..state.itemCount) {
                    edge
                } else {
                    half
                }
            when {
                position % colCount == 0 -> {
                    outRect.left = edge
                    outRect.right = half
                }

                position % colCount == colCount - 1 -> {
                    outRect.right = edge
                    outRect.left = half
                }

                else -> {
                    outRect.left = half
                    outRect.right = half
                }
            }
        }
    }
}