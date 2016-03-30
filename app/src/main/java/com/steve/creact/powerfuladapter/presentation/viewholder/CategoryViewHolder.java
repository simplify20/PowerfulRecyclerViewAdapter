package com.steve.creact.powerfuladapter.presentation.viewholder;

import android.view.View;
import android.widget.TextView;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;
import com.steve.creact.powerfuladapter.R;
import com.steve.creact.powerfuladapter.data.Category;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class CategoryViewHolder extends BaseRecyclerViewHolder<Category> {
    public static final int LAYOUT_ID = R.layout.item_book_catagory;
    private TextView catrgoryNameTxt;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        catrgoryNameTxt = findView(R.id.book_category);
    }

    @Override
    public void setData(Category data) {
        if (data == null)
            return;
        catrgoryNameTxt.setText(data.getName());
    }
}
