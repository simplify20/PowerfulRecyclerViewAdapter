package com.steve.creact.processor.core.model;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface ModelCreator {

    /**
     * create a model
     * @param element
     * @param processingEnv
     * @return
     */
    AbstractModel createModel(Element element, ProcessingEnvironment processingEnv);
}
