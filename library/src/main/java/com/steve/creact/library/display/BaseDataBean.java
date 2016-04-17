package com.steve.creact.library.display;


import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;

/**
 * A base implementation for DataBean,you can extend it to write your custom DataBean
 * @param <T>
 * @param <VH>
 */
public abstract class BaseDataBean<T,VH extends BaseRecyclerViewHolder> extends BaseDisplayBean<VH> implements DataBean<T,VH> {
    protected T data;

    public BaseDataBean(T data) {
        this.data = data;
    }

    @Override
    public void bindData(VH holder) {
        holder.setData(data);
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
