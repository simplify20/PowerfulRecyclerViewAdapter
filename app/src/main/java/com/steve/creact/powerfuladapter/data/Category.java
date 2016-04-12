package com.steve.creact.powerfuladapter.data;

import com.steve.creact.powerfuladapter.presentation.viewholder.ICategory;

/**
 * @author:YJJ
 * @date:2016/3/30
 * @email:yangjianjun@117go.com
 */
public class Category implements ICategory {
    private long id;
    private String name = "";

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Category) && (this.id == ((Category) o).getId()) && this.name.equals(((Category) o).getName());
    }
}
