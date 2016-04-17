package com.steve.creact.processor.app.model;

import com.steve.creact.processor.core.model.AbstractModel;
import com.steve.creact.processor.core.model.ModelCreator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataBeanModelCreator implements ModelCreator {
    @Override
    public AbstractModel createModel(Element element, ProcessingEnvironment processingEnv) {
        return new DataBeanModel(element,processingEnv);
    }
}
