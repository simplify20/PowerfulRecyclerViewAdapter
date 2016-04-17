package com.steve.creact.processor.core;

import com.steve.creact.processor.core.model.AbstractModel;
import com.steve.creact.processor.core.model.ModelCreator;
import com.steve.creact.processor.core.view.ViewGenerator;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by Administrator on 2016/4/17.
 */
public abstract class BaseProcessor extends AbstractProcessor {
    public enum ProcessType {
        PROCESS_ANNOTATION_OF_TYPE_ELEMENT,
        PROCESS_ANNOTATION_OF_ELEMENT
    }

    protected Set<? extends Element> elements;
    protected Set<? extends TypeElement> typeElements;
    protected Logger logger;
    protected ModelCreator modelCreator;
    protected ViewGenerator viewGenerator;

    public BaseProcessor(ModelCreator modelCreator, ViewGenerator viewGenerator) {
        super();
        this.modelCreator = modelCreator;
        this.viewGenerator = viewGenerator;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        logger = Logger.getInstance(processingEnv.getMessager());
        typeElements = annotations;
        elements = roundEnv.getElementsAnnotatedWith(getTargetAnnotationClass());
        long startR = System.currentTimeMillis();
        switch (getProcessType()) {
            case PROCESS_ANNOTATION_OF_ELEMENT:
                processEachElement();
                break;
            case PROCESS_ANNOTATION_OF_TYPE_ELEMENT:
                processEachTypeElement();
                break;
            default:
                throw new IllegalStateException("not a supported type");
        }
        logger.log(Diagnostic.Kind.NOTE,
                "\nFinished a round,time:" + (System.currentTimeMillis() - startR) + "ms");
        return true;
    }

    private void processEachTypeElement() {
        for (Element element : typeElements) {
            logger.log(Diagnostic.Kind.NOTE,
                    "\nCollect Model Info:");
            long start = System.currentTimeMillis();
            //collect Model info
            AbstractModel abstractModel = collectModeInfo(element);
            logger.log(Diagnostic.Kind.NOTE,
                    "\nStart Generating View");
            //generate View
            createView(abstractModel);

            logger.log(Diagnostic.Kind.NOTE,
                    "\nFinished Generating View,time:" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    private void processEachElement() {
        for (Element element : elements) {
            logger.log(Diagnostic.Kind.NOTE,
                    "\nCollect Model Info:");
            long start = System.currentTimeMillis();
            //collect Model info
            AbstractModel abstractModel = collectModeInfo(element);
            logger.log(Diagnostic.Kind.NOTE,
                    "\nStart Generating View");
            //generate View
            createView(abstractModel);

            logger.log(Diagnostic.Kind.NOTE,
                    "\nFinished Generating View,time:" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * collect model info
     *
     * @param element
     * @return
     */
    protected AbstractModel collectModeInfo(Element element) {
        return modelCreator.createModel(element, processingEnv);
    }

    /**
     * generate code
     *
     * @param dataBeanModel
     */
    protected void createView(AbstractModel dataBeanModel) {
        viewGenerator.generate(dataBeanModel, processingEnv);

    }

    protected abstract Class<? extends Annotation> getTargetAnnotationClass();

    protected abstract ProcessType getProcessType();


}
