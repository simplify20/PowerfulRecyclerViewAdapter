package com.steve.creact.powerfuladapter.presentation.displaybean;

import android.view.ViewGroup;

import com.steve.creact.library.display.BaseDataBean;
import com.steve.creact.powerfuladapter.data.Category;
import com.steve.creact.powerfuladapter.presentation.viewholder.CategoryViewHolder;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class CategoryBean extends BaseDataBean<Category, CategoryViewHolder> {
    private Category category;

    public CategoryBean(Category category) {
        this.category = category;
    }

    @Override
    public void bindData(CategoryViewHolder holder) {
        holder.setData(category);
    }

    @Override
    public Category getData() {
        return category;
    }

    @Override
    public CategoryViewHolder createHolder(ViewGroup parent) {
        return new CategoryViewHolder(getView(parent, CategoryViewHolder.LAYOUT_ID));
    }
}
