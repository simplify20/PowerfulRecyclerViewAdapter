package com.steve.creact.library.display;


import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;

/**
 * @author:YJJ
 * @date:2015/10/22
 * @email:yangjianjun@117go.com
 */
public interface DataBean<T,VH extends BaseRecyclerViewHolder> extends DisplayBean<VH> {
    void bindData(VH holder);
    T getData();
}
