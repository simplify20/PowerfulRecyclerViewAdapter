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
import com.steve.creact.powerfuladapter.presentation.viewholder.ICategory;
import com.steve.creact.powerfuladapter.presentation.viewholder.databean.BookTitleBean;
import com.steve.creact.powerfuladapter.presentation.viewholder.databean.CategoryBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Convert normal DataSet to DisplayBeans
     * If the data set which  returned by the server in the same order as in the list on ui,
     * this process will be easy
     */
    protected void initData() {
        //fake data
        Map<ICategory, List<Book>> sourceData = fetchSourceData();
        //display beans
        List<DisplayBean> displayBeans = new ArrayList<>(20);

        //Add a ProgressBar DisplayBean to show a ProgressBar in the top of the list
        CommonDisplayBean progressBean = new CommonDisplayBean(R.layout.item_progress);
        displayBeans.add(progressBean);

        //add categories and books to the list
        for (Iterator<Map.Entry<ICategory, List<Book>>> iterator = sourceData.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<ICategory, List<Book>> entry = iterator.next();
            ICategory category = entry.getKey();
            //add category to the list
            displayBeans.add(new CategoryBean(category));
            List<Book> books = entry.getValue();
            //add books to the category
            if (category != null && books != null) {
                for (Book book : books) {
                    BookTitleBean bookTitleBean = new BookTitleBean(book);
                    displayBeans.add(bookTitleBean);
                }
            }
        }
        //load data and show data in the list
        adapter.loadData(displayBeans);
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

    //fake data
    private Map<ICategory, List<Book>> fetchSourceData() {
        Map<ICategory, List<Book>> result = new LinkedHashMap<>();
        for (int i = 0; i < 5; i++) {
            ICategory category = i == 0 ? new MockCategory() : new Category(i, "category" + i);
            List<Book> books = new ArrayList<>(10);
            for (int j = 0; j < 10; j++) {
                Book book = new Book(j + 1, "book" + j, (200.5f + j), 100);
                books.add(book);
            }
            result.put(category, books);
        }
        return result;
    }

}
