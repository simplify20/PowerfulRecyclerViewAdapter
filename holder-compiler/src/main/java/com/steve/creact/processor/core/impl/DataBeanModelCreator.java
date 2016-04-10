package com.steve.creact.processor.core.impl;

import com.steve.creact.processor.core.ModelCreator;
import com.steve.creact.processor.model.AbstractModel;
import com.steve.creact.processor.model.DataBeanModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataBeanModelCreator implements ModelCreator {
    public DataBeanModelCreator() {
    }

    @Override
    public AbstractModel createModel(Element element, ProcessingEnvironment processingEnv) {
        return new DataBeanModel(element,processingEnv);
    }
}
