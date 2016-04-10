package com.steve.creact.processor;

import com.steve.creact.annotation.DataBean;
import com.steve.creact.processor.model.BeanInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * A Annotation Processor to generate DataBean code
 */
@SupportedAnnotationTypes("com.steve.creact.annotation.DataBean")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DataBeanProcessor extends AbstractProcessor {
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
            genView(beanInfo);

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "\nGenerate View End.");
        }
        return true;
    }

    protected BeanInfo collectModeInfo(Element element) {

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


    /**
     * TODO : Template Engine
     *
     * @param beanInfo
     */
    protected void genView(BeanInfo beanInfo) {

        Filer filer = processingEnv.getFiler();
//        PackageElement packageElement = processingEnv.getElementUtils().getPackageElement(beanInfo.dataBeanPackage);
        try {
            JavaFileObject jfo = filer.createSourceFile(beanInfo.dataBeanPackage + "." + beanInfo.dataBeanName);
            BufferedWriter bw = new BufferedWriter(jfo.openWriter());
            //import
            bw.append("package " + beanInfo.dataBeanPackage + ";");
            bw.newLine();
            bw.newLine();
            bw.append("import android.view.ViewGroup;");
            bw.newLine();
            bw.append("import com.steve.creact.library.display.BaseDataBean;");
            bw.newLine();
            bw.append("import " + beanInfo.dataPackage + beanInfo.dataName + ";");
            bw.newLine();
            bw.append("import " + beanInfo.holderPackage + beanInfo.holderName + ";");
            //code content
            bw.newLine();
            bw.newLine();
            bw.append("public class " + beanInfo.dataBeanName
                    + " extends BaseDataBean<" + beanInfo.dataName + ", " + beanInfo.holderName + "> {");
            bw.newLine();
            bw.append("    ");
            bw.append("public " + beanInfo.dataBeanName + "(" + beanInfo.dataName + " data) {super(data);}");
            bw.newLine();
            bw.newLine();
            bw.append("    ");
            bw.append("@Override");
            bw.newLine();
            bw.append("    ");
            bw.append("public " + beanInfo.holderName + " createHolder(ViewGroup parent) {");
            bw.newLine();
            bw.append("    ");
            bw.append("    ");
            bw.append("return new " + beanInfo.holderName + "(getView(parent, " + beanInfo.holderName + ".LAYOUT_ID));");
            bw.newLine();
            bw.append("    ");
            bw.append("}");
            bw.newLine();
            bw.append("}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
