package com.steve.creact.library.display;

import android.view.ViewGroup;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;


/**
 * Use for ViewHolders without data or just have static data.
 */
public class CommonDisplayBean extends BaseDisplayBean<BaseRecyclerViewHolder> {
    private int layoutId;

    public CommonDisplayBean(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public BaseRecyclerViewHolder createHolder(ViewGroup parent) {
        return new BaseRecyclerViewHolder(getView(parent, layoutId)){

            @Override
            public void setData(Object data) {
                //do nothing
            }
        };
    }
}
