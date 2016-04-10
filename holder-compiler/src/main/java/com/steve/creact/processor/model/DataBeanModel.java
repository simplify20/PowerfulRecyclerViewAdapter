package com.steve.creact.processor.model;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataBeanModel extends AbstractModel<BeanInfo> {
    public DataBeanModel(Element element,ProcessingEnvironment processingEnv) {
        super(element);
        setElementVisitor(DataBeanVisitor.getInstance(processingEnv));
        visit(null);
    }
}
