package com.steve.creact.library.adapter;

import android.support.v7.widget.RecyclerView;

import com.steve.creact.library.IDataArranger;
import com.steve.creact.library.IDataConsumer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author:YJJ
 * @date:2015/10/15
 * @email:yangjianjun@117go.com
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IDataConsumer<T>, IDataArranger<T> {
    protected List<T> data = new LinkedList<>();

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public void loadData(List<? extends T> datas) {
        this.data.clear();
        this.data.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void removeDatas(int[] positions) {
        if (positions == null || positions.length == 0) {
            return;
        }
        int length = positions.length;
        for (int i = 0; i < length; i++) {
            removeData(positions[i], true);
        }
        if (!useItemAnimation()) {
            notifyDataSetChanged();
        }
    }

    /**
     * @param position
     * @param oneOfMore
     */
    @Override
    public void removeData(int position, boolean oneOfMore) {
        if (position < 0 || position > data.size())
            return;
        data.remove(position);
        if (useItemAnimation()) {
            notifyItemRemoved(position);
        } else if (!oneOfMore) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void insertData(int position, T obj) {
        if (position < 0 || position > data.size() || obj == null)
            return;
        data.add(position, obj);
        if (useItemAnimation()) {
            notifyItemInserted(position);
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public void insertDatas(int position, List<T> insertedList) {
        if (position < 0 || position > data.size() || insertedList == null)
            return;
        data.addAll(position, insertedList);
        if (useItemAnimation()) {
            notifyItemRangeInserted(position, insertedList.size() + position);
        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * 只读
     *
     * @return
     */
    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    protected abstract boolean useItemAnimation();

}
