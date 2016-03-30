package com.steve.creact.powerfuladapter.data;

import com.steve.creact.powerfuladapter.presentation.viewholder.ICategory;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class MockCategory implements ICategory {
    @Override
    public String getName() {
        return "mockCategory";
    }

    @Override
    public long getId() {
        return 0;
    }
}
