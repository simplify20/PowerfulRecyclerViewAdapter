package com.steve.creact.library.display;

import android.view.ViewGroup;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;


/**
 * @author:YJJ
 * @date:2015/10/22
 * @email:yangjianjun@117go.com
 */
public interface DisplayBean<VH extends BaseRecyclerViewHolder> {

    VH createHolder(ViewGroup parent);
}
