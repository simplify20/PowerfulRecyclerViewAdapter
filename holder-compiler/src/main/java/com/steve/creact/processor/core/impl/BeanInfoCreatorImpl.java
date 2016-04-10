package com.steve.creact.processor.core.impl;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.core.BeanInfoCreator;
import com.steve.creact.processor.model.BeanInfo;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Created by Administrator on 2016/4/10.
 */
public class BeanInfoCreatorImpl implements BeanInfoCreator {
    public BeanInfoCreatorImpl() {
    }

    @Override
    public BeanInfo createDataBeanInfo(Element element, ProcessingEnvironment processingEnv) {
        return collectModeInfo(element,processingEnv);
    }

    private BeanInfo collectModeInfo(Element element,ProcessingEnvironment processingEnv) {

        TypeMirror typeMirror = null;
        BeanInfo beanInfo = new BeanInfo();
        //get annotated Element info.
        String annotatedElementName = element.getSimpleName().toString();
        String annotatedElementPackage = "";
        if (element instanceof TypeElement) {
            String qualifiedName = ((TypeElement) element).getQualifiedName().toString();
            annotatedElementPackage = qualifiedName.replace(annotatedElementName, "");
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                "\nannotatedElementPackage:" + annotatedElementPackage +
                        "\nannotatedElementName:" + annotatedElementName);
        beanInfo.holderName = annotatedElementName;
        beanInfo.holderPackage = annotatedElementPackage;
        //get annotation info
        try {
            DataBean dataBean = element.getAnnotation(DataBean.class);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "\nMetaData:\ndataBean:" + dataBean.beanName() + "\nlayoutId:" + dataBean.layout());
            beanInfo.dataBeanName = dataBean.beanName();
            beanInfo.dataBeanPackage = annotatedElementPackage + "databean";
            beanInfo.layoutId = dataBean.layout();
            //get type of DataBean,may throw exception
            dataBean.data();
        } catch (MirroredTypeException mte) {
            typeMirror = mte.getTypeMirror();
        }
        if (typeMirror != null) {
            switch (typeMirror.getKind()) {
                case DECLARED:
                    DeclaredType declaredType = (DeclaredType) typeMirror;
                    TypeElement dataBeanElement = (TypeElement) declaredType.asElement();
                    String qualifiedName = dataBeanElement.getQualifiedName().toString();
                    String simpleName = dataBeanElement.getSimpleName().toString();
                    String dataPackage = qualifiedName.replace(simpleName, "");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                            "\ndataPackage:" + dataPackage +
                                    "\ndataType:" + simpleName);
                    beanInfo.dataPackage = dataPackage;
                    beanInfo.dataName = simpleName;
                    break;
            }
        }
        return beanInfo;
    }
}
