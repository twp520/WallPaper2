package com.flight.wallpaper2.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.flight.wallpaper2.R
import com.flight.wallpaper2.bean.Category


class CategoryAdapter : BaseQuickAdapter<Category, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Category?) {
        item ?: return
        Glide.with(context)
            .load(item.cover)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.icon_placeholder)
            .into(holder.getView(R.id.item_category_cover))
        holder.setText(R.id.item_category_title, item.title)
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_category, parent)
    }
}