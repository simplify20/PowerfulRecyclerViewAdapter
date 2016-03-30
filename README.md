##PowerfulRecyclerAdapter
支持各种ViewHolder类型的RecyclerView.Adapter的实现，是一个万能适配器
###类图：
	tips:图片看不清可右键另存或新标签页打开后查看
![这里写图片描述](http://img.blog.csdn.net/20160330181333561)
###主要类：

`class CommonRecyclerAdapter`：万能适配器，支持插入和删除数据

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
	    //创建一个ViewHolder实例
        return new BookTitleViewHolder(getView(parent, BookTitleViewHolder.LAYOUT_ID));
    }
}
```
- 根据item内容，继承BaseRecyclerViewHolder，实现自定义ViewHolder；
```java
public class BookTitleViewHolder extends BaseRecyclerViewHolder<Book> {
	//声明布局ID
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
- 使用DataBean
```java
    protected void initData() {
	    //数据集
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
                    continue;
                }
                Category category = new Category(i / 5, "category" + (i / 5 + 1));
                CategoryBean categoryBean = new CategoryBean(category);
                bookTitleBeans.add(categoryBean);
            }
            float price = random.nextFloat() * 200 + 1.0f;
            Book book = new Book(i, "book" + i, (float) (Math.round(price * 100) / 100.0), (i + 50));
            BookTitleBean bookTitleBean = new BookTitleBean(book);
            bookTitleBeans.add(bookTitleBean);
        }
        adapter.loadData(bookTitleBeans);
    }
```
效果：
![这里写图片描述](http://img.blog.csdn.net/20160330174123392)

- 对于只展示静态数据或没有数据（无文字显示）的item，如只显示一个progressBar,使用CommonDisplayBean，默认创建BaseRecyclerViewHolder
![这里写图片描述](http://img.blog.csdn.net/20160330174155924)

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
