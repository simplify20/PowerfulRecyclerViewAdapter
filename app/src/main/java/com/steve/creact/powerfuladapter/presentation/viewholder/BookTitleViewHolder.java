package com.steve.creact.powerfuladapter.presentation.viewholder;

import android.view.View;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;
import com.steve.creact.powerfuladapter.R;
import com.steve.creact.powerfuladapter.data.Book;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
@DataBean(beanName = "BookTitleBean", data = Book.class)
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {

    public static final int LAYOUT_ID = R.layout.item_book_title;

    public BookTitleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Book data) {
        if (data == null)
            return;
        setText(R.id.name, data.getName());
        setText(R.id.price, String.valueOf(data.getPrice()));
    }
}
