package com.steve.creact.library.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.steve.creact.library.HolderAPI;
import com.steve.creact.library.listener.TextWatcherAdapter;

/**
 * A Base ViewHolder provides some useful api
 *
 * @param <D>
 */
public abstract class BaseRecyclerViewHolder<D> extends RecyclerView.ViewHolder implements HolderAPI {

  protected final HolderAPI holderAPI;

  public BaseRecyclerViewHolder( View itemView ) {
    super( itemView );
    holderAPI = new HolderHelper( itemView );
    initView();
  }

  @Override
  public Context getContext() {
    return holderAPI.getContext();
  }

  @Override
  public void setOnItemClickListener( View.OnClickListener listener ) {
    holderAPI.setOnItemClickListener( listener );
  }

  /**
   * Notes:when you remove a view,you must remove it from cache
   *
   * @param id
   */
  @Override
  public void removeFromCache( @IdRes int id ) {
    holderAPI.removeFromCache( id );
  }

  /**
   * remove all views in cache
   */
  @Override
  public void removeAll() {
    holderAPI.removeAll();
  }

  /**
   * subclass can overwrite this method
   */
  @Override
  public void initView() {
    holderAPI.initView();
  }

  @Override
  public <T extends View> T getView( @IdRes int id ) {
    return holderAPI.getView( id );
  }

  @Override
  public void setText( @IdRes int id, CharSequence text ) {
    holderAPI.setText( id, text );
  }

  @Override
  public void setTextWatcher( @IdRes int id, TextWatcherAdapter watcherAdapter ) {
    holderAPI.setTextWatcher( id, watcherAdapter );
  }

  @Override
  public CharSequence getText( @IdRes int id ) {
    return holderAPI.getText( id );
  }

  @Override
  public void setOnClickListener( @IdRes int id, View.OnClickListener onClickListener ) {
    holderAPI.setOnClickListener( id, onClickListener );
  }

  @Override
  public void setOnLongClickListener( @IdRes int id, View.OnLongClickListener onLongClickListener ) {
    holderAPI.setOnLongClickListener( id, onLongClickListener );
  }

  @Override
  public void setVisibility( @IdRes int id, int visibility ) {
    holderAPI.setVisibility( id, visibility );
  }

  @Override
  public void setImageSrc( @IdRes int id, @DrawableRes int res ) {
    holderAPI.setImageSrc( id, res );
  }

  @Override
  public void setImageDrawable( @IdRes int id, Drawable drawable ) {
    holderAPI.setImageDrawable( id, drawable );
  }

  @Override
  public void setImageBitmap( @IdRes int id, Bitmap bitmap ) {
    holderAPI.setImageBitmap( id, bitmap );
  }

  public abstract void setData( D data );
}
