package com.steve.creact.powerfuladapter.presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.steve.creact.library.adapter.CommonRecyclerAdapter;
import com.steve.creact.library.display.CommonDisplayBean;
import com.steve.creact.library.display.DisplayBean;
import com.steve.creact.powerfuladapter.R;
import com.steve.creact.powerfuladapter.data.Book;
import com.steve.creact.powerfuladapter.data.Category;
import com.steve.creact.powerfuladapter.data.MockCategory;
import com.steve.creact.powerfuladapter.presentation.displaybean.BookTitleBean;
import com.steve.creact.powerfuladapter.presentation.displaybean.CategoryBean;
import com.steve.creact.powerfuladapter.presentation.viewholder.databean.TestDataBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseListActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected CommonRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    protected void initData() {
        List<DisplayBean> bookTitleBeans = new ArrayList<>(20);
        //add progress display bean
        CommonDisplayBean progressBean = new CommonDisplayBean(R.layout.item_progress);
        bookTitleBeans.add(progressBean);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            if (i % 5 == 0) {
                //add category
                if (i == 0) {
                    //add mock category
                    MockCategory mockCategory = new MockCategory();
                    CategoryBean categoryBean = new CategoryBean(mockCategory);
                    bookTitleBeans.add(categoryBean);
                } else {
                    Category category = new Category(i / 5, "category" + (i / 5 + 1));
                    CategoryBean categoryBean = new CategoryBean(category);
                    bookTitleBeans.add(categoryBean);
                }
            }
            float price = random.nextFloat() * 200 + 1.0f;
            Book book = new Book(i, "book" + i, (float) (Math.round(price * 100) / 100.0), (i + 50));
//            BookTitleBean bookTitleBean = new BookTitleBean(book);
            TestDataBean bookTitleBean = new TestDataBean(book);
            bookTitleBeans.add(bookTitleBean);
        }
        //load data
        adapter.loadData(bookTitleBeans);
    }

    protected void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = getAdapter();
        recyclerView.setAdapter(adapter);
    }

    protected CommonRecyclerAdapter getAdapter() {
        return new CommonRecyclerAdapter();
    }

}
