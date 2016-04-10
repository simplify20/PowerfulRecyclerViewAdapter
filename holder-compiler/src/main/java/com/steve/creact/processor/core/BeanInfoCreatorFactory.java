package com.steve.creact.processor.core;

import com.steve.creact.processor.core.impl.BeanInfoCreatorImpl;

/**
 * Created by Administrator on 2016/4/10.
 */
public class BeanInfoCreatorFactory {
    private BeanInfoCreatorFactory() {
    }

    public static BeanInfoCreator get(Class<? extends BeanInfoCreator> clz) {
        if (clz == null) {
            throw new IllegalArgumentException("clz argument can not be null.");
        }
        BeanInfoCreator result = null;
        try {
            result = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (result == null)
            result = new BeanInfoCreatorImpl();
        return result;
    }
}
