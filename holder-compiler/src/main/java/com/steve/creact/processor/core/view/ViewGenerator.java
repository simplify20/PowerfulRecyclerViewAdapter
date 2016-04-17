package com.steve.creact.processor.core.view;

import com.steve.creact.processor.core.model.AbstractModel;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface ViewGenerator<T> {

    /**
     * Generate View according to the model
     * @param abstractModel
     * @param processingEnv
     */
    void generate(AbstractModel<T> abstractModel,ProcessingEnvironment processingEnv);
}
