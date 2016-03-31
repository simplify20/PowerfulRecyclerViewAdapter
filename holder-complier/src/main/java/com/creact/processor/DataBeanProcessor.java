package com.creact.processor;

import com.creact.annotation.DataBean;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.creact.annotation.DataBean")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DataBeanProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(DataBean.class)) {
            DataBean dataBean = element.getAnnotation(DataBean.class);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, dataBean.packageName() + "."
                    + dataBean.beanName() + "\n" + dataBean.data() + "\n" + dataBean.holder() + "\n" + dataBean.layout());
        }
        return true;
    }
}
