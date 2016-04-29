##PowerfulRecyclerAdapter
[![Bintray](https://img.shields.io/badge/maven%20center-v1.0.0-brightgreen.svg)](https://bintray.com/creact/maven/powerful-adapter-lib-release)
[![android-arsenal](https://img.shields.io/badge/Android%20Arsenal%20-PowerfulRecyclerViewAdapter-blue.svg)](http://android-arsenal.com/details/1/3472)
[![Apache 2](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](https://github.com/simplify20/PowerfulRecyclerViewAdapter/blob/master/LICENSE)

RecyclerView.Adapter.的一种实现,支持任意类型的Item，并支持对数据集的增删操作，是一个通用适配器。


**特性：**

- 数据集操作，如增加，删除等；
- 使用DataBean关联Data(Model)与ViewHolder,DataBean相当于Data的Wrapper；
- DataBean控制ViewHolder的创建以及数据到ViewHolder的绑定；
- Adapter的一部分职能由DataBean承担，如创建不同类型的ViewHolder以及绑定数据到ViewHolder,Adapter只用维护数据的相关操作即可；
- Adapter的onCreateViewHolder()和onBindViewHolder()中没有switch..case语句，通过DataBean的多态性实现不同的创建和绑定；
- 使用了本项目的Adapter,使用RecyclerView时就不用写Adapter了；
- 一个列表可以有任意多种item(对应于任意种类的ViewHolder)
- 使用接口关联DataBean和ViewHolder可以提高ViewHolder及Data的复用性，并且利于测试。

**新特性：**

    2016-4-16 添加：
    1.在BaseRecyclerViewHolder中新增了一些有用的api以简化ViewHolder的编写，例如setText(id,text),setImageBitmap(id,bitmap)等；
    2.在BaseRecyclerAdapter中添加了一些友好的，有用的api,例如removeData(data),removeFirst(),removeLast()等;
    3.使用SparseArray缓存ViewHolder中的View,如需了解细节，请看BaseRecyclerViewHolder的源码。
    
    2016-4-10 添加：
    新增 @DataBean 注解
    使用类似Dagger2和DataBinding的编译期注解处理器，在编译器根据模板生成DataBean代码（模板引擎），这样可以省去编写DataBean的成本。
    参考下文[使用DataBean注解]




###主要类：

`class CommonRecyclerAdapter`：万能适配器，支持插入和删除数据,支持任意类型的ViewHolder(限于RecyclerView)

`interface DisplayBean`：用于创建ViewHolder

`interface DataBean`：扩展了DisplayBean接口，可以绑定数据到ViewHolder，也可以创建ViewHolder,是数据与ViewHolder之间的桥梁

###使用步骤：

- 使用CommonRecyclerAdapter作为RecyclerView的Adapter；
```java
 protected CommonRecyclerAdapter adapter;
 ...
 recyclerView.setAdapter(adapter);
```
- 根据item内容，继承BaseDataBean，实现自定义的DataBean；
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
- 根据item内容，继承BaseRecyclerViewHolder，实现自定义ViewHolder；

```java
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {


    public static final int LAYOUT_ID = R.layout.item_book_title;

    public BookTitleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Book data) {
        if (data == null)
            return;
        setText(R.id.name, data.getName());
        setText(R.id.price, String.valueOf(data.getPrice()));
    }
}
```

- 构建用于显示的数据集：data set -> displaybean set

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
效果：

![这里写图片描述](http://img.blog.csdn.net/20160412195405867)

- 对于只展示静态数据或没有数据（无文字显示）的item，如只显示一个progressBar,使用CommonDisplayBean，默认创建BaseRecyclerViewHolder

![这里写图片描述](http://img.blog.csdn.net/20160412195422029)

- 复用ViewHolder和DataBean

 使用接口关联DateBean和ViewHolder
 ####`ICategory` 接口
 ```java
 /**
 * Notes:
 * 1.使用接口以降低耦合，提高ViewHolder及DataBean的复用性
 * 2.在有复用需求的情况下，使用接口，没有这样的需求可以使用具体Data类
 * 3.这个接口表明了ViewHolder的需求,表明了ViewHolder上要展示的内容，或者潜在的交互
 * 4.在服务器接口还没有完成时，可以创建mock实现，以和服务器进行独立开发测试
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
```



###使用@DataBean注解
因为BaseDataBean及其父类已经完成了很多工作，编写具体DataBean的代码很少，且都是样板代码，为了简单省事，所以采用代码生成器来生成这部分代码。而生成代码的工具是holder-compiler：注解处理器，这个我已经写好了，在使用时，你只需用@DataBean注解你的具体ViewHolder,注解处理器会帮你生成所需的代码，非常方便。


**使用步骤,以BookTitleViewHolder为例:**
```java
//use DataBean annotation to annotate your ViewHolder
@DataBean(beanName = "BookTitleBean", data = Book.class)
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {

    public static final int LAYOUT_ID = R.layout.item_book_title;

    public BookTitleViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Book data) {
        if (data == null)
            return;
        setText(R.id.name, data.getName());
        setText(R.id.price, String.valueOf(data.getPrice()));
    }
}
```
1.继承BaseRecyclerViewHolder创建你的ViewHolder；

	注:ViewHolder中LAYOUT_ID字段是必填的，且命名限定为LAYOUT_ID,是一个公有常量，因为生成的代码要引用ViewHolder的这个字段。
2.使用@DataBean注解你的ViewHolder(只能注解类，详见DataBean注解的源码，在holder-annotation module下)

DataBean的几个属性：

- beanName->要生成的DataBean的简单类名，String类型；
- data->要绑定的数据的类型，Class类型。

3.build项目，注解处理器会在编译器获得注解信息，并生成代码，生成的BookTitleBean如下：
app\build\generated\source\apt\debug\ [package]\BookTitleBean.java
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
public class BookTitleBean extends BaseDataBean<Book, BookTitleViewHolder> {

    public BookTitleBean(Book data) {
        super(data);
    }

    @Override
    public BookTitleViewHolder createHolder(ViewGroup parent) {
        return new BookTitleViewHolder(getView(parent, BookTitleViewHolder.LAYOUT_ID));//that's why need LAYOUT_ID field in ViewHolder
    }
}
```
可见，与手写代码完全一样。

**build时可能存在的问题：**
holder-compiler.jar无法删除->打开任务管理器，结束java se进程，重新build.

###联系我（Contact Me）:

>Email:creact92@gmail.com

>Weibo:http://weibo.com/u/3398987850

>Github:https://github.com/simplify20

>CSDN:http://blog.csdn.net/u012825445
