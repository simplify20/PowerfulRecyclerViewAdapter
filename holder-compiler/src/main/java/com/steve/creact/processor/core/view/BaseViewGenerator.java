package com.steve.creact.processor.core.view;

import com.steve.creact.processor.core.Constants;
import com.steve.creact.processor.core.Logger;
import com.steve.creact.processor.core.model.AbstractModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.HashMap;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Base Template Engine
 * Created by Administrator on 2016/4/17.
 */
public abstract class BaseViewGenerator<T> implements ViewGenerator<T>, Replacer {
    /**
     * when template file has updated,call this method to flush cache
     *
     * @param templateFilePath
     */
    public static void flushCache(String templateFilePath) {

        if (templateFilePath != null)
            templateCache.remove(templateFilePath);
    }

    /**
     * flush all
     */
    public static void flushCache() {

        templateCache.clear();
    }

    protected ProcessingEnvironment processingEnvironment;
    protected Logger logger;
    protected T realModel;
    //use to cache template read in last time,and reduce cost of I/O operations
    private static final HashMap<String, String> templateCache = new HashMap<>(2);

    @Override
    public void generate(AbstractModel<T> model, ProcessingEnvironment processingEnv) {
        logger = Logger.getInstance(processingEnv.getMessager());
        processingEnvironment = processingEnv;
        //realModel is initialized by AbstractModel
        realModel = model.getRealModel();
        if (realModel == null) {
            logger.log(Diagnostic.Kind.NOTE, "readModel is null,aborted");
            return;
        }
        //load template
        logger.log(Diagnostic.Kind.NOTE, "load template file...");
        String template = loadTemplate();
        if ("".equals(template)) {
            return;
        }
        //logger.log(Diagnostic.Kind.NOTE, "template file:" + template);
        //create file
        logger.log(Diagnostic.Kind.NOTE, "create source file...");
        JavaFileObject jfo = createFile();
        if (jfo == null) {
            logger.log(Diagnostic.Kind.NOTE, "create java file failed,aborted");
            return;
        }
        //write file
        logger.log(Diagnostic.Kind.NOTE, "write source file...");
        try {
            writeFile(template, jfo.openWriter());
            String sourceFileName = getModelClassName();
            logger.log(Diagnostic.Kind.NOTE, "generate source file successful:" + sourceFileName);
        } catch (IOException e) {
            logger.log(Diagnostic.Kind.ERROR, "create file failed");
            e.printStackTrace();
        }
    }

    /**
     * Firstly,check whether template cache contains the target template,if not,
     * load template file into memory from resource directory.
     *
     * @return template content
     */
    protected String loadTemplate() {
        String result = "";
        String templateFilePath = getTemplateFilePath();
        //check cache first
        if (templateFilePath != null && (result = templateCache.get(templateFilePath)) != null)
            return result;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(Constants.TEMPLATE_PATH);
        if (is == null) {
            logger.log(Diagnostic.Kind.ERROR, "open template file failed");
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
            //save template to cache
            templateCache.put(templateFilePath,result);
        } catch (IOException e) {
            logger.log(Diagnostic.Kind.ERROR, "read template file failed");
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
            result = filer.createSourceFile(getModelClassName());
        } catch (IOException e) {
            logger.log(Diagnostic.Kind.ERROR, "create source file failed");
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
        String output = replace(template, null);
        //logger.log(Diagnostic.Kind.NOTE, "output source file:" + output);
        try {
            bufferedWriter.append(output);
        } catch (IOException e) {
            logger.log(Diagnostic.Kind.ERROR, "write file failed");
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get name of the class will be generated
     * @return
     */
    protected abstract String getModelClassName();

    /**
     * Get specific template file path in resource directory
     * @return
     */
    protected abstract String getTemplateFilePath();
}
