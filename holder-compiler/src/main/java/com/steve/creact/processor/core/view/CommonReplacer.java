package com.steve.creact.processor.core.view;

import com.steve.creact.processor.core.Constants;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/10.
 */
public class CommonReplacer implements Replacer {

    public static CommonReplacer getInstance() {
        if (instance == null) {
            synchronized (CommonReplacer.class) {
                if (instance != null)
                    return instance;
                instance = new CommonReplacer();
            }
        }
        return instance;
    }

    private static HashSet<String> placeHolders = new HashSet<>();
    private volatile static CommonReplacer instance;

    private CommonReplacer() {
    }

    static {
        placeHolders.add(Constants.PACKAGE_NAME);
        placeHolders.add(Constants.DATA_BEAN_SIMPLE_CLASS_NAME);
        placeHolders.add(Constants.DATA_ENTITY_FULL_QUALIFIED_CLASS_NAME);
        placeHolders.add(Constants.DATA_ENTITY_SIMPLE_CLASS_NAME);
        placeHolders.add(Constants.VIEW_HOLDER_FULL_QUALIFIED_CLASS_NAME);
        placeHolders.add(Constants.VIEW_HOLDER_SIMPLE_CLASS_NAME);
    }

    @Override
    public String replace(String input, Map<String, String> replaceValues) {
        if (input == null) {
            throw new IllegalArgumentException("input template can not be null");
        }
        if (replaceValues == null)
            return input;
        String result = input;
        //replace all placeholders
        for (Iterator<Map.Entry<String, String>> iterator = replaceValues.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (placeHolders.contains(key) && value != null) {
                result = result.replace(key, value);
            }
        }
        return result;
    }

    /**
     * add placeholder to set
     *
     * @param placeHolders
     */
    public void registerPlaceHolders(String... placeHolders) {
        for (String placeHolder : placeHolders) {
            if (this.placeHolders.contains(placeHolder))
                continue;
            this.placeHolders.add(placeHolder);
        }
    }
}
