package com.steve.creact.processor.core;

import com.steve.creact.processor.model.BeanInfo;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface ViewGenerator {

    void generate(BeanInfo beanInfo,ProcessingEnvironment processingEnv);
}
