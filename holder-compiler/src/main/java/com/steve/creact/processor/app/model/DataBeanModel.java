package com.steve.creact.processor.app.model;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.core.model.AbstractModel;
import com.steve.creact.processor.core.model.ElementVisitorAdapter;

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
public class DataBeanModel extends AbstractModel<BeanInfo> {
    public DataBeanModel(Element element, ProcessingEnvironment processingEnv) {
        super(element);
        setElementVisitor(DataBeanVisitor.getInstance(processingEnv));
        visit(null);
    }

    /**
     * Element Visitor
     */
    public static class DataBeanVisitor extends ElementVisitorAdapter<BeanInfo, Void> {

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


        private static volatile DataBeanVisitor instance;

        private DataBeanVisitor(ProcessingEnvironment processingEnv) {
            super(processingEnv);
        }

        @Override
        public BeanInfo visitType(TypeElement element, Void aVoid) {
            //check element type
            if (!VALIDATED_CLASS_NAME.equals(getQualifiedName(element)))
                checkElementType(element, VALIDATED_CLASS_NAME);
            TypeMirror typeMirror = null;
            BeanInfo beanInfo = new BeanInfo();
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
                }
            }
            //log
            logger.log(Diagnostic.Kind.NOTE,
                    beanInfo.toString());
            return beanInfo;
        }


        @Override
        public BeanInfo visitUnknown(Element e, Void aVoid) {
            logger.log(Diagnostic.Kind.NOTE,
                    "unknown Element type:" + e.getKind().name());
            return new BeanInfo();
        }

        //check if checked type is subtype of the given superType
        private void checkElementType(TypeElement checkedType, String superType) {
            if (superType == null)
                throw new IllegalArgumentException("super class name can not be null");
            TypeMirror typeMirror = checkedType.getSuperclass();
            switch (typeMirror.getKind()) {
                case DECLARED:
                    TypeElement st = (TypeElement) typeUtils.asElement(typeMirror);
                    logger.log(Diagnostic.Kind.NOTE, "super class:" + getQualifiedName(st));
                    if (superType.equals(getQualifiedName(st)))
                        return;
                    //recursively check super element
                    checkElementType(st, superType);
            }

            throw new IllegalStateException(ANNOTATED_WRONG_CLASS_ERROR);
        }

    }
}
