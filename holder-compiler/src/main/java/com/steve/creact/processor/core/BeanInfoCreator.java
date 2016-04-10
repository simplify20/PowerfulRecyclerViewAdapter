package com.steve.creact.processor.core;

import com.steve.creact.processor.model.BeanInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface BeanInfoCreator {

    BeanInfo createDataBeanInfo(Element element,ProcessingEnvironment processingEnv);
}
