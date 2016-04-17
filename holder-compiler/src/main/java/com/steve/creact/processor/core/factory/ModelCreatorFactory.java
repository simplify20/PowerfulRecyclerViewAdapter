package com.steve.creact.processor.core.factory;

import com.steve.creact.processor.core.model.ModelCreator;

/**
 * Created by Administrator on 2016/4/10.
 */
public class ModelCreatorFactory {
    private ModelCreatorFactory() {
    }

    public static ModelCreator getCreator(Class<? extends ModelCreator> clz) {
        if (clz == null) {
            throw new IllegalArgumentException("clz argument can not be null.");
        }
        ModelCreator result = null;
        try {
            result = clz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
