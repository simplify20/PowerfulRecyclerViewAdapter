package com.steve.creact.library.adapter;

import android.view.ViewGroup;

import com.steve.creact.library.display.CommonDisplayBean;
import com.steve.creact.library.display.DataBean;
import com.steve.creact.library.display.DisplayBean;
import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * A common adapter which extends from BaseRecyclerAdapter,can handle ViewHolder's creation
 * and bind data to ViewHolder.
 * <p/>
 * Use this class as your RecyclerView's adapter,it will do all tasks for you,
 * what you need to do is extending BaseRecyclerViewHolder,writing your custom ViewHolder class,
 * and extending  BaseDataBean ,writing your DataBean,or just using CommonDisplayBean.
 * <p/>
 * Alternatively,you can use @DataBean annotation(which can generate DataBean class at compiler time for you)
 * on your custom ViewHolder class
 *
 * @see DisplayBean
 * @see DataBean
 * @see CommonDisplayBean
 * @see BaseRecyclerViewHolder
 */
public class CommonRecyclerAdapter extends BaseRecyclerAdapter<DisplayBean, BaseRecyclerViewHolder> {
    private boolean userAnimation = false;
    //those bean instance will use to create concrete ViewHolders
    private List<DisplayBean> createBeans = new ArrayList<>();
    //those bean class will use to get correct item type
    private List<Class<DisplayBean>> createBeanClass = new ArrayList<>();

    //load data
    @Override
    public void loadData(List<? extends DisplayBean> dataSet) {
        if (dataSet == null || dataSet.size() == 0)
            return;
        for (int i = 0; i < dataSet.size(); i++) {
            DisplayBean bean = dataSet.get(i);
            if (!createBeanClass.contains(bean.getClass())) {
                addCreateBean(bean);
            }
        }
        super.loadData(dataSet);
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DisplayBean createBean = createBeans.get(viewType);
        return createBean.createHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        DisplayBean bindBean = data.get(position);
        if (bindBean instanceof DataBean) {
            ((DataBean) bindBean).bindData(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        DisplayBean bean = data.get(position);
        Class clz = bean.getClass();
        int index = createBeanClass.indexOf(clz);
        if (index < 0) {
            addCreateBean(bean);
            return (createBeans.size() - 1);
        }
        return index;
    }

    @Override
    protected boolean useItemAnimation() {
        return this.userAnimation;
    }

    public void setUserAnimation(boolean userAnimation) {
        this.userAnimation = userAnimation;
    }

    private void addCreateBean(DisplayBean bean) {
        Class<DisplayBean> clz = (Class<DisplayBean>) bean.getClass();
        createBeanClass.add(clz);
        createBeans.add(bean);
    }

}
