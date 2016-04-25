package com.steve.creact.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;

import com.steve.creact.library.listener.TextWatcherAdapter;

/**
 * An interface provides useful APIs for all kinds of ViewHolder,and can be reused.
 * Created by jiyang on 16/4/25.
 */
public interface HolderAPI {

  Context getContext();
  void removeFromCache(@IdRes int id);
  void removeAll();
  void initView();
  <T extends View> T getView(@IdRes int id);

  void setText(@IdRes int id, CharSequence text);
  CharSequence getText(@IdRes int id);

  void setOnItemClickListener(View.OnClickListener listener);
  void setTextWatcher(@IdRes int id, TextWatcherAdapter watcherAdapter);
  void setOnClickListener(@IdRes int id, View.OnClickListener onClickListener);
  void setOnLongClickListener(@IdRes int id, View.OnLongClickListener onLongClickListener);

  void setVisibility(@IdRes int id, int visibility);

  void setImageSrc(@IdRes int id, @DrawableRes int res);
  void setImageBitmap(@IdRes int id, Bitmap bitmap);
  void setImageDrawable(@IdRes int id, Drawable drawable);
}
