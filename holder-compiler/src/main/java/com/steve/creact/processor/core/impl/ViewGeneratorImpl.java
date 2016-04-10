package com.steve.creact.processor.core.impl;

import com.steve.creact.processor.core.Constants;
import com.steve.creact.processor.core.ViewGenerator;
import com.steve.creact.processor.model.BeanInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;


/**
 * Template Engine
 * Created by Administrator on 2016/4/10.
 */
public class ViewGeneratorImpl implements ViewGenerator {
    protected ProcessingEnvironment processingEnvironment;
    protected Messager messager;

    @Override
    public void generate(BeanInfo beanInfo, ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        processingEnvironment = processingEnv;
        if (beanInfo == null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "bean info is null,aborted");
            return;
        }
        //load template
        messager.printMessage(Diagnostic.Kind.NOTE, "load template file...");
        String template = loadTemplate();
        //messager.printMessage(Diagnostic.Kind.NOTE, "template file:" + template);
        //create file
        messager.printMessage(Diagnostic.Kind.NOTE, "create source file...");
        JavaFileObject jfo = createFile(beanInfo);
        if (jfo == null) {
            messager.printMessage(Diagnostic.Kind.NOTE, "create java file failed,aborted");
            return;
        }
        //write file
        messager.printMessage(Diagnostic.Kind.NOTE, "write source file...");
        try {
            writeFile(beanInfo, template, jfo.openWriter());
            String sourceFileName = beanInfo.dataBeanPackage + "." + beanInfo.dataBeanName;
            messager.printMessage(Diagnostic.Kind.NOTE, "generate source file successful:" + sourceFileName);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "create file failed");
            e.printStackTrace();
        }
    }

    protected String loadTemplate() {
        String result = "";
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_PATH);
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
            messager.printMessage(Diagnostic.Kind.ERROR, "load template file failed");
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

    protected JavaFileObject createFile(BeanInfo beanInfo) {
        JavaFileObject result = null;
        Filer filer = processingEnvironment.getFiler();
        try {
            result = filer.createSourceFile(beanInfo.dataBeanPackage + "." + beanInfo.dataBeanName);
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "create source file failed");
            e.printStackTrace();
        }
        return result;
    }

    protected void writeFile(BeanInfo beanInfo, String template, Writer writer) {
        if (template == null) {
            throw new IllegalArgumentException("template content can not be null.");
        }
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        String output;
        output = template.replace(Constants.PACKAGE_NAME, beanInfo.dataBeanPackage);
        output = output.replace(Constants.DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME, beanInfo.dataPackage + beanInfo.dataName);
        output = output.replace(Constants.VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME, beanInfo.holderPackage + beanInfo.holderName);
        output = output.replace(Constants.DATA_BEAN_SIMPLE_CLASS_NAME, beanInfo.dataBeanName);
        output = output.replace(Constants.DATA_ENTITY_SIMPLE_CLASS_NAME, beanInfo.dataName);
        output = output.replace(Constants.VIEW_HOLDER_SIMPLE_CLASS_NAME, beanInfo.holderName);

        messager.printMessage(Diagnostic.Kind.NOTE, "output source file:" + output);
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
}
