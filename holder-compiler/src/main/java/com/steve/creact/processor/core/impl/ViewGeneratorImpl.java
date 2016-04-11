package com.steve.creact.processor.core.impl;

import com.steve.creact.processor.core.Constants;
import com.steve.creact.processor.core.Replacer;
import com.steve.creact.processor.core.ViewGenerator;
import com.steve.creact.processor.model.AbstractModel;
import com.steve.creact.processor.model.BeanInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


/**
 * Template Engine
 * Created by Administrator on 2016/4/10.
 */
public class ViewGeneratorImpl implements ViewGenerator<BeanInfo>, Replacer {
    protected ProcessingEnvironment processingEnvironment;
    protected Messager messager;
    protected BeanInfo beanInfo;

    @Override
    public void generate(AbstractModel<BeanInfo> model, ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        processingEnvironment = processingEnv;
        //beanInfo is initialized by AbstractModel
        beanInfo = model.getRealModel();
        if (beanInfo == null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "bean info is null,aborted");
            return;
        }
        //load template
        messager.printMessage(Diagnostic.Kind.NOTE, "load template file...");
        String template = loadTemplate();
        if ("".equals(template)) {
            return;
        }
        //messager.printMessage(Diagnostic.Kind.NOTE, "template file:" + template);
        //create file
        messager.printMessage(Diagnostic.Kind.NOTE, "create source file...");
        JavaFileObject jfo = createFile();
        if (jfo == null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "create java file failed,aborted");
            return;
        }
        //write file
        messager.printMessage(Diagnostic.Kind.NOTE, "write source file...");
        try {
            writeFile(template, jfo.openWriter());
            String sourceFileName = beanInfo.beanClassName();
            messager.printMessage(Diagnostic.Kind.NOTE, "generate source file successful:" + sourceFileName);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "create file failed");
            e.printStackTrace();
        }
    }

    /**
     * load template file into memory from resource directory
     *
     * @return
     */
    protected String loadTemplate() {
        String result = "";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_PATH);
        if (is == null) {
            messager.printMessage(Diagnostic.Kind.ERROR, "open template file failed");
            return "";
        }
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String target;
            StringBuilder builder = new StringBuilder();
            while ((target = bufferedReader.readLine()) != null) {
                builder.append(target);
                builder.append("\n");
            }
            result = builder.toString();
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "read template file failed");
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * create a Java source file
     *
     * @return
     */
    protected JavaFileObject createFile() {
        JavaFileObject result = null;
        Filer filer = processingEnvironment.getFiler();
        try {
            result = filer.createSourceFile(beanInfo.beanClassName());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "create source file failed");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * replace placeholder in template
     *
     * @param template
     * @param writer
     */
    protected void writeFile(String template, Writer writer) {
        if (template == null) {
            throw new IllegalArgumentException("template content can not be null.");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        String output = this.replace(template, null);
        //messager.printMessage(Diagnostic.Kind.NOTE, "output source file:" + output);
        try {
            bufferedWriter.append(output);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "write file failed");
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String replace(String input, Map<String, String> replaceValues) {
        String output = input.replace(Constants.PACKAGE_NAME, beanInfo.dataBeanPackage);
        output = output.replace(Constants.DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME, beanInfo.dataClassName());
        output = output.replace(Constants.VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME, beanInfo.holderClassName());
        output = output.replace(Constants.DATA_BEAN_SIMPLE_CLASS_NAME, beanInfo.dataBeanName);
        output = output.replace(Constants.DATA_ENTITY_SIMPLE_CLASS_NAME, beanInfo.dataName);
        output = output.replace(Constants.VIEW_HOLDER_SIMPLE_CLASS_NAME, beanInfo.holderName);
        return output;
    }

//    private HashMap<String, String> createValuesMap(BeanInfo beanInfo) {
//        HashMap<String, String> result = new HashMap<>();
//        result.put(Constants.PACKAGE_NAME, beanInfo.dataBeanPackage);
//        result.put(Constants.DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME, beanInfo.dataPackage + "." + beanInfo.dataName);
//        result.put(Constants.VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME, beanInfo.holderPackage + "." + beanInfo.holderName);
//        result.put(Constants.DATA_BEAN_SIMPLE_CLASS_NAME, beanInfo.dataBeanName);
//        result.put(Constants.DATA_ENTITY_SIMPLE_CLASS_NAME, beanInfo.dataName);
//        result.put(Constants.VIEW_HOLDER_SIMPLE_CLASS_NAME, beanInfo.holderName);
//        return result;
//    }
}
