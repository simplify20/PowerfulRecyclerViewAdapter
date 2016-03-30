package com.steve.creact.library.adapter;

import android.view.ViewGroup;

import com.steve.creact.library.display.DataBean;
import com.steve.creact.library.display.DisplayBean;
import com.steve.creact.library.viewholder.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:YJJ
 * @date:2015/10/14
 * @email:yangjianjun@117go.com
 */
public class CommonRecyclerAdapter extends BaseRecyclerAdapter<DisplayBean, BaseRecyclerViewHolder> {
    private boolean userAnimation = false;
    //for create
    private List<DisplayBean> createBeans = new ArrayList<>();
    private List<Class<DisplayBean>> createBeanClass = new ArrayList<>();

    //data
    @Override
    public void loadData(List<? extends DisplayBean> datas) {
        for (DisplayBean bean : datas) {
            if (!createBeanClass.contains(bean.getClass())) {
                addCreateBean(bean, (Class<DisplayBean>) bean.getClass());
            }
        }
        super.loadData(datas);
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
            addCreateBean(bean, clz);
            return (createBeans.size() - 1);
        }
        return index;
    }

    @Override
    protected boolean useItemAnimation() {
        return userAnimation;
    }

    private void addCreateBean(DisplayBean bean, Class<DisplayBean> aClass) {
        createBeanClass.add(aClass);
        createBeans.add(bean);
    }

}
