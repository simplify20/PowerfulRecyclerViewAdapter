package com.steve.creact.processor.model;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.core.ElementVisitorAdapter;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataBeanVisitor extends ElementVisitorAdapter<BeanInfo, Void> {

    public static DataBeanVisitor getInstance(ProcessingEnvironment pe) {
        if (instance == null) {
            synchronized (DataBeanVisitor.class) {
                //double-check
                if (instance != null)
                    return instance;
                instance = new DataBeanVisitor(pe);
            }
        }
        return instance;
    }

    private ProcessingEnvironment processingEnv;
    private static volatile DataBeanVisitor instance;

    private DataBeanVisitor(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
    }

    @Override
    public BeanInfo visitType(TypeElement element, Void aVoid) {

        TypeMirror typeMirror = null;
        BeanInfo beanInfo = new BeanInfo();
        Messager messager = processingEnv.getMessager();
        //get annotated Element info.
        String annotatedElementPackage = getPackageName(element);
        beanInfo.holderName = getSimpleName(element);
        beanInfo.holderPackage = annotatedElementPackage;
        //get annotation info
        try {
            DataBean dataBean = element.getAnnotation(DataBean.class);
            beanInfo.dataBeanName = dataBean.beanName();
            beanInfo.dataBeanPackage = annotatedElementPackage + ".databean";
            beanInfo.layoutId = dataBean.layout();
            //Get type of DataBean,may throw exception
            dataBean.data();
        } catch (MirroredTypeException mte) {
            typeMirror = mte.getTypeMirror();
        }
        if (typeMirror != null) {
            if (typeMirror.getKind() == TypeKind.DECLARED) {
                DeclaredType declaredType = (DeclaredType) typeMirror;
                TypeElement dataBeanElement = (TypeElement) declaredType.asElement();
                beanInfo.dataPackage = getPackageName(dataBeanElement);
                beanInfo.dataName = getSimpleName(dataBeanElement);
            }
        }
        //log
        messager.printMessage(Diagnostic.Kind.NOTE,
                beanInfo.toString());
        return beanInfo;
    }

    @Override
    public BeanInfo visitUnknown(Element e, Void aVoid) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE,
                "unknown Element type:" + e.getKind().name());
        return new BeanInfo();
    }
}
