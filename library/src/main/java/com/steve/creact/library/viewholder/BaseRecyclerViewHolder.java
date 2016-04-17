package com.steve.creact.library.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A base ViewHolder supports some useful api
 *
 * @param <D>
 */
public abstract class BaseRecyclerViewHolder<D> extends RecyclerView.ViewHolder {
    /**
     * view cache
     */
    protected SparseArray<View> views = new SparseArray<>();

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
        initView();
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public void setOnItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    /**
     * Notes:when you remove a view,you must remove it from cache
     *
     * @param id
     */
    public void removeFromCache(@IdRes int id) {
        views.remove(id);
    }

    /**
     * remove all views in cache
     */
    public void removeAll() {
        views.clear();
    }

    /**
     * subclass can overwrite this method
     */
    public void initView() {
    }

    public <T extends View> T getView(@IdRes int id) {
        T result = null;
        try {
            if ((result = (T) views.get(id)) != null)
                return result;
            result = (T) itemView.findViewById(id);
            if (result == null)
                return result;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return result;
        }
        //add to cache
        views.put(id, View.class.cast(result));
        return result;
    }

    public void setText(@IdRes int id, CharSequence text) {

        TextView targetTxt = getView(id);
        if (targetTxt != null)
            targetTxt.setText(text);

    }

    public void setTextWatcher(@IdRes int id, TextWatcherAdapter watcherAdapter) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            targetTxt.addTextChangedListener(watcherAdapter);
    }

    public CharSequence getText(@IdRes int id) {
        TextView targetTxt = getView(id);
        if (targetTxt != null)
            return targetTxt.getText();
        return "";
    }


    public void setOnClickListener(@IdRes int id, View.OnClickListener onClickListener) {
        View targetView = getView(id);
        if (targetView != null) {
            targetView.setOnClickListener(onClickListener);
        }
    }

    public void setOnLongClickListener(@IdRes int id, View.OnLongClickListener onLongClickListener) {
        View targetView = getView(id);
        if (targetView != null) {
            targetView.setOnLongClickListener(onLongClickListener);
        }
    }

    public void setVisibility(@IdRes int id, int visibility) {
        View targetView = getView(id);
        if (targetView != null) {
            targetView.setVisibility(visibility);
        }
    }


    public void setImageSrc(@IdRes int id, @DrawableRes int res) {
        ImageView targetImageView = getView(id);
        if (targetImageView != null)
            targetImageView.setImageResource(res);
    }

    public void setImageDrawable(@IdRes int id, Drawable drawable) {
        ImageView targetImageView = getView(id);
        if (targetImageView != null)
            targetImageView.setImageDrawable(drawable);
    }

    public void setImageBitmap(@IdRes int id, Bitmap bitmap) {
        ImageView targetImageView = getView(id);
        if (targetImageView != null)
            targetImageView.setImageBitmap(bitmap);
    }


    public static class TextWatcherAdapter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public abstract void setData(D data);
}
