package com.steve.creact.powerfuladapter.presentation.viewholder;

import android.view.View;
import android.widget.TextView;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;
import com.steve.creact.powerfuladapter.R;
import com.steve.creact.powerfuladapter.data.Book;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {
    public static final int LAYOUT_ID = R.layout.item_book_title;
    private TextView nameTxt;
    private TextView priceTxt;

    public BookTitleViewHolder(View itemView) {
        super(itemView);
        nameTxt = findView(R.id.name);
        priceTxt = findView(R.id.price);
    }

    @Override
    public void setData(Book data) {
        if (data == null)
            return;
        nameTxt.setText(data.getName());
        priceTxt.setText(String.valueOf(data.getPrice()));
    }
}
