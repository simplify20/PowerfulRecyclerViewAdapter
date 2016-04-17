package com.steve.creact.processor.app.processors;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.app.model.DataBeanModelCreator;
import com.steve.creact.processor.app.view.DataBeanGenerator;
import com.steve.creact.processor.core.BaseProcessor;
import com.steve.creact.processor.core.factory.ModelCreatorFactory;
import com.steve.creact.processor.core.factory.ViewGeneratorFactory;

import java.lang.annotation.Annotation;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 * A Annotation Processor to generate DataBean code
 */
@SupportedAnnotationTypes("com.steve.creact.annotation.DataBean")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DataBeanProcessor extends BaseProcessor {

    public DataBeanProcessor() {
        super(ModelCreatorFactory.getCreator(DataBeanModelCreator.class), ViewGeneratorFactory.getGenerator(DataBeanGenerator.class));
    }

    @Override
    protected ProcessType getProcessType() {
        return ProcessType.PROCESS_ANNOTATION_OF_ELEMENT;
    }

    @Override
    protected Class<? extends Annotation> getTargetAnnotationClass() {
        return DataBean.class;
    }

}
