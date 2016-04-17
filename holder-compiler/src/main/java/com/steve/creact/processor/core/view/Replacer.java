package com.steve.creact.processor.core.view;

import java.util.Map;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface Replacer {

    /**
     * replace matched substring(s) in input string with value in value map
     * @param input
     * @param replaceValues
     * @return
     */
    String replace(String input,Map<String,String> replaceValues);
}
