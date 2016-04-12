package com.steve.creact.powerfuladapter.data;

import com.steve.creact.powerfuladapter.presentation.viewholder.ICategory;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class MockCategory implements ICategory {
    private long id = 0;
    private String name = "mockCategory";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Category) && (this.id == ((Category) o).getId()) && this.name.equals(((Category) o).getName());
    }
}
