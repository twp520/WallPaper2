package com.flight.wallpaper2.ui.adapter

import android.content.Context
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.flight.wallpaper2.R
import com.flight.wallpaper2.bean.Wallpaper


class WallPaperListAdapter : BaseQuickAdapter<Wallpaper, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: Wallpaper?) {
        item ?: return
        Glide.with(context)
            .load(item.thumb)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.icon_placeholder)
            .into(holder.getView(R.id.item_wallpaper))
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(R.layout.item_wallpaper_list, parent)
    }
}