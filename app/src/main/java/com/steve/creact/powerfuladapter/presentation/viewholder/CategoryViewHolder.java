package com.steve.creact.powerfuladapter.presentation.viewholder;

import android.view.View;
import android.widget.TextView;

import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;
import com.steve.creact.powerfuladapter.R;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class CategoryViewHolder extends BaseRecyclerViewHolder<ICategory> {
    public static final int LAYOUT_ID = R.layout.item_book_catagory;
    protected TextView categoryNameTxt;

    public CategoryViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView() {
        categoryNameTxt = findView(R.id.book_category);
    }

    @Override
    public void setData(ICategory category) {
        if (category == null)
            return;
        categoryNameTxt.setText(category.getName());
    }
}
