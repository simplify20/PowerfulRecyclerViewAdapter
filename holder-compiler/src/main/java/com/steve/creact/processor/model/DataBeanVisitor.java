package com.steve.creact.processor.model;

import com.steve.creact.annotation.DataBean;

import java.util.List;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by Administrator on 2016/4/10.
 */
public class DataBeanVisitor extends ElementVisitorAdapter<BeanInfo, Void> {

    private static final String ANNOTATED_WRONG_CLASS_ERROR = "@DataBean must use on classes that extend from BaseRecyclerViewHolder OR BaseRecyclerViewHolder itself";
    private static final String VALIDATED_CLASS_NAME = "com.steve.creact.library.viewholder.BaseRecyclerViewHolder";

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
        //check element type
        checkElementType(element);
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
            //Get type of Data,may throw exception
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
                //Check type parameter of the Holder
                checkElementTypeParameter(element, beanInfo);
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

    //check if the annotated type is subtype of the BaseRecyclerViewHolder
    private void checkElementType(TypeElement element) {
        TypeElement validated = processingEnv.getElementUtils().getTypeElement(VALIDATED_CLASS_NAME);
        if (validated == null) {
            throw new IllegalStateException("can not find validated type:" + VALIDATED_CLASS_NAME);
        }

        TypeMirror validatedTypeMirror = validated.asType();
        TypeMirror currentTypeMirror = element.asType();
        if (processingEnv.getTypeUtils().isSubtype(currentTypeMirror, validatedTypeMirror)) {
            return;
        }
        throw new IllegalStateException(ANNOTATED_WRONG_CLASS_ERROR);

    }

    //check if annotated data type is subtype of type parameter's type in holder class
    private void checkElementTypeParameter(TypeElement element, BeanInfo beanInfo) {
        List<? extends TypeParameterElement> tpe = element.getTypeParameters();
        if (tpe.size() > 0) {
            TypeParameterElement parameterElement = tpe.get(0);
            TypeMirror parameterTypeMirror = parameterElement.asType();
            TypeElement annotatedTypeElement = processingEnv.getElementUtils().getTypeElement(beanInfo.dataClassName());
            TypeMirror annotatedTypeMirror = annotatedTypeElement.asType();
            boolean isSub = processingEnv.getTypeUtils().isSubtype(annotatedTypeMirror, parameterTypeMirror);
            if (isSub)
                return;
        }
        throw new IllegalStateException("type parameter of the [" + beanInfo.holderClassName() + "]is not the same as Annotation data class");
    }
}
