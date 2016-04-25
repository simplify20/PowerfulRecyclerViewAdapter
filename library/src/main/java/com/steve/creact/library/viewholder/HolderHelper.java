package com.steve.creact.library.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.steve.creact.library.HolderAPI;
import com.steve.creact.library.listener.TextWatcherAdapter;

/**
 * A HolderAPI implementation which can be reused
 * Created by jiyang on 16/4/25.
 */
public class HolderHelper implements HolderAPI {
  /**
   * view cache
   */
  protected SparseArray<View> views = new SparseArray<>();
  protected View itemView;

  public HolderHelper( View itemView ) {
    this.itemView = itemView;
  }

  @Override
  public Context getContext() {
    return itemView.getContext();
  }

  @Override
  public void removeFromCache( @IdRes int id ) {
    views.remove(id);
  }

  @Override
  public void removeAll() {
    views.clear();
  }

  @Override
  public void initView() {
    //do nothing
  }

  @Override
  public <T extends View> T getView( @IdRes int id ) {
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

  @Override
  public void setText( @IdRes int id, CharSequence text ) {
    TextView targetTxt = getView(id);
    if (targetTxt != null)
      targetTxt.setText(text);
  }

  @Override
  public CharSequence getText( @IdRes int id ) {
    TextView targetTxt = getView(id);
    if (targetTxt != null)
      return targetTxt.getText();
    return "";
  }

  @Override
  public void setOnItemClickListener( View.OnClickListener listener ) {
    itemView.setOnClickListener(listener);
  }

  @Override
  public void setTextWatcher( @IdRes int id, TextWatcherAdapter watcherAdapter ) {
    TextView targetTxt = getView(id);
    if (targetTxt != null)
      targetTxt.addTextChangedListener(watcherAdapter);
  }

  @Override
  public void setOnClickListener( @IdRes int id, View.OnClickListener onClickListener ) {
    View targetView = getView(id);
    if (targetView != null) {
      targetView.setOnClickListener(onClickListener);
    }
  }

  @Override
  public void setOnLongClickListener( @IdRes int id, View.OnLongClickListener onLongClickListener ) {
    View targetView = getView(id);
    if (targetView != null) {
      targetView.setOnLongClickListener(onLongClickListener);
    }
  }

  @Override
  public void setVisibility( @IdRes int id, int visibility ) {
    View targetView = getView(id);
    if (targetView != null) {
      targetView.setVisibility(visibility);
    }
  }

  @Override
  public void setImageSrc( @IdRes int id, @DrawableRes int res ) {
    ImageView targetImageView = getView(id);
    if (targetImageView != null)
      targetImageView.setImageResource(res);
  }

  @Override
  public void setImageBitmap( @IdRes int id, Bitmap bitmap ) {
    ImageView targetImageView = getView(id);
    if (targetImageView != null)
      targetImageView.setImageBitmap(bitmap);
  }

  @Override
  public void setImageDrawable( @IdRes int id, Drawable drawable ) {
    ImageView targetImageView = getView(id);
    if (targetImageView != null)
      targetImageView.setImageDrawable(drawable);
  }
}
