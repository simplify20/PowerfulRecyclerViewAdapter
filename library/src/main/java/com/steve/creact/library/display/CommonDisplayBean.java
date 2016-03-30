package com.steve.creact.library.display;

import android.view.ViewGroup;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;


/**
 * @author:YJJ
 * @date:2015/10/22
 * @email:yangjianjun@117go.com
 */
public class CommonDisplayBean extends BaseDisplayBean<BaseRecyclerViewHolder> {
    private int layoutId;

    public CommonDisplayBean(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public BaseRecyclerViewHolder createHolder(ViewGroup parent) {
        return new BaseRecyclerViewHolder(getView(parent, layoutId));
    }
}
