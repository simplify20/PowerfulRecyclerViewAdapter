package com.steve.creact.library.display;


import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;

/**
 * Extends from DisplayBean,use to bind data to ViewHolder
 * @see DisplayBean
 */
public interface DataBean<T,VH extends BaseRecyclerViewHolder> extends DisplayBean<VH> {
    void bindData(VH holder);
    T getData();
}
