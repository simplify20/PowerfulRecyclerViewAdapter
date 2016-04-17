package com.steve.creact.powerfuladapter.presentation.viewholder;

import android.view.View;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;
import com.steve.creact.powerfuladapter.R;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
@DataBean(beanName = "CategoryBean",data = ICategory.class)
public class CategoryViewHolder extends BaseRecyclerViewHolder<ICategory> {
    public static final int LAYOUT_ID = R.layout.item_book_catagory;
    public CategoryViewHolder(View itemView) {
        super(itemView);
    }
    @Override
    public void setData(ICategory category) {
        if (category == null)
            return;
        setText(R.id.book_category,category.getName());
    }

}
