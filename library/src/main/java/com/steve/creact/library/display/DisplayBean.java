package com.steve.creact.library.display;

import android.view.ViewGroup;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;


/**
 * An interface use to create a ViewHolder instance
 */
public interface DisplayBean<VH extends BaseRecyclerViewHolder> {

    VH createHolder(ViewGroup parent);
}
