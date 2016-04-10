package com.steve.creact.processor;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.core.ModelCreator;
import com.steve.creact.processor.core.ViewGenerator;
import com.steve.creact.processor.core.factory.ModelCreatorFactory;
import com.steve.creact.processor.core.factory.ViewGeneratorFactory;
import com.steve.creact.processor.core.impl.DataBeanModelCreator;
import com.steve.creact.processor.core.impl.ViewGeneratorImpl;
import com.steve.creact.processor.model.AbstractModel;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * A Annotation Processor to generate DataBean code
 */
@SupportedAnnotationTypes("com.steve.creact.annotation.DataBean")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DataBeanProcessor extends AbstractProcessor {
    protected ModelCreator modelCreator = ModelCreatorFactory.getCreator(DataBeanModelCreator.class);
    protected ViewGenerator viewGenerator = ViewGeneratorFactory.getGenerator(ViewGeneratorImpl.class);

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        long startr = System.currentTimeMillis();
        for (Element element : roundEnv.getElementsAnnotatedWith(DataBean.class)) {
            messager.printMessage(Diagnostic.Kind.NOTE,
                    "\nCollect Model Info:");
            long start = System.currentTimeMillis();
            //collect Model info
            AbstractModel abstractModel = collectModeInfo(element);
            messager.printMessage(Diagnostic.Kind.NOTE,
                    "\nStart Generating View");
            //generate View
            createView(abstractModel);

            messager.printMessage(Diagnostic.Kind.NOTE,
                    "\nFinished Generating View,time:" + (System.currentTimeMillis() - start) + "ms");
        }
        messager.printMessage(Diagnostic.Kind.NOTE,
                "\nFinished a round,time:" + (System.currentTimeMillis() - startr) + "ms");
        return true;
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
}
