package com.steve.creact.library.adapter;

import android.support.v7.widget.RecyclerView;

import com.steve.creact.library.IDataArranger;
import com.steve.creact.library.IDataConsumer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * BaseAdapter which supports insert|add|get operations on DataSet
 * Also support item animation.
 */
public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements IDataConsumer<T>, IDataArranger<T> {
    //DataSet
    protected List<T> data = new LinkedList<>();

    @Override
    public T getItem(int position) {
        if (position < 0 || position > data.size() - 1)
            return null;
        return data.get(position);
    }

    @Override
    public void loadData(List<? extends T> datas) {
        if (datas == null || datas.size() == 0)
            return;
        this.data.clear();
        this.data.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void removeData(int[] positions) {
        if (positions == null || positions.length == 0) {
            return;
        }
        int length = positions.length;
        boolean useAnimation = useItemAnimation();
        for (int i = 0; i < length; i++) {
            removeData(positions[i],useAnimation);
        }
        if (!useAnimation) {
            notifyDataSetChanged();
        }
    }

    //if we don't use animation ,no need call notifyDataSetChanged() every time
    private void removeData(int position,boolean useAnimation){

        if (position < 0 || position > data.size() - 1)
            return;
        data.remove(position);
        if (useAnimation)
            notifyItemRemoved(position);
    }


    @Override
    public void removeData(int position) {
        if (position < 0 || position > data.size() - 1)
            return;
        data.remove(position);
        if (useItemAnimation())
            notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public void removeData(T obj) {
        int pos = data.indexOf(obj);
        removeData(pos);
    }

    @Override
    public void removeFirst() {
        removeData(0);
    }

    @Override
    public void removeLast() {
        int size = getItemCount();
        removeData(size == 0 ? -1 : size - 1);
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
    public void insertFirst(T obj) {
        insertData(0,obj);
    }

    @Override
    public void insertLast(T obj) {
        int size = getItemCount();
        insertData(size,obj);
    }

    @Override
    public void insertData(int position, List<T> insertedList) {
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
