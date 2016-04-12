##PowerfulRecyclerAdapter

An implement for RecyclerView.Adapter that supports any kind of list items, is a common Adapter.


**Features**

- Bind Data(Model) and ViewHolder using DataBean,DataBean is a Wrapper of Data(Model);
- DataBean control creation of the ViewHolder and binds data to the ViewHolder;
- Separate part of Adapter' responsibilities to DataBean，such as creating an instance of the ViewHolder and binding data to ViewHolder.Adapter only operate data;
- Remove all switch..case statemens in onCreateViewHolder() or onBindViewHolder() taking advantage of Polymorphism;
- You do not need to write any Recycler.Adapters after you use this powerful common adapter;
- Your RecyclerView can have any kind of items(or viewHolders).

**New features**

	Added in 2016-4-10：
	@DataBean Annotation(dev branch)
	Use apt(Annotation Processor Tool) like used in Dagger2 and DataBinding to process annotations and generate DataBean source code for you,you don't need to write databean classes anymore,that's a progress.
	see [Use @DataBean] guide module

dev branch：https://github.com/simplify20/PowerfulRecyclerViewAdapter/tree/dev
###Class diagram：
	tips:if the image is not clear,save it to local and use a picture browser to view.

![Class diagram](http://img.blog.csdn.net/20160330181333561)
###Important classes：

`class CommonRecyclerAdapter`:common adapter whose super class is RecyclerView.Adapter，support inserting and droping datas and support any kind of items.

`interface DisplayBean`:use to create an instance of the ViewHolder

`interface DataBean`:extends from DisplayBean，use to bind data to viewholders

###How To：

- Declare CommonRecyclerAdapter as your RecyclerView adapter;
```java
 protected CommonRecyclerAdapter adapter;
 ...
 recyclerView.setAdapter(adapter);
```
- Extend BaseDataBean，create your DataBean;
```java
public class BookTitleBean extends BaseDataBean<Book, BookTitleViewHolder> {

    public BookTitleBean(Book data) {
        super(data);
    }

    @Override
    public BookTitleViewHolder createHolder(ViewGroup parent) {
	    //create an instance of Your ViewHolder
        return new BookTitleViewHolder(getView(parent, BookTitleViewHolder.LAYOUT_ID));
    }
}
```
- Extend BaseRecyclerViewHolder，create your ViewHolder;

```java
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {
	//declare LAYOUT_ID
    public static final int LAYOUT_ID = R.layout.item_book_title;
    private TextView nameTxt;
    private TextView priceTxt;

    public BookTitleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView() {
        nameTxt = findView(R.id.name);
        priceTxt = findView(R.id.price);
    }

    @Override
    public void setData(Book data) {
        if (data == null)
            return;
        nameTxt.setText(data.getName());
        priceTxt.setText(String.valueOf(data.getPrice()));
    }
}
```

- Construct DisplayBeans：convert data set to displaybean set

```java
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

```
Screenshot：
![screenshot1](http://img.blog.csdn.net/20160330174123392)

- For items have no data or only display static data，for example,just show a progressBar,use CommonDisplayBean which creates an instance of BaseRecyclerViewHolder
![screenshot2](http://img.blog.csdn.net/20160330174155924)

- Reuse ViewHolder and DataBean

 Use interface or abstract class (type parameter of BaseRecyclerViewHolder)to bind DateBean and ViewHolder 

 Demo:
 ####`ICategory` interface
 ```java
 /**
 * An interface represents an abstract category
 */
public interface ICategory {

    String getName();
    long getId();
}

 ```
 ####`CategoryBean`:
 ```java
 public class CategoryBean extends BaseDataBean<ICategory, CategoryViewHolder> {

    public CategoryBean(ICategory data) {
        super(data);
    }

    @Override
    public CategoryViewHolder createHolder(ViewGroup parent) {
        return new CategoryViewHolder(getView(parent, CategoryViewHolder.LAYOUT_ID));
    }
}
 ```
 
 ####`CategoryViewHolder`
```java
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
```



### Use @DataBean
Because BaseDataBean and its super classes have done much work for us，our DataBean's code is very simple,only have couple of lines.Simpler than Simpler,we use apt to genreate your DataBean source code.The tool is holder-compiler.Now you can use @DataBean on your ViewHolder,The apt will generate corresponding code for you as you wanted，it's very convenient.


**How to:take BookTitleViewHolder as an example**
```java
//use DataBean annotation to annotate your ViewHolder
@DataBean(beanName = "TestDataBean", data = Book.class)
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {
	//declare LAYOUT_ID,the name must be LAYOUT_ID
    public static final int LAYOUT_ID = R.layout.item_book_title;
    private TextView nameTxt;
    private TextView priceTxt;

    public BookTitleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView() {
        nameTxt = findView(R.id.name);
        priceTxt = findView(R.id.price);
    }

    @Override
    public void setData(Book data) {
        if (data == null)
            return;
        nameTxt.setText(data.getName());
        priceTxt.setText(String.valueOf(data.getPrice()));
    }
}
```
1.Extend BaseRecyclerViewHolder to create your ViewHolder;

	Notes:public field LAYOUT_ID of your ViewHolder is 
required ，and the name must be LAYOUT_ID(apt need this constant).
2.Use @DataBean on your ViewHolder(only for class whose super class is BaseRecyclerViewHolder)
3.Elements of DataBean：

- beanName->simple class name of the generated DataBean，String type;
- data->class of data which wrapped by DataBean and used by ViewHolder，Class type.

4.build the project，apt will generated the DataBean code for you,and the generated TestDataBean:
app\build\generated\source\apt\debug\ [package]\TestDataBean.java
```java
package com.steve.creact.powerfuladapter.presentation.viewholder.databean;

import android.view.ViewGroup;

import com.steve.creact.library.display.BaseDataBean;
import com.steve.creact.powerfuladapter.data.Book;
import com.steve.creact.powerfuladapter.presentation.viewholder.BookTitleViewHolder;

/**
 * Generated DataBean for BookTitleViewHolder
 * Powered by Holder-Compiler
 */
public class TestDataBean extends BaseDataBean<Book, BookTitleViewHolder> {

    public TestDataBean(Book data) {
        super(data);
    }

    @Override
    public BookTitleViewHolder createHolder(ViewGroup parent) {
        return new BookTitleViewHolder(getView(parent, BookTitleViewHolder.LAYOUT_ID));//that's why need LAYOUT_ID field in ViewHolder
    }
}
```
Just like handwriting code!

**problems may occur when you build:**

- can't delete holder-compiler.jar->open task manager，end process of java se，rebuild.

###Contact Me:

>Email:creact92@gmail.com

>Weibo:http://weibo.com/u/3398987850

>Github:https://github.com/simplify20

>CSDN:http://blog.csdn.net/u012825445
