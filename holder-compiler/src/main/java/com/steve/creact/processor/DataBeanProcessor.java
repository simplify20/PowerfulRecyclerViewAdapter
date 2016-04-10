package com.steve.creact.processor;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.core.BeanInfoCreator;
import com.steve.creact.processor.core.BeanInfoCreatorFactory;
import com.steve.creact.processor.core.ViewGenerator;
import com.steve.creact.processor.core.ViewGeneratorFactory;
import com.steve.creact.processor.core.impl.BeanInfoCreatorImpl;
import com.steve.creact.processor.core.impl.ViewGeneratorImpl;
import com.steve.creact.processor.model.BeanInfo;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
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
    protected BeanInfoCreator beanInfoCreator = BeanInfoCreatorFactory.get(BeanInfoCreatorImpl.class);
    protected ViewGenerator viewGenerator = ViewGeneratorFactory.getGenerator(ViewGeneratorImpl.class);
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(DataBean.class)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "\nCollect Model Info:");
            //collect Model info
            BeanInfo beanInfo = collectModeInfo(element);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "\nGenerate View Start");
            //generate View
            createView(beanInfo);

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "\nGenerate View End.");
        }
        return true;
    }

    /**
     * collect mode info
     * @param element
     * @return
     */
    protected BeanInfo collectModeInfo(Element element) {
        return beanInfoCreator.createDataBeanInfo(element,processingEnv);
    }


    /**
     * generate code
     * @param beanInfo
     */
    protected void createView(BeanInfo beanInfo) {

        viewGenerator.generate(beanInfo,processingEnv);

    }
}
