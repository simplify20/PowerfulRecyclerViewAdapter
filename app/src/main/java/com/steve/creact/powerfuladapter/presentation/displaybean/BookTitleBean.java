package com.steve.creact.powerfuladapter.presentation.displaybean;

import android.view.ViewGroup;

import com.steve.creact.library.display.BaseDataBean;
import com.steve.creact.powerfuladapter.data.Book;
import com.steve.creact.powerfuladapter.presentation.viewholder.BookTitleViewHolder;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class BookTitleBean extends BaseDataBean<Book, BookTitleViewHolder> {
    private Book book;

    public BookTitleBean(Book book) {
        this.book = book;
    }

    @Override
    public void bindData(BookTitleViewHolder holder) {
        holder.setData(book);
    }

    @Override
    public Book getData() {
        return book;
    }

    @Override
    public BookTitleViewHolder createHolder(ViewGroup parent) {
        return new BookTitleViewHolder(getView(parent, BookTitleViewHolder.LAYOUT_ID));
    }
}
